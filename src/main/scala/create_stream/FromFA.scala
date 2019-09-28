package create_stream

import cats.effect.{Blocker, ConcurrentEffect, ContextShift, Sync, Timer}
import fs2.Stream

import scala.util.chaining._

object FromFA extends StreamDemoApp {
  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): Stream[F, String] = {
    val strings: F[List[String]] =
      Sync[F].delay(List("apple", "banana", "chocolate"))
    Stream.evalSeq(strings)
  }
}
