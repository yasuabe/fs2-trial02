package create_stream

import scala.util.chaining.*
import cats.syntax.either.*
import cats.effect.{ IO, IOApp, Async }
import fs2.Stream

object FromEitherRight extends StreamDemoApp:
  def stream[F[_]: Async]: fs2.Stream[F, String] =
    Stream.fromEither[F]("hello".asRight)

object FromEitherLeft extends StreamDemoApp:
  def stream[F[_]: Async]: fs2.Stream[F, String] =
    Stream.fromEither[F](Exception("test").asLeft)

object FromEitherIO extends IOApp.Simple:
  def run: IO[Unit] =
    Stream.fromEither[IO](Exception("test").asLeft)
      .compile.drain
      .handleErrorWith(_.getMessage pipe IO.println)
