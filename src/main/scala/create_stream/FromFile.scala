package create_stream

import cats.syntax.flatMap.*

import scala.io.Source
import cats.ApplicativeThrow
import cats.effect.{ Temporal, Async, Resource }
import fs2.Stream

object FromFile extends StreamDemoApp:
  def stream[F[_]: Async]: Stream[F, String] =
    val file: Resource[F, Source] =
      Resource.fromAutoCloseable(Async[F].delay(Source.fromFile("README.md")))

    Stream.resource(file) >>= (s => Stream.fromIterator(s.getLines, 4096))
