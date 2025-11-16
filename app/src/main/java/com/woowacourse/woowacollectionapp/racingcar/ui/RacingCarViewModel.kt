package com.woowacourse.woowacollectionapp.racingcar.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class RacingCarUiState(
    val carNames: String = "",
    val tryCount: String = "",
    val currentRound: Int = 0,
    val currentRoundProgress: List<String> = emptyList(),
    val gameHistory: List<List<String>> = emptyList(),
    val winners: List<String> = emptyList(),
    val errorMessage: String? = null,
    val isGameStarted: Boolean = false,
    val isGameFinished: Boolean = false,
    val showHistory: Boolean = false
)

class RacingCarViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RacingCarUiState())
    val uiState: StateFlow<RacingCarUiState> = _uiState.asStateFlow()

    fun setCarNames(names: String) {
        // TODO: 입력값 처리 구현
    }

    fun setTryCount(count: String) {
        // TODO: 입력값 처리 구현
    }

    fun startGame() {
        // TODO: 게임 시작 로직 구현
    }

    fun resetGame() {
        // TODO: 게임 초기화 로직 구현
    }

    fun toggleHistory() {
        // TODO: 기록 토글 로직 구현
    }
}

