package com.github.baocin.CardAPI

import org.scalatra._
import scalate.ScalateSupport
import argonaut._, Argonaut._
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

//Using a Mixin for Logging - essentially taking a diff between the extended class and the class specified using the "with" keyword and adding any unmatched functionality
class CardServlet extends CardapiStack with Logging{
  logger.info("STARTED!") //Just to show that logging is, infact, working with the mixin
  
  //A hash table to organize the decks user's create
  val map = HashMap.empty[String,Deck]

  //Ensure there is always a testing deck with id of 0  (so I can keep the same testing links after restarts)
  map += ("0" -> new Deck())
  map("0").id = "0";
  logger.info(s"Created the default deck with id #{map('0').id}")   //String Interpolation with a variable!

  //Error messages
  val noSuchDeckError = "Error: 404\nCould not find Deck with that ID!"
  val html400Error = "Error: 400\nBad request! Invalid Request. Please consult /help to find out required parameters for each operation."
  val noSuchCardError = "Error: 404\nBad request! Specified card not in specified deck!"
  val deckEmptyError = "Error!\nDeck contains contains zero cards!"
  val drawCountTooHighError = "Error: 400\nThe draw count is higher than size of the specified deck!"
  val invalidCardNameError = "Error: 400\nInvalid card name. To be valid a card's short name should have two sections - rank first, then suit. Rank can be 2-10,A,J,Q, or K. Suit can be H,S,D, or C. For example, the eight of clubs would be named \"8C\"."
  
  get("/") {
    redirect("/main.html")
  }

  get("/help") {
    redirect("/help.html")
  }

  get("/deck/:id/remove/:card/?") {
  	val mapID = params.getOrElse("id", halt(404, noSuchDeckError))
    val cardName = params.getOrElse("card", halt(400, html400Error))
    if (map(mapID).cardList.length == 0) halt(400, deckEmptyError)
  	map(mapID).removeCard(cardName)
    map(mapID).toJsonString(2)
  }

  get("/deck/:id/removeAll") {
  	val mapID = params.getOrElse("id", halt(404, noSuchDeckError))
  	map(mapID).cardList.clear()
    map(mapID).toJsonString(2)
  }
  
  get("/deck/:id/add/:card/?") {
  	val mapID = params.getOrElse("id", halt(404, noSuchDeckError))
    val cardShortName = params.getOrElse("card", halt(400, html400Error))
  	var status = map(mapID).addCard(cardShortName)
    map(mapID).toJsonString(2)
  }

  get("/deck/:id/probability/:card/?") {
    val mapID = params.getOrElse("id", halt(404, noSuchDeckError))
    val cardShortName = params.getOrElse("card", halt(400, html400Error))
    val card = new Card(cardShortName)
    val countFound = map(mapID).findAll(cardShortName)
    val probability = map(mapID).probabilityToChoose(cardShortName)
    logger.info("count:" + countFound + " probability:" + probability)
    var rawJson = Json("probability" -> probability.asJson, "countFound" -> countFound.asJson, "card" -> card.toJson)
    rawJson.spaces2
  }
  
  get("/deck/:id/shuffle/?") {
    //return the shuffled deck of id ID
    val mapID = params.getOrElse("id", halt(404, noSuchDeckError))
    try map(mapID) catch { case e : NoSuchElementException => halt(404, noSuchDeckError) }
    map(mapID).shuffle
    map(mapID).toJsonString(2)
  }

  get("/deck/:id/default/?") {
    val mapID = params.getOrElse("id", halt(404, noSuchDeckError))
  	map(mapID) = new Deck()
  }
  
  get("/deck/:id/draw/?") {
    val mapID = params.getOrElse("id", halt(404, noSuchDeckError))
    implicit var count : Int = params.getOrElse("count", "1").toInt     //Must draw atleast 1 card
    if (count > map(mapID).cardList.length) halt(400, drawCountTooHighError)
    if (map(mapID).cardList.length == 0) halt(400, deckEmptyError)

    var drawnCards = map(mapID).draw;

    // var cardField : Json.JsonField = "cards"
    var cardList = drawnCards.toList
    var jsonCardList = cardList.map(x => x.toJson)
    var rawJson = Json("remaining" -> map(mapID).cardList.length.asJson, "cards" -> jsonCardList.asJson)
    rawJson.spaces2
  }

  get("/deck/:id/?") {
  	val mapID = params.getOrElse("id", halt(404, noSuchDeckError))
    map(mapID).toJsonString(2)
  }

  get("/deck/new/?") {
    multiParams("cards")	//comma separated
  	var cards = params.getOrElse("cards", "")
    var newDeck : Deck = null;

    if (cards.isEmpty){
      newDeck = new Deck(); //Default Deck
    }else{
      try{
        newDeck = new Deck(cards.split(","));
      }catch{
        case e : InvalidNameException => halt(400, invalidCardNameError)
        case e : Throwable => halt(400, html400Error)
      }
    }
    //Add the newly made deck to the HashMap
    map += (newDeck.id -> newDeck)
    newDeck.toJsonString(2)
  }

  //Do some basic error handling BEFORE the above routes
  before ("/deck/:id/remove/:card*") {
  	val mapID = params.getOrElse("id", halt(404, noSuchDeckError))
    try map(mapID) catch {
      case e : NoSuchElementException => halt(404, noSuchDeckError)
    }
    val cardName = params.getOrElse("card", halt(400, html400Error))
    if (map(mapID).cardList.find(x => x.shortName == cardName).isEmpty) {
      halt(404, noSuchCardError)
    }
  }

  before ("/deck/:id/*/:card*") {
    val cardShortName = params.getOrElse("card", halt(400, html400Error)).toUpperCase()
    //Can drop off the parenthesis in most cases similar to Ruby's poetry mode
    //This is called an Extractor - it breaks apart the regex and checks it against the given sequence
    if ((Card.validShortNameRegex unapplySeq cardShortName) == None) {  //match proposed card short name against validity regex
      halt(400,invalidCardNameError)
    }
    //else continue to the next matching route (ex. add or remove card)
  }

  before ("/deck/:id*") {
  	val mapID = params.getOrElse("id", halt(404, noSuchDeckError))
    if (mapID == "new") pass
    try map(mapID) catch { case e : NoSuchElementException => halt(404, noSuchDeckError) }
  }

}
