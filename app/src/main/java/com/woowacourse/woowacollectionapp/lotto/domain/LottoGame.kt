package com.woowacourse.woowacollectionapp.lotto.domain

data class LottoResult(
    val lotto: Lotto,
    val rank: LottoRank
)

class LottoGame(
    val lottos: List<Lotto>,
    val winNumbers: Lotto,
    val bonusNumber: Int
) {
    val lottoResults: List<LottoResult> by lazy {
        lottos.map { lotto ->
            LottoResult(lotto, RankDecider.decide(lotto, winNumbers, bonusNumber))
        }
    }
    
    val rankCount: Map<LottoRank, Int> by lazy {
        val tempRankCount = mutableMapOf<LottoRank, Int>()
        lottoResults.forEach { result ->
            if (result.rank != LottoRank.NOTHING) {
                tempRankCount[result.rank] = tempRankCount.getOrDefault(result.rank, 0) + 1
            }
        }
        tempRankCount
    }
}

