package create_stream

import cats.effect.{Blocker, ConcurrentEffect, ContextShift, Resource, Sync, Timer}
import cats.syntax.flatMap._
import fs2.Stream

import scala.io.Source

object FromFile extends StreamDemoApp {
  def file[F[_]: Sync]: Resource[F, Source] =
    Resource.fromAutoCloseable(Sync[F].delay(Source.fromFile("README.md")))

  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): Stream[F, String] =
    Stream.resource(file) >>= (s => Stream.fromIterator(s.getLines))
}
