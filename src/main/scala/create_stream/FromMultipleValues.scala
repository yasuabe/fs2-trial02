package create_stream

import cats.syntax.show.*
import cats.effect.Async
import fs2.Stream

object FromMultipleValues extends StreamDemoApp:
  def stream[F[_]: Async]: Stream[F, String] = Stream("apple", "banana", "chocolate")

object FromSeq extends StreamDemoApp:
  def stream[F[_]: Async]: Stream[F, String] =
    val fib = LazyList.iterate((0, 1))((a, b) => (b, a + b)).take(100)
    Stream.emits(fib).map(_._1.show)
