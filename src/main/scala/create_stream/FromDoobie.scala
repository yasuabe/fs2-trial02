package create_stream

import cats.effect.{Blocker, Concurrent, ConcurrentEffect, ContextShift, Timer}
import doobie.implicits._
import doobie.util.transactor.Transactor

object FromDoobie extends StreamDemoApp {
  def stream[F[_] : ConcurrentEffect : Timer : ContextShift](implicit bl: Blocker): fs2.Stream[F, String] = {
    val xa = Transactor.fromDriverManager[F](
      "org.postgresql.Driver",     // driver classname
      "jdbc:postgresql:world",     // connect URL (driver-specific)
      "postgres",                  // user
      "",                          // password
      bl
    )
    sql"select name from country where population > 50000000 order by population desc"
      .query[String]
      .stream
      .transact(xa) // Stream[ConnectionIO, String] is converted into Stream[F, String] here.
  }
}

