package com.woowacourse.woowacollectionapp.lotto.domain

object RankDecider {
    fun decide(lotto: Lotto, winNumbers: Lotto, bonus: Int): LottoRank {
        val matchCount = lotto.getNumbers().count { it in winNumbers.getNumbers() }
        val hasBonus = bonus in lotto.getNumbers()

        return when {
            matchCount == 6 -> LottoRank.FIRST
            matchCount == 5 && hasBonus -> LottoRank.SECOND
            matchCount == 5 -> LottoRank.THIRD
            matchCount == 4 -> LottoRank.FORTH
            matchCount == 3 -> LottoRank.FIFTH
            else -> LottoRank.NOTHING
        }
    }
}

