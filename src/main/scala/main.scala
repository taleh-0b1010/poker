case class Card(rank: String, suit: String):
  override def toString: String = rank + suit

case class Hand(cards: List[Card])

enum Result:
  case WIN
  case LOOSE
  case DRAW

object Constants {
  val RANKS: List[String] = List("A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2")
  val SUITS: List[String] = List("C", "D", "H", "S")

  private type Environment = String => Int
  val env: Environment = {
    case "Flush" => 5
    case "Straight" => 6
    case "Three of a Kind" => 7
    case "Two Pair" => 8
    case "One Pair" => 9
    case "High Card" => 10
  }
}

import scala.collection.mutable.HashMap
import scala.io.StdIn

object Repl extends App {
  @main
  def main(): Unit = {

    var userName = ""
    var wins = 0
    var continue = true
    while (continue) {
      if userName.isBlank then
        println("What is your name?")
        userName = StdIn.readLine()

      var userCards = List(getCard, getCard)
      println(s"Dealing $userName cards: " + userCards)

      println("What is your bet?")
      val bet = StdIn.readInt()

      val communityCards = List(getCard, getCard, getCard)
      println("Dealing community cards: " + communityCards)
      var dealerCards = List(getCard, getCard)
      println("Revealing dealer cards: " + dealerCards)

      userCards = userCards ++ communityCards
      dealerCards = dealerCards ++ communityCards

      val userHandCalculated = calculateHand(Hand(userCards))
      val dealerHandCalculated = calculateHand(Hand(dealerCards))

      if Constants.env.apply(userHandCalculated) < Constants.env.apply(dealerHandCalculated) then
        println(s"$userName wins by " + userHandCalculated)
        wins = wins + 1
        Connection.insertGameResult(userName, Result.WIN, userHandCalculated, bet)
      else if Constants.env.apply(userHandCalculated) == Constants.env.apply(dealerHandCalculated) then
        println("Draw by " + userHandCalculated)
        Connection.insertGameResult(userName, Result.DRAW, userHandCalculated, bet)
      else
        println("Dealer wins by " + dealerHandCalculated)
        Connection.insertGameResult(userName, Result.LOOSE, userHandCalculated, bet)

      println("One more round? [y/n]")
      val nextRound = StdIn.readLine()

      if (nextRound == "n") {
        continue = false
        println(s"Leaving the table, total win: $wins")
      } else if (nextRound == "y") {
        println("------------------------------------")
      }
    }
  }

  private def getCard: Card = {
    Card(getRandomRank, getRandomSuit)
  }

  private def getRandomRank: String = {
    val randomInt = scala.util.Random.nextInt(13)
    Constants.RANKS(randomInt)
  }

  private def getRandomSuit: String = {
    val randomInt = scala.util.Random.nextInt(4)
    Constants.SUITS(randomInt)
  }

  def calculateHand(hand: Hand): String = {
    val cards: List[Card] = hand.cards
    val rankCount = HashMap[String, Int]()
    val suitCount = HashMap[String, Int]()

    for (card <- cards) {
      rankCount.put(card.rank, rankCount.getOrElse(card.rank, 0) + 1)
      suitCount.put(card.suit, suitCount.getOrElse(card.suit, 0) + 1)
    }

    val isFlush = suitCount.size == 1
    val isStraight = isStraightHand(rankCount.keys.toSet)
    if isFlush then "Flush"
    else if isStraight then "Straight"
    else if rankCount.values.toList.contains(3) then "Three of a Kind"
    else if rankCount.values.toList.count(_ == 2) == 2 then "Two Pair"
    else if rankCount.values.toList.contains(2) then "One Pair"
    else "High Card"
  }

  private def isStraightHand(ranks: Set[String]): Boolean = {
    if ranks.size != 5 then return false
    val list: List[Int] = ranks.toList.map(rank => mapRankToNumber(rank)).map(rank => rank.toInt).sorted
    if list(4) - list.head == 4 then true
    else false
  }

  private def mapRankToNumber(rank: String): String = rank match
    case "J" => "11"
    case "Q" => "12"
    case "K" => "13"
    case "A" => "14"
    case _ => rank
}