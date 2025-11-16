package com.woowacourse.woowacollectionapp.lotto.ui

import androidx.lifecycle.ViewModel
import com.woowacourse.woowacollectionapp.lotto.domain.InputValidator
import com.woowacourse.woowacollectionapp.lotto.domain.Lotto
import com.woowacourse.woowacollectionapp.lotto.domain.LottoConstants
import com.woowacourse.woowacollectionapp.lotto.domain.LottoGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class LottoUiState(
    val purchaseMoney: String = "",
    val lottos: List<Lotto> = emptyList(),
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
        _uiState.value = _uiState.value.copy(
            purchaseMoney = money,
            errorMessage = null
        )
    }

    fun purchaseLottos() {
        val money = _uiState.value.purchaseMoney.trim()
        if (money.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "구입 금액을 입력해주세요."
            )
            return
        }

        try {
            val purchaseMoney = InputValidator.purchaseMoneyInput(money)
            val lottos = generateLottos(purchaseMoney)

            _uiState.value = _uiState.value.copy(
                lottos = lottos,
                currentStep = LottoStep.WIN_NUMBERS,
                errorMessage = null,
                winNumbers = "",
                bonusNumber = "",
                isWinNumbersValidated = false,
                isLottosConfirmed = false
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                errorMessage = e.message ?: "구매 중 오류가 발생했습니다."
            )
        }
    }

    private fun generateLottos(purchaseMoney: Int): List<Lotto> {
        val count = purchaseMoney / LottoConstants.LOTTO_PRICE
        return LottoGenerator.generate(count)
    }

    fun setWinNumbers(numbers: String) {
        _uiState.value = _uiState.value.copy(
            winNumbers = numbers,
            errorMessage = null,
            isWinNumbersValidated = false
        )
    }

    fun validateWinNumbers() {
        val numbers = _uiState.value.winNumbers.trim()
        if (numbers.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "당첨 번호를 입력해주세요."
            )
            return
        }

        try {
            InputValidator.winNumbersInput(numbers)
            _uiState.value = _uiState.value.copy(
                isWinNumbersValidated = true,
                errorMessage = null
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                errorMessage = e.message ?: "당첨 번호 입력 중 오류가 발생했습니다.",
                isWinNumbersValidated = false
            )
        }
    }

    fun confirmLottos() {
        _uiState.value = _uiState.value.copy(
            isLottosConfirmed = true
        )
    }

    fun setBonusNumber(number: String) {
        _uiState.value = _uiState.value.copy(
            bonusNumber = number,
            errorMessage = null
        )
    }

    fun calculateResult() {
        val winNumbersStr = _uiState.value.winNumbers.trim()
        val bonusStr = _uiState.value.bonusNumber.trim()

        validateInputs(winNumbersStr, bonusStr) ?: return

        try {
            val winNumbers = InputValidator.winNumbersInput(winNumbersStr)
            val bonus = InputValidator.bonusNumberInput(bonusStr, winNumbers)
            val game = createLottoGame(winNumbers, bonus)

            _uiState.value = _uiState.value.copy(
                game = game,
                currentStep = LottoStep.RESULT,
                errorMessage = null
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                errorMessage = e.message ?: "결과 계산 중 오류가 발생했습니다."
            )
        }
    }

    private fun validateInputs(winNumbersStr: String, bonusStr: String): Boolean? {
        if (winNumbersStr.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "당첨 번호를 입력해주세요."
            )
            return null
        }

        if (bonusStr.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "보너스 번호를 입력해주세요."
            )
            return null
        }
        return true
    }

    private fun createLottoGame(winNumbers: List<Int>, bonus: Int): com.woowacourse.woowacollectionapp.lotto.domain.LottoGame {
        val winLotto = Lotto(winNumbers)
        return com.woowacourse.woowacollectionapp.lotto.domain.LottoGame(_uiState.value.lottos, winLotto, bonus)
    }

    fun reset() {
        _uiState.value = LottoUiState()
    }
}

