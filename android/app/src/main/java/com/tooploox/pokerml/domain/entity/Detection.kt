package com.tooploox.pokerml.domain.entity

import com.tooploox.pokerml.domain.entity.Card
import java.io.Serializable

data class Detection(val card: Card, val probability: Float) : Serializable
