package com.tooploox.pokerml.domain.usecase.base

interface Usecase<T> {
    fun execute(): T
}
