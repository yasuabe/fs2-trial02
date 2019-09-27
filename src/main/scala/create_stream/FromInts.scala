package create_stream

import cats.effect.{Blocker, ConcurrentEffect, ContextShift, Timer}
import fs2.Stream
import cats.syntax.show._
import cats.instances.int._

object FromInts extends StreamDemoApp {
  def stream[F[_]: ConcurrentEffect: Timer :ContextShift](implicit bl: Blocker): Stream[F, String] =
    Stream(1, 1, 2, 3, 5).map(_.show)
}
