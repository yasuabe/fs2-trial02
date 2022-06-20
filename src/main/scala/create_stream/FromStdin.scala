package create_stream

import cats.effect.Async
import fs2.{ Stream, io, text }

object FromStdin extends StreamDemoApp:
  def stream[F[_]: Async]: Stream[F, String] =
    io.stdinUtf8(4096).through(text.lines)
