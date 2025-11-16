package com.woowacourse.woowacollectionapp.lotto.domain

import kotlin.math.roundToInt

object ProfitCalculator {
    fun calculate(ranks: Map<LottoRank, Int>, count: Int): Double {
        val prize = ranks.entries.sumOf { it.key.money * it.value }
        val purchaseMoney = count * Lotto.PRICE

        return (prize.toDouble() / purchaseMoney * 1000).roundToInt() / 10.0
    }
}

