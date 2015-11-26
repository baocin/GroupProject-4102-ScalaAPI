package com.github.baocin.CardAPI

import argonaut._, Argonaut._

case class JsonCard(suit : String, rank : String)
object JsonCard {
  // Define codecs easily from case classes
  implicit def CardCodecJson: CodecJson[JsonCard] =
    casecodec2(JsonCard.apply, JsonCard.unapply)("suit", "rank")
}

class Card(var suit : String, var rank : String) {
  def this(cardID : String) {
    this(null, null)
  }

  override def toString() : String =
  {
    var representation : StringBuilder = new StringBuilder(suit + rank)

    return representation.toString()
  }

  def getRank() : String =
  {
    return rank
  }

  def getSuit() : String =
  {
    return suit
  }
}
