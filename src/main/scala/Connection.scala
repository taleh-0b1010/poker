import java.sql.*

object Connection {
  Class.forName("org.postgresql.Driver")
  private val url = "jdbc:postgresql://localhost:5432/poker_db"
  private val username = "postgres"
  private val password = "123456"

  private val connection: Connection = DriverManager.getConnection(url, username, password)

  def insertGameResult(username: String, gameResult: Result, hand: String, bet: Int): Unit = {
    val statement = connection.createStatement()
    val insertQuery = s"INSERT INTO game_result (username, game_result, hand, bet) VALUES ('$username', '$gameResult', '$hand', '$bet');"

    try {
      statement.execute(insertQuery)
    } catch {
        case e: org.postgresql.util.PSQLException =>
          println("Error while executing insert query: " + e)
        case e: Exception =>
          println("Internal Error: " + e)
    }
  }
}