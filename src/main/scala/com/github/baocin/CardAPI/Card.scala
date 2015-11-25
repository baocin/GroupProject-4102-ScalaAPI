package com.github.baocin.CardAPI

import argonaut._, Argonaut._

case class JsonCard(suit : String, rank : String)
object JsonCard {
  // Define codecs easily from case classes
  implicit def CardCodecJson: CodecJson[JsonCard] =
    casecodec2(JsonCard.apply, JsonCard.unapply)("suit", "rank")
}

class Card(var suit : String, var rank : String) {
  
}
