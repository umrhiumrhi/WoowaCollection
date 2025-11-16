package com.woowacourse.woowacollectionapp.racingcar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowacourse.woowacollectionapp.racingcar.domain.Car
import com.woowacourse.woowacollectionapp.racingcar.domain.Game
import com.woowacourse.woowacollectionapp.racingcar.domain.GameValidator
import com.woowacourse.woowacollectionapp.racingcar.domain.RacingCarConstants
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
    private var autoPlayJob: Job? = null

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
            startAutoPlay()
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

    private fun startAutoPlay() {
        autoPlayJob?.cancel()
        autoPlayJob = viewModelScope.launch {
            repeat(totalRounds) { roundIndex ->
                playNextRound()
                if (roundIndex < totalRounds - 1) {
                    delay(RacingCarConstants.AUTO_PLAY_DELAY_MS)
                }
            }
            finishGame()
        }
    }

    private fun playNextRound() {
        val currentGame = game ?: return
        val currentRound = _uiState.value.currentRound

        if (currentRound >= totalRounds) {
            return
        }

        val progress = currentGame.play()
        val newHistory = updateGameHistory()

        _uiState.value = _uiState.value.copy(
            currentRound = currentRound + 1,
            currentRoundProgress = progress,
            gameHistory = newHistory
        )
    }

    private fun updateGameHistory(): List<List<String>> {
        val previousRound = _uiState.value.currentRoundProgress
        return if (previousRound.isNotEmpty()) {
            _uiState.value.gameHistory + listOf(previousRound)
        } else {
            _uiState.value.gameHistory
        }
    }

    private fun finishGame() {
        val currentGame = game ?: return
        val winners = currentGame.getWinners()
        val finalHistory = addFinalRoundToHistory()

        _uiState.value = _uiState.value.copy(
            winners = winners,
            isGameFinished = true,
            gameHistory = finalHistory
        )
    }

    private fun addFinalRoundToHistory(): List<List<String>> {
        val currentRoundProgress = _uiState.value.currentRoundProgress
        return if (currentRoundProgress.isNotEmpty()) {
            _uiState.value.gameHistory + listOf(currentRoundProgress)
        } else {
            _uiState.value.gameHistory
        }
    }

    fun resetGame() {
        autoPlayJob?.cancel()
        _uiState.value = RacingCarUiState()
        game = null
        totalRounds = 0
    }

    override fun onCleared() {
        super.onCleared()
        autoPlayJob?.cancel()
    }

    fun toggleHistory() {
        _uiState.value = _uiState.value.copy(
            showHistory = !_uiState.value.showHistory
        )
    }
}

