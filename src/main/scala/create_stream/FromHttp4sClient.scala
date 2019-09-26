package create_stream

import cats.effect._
import fs2.Stream
import io.circe.Json
import jawnfs2._
import org.http4s._
import org.http4s.client.blaze._
import org.http4s.client.oauth1
import org.http4s.implicits._
import org.typelevel.jawn.RawFacade
import cats.syntax.flatMap._
import cats.syntax.functor._
import io.circe.jawn.CirceSupportParser

object FromHttp4sClient extends StreamDemoApp {
  implicit val f: RawFacade[Json] = new CirceSupportParser(None, false).facade

  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): Stream[F, String] = {
    val req = Request[F](Method.GET, uri"https://stream.twitter.com/1.1/statuses/sample.json")

    def env(key: String)(implicit F: Sync[F]): F[String] =
      F.delay(sys.env.get(key).toRight(new RuntimeException(s"no env value for key: $key"))) >>= F.fromEither

    def sign(req: Request[F]): F[Request[F]] = for {
        consumerKey    <- env("consumerKey")
        consumerSecret <- env("consumerSecret")
        consumer       =  oauth1.Consumer(consumerKey, consumerSecret)

        accessToken  <- env("accessToken")
        accessSecret <- env("accessSecret")
        token        =  oauth1.Token(accessToken, accessSecret)

        signedReq <- oauth1.signRequest(req, consumer, callback = None, verifier = None, token = Some(token))
      } yield signedReq

    for {
      client    <- BlazeClientBuilder(bl.blockingContext).stream
      signedReq <- Stream.eval(sign(req))
      res       <- client.stream(signedReq)
                     .flatMap(_.body.chunks.parseJsonStream)
                     .take(5)
    } yield res.spaces2
  }
}
