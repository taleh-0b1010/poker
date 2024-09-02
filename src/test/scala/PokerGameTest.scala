import org.scalatest.funsuite.AnyFunSuite

class PokerGameTest extends AnyFunSuite {

  test("Calculating hand for Flush") {
    val hand = Hand(List(Card("A", "H"), Card("K", "H"), Card("10", "H"), Card("4", "H"), Card("3", "H")))
    assert(Repl.calculateHand(hand) == "Flush")
  }

  test("Calculating hand for Three of a Kind") {
    val hand = Hand(List(Card("2", "C"), Card("2", "H"), Card("2", "D"), Card("4", "H"), Card("3", "H")))
    assert(Repl.calculateHand(hand) == "Three of a Kind")
  }

  test("Calculating hand for Straight") {
    val hand = Hand(List(Card("2", "C"), Card("3", "H"), Card("4", "D"), Card("5", "H"), Card("6", "H")))
    assert(Repl.calculateHand(hand) == "Straight")
  }
}
