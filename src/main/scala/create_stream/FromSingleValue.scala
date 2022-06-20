package create_stream

import cats.effect.Async
import fs2.Stream

object FromSingleValue extends StreamDemoApp:
  def stream[F[_]: Async]: Stream[F, String] = Stream.emit("apple")

object RepeatSingleValue extends StreamDemoApp:
  def stream[F[_]: Async]: Stream[F, String] = Stream.constant("apple")