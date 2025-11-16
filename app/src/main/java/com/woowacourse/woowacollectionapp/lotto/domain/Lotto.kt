package com.woowacourse.woowacollectionapp.lotto.domain

class Lotto(private val numbers: List<Int>) {
    companion object {
        const val PRICE = 1000
        const val MIN_NUMBER = 1
        const val MAX_NUMBER = 45
        const val NUMBER_COUNT = 6
    }

    init {
        // TODO: 검증 로직 구현
    }

    override fun toString(): String = numbers.sorted().toString()

    fun getNumbers(): List<Int> = numbers
}

