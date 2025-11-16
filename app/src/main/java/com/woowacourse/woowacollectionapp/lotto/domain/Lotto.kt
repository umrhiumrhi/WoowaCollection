package com.woowacourse.woowacollectionapp.lotto.domain

class Lotto(private val numbers: List<Int>) {
    companion object {
        const val PRICE = 1000
        const val MIN_NUMBER = 1
        const val MAX_NUMBER = 45
        const val NUMBER_COUNT = 6
    }

    init {
        checkCount()
        checkDuplicate()
        checkRange()
    }

    private fun checkCount() {
        require(numbers.size == NUMBER_COUNT) { ExceptionMessage.NOT_VALID_LOTTO_NUMBERS }
    }

    private fun checkDuplicate() {
        require(numbers.distinct().size == NUMBER_COUNT) { ExceptionMessage.DUPLICATE_WIN_NUMBER }
    }

    private fun checkRange() {
        require(numbers.all { it in MIN_NUMBER..MAX_NUMBER }) { ExceptionMessage.NOT_IN_RANGE }
    }

    override fun toString(): String = numbers.sorted().toString()

    fun getNumbers(): List<Int> = numbers
}

