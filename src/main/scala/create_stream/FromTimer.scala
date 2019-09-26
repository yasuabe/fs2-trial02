package create_stream

import cats.Functor
import cats.effect.{Blocker, Concurrent, ConcurrentEffect, ContextShift, Sync, Timer}
import cats.syntax.functor._
import fs2.Stream

import scala.concurrent.duration._

object FromTimer extends StreamDemoApp {
  def currentSec[F[_]: Timer: Functor]: F[String] =
    Timer[F].clock.realTime(SECONDS).map(n => (n % 60).toString)

  def task[F[_]: Sync: ContextShift: Timer](b: Blocker): Stream[F, String] = for {
    s <- Stream.eval(currentSec[F])
    _ <- Stream.sleep[F](3 seconds)
    e <- Stream.eval(currentSec[F])
  } yield s"$s ---> $e"

  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): Stream[F, String] =
    Stream.fixedRate[F](2 seconds) zipRight task[F](bl).repeat
}
// def fixedRate[F[_]] (d: FiniteDuration)(implicit timer: Timer[F]): Stream[F, Unit]
// def fixedDelay[F[_]](d: FiniteDuration)(implicit timer: Timer[F]): Stream[F, Unit]
// def awakeDelay[F[x] >: Pure[x]](d: FiniteDuration)(implicit timer: Timer[F], F: Functor[F]): Stream[F, FiniteDuration]
// def awakeEvery[F[x] >: Pure[x]](d: FiniteDuration)(implicit timer: Timer[F], F: Functor[F]): Stream[F, FiniteDuration]

