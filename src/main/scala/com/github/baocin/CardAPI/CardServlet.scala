package com.github.baocin.CardAPI

import org.scalatra._
import scalate.ScalateSupport
import argonaut._, Argonaut._
import scala.collection.mutable.HashMap
import spray.json._
import DefaultJsonProtocol._ // if you don't supply your own Protocol (see below)

class CardServlet extends CardapiStack {
  val map = scala.collection.mutable.HashMap.empty[Int,Deck]

  get("/") {
    <html>
	  <head>
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous"></link>
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" integrity="sha384-aUGj/X2zp5rLCbBxumKTCw2Z50WgIr1vs/PFN4praOTvYXWlVyh2UtNUU0KAUhAX" crossorigin="anonymous"></link>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
		<style rel="stylesheet" href="/main.css"></style>
	  </head>
      <body>
        <h1>Card API</h1>
        An easy to use api for building card games!<br></br>
        Built with <a href="http://www.scala-lang.org/">Scala</a>, <a href="http://scalatra.org/">Scalatra</a>, and <a href="http://argonaut.io/">Argonaut</a>.
      </body>
    </html>
  }

  get("/deck/:id/") {
  	val mapID = params.getOrElse("id", halt(404)).toInt
    Mapper.deckToJson(map(mapID));
  }
  get("/deck/:id/remove/:card") {
  	val mapID = params.getOrElse("id", halt(404)).toInt
    val cardName = params.getOrElse("card", halt(404))
  	// map(mapID).removeCard(cardName)
    println(mapID, " ", cardName)
    Mapper.deckToJson(map(mapID));
  }
  get("/deck/:id/shuffle") {
    //return the shuffled deck of id ID
    val mapID = params.getOrElse("id", halt(404)).toInt
    //map(mapID).shuffle()

  }
  get("/deck/:id/draw") {
    val count : Int = params.getOrElse("count", "1").toInt

    //draw the specified number of cards from the deck of id ID (randomly)
  }
  get("/deck/new") {
    multiParams("cards")	//comma separated
    val validCardsRegex = "([A-Za-z0-9][A-Za-z],?)+".r
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

    Mapper.deckToJson(newDeck);
  }

  get("/card"){
  	var card = new Card("suit", "rank")
  	Mapper.cardToJson(card)
      //Utilities.convertToJson(new Card("suit_test", 999))
  }


}
