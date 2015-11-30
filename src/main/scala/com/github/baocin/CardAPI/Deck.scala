package com.github.baocin.CardAPI

import scala.util.Random
import argonaut._, Argonaut._
import scala.collection.mutable.ArrayBuffer   //Scala equivalent of Array List of Java

class Deck(cards : Array[String]) extends Logging {
  var cardList = ArrayBuffer[Card]()
  //Optional parenthesis!
  var id : String = "%X" format java.lang.System.identityHashCode(this);    //Hexadecimal id

  //Populate the deck with cards specified in cards parameter
  populate(cards);

  //The empty constructor for the Deck
  def this() = {
    this(Array[String]())  //call default constructor with no cards
    populateDefault();  //populate with default 52 cards (4 suits, 13 cards in each)
  }

  //populate the deck with cards from the given seq
  def populate(cards : Array[String]) {
    for (cardShortName <- cards){
      addCard(cardShortName)
    }
  }

  //populate the deck with default 52 cards
  def populateDefault() {
    var i,j = 0
    //Loop through suits
    for(i <- 0 until Card.suits.length)  //0 to 3
    {
      //Loop through ranks
      for(j <- 0 until Card.ranks.length)  // 0 to 12
      {
        //Create instances of Card class and store to Deck's cards field
        cardList.append(new Card(Card.ranks(j), Card.suits(i)))
      }
    }
  }

  def randomCard() : Card =
  {
    //Pick a random card from the array buffer
    var cardNumber = Random.nextInt(cardList.length)
    cardList(cardNumber)
  }

  //Unneeded in program but used in testing
  override def toString() : String =
  {
    var representation : StringBuilder = new StringBuilder()
    //Loop through all of the cards in the array
    for(i <- 0 to (cards.length - 1))
    {
      representation.append(cardList(i).suit + cardList(i).rank + " ")
    }
    representation.toString()
  }

  //Alternative method for shuffling the array
  /*
  def shuffle {
    cardList = Random.shuffle(cardList)
  }
  */

  //Shuffle the deck using explicit algorithm
  def shuffle
  {
    var r = 0
    var i = 0
    var tempCard = cardList(0)

    for(i <- 0 until cardList.length)
    {
      r = Random.nextInt(cardList.length)
      
      //temporarily save card
      tempCard = cardList(r)
      
      //swap
      cardList(r) = cardList(i)
      cardList(i) = tempCard
    }
  }

  //Can get rid of some curly braces if function fits on one line
  def addCard(cardToAdd: Card) = cardList.append(cardToAdd)
  def addCard(cardShortName : String) = {
    cardList.append(new Card(cardShortName))
  }

  def removeCard(cardToRemove : Card) {
    cardList.remove(cardList.indexWhere(x => x == cardToRemove))
  }

  def removeCard(cardShortName : String) {
    cardList.remove(cardList.indexWhere(x => x.shortName == cardShortName))
  }
  
  //find all cards that match the specified card in this deck
  def findAll(cardShortName : String) : Int = {
    cardList.count(x => x.shortName == cardShortName)
  }
  
  //return probability of choosing a given card from this deck
  def probabilityToChoose(cardShortName : String) : Double = {
    findAll(cardShortName)/(1.0 * cardList.length)
  }
  
  def probabilityToChoose(card : Card) : Double = {
    probabilityToChoose(card.shortName)
  }
  
  def draw(implicit count : Int) : ArrayBuffer[Card] = {
    var drawnCards = ArrayBuffer[Card]()
    var i = 1;
    for (i <- 1 to count){
      var drawnCard = cardList(0)     //draw off the top card
      drawnCards.append(drawnCard)
      removeCard(drawnCard)
    }
    drawnCards
  }
  
  //Using Argonaut to construct json
  def toJson : Json = {
    var cardField : Json.JsonField = "cards"
    var cardList = this.cardList.toList
    var jsonCardList = cardList.map( x => x.toJson)   //convert every element in cardList to json
    val rawJson : Json = Json("id" := id, "size" := cardList.length, cardField := jsonCardList)
    rawJson   //return
  }

  //Default constructor sets spacing to 2
  def toJsonString(spacing : Int = 2) : String = {
    val rawJson = toJson;
    val resultJson = spacing match {    //switch case
      case 0 => rawJson.nospaces
      case 2 => rawJson.spaces2
      case 4 => rawJson.spaces4
      case _ => rawJson.nospaces
    }
    resultJson
  }
}
