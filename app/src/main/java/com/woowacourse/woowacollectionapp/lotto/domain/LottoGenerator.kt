package com.woowacourse.woowacollectionapp.lotto.domain

import kotlin.random.Random

object LottoGenerator {
    fun generate(count: Int): List<Lotto> =
        List(count) { generateOne() }

    private fun generateOne(): Lotto =
        Lotto(
            (Lotto.MIN_NUMBER..Lotto.MAX_NUMBER).shuffled()
                .take(Lotto.NUMBER_COUNT)
        )
}

