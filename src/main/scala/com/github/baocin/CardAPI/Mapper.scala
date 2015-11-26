package com.github.baocin.CardAPI

import argonaut._, Argonaut._

object Mapper {
  // def deckToJsonDeck(deck : Deck) : JsonDeck = {
  //   var cardList = deck.cardList.toList
  //   var jsonCardList = cardList.map( x => cardToJsonCard(x))
  //   JsonDeck(jsonCardList)
  // }

  // def arrayBuffertoJson
  // def deckToJson(deck : Deck) : Json = {
  //   deckToJsonDeck(deck).asJson
  // }
  def cardToJsonCard(card : Card) : JsonCard = {
    JsonCard(card.suit, card.rank)
  }

  def cardToJson(card : Card) : Json = {
    cardToJsonCard(card).asJson
  }
}
