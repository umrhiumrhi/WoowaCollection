package com.woowacourse.woowacollectionapp.lotto.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class LottoUiState(
    val purchaseMoney: String = "",
    val lottos: List<com.woowacourse.woowacollectionapp.lotto.domain.Lotto> = emptyList(),
    val winNumbers: String = "",
    val bonusNumber: String = "",
    val game: com.woowacourse.woowacollectionapp.lotto.domain.LottoGame? = null,
    val errorMessage: String? = null,
    val currentStep: LottoStep = LottoStep.PURCHASE,
    val isWinNumbersValidated: Boolean = false,
    val isLottosConfirmed: Boolean = false
)

enum class LottoStep {
    PURCHASE,
    WIN_NUMBERS,
    RESULT
}

class LottoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LottoUiState())
    val uiState: StateFlow<LottoUiState> = _uiState.asStateFlow()

    fun setPurchaseMoney(money: String) {
        // TODO: 입력값 처리 구현
    }

    fun purchaseLottos() {
        // TODO: 로또 구매 로직 구현
    }

    fun setWinNumbers(numbers: String) {
        // TODO: 입력값 처리 구현
    }

    fun validateWinNumbers() {
        // TODO: 당첨 번호 검증 로직 구현
    }

    fun setBonusNumber(number: String) {
        // TODO: 입력값 처리 구현
    }

    fun calculateResult() {
        // TODO: 결과 계산 로직 구현
    }

    fun reset() {
        // TODO: 초기화 로직 구현
    }
}

