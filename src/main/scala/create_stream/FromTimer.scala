package create_stream

import cats.effect.{Blocker, ConcurrentEffect, ContextShift, Timer}
import cats.syntax.functor._
import fs2.Stream

import scala.concurrent.duration._

object FromTimer extends StreamDemoApp {
  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): Stream[F, String] = {
    def currentSec: F[String] =
      Timer[F].clock.realTime(SECONDS).map(n => (n % 60).toString)

    val task: Stream[F, String] = for {
      s <- Stream.eval(currentSec)
      _ <- Stream.sleep[F](2 seconds)
      e <- Stream.eval(currentSec)
    } yield s"$s ---> $e"

    Stream.fixedDelay[F](3 seconds) zipRight task.repeat
  }
}

