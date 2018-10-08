package com.tooploox.pokerml.domain.gateway

import java.nio.MappedByteBuffer

interface ModelFactory {
    fun fromAsset(path: String): MappedByteBuffer
}
