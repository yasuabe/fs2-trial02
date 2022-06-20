package create_stream

import cats.effect.Async
import fs2.Stream
import doobie.implicits.*
import doobie.util.transactor.Transactor

object FromDoobie extends StreamDemoApp:
  def stream[F[_]: Async]: Stream[F, String] =
    val xa = Transactor.fromDriverManager[F](
      "org.postgresql.Driver", // driver classname
      "jdbc:postgresql:world", // connect URL (driver-specific)
      "postgres",              // user
      "",                      // password
    )
    sql"select name from country where population > 50000000 order by population desc"
      .query[String] // Query0[String]
      .stream        // Stream[ConnectionIO, String]
      .transact(xa)  // Stream[F, String]
