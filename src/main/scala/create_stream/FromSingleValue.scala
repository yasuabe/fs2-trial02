package create_stream

import cats.effect.{Blocker, ConcurrentEffect, ContextShift, Timer}
import fs2.Stream

object FromSingleValue extends StreamDemoApp {
  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): Stream[F, String] =
    Stream.emit("apple")
}
object RepeatSingleValue extends StreamDemoApp {
  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): Stream[F, String] =
    Stream.constant("apple")
}
