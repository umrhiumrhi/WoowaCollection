package com.woowacourse.woowacollectionapp.racingcar.ui

import androidx.lifecycle.ViewModel
import com.woowacourse.woowacollectionapp.racingcar.domain.Car
import com.woowacourse.woowacollectionapp.racingcar.domain.Game
import com.woowacourse.woowacollectionapp.racingcar.domain.GameValidator
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

    private var game: Game? = null
    private var totalRounds: Int = 0

    fun setCarNames(names: String) {
        _uiState.value = _uiState.value.copy(carNames = names, errorMessage = null)
    }

    fun setTryCount(count: String) {
        _uiState.value = _uiState.value.copy(tryCount = count, errorMessage = null)
    }

    fun startGame() {
        val names = _uiState.value.carNames
        val tryCount = _uiState.value.tryCount

        try {
            val nameList = parseCarNames(names)
            val count = tryCount.toIntOrNull()
            
            GameValidator.validateCarNames(nameList, names)
            GameValidator.validateTryCount(count)

            initializeGame(nameList, count!!)
        } catch (e: IllegalArgumentException) {
            _uiState.value = _uiState.value.copy(errorMessage = e.message)
        }
    }

    private fun parseCarNames(names: String): List<String> {
        return names.split(",")
            .map { it.trim() }
            .filter { it.isNotBlank() }
    }

    private fun initializeGame(nameList: List<String>, count: Int) {
        val cars = nameList.map { Car(it) }
        game = Game(cars)
        totalRounds = count

        _uiState.value = _uiState.value.copy(
            isGameStarted = true,
            currentRound = 0,
            currentRoundProgress = emptyList(),
            gameHistory = emptyList(),
            winners = emptyList(),
            isGameFinished = false,
            showHistory = false,
            errorMessage = null
        )
    }

    fun resetGame() {
        _uiState.value = RacingCarUiState()
        game = null
        totalRounds = 0
    }

    fun toggleHistory() {
        _uiState.value = _uiState.value.copy(
            showHistory = !_uiState.value.showHistory
        )
    }
}

