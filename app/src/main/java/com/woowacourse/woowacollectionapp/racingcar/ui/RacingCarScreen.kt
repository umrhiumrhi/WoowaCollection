package com.woowacourse.woowacollectionapp.racingcar.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RacingCarScreen(
    onNavigateBack: () -> Unit,
    viewModel: RacingCarViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "레이싱카",
                        style = MaterialTheme.typography.titleLarge
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text(
                            "←",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (!uiState.isGameStarted) {
                GameSetupSection(
                    carNames = uiState.carNames,
                    tryCount = uiState.tryCount,
                    onCarNamesChange = viewModel::setCarNames,
                    onTryCountChange = viewModel::setTryCount,
                    onStartGame = viewModel::startGame,
                    errorMessage = uiState.errorMessage
                )
            } else {
                GameProgressSection(
                    uiState = uiState,
                    onReset = viewModel::resetGame,
                    onToggleHistory = viewModel::toggleHistory
                )
            }
        }
    }
}

@Composable
fun GameSetupSection(
    carNames: String,
    tryCount: String,
    onCarNamesChange: (String) -> Unit,
    onTryCountChange: (String) -> Unit,
    onStartGame: () -> Unit,
    errorMessage: String?
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = carNames,
            onValueChange = onCarNamesChange,
            label = { Text("자동차 이름 (쉼표로 구분, 최대 10개)") },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("예: pobi,woni,jun") }
        )

        OutlinedTextField(
            value = tryCount,
            onValueChange = onTryCountChange,
            label = { Text("시도 횟수 (최대 20)") },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("예: 5") }
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedButton(
            onClick = onStartGame,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            border = BorderStroke(
                1.5.dp,
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
            ),
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                "게임 시작",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.3.sp
            )
        }
    }
}

@Composable
fun GameProgressSection(
    uiState: RacingCarUiState,
    onReset: () -> Unit,
    onToggleHistory: () -> Unit
) {
    val totalRounds = uiState.tryCount.toIntOrNull() ?: 0
    val currentRound = uiState.currentRound
    val scrollState = rememberScrollState()

    LaunchedEffect(uiState.currentRoundProgress) {
        if (uiState.currentRoundProgress.isNotEmpty() && !uiState.showHistory) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // TODO: 게임 진행 화면 구현
    }
}

