package com.github.baocin.CardAPI

import scala.util.Random
import argonaut._, Argonaut._
import scala.collection.mutable.ArrayBuffer

class Deck(cards : Array[String]) {
  //Array of possible suits (Hearts, Spaces, Diamonds, Clubs)
  var suits = Array("H", "S", "D", "C")
  //Array of possible ranks (Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King)
  var ranks = Array("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
  var cardList = ArrayBuffer[Card]()
  var id : String = "%X" format java.lang.System.identityHashCode(this);    //Hexadecimal id

  //Populate the deck with cards specified in cards parameter
  populate(cards);

  //The empty constructor for the Deck
  def this() = {
    this(null)  //call default constructor with no cards
    populateDefault();  //populate with default 52 cards (4 suits, 13 cards in each)
  }

  //populate the deck with cards from the given seq
  def populate(cards : Array[String]) {
    // println("Populating with cards list")
  }

  //populate the deck with default 52 cards
  def populateDefault() {
    // println("Populating default cards list")
    var i,j = 0
    //Loop through suits
    for(i <- 0 to 3)
    {
      //Loop through ranks
      for(j <- 0 to 12)
      {
        //Create instances of Card class and store to Deck's cards field
        cardList.append(new Card(suits(i), ranks(j)))
      }
    }
  }

  def randomCard() : Card =
  {
    //Pick a random card from the array
    var cardNumber = Random.nextInt(cardList.length)
    cardList(cardNumber)
  }

  override def toString() : String =
  {
    var representation : StringBuilder = new StringBuilder()

    //Loop through all of the cards in the array
    for(i <- 0 to (cards.length - 1))
    {
      representation.append(cardList(i).getSuit() + cardList(i).getRank() + " ")
    }

    representation.toString()
  }

  def shuffle {
    cardList = Random.shuffle(cardList)
  }
  //Add a card to the deck
  def addCard(cardToAdd: Card) = cardList.append(cardToAdd)
  def addCard(cardName : String) = cardList.append(new Card(cardName))


  def removeCard(cardToRemove : Card) {
    cardList.remove(cardList.indexWhere(x => x == cardToRemove))
  }

  def removeCard(cardName : String) {
    cardList.remove(cardList.indexWhere(x => x.shortName == cardName))
  }

  def toJson : Json = {
    var cardField : Json.JsonField = "cards"
    var cardList = this.cardList.toList
    var jsonCardList = cardList.map( x => x.toJson)
    val rawJson : Json = Json("id" := id, "size" := cardList.length, cardField := jsonCardList)
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
