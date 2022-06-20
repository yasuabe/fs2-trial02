package create_stream

import scala.concurrent.duration.*
import cats.Monad
import cats.syntax.functor.*
import cats.effect.{ Sync, Async, Clock }
import fs2.Stream

object FromTimer extends StreamDemoApp:
  def stream[F[_]: Async]: Stream[F, String] =
    val currentSec: F[String] =
      Clock[F].realTime.map(n => (n.toSeconds % 60).toString)

    val task: Stream[F, String] = for {
      s <- Stream.eval(currentSec)
      _ <- Stream.sleep[F](2 seconds)
      e <- Stream.eval(currentSec)
    } yield s"$s ---> $e"

    Stream.fixedDelay[F](3 seconds) zipRight task.repeat
