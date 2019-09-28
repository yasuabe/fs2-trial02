package create_stream

import cats.effect._
import cats.syntax.functor._
import fs2.{Stream, io, text}

trait StreamDemoApp extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    Stream.resource(Blocker[IO]).flatMap { implicit bl: Blocker =>
      stream[IO]
        .take(5)
        .map(s => s"$s\n")
        .through(text.utf8Encode)
        .through(io.stdout[IO](bl))
    }
    .compile.drain
    .as(ExitCode.Success)

  def stream[F[_] : ConcurrentEffect : Timer : ContextShift]
    (implicit bl: Blocker): Stream[F, String]
}
