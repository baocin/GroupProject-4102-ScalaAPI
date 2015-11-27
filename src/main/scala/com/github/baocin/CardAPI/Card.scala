package com.github.baocin.CardAPI

import argonaut._, Argonaut._

case class Card(var suit : String, var rank : String, var shortName : String, var longName : String) {
  def this(suit : String, rank : String) = this(suit, rank, suit+rank, null)
  def this(shortName : String) = this(shortName(0).toString, shortName(1).toString, shortName, null)
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
