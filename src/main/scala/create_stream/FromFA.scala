package create_stream

import cats.effect.{Blocker, ConcurrentEffect, ContextShift, Sync, Timer}
import fs2.Stream

import scala.util.chaining._

object FromFA extends StreamDemoApp {
  def strings[F[_]: Sync]: F[List[String]] = Sync[F].delay(List("apple", "banana", "chocolate"))

  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): Stream[F, String] =
      Stream.eval(strings).flatMap(_.iterator pipe (Stream.fromIterator(_)))
}
