package com.github.baocin.CardAPI

import org.scalatra._
import scalate.ScalateSupport
import argonaut._, Argonaut._
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class CardServlet extends CardapiStack {
  //A hash table to organize the decks user's create
  val map = HashMap.empty[String,Deck]

  //Ensure there is always a testing deck with id of 0  (so I can keep the same testing links after restarts)
  map += ("0" -> new Deck())

  //Error messages
  val html404Error = "Error: 404\nCould not find Deck with that ID!"
  val html400Error = "Error: 400\nBad request! See /help for instructions"
  val deckEmptyError = "Error!\nDeck contains contains zero cards!"

  get("/") {
    redirect("/main.html")
  }

  get("/help") {
    redirect("/help.html")
  }

  get("/deck/:id/removeAll") {
  	val mapID = params.getOrElse("id", halt(404, html404Error))
  	map(mapID).cardList.clear()
    map(mapID).toJsonString(2)
  }

  get("/deck/:id/remove/:card/?") {
  	val mapID = params.getOrElse("id", halt(404, html404Error))
    val cardName = params.getOrElse("card", halt(400, html400Error))
    if (map(mapID).cardList.length == 0) halt(400, deckEmptyError)
  	map(mapID).removeCard(cardName)
    map(mapID).toJsonString(2)
  }

  get("/deck/:id/add/:card/?") {
  	val mapID = params.getOrElse("id", halt(404, html404Error))
    val cardShortName = params.getOrElse("card", halt(400, html400Error))
  	var status = map(mapID).addCard(cardShortName)
    map(mapID).toJsonString(2)
  }

  get("/deck/:id/shuffle/?") {
    //return the shuffled deck of id ID
    val mapID = params.getOrElse("id", halt(404, html404Error))
    try map(mapID) catch { case e : NoSuchElementException => halt(404, html404Error) }
    map(mapID).shuffle
    map(mapID).toJsonString(2)
  }

  get("/deck/:id/draw/?") {
    val mapID = params.getOrElse("id", halt(404, html404Error))
    var count : Int = params.getOrElse("count", "1").toInt     //Must draw atleast 1 card
    if (count > map(mapID).cardList.length) halt(400, html400Error)
    if (map(mapID).cardList.length == 0) halt(400, deckEmptyError)

    // var availableCards = map(mapID).cardList
    var removedCards = ArrayBuffer[Card]();

    var i = 1;
    for (i <- 1 to count){
      var removedCard = map(mapID).randomCard();
      removedCards.append(removedCard)
      map(mapID).removeCard(removedCard)
    }

    // var cardField : Json.JsonField = "cards"
    var cardList = removedCards.toList
    var jsonCardList = cardList.map(x => x.toJson)
    var rawJson = Json("remaining" -> map(mapID).cardList.length.asJson, "cards" -> jsonCardList.asJson)
    rawJson.spaces2
  }

  get("/deck/:id/?") {
  	val mapID = params.getOrElse("id", halt(404, html404Error))
    map(mapID).toJsonString(2)
  }

  get("/deck/new/?") {
    multiParams("cards")	//comma separated
  	var cards = params.getOrElse("cards", "")
    var newDeck : Deck = null;
// (validCardsRegex match cards))
    if (cards.isEmpty){
      newDeck = new Deck(); //Default Deck
    }else{
      newDeck = new Deck(cards.split(","));
    }
    //Add the newly made deck to the HashMap
    map += (newDeck.id -> newDeck)
    newDeck.toJsonString(2)
  }

  //Do some basic error handling BEFORE the above routes
  before ("/deck/:id/remove/:card*") {
  	val mapID = params.getOrElse("id", halt(404, html404Error))
    try map(mapID) catch { case e : NoSuchElementException => halt(404, html404Error) }
    val cardName = params.getOrElse("card", halt(400, html400Error))
    if (map(mapID).cardList.find(x => x.shortName == cardName).isEmpty) {
      halt(404, "Error: 404\nSpecified card does not exist in this deck")
    }
  }

  before ("/deck/:id/*/:card*") {
    val cardShortName = params.getOrElse("card", halt(400, html400Error)).toUpperCase()
    //Can drop off the parenthesis in most cases similar to Ruby's poetry mode
    if ((Card.validShortNameRegex unapplySeq cardShortName) == None) {
      halt(400,html400Error)
    }
    //else continue to the next matching route (ex. add or remove card)
  }

  before ("/deck/:id*") {
  	val mapID = params.getOrElse("id", halt(404, html404Error))
    if (mapID == "new") pass
    try map(mapID) catch { case e : NoSuchElementException => halt(404, html404Error) }
  }

}
