package com.github.baocin.CardAPI

import scala.util.Random
import argonaut._, Argonaut._
import scala.collection.mutable.ArrayBuffer

case class JsonDeck(cardList : List[JsonCard])
object JsonDeck {
  implicit def DeckCodecJson: CodecJson[JsonDeck] =
    casecodec1(JsonDeck.apply, JsonDeck.unapply)("cardList")
}

class Deck(cards : Array[String]) {
  //Array of possible suits (Hearts, Spaces, Diamonds, Clubs)
  var suits = Array("H", "S", "D", "C")
  //Array of possible ranks (Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King)
  var ranks = Array("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")

  var cardList = ArrayBuffer[Card]()
  var id = 0;

  def this() = {
    this(null)
    populateDefault();
  }

  //populate the deck with cards from the given seq
  def populate(cards : Array[String]) {
    println("Populating with cards list")
  }

  //populate the deck with default 52 cards
  def populateDefault() {
    println("Populating default cards list")
    // (0 to suits.length) foreach {
    //   (0 to ranks.length) foreach {
    //     var newCard = new Card()
    //   }
    // }
    //Iteration variables
    var i = 0
    var j = 0
    var k = 0

    //Loop through suits
    for(i <- 0 to 3)
    {

      //Loop through ranks
      for(j <- 0 to 12)
      {

        //Create instances of Card class and store to Deck's cards field
        cardList.append(new Card(suits(i), ranks(j)))

      }
      k = k + 1

    }
  }

  def randomCard() : Card =
  {

    //Pick a random card from the array
    var cardNumber = Random.nextInt(cards.length) - 1

    return cardList(cardNumber)
  }

  override def toString() : String =
  {
    var representation : StringBuilder = new StringBuilder()

    //Loop through all of the cards in the array
    for(i <- 0 to (cards.length - 1))
    {
      representation.append(cardList(i).getSuit() + cardList(i).getRank() + " ")
      println(cardList(i).getSuit() + cardList(i).getRank() + " ")
    }

    return representation.toString()
  }

  //Add a card to the deck
  def addCard(cardToAdd: Card): Unit =
  {
    // cards(cardList.length - 1) = cardToAdd
  }
  // Define codecs easily from case classes


  // implicit def DeckEncodeJson: EncodeJson[Deck] =
  //   EncodeJson((d: Deck) => ("id" := d.id) ->: ("cards" := d.cardList) ->: jEmptyObject)
  //       // jencode2L((d: Deck) => (d.id, d.cardList))("id", "cards")
  //
  // implicit def DeckDecodeJson: DecodeJson[Deck] =
  //   DecodeJson(c => for {
  //     cardList <- (c --\ "cards").as[List[Card]]
  //   } yield Deck(cardList, id))
  // implicit val codec = CodecJson.derive[Deck]
}
