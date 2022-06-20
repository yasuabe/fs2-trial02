package create_stream

import cats.effect.{ IO, IOApp, Async, Clock }
import cats.syntax.functor.*
import fs2.{ Stream, io, text }

trait StreamDemoApp extends IOApp.Simple:
  def run: IO[Unit] = stream[IO]
    .take(5)
    .map(s => s"$s\n")
    .through(text.utf8.encode)
    .through(io.stdout)
    .compile.drain

  def stream[F[_]: Async] : Stream[F, String]
