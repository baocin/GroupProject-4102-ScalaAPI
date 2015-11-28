package com.github.baocin.CardAPI

import argonaut._, Argonaut._

//Static fields & methods for a Card - like available ranks (Ace) and suits(Hearts)
object Card {
  val ranks = Array("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
  val expandedRankNames = Array("Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King")
  val suits = Array("H", "S", "D", "C")
  val expandedSuitNames = Array("Hearts", "Spades", "Diamonds", "Clubs")
  val validShortNameRegex = "([2-9AJQK]|10)([HSDC])".r

}

//Parameters autogenerate fields, getters, and setters(if defined with var) in the class
class Card(var rank : String, var suit : String, var shortName : String, var longName : String) {

  //Auxilliary constructors
  def this(rank : String, suit : String) = this(rank, suit, rank+suit, null)
  def this(shortName : String) = this(shortName.slice(0, shortName.length-1), shortName.slice(shortName.length-1, shortName.length), shortName, null)

  //Will be called as soon as class is created
  decodeShortName

  //Decode the short name (KH) into the long, english equivalent (King of Hearts)
  def decodeShortName = {
    var fullName : String = "";
    fullName += Card.expandedRankNames(Card.ranks.indexOf(rank))
    fullName += " of "
    fullName += Card.expandedSuitNames(Card.suits.indexOf(suit))
    longName = fullName
  }

  //used for testing, unneeded in actual program
  override def toString() : String = {
    var representation : StringBuilder = new StringBuilder(rank + suit)
    return representation.toString()
  }

  //sing Argonaut to construct json
  def toJson : Json = {
    var longNameField : Json.JsonField = "longName"
    var shortNameField : Json.JsonField = "shortName"
    var suitField : Json.JsonField = "rank"
    var rankField : Json.JsonField = "suit"
    val rawJson : Json = Json( longNameField := longName, shortNameField := shortName, suitField := rank, rankField := suit)
    rawJson
  }

  //Actually convert json to a string for output
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
