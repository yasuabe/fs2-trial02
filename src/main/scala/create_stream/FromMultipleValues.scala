package create_stream

import cats.effect.{Blocker, ConcurrentEffect, ContextShift, Timer}
import fs2.Stream
import cats.syntax.show._
import cats.instances.int._

object FromSeq extends StreamDemoApp {
  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): fs2.Stream[F, String] = {
    val fib = LazyList.iterate[(Int, Int)]((0, 1)) { case (a, b) => (b, a + b) }.map(_._1)
    Stream.emits(fib).map(_.show)
  }
}
object FromMultipleValues extends StreamDemoApp {
  def stream[F[_]: ConcurrentEffect: Timer :ContextShift](implicit bl: Blocker): Stream[F, String] =
    Stream("apple", "banana", "chocolate")
}
