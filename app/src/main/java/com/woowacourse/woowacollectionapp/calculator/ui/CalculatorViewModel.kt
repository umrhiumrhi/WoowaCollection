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
        // TODO: 입력값 처리 구현
    }

    fun calculate() {
        // TODO: 계산 로직 구현
    }

    fun clear() {
        // TODO: 초기화 로직 구현
    }
}

