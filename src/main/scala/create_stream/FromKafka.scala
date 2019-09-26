package create_stream

import cats.effect.{Blocker, ConcurrentEffect, ContextShift, Timer}
import fs2.Stream
import fs2.kafka._

object FromKafka extends StreamDemoApp {
  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): Stream[F, String] = {
    val consumerSettings = ConsumerSettings[F, String, String]
        .withAutoOffsetReset(AutoOffsetReset.Earliest)
        .withBootstrapServers("localhost:9092")
        .withGroupId("group")

    consumerStream[F]
        .using(consumerSettings)
        .evalTap(_.subscribeTo("topic"))
        .flatMap(_.stream)
        .map(c => s"${c.record.key}->${c.record.value}")
  }
}
