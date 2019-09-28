package create_stream

import cats.effect.{Blocker, ConcurrentEffect, ContextShift, ExitCode, IO, IOApp, Sync, Timer}
import fs2.Stream
import cats.syntax.either._

object FromEitherRight extends StreamDemoApp {
  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): fs2.Stream[F, String] =
    Stream.fromEither[F]("hello".asRight[Throwable])
}
object FromEitherLeft extends StreamDemoApp {
  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): fs2.Stream[F, String] =
    Stream.fromEither[F](new Exception("test").asLeft[String])
}
object FromEitherIO extends IOApp {
  import cats.syntax.functor._
  import cats.syntax.applicativeError._

  def program[F[_]](implicit F: Sync[F]): F[Unit] =
    Stream.fromEither[F](new Exception("test").asLeft[String])
      .compile.drain
      .handleErrorWith(th => F.delay(println(th.getMessage)))

  def run(args: List[String]): IO[ExitCode] =
    program[IO] as ExitCode.Success
}

