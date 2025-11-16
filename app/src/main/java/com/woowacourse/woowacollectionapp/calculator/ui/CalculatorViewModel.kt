package com.woowacourse.woowacollectionapp.calculator.ui

import androidx.lifecycle.ViewModel
import com.woowacourse.woowacollectionapp.calculator.domain.Calculator
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
            _uiState.value = _uiState.value.copy(
                result = "결과: $result",
                errorMessage = null
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                errorMessage = e.message ?: "계산 중 오류가 발생했습니다.",
                result = ""
            )
        }
    }

    fun clear() {
        _uiState.value = CalculatorUiState()
    }
}

