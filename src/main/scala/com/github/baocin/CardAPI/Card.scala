package com.github.baocin.CardAPI

import argonaut._, Argonaut._

case class Card(suit : String, value : Int)
object Card {
  
  // Define codecs easily from case classes
  implicit def CardCodecJson: CodecJson[Card] =
    casecodec2(Card.apply, Card.unapply)("suit", "value")
    
 
}
