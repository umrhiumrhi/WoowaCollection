package com.woowacourse.woowacollectionapp.calculator.ui

import androidx.lifecycle.ViewModel
import com.woowacourse.woowacollectionapp.calculator.domain.Calculator
import com.woowacourse.woowacollectionapp.calculator.domain.CalculatorConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CalculatorHistory(
    val input: String,
    val result: Int
)

data class CalculatorUiState(
    val input: String = "",
    val result: String = "",
    val errorMessage: String? = null,
    val history: List<CalculatorHistory> = emptyList()
)

class CalculatorViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    private val calculator = Calculator()

    fun setInput(input: String) {
        _uiState.value = _uiState.value.copy(
            input = input,
            errorMessage = null,
            result = ""
        )
    }

    fun calculate() {
        val input = _uiState.value.input.trim()
        if (input.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "입력을 입력해주세요."
            )
            return
        }

        try {
            val result = calculator.calculate(input)
            val newHistory = updateHistory(input, result)
            _uiState.value = _uiState.value.copy(
                result = "결과: $result",
                errorMessage = null,
                history = newHistory
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                errorMessage = e.message ?: "계산 중 오류가 발생했습니다.",
                result = ""
            )
        }
    }

    private fun updateHistory(input: String, result: Int): List<CalculatorHistory> {
        return (_uiState.value.history + CalculatorHistory(input, result))
            .takeLast(CalculatorConstants.MAX_HISTORY_COUNT)
    }

    fun clear() {
        _uiState.value = CalculatorUiState()
    }
}

