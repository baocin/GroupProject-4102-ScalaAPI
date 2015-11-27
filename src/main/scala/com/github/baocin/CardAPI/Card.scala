package com.github.baocin.CardAPI

import argonaut._, Argonaut._

// case class JsonCard(suit : String, rank : String)
// object JsonCard {
//   // Define codecs easily from case classes
//   implicit def CardCodecJson: CodecJson[JsonCard] =
//     casecodec2(JsonCard.apply, JsonCard.unapply)("suit", "rank")
// }

case class Card(var suit : String, var rank : String, var shortName : String, var longName : String) {
  def this(suit : String, rank : String) = this(suit, rank, suit+rank, null)

  decodeLongName(suit, rank)

  def decodeLongName(suit : String, rank : String) = {
    longName = "decoded " + shortName
  }

  override def toString() : String = {
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

  def toJson : Json = {
    var longNameField : Json.JsonField = "longName"
    var shortNameField : Json.JsonField = "shortName"
    var suitField : Json.JsonField = "suit"
    var rankField : Json.JsonField = "rank"
    val rawJson : Json = Json( longNameField := longName, shortNameField := shortName, suitField := suit, rankField := rank)
    rawJson
  }

  def toJsonString(spacing : Int = 2) : String = {
    val rawJson = toJson;
    val resultJson = spacing match {
      case 0 => rawJson.nospaces
      case 2 => rawJson.spaces2
      case 4 => rawJson.spaces4
      case _ => rawJson.nospaces
    }
    resultJson
  }
}
