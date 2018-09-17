package com.tooploox.pokerml.domain.entity

import java.io.Serializable

data class Card(val rank: Rank, val suit: Suit) : Serializable
