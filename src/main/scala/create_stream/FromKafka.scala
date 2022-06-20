package create_stream

import cats.effect.Async
import fs2.Stream
import fs2.kafka.*

object FromKafka extends StreamDemoApp:
  def stream[F[_]: Async]: Stream[F, String] =
    val consumerSettings = ConsumerSettings[F, String, String]
      .withAutoOffsetReset(AutoOffsetReset.Earliest)
      .withBootstrapServers("localhost:9092")
      .withGroupId("group")

    KafkaConsumer.stream(consumerSettings)
      .evalTap(_.subscribeTo("topic"))
      .flatMap(_.stream)
      .map(c => s"${c.record.key}->${c.record.value}")
