package com.github.baocin.CardAPI

import argonaut._, Argonaut._
import scala.collection.mutable.ArrayBuffer

case class JsonDeck(cardList : List[JsonCard])
object JsonDeck {
  implicit def DeckCodecJson: CodecJson[JsonDeck] =
    casecodec1(JsonDeck.apply, JsonDeck.unapply)("cardList")
}

class Deck(cards : Array[String]) {
  var suits = Array("hearts", "spades", "diamonds", "clubs")
  var ranks = Array("ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king")
  var cardList = ArrayBuffer[Card]()
  var id = 0;

  def this() = {
    this(null)
    populateDefault();
  }

  // override def this(cards : Array[String]) = {
  //   this(cards)
  //   populate(cards);
  // }


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
