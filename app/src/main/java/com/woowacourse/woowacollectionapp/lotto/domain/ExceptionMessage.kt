package com.woowacourse.woowacollectionapp.lotto.domain

object ExceptionMessage {
    const val NOT_POSITIVE_NUMBER = "[ERROR] 숫자를 입력해 주세요."
    const val NOT_UNIT_NUMBER = "[ERROR] ${LottoConstants.LOTTO_PRICE}원 단위의 양수를 입력해 주세요."
    const val MAX_PURCHASE_MONEY_EXCEEDED = "[ERROR] 구입 금액은 최대 ${LottoConstants.MAX_PURCHASE_MONEY}원까지 가능합니다."
    const val NOT_VALID_LOTTO_NUMBERS = "[ERROR] 로또 번호는 ${Lotto.NUMBER_COUNT}개여야 합니다."
    const val DUPLICATE_WIN_NUMBER = "[ERROR] 중복된 숫자가 있습니다."
    const val NOT_IN_RANGE =
        "[ERROR] ${Lotto.MIN_NUMBER}~${Lotto.MAX_NUMBER} 범위의 숫자만 입력이 가능합니다."
    const val NOT_VALID_WIN_NUMBER =
        "[ERROR] 정확히 ${Lotto.NUMBER_COUNT}개의 숫자를 ','로 구분해 입력해 주세요."
    const val DUPLICATE_BONUS_NUMBER = "[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다."
}

