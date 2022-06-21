package create_stream

import cats.syntax.flatMap.*
import cats.syntax.functor.*
import cats.syntax.option.*
import cats.effect.Async
import fs2.Stream
import io.circe.Json
import io.circe.jawn.CirceSupportParser
import org.http4s.*
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.client.oauth1
import org.http4s.client.oauth1.ProtocolParameter.*
import org.http4s.implicits.*
import org.typelevel.jawn.fs2.*
import org.typelevel.jawn.Facade

object FromHttp4sClient extends StreamDemoApp:
  given Facade[Json] = CirceSupportParser(None, false).facade

  def stream[F[_]: Async]: Stream[F, String] =
    val req = Request[F](Method.GET, uri"https://stream.twitter.com/1.1/statuses/sample.json")

    val env = (F: Async[F]) ?=> (key: String) => F.delay(
      sys.env.get(key).toRight(RuntimeException(s"no env value for key: $key"))
    ) >>= F.fromEither

    val sign: Request[F] => F[Request[F]] = req => for {
      consumerKey    <- env("consumerKey")
      consumerSecret <- env("consumerSecret")
      accessToken    <- env("accessToken")
      accessSecret   <- env("accessSecret")
      signedReq      <- oauth1.signRequest(
        req                = req,
        consumer           = Consumer(consumerKey, consumerSecret),
        token              = Token(accessToken, accessSecret).some,
        realm              = None,
        timestampGenerator = Timestamp.now,
        nonceGenerator     = Nonce.now)
      } yield signedReq

    for {
      client    <- BlazeClientBuilder[F].stream
      signedReq <- Stream.eval(sign(req))
      res       <- client.stream(signedReq).flatMap(_.body.chunks.parseJsonStream)
    } yield res.spaces2
