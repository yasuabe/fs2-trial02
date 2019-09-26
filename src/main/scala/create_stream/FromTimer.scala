package create_stream

import cats.Functor
import cats.effect.{Blocker, Concurrent, ConcurrentEffect, ContextShift, Sync, Timer}
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
// def fixedRate[F[_]] (d: FiniteDuration)(implicit timer: Timer[F]): Stream[F, Unit]
// def fixedDelay[F[_]](d: FiniteDuration)(implicit timer: Timer[F]): Stream[F, Unit]
// def awakeDelay[F[x] >: Pure[x]](d: FiniteDuration)(implicit timer: Timer[F], F: Functor[F]): Stream[F, FiniteDuration]
// def awakeEvery[F[x] >: Pure[x]](d: FiniteDuration)(implicit timer: Timer[F], F: Functor[F]): Stream[F, FiniteDuration]

