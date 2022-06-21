package create_stream

import cats.effect.{ Async, Sync }
import fs2.Stream

object FromFA extends StreamDemoApp:
  def stream[F[_] : Async]: Stream[F, String] =
    val strings: F[List[String]] = Sync[F].delay(List("apple", "banana", "chocolate"))
    Stream.evalSeq(strings)
