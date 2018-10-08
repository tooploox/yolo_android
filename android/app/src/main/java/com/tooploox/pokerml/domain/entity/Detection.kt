package com.tooploox.pokerml.domain.entity

import java.io.Serializable

data class Detection(
        val card: Card,
        val probability: Float
) : Serializable
