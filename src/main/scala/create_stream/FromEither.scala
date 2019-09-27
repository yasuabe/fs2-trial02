package create_stream

import cats.effect.{Blocker, ConcurrentEffect, ContextShift, Sync, Timer}
import fs2.Stream
import cats.syntax.either._

object FromEitherRight extends StreamDemoApp {
  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): fs2.Stream[F, String] =
    Stream.fromEither[F]("hello".asRight[Throwable])
}
object FromEitherLeft extends StreamDemoApp {
  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): fs2.Stream[F, String] =
    Stream.fromEither[F](new Exception("test").asLeft[String])
}
object FromList extends StreamDemoApp {
  val strings = LazyList("apple", "banana", "chocolate")

  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): fs2.Stream[F, String] =
    Stream.fromIterator(strings.iterator)
}
