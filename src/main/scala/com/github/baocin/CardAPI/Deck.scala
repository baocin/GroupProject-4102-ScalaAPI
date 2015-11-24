package com.github.baocin.CardAPI

import argonaut._, Argonaut._
import scala.collection.mutable.ArrayBuffer

case class Deck(cardList : List[Card])

object Deck {
  //var suits:Array[String] = new Array[String](4)
  var suits = Array("hearts", "spades", "diamonds", "clubs")
  //var ranks:Array[String] = new Array[String](13)
  var ranks = Array("ace", "1", "2", "3", "4", "5", "6", "7", "8", "9", "jack", "queen", "king")
  //var cardList : ArrayBuffer[Card] = new ArrayBuffer[Card];
  
  /*
  def this(cardList : ArrayBuffer[Card]) = {
    this();
  }
  def shuffle {
    //shuffle the deck
    
  }*/
  
  // Define codecs easily from case classes
  implicit def DeckCodecJson: CodecJson[Deck] =
    casecodec1(Deck.apply, Deck.unapply)("cardList")
    
}
