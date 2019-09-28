package create_stream

import cats.effect.{Blocker, ConcurrentEffect, ContextShift, Timer}
import fs2.{Stream, io, text}

object FromStdin extends StreamDemoApp {
  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): Stream[F, String] =
    io.stdin(4096, bl)
      .through(text.utf8Decode)
}
