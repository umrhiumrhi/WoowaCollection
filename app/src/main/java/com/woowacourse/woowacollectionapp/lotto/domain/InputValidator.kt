package com.woowacourse.woowacollectionapp.lotto.domain

object InputValidator {
    fun purchaseMoneyInput(input: String): Int {
        val money = input.toIntOrNull() 
            ?: throw IllegalArgumentException(ExceptionMessage.NOT_POSITIVE_NUMBER)

        require(money > 0 && money % LottoConstants.LOTTO_PRICE == 0) {
            ExceptionMessage.NOT_UNIT_NUMBER
        }
        
        require(money <= LottoConstants.MAX_PURCHASE_MONEY) {
            ExceptionMessage.MAX_PURCHASE_MONEY_EXCEEDED
        }

        return money
    }

    fun winNumbersInput(input: String): List<Int> {
        val winNumbers = input.split(",")
            .map { it.trim() }
            .mapNotNull { it.toIntOrNull() }

        require(winNumbers.size == Lotto.NUMBER_COUNT) {
            ExceptionMessage.NOT_VALID_WIN_NUMBER
        }
        require(winNumbers.distinct().size == Lotto.NUMBER_COUNT) { ExceptionMessage.DUPLICATE_WIN_NUMBER }
        require(winNumbers.all { it in Lotto.MIN_NUMBER..Lotto.MAX_NUMBER }) {
            ExceptionMessage.NOT_IN_RANGE
        }

        return winNumbers
    }

    fun bonusNumberInput(input: String, winNumbers: List<Int>): Int {
        val bonus = input.toIntOrNull() ?: throw IllegalArgumentException("[ERROR] 숫자를 입력해 주세요.")

        require(bonus in Lotto.MIN_NUMBER..Lotto.MAX_NUMBER) {
            ExceptionMessage.NOT_IN_RANGE
        }
        require(bonus !in winNumbers) { ExceptionMessage.DUPLICATE_BONUS_NUMBER }

        return bonus
    }
}

