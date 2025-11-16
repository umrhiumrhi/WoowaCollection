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
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text(
                            "←",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = !uiState.showHistory,
                onClick = { if (uiState.showHistory) onToggleHistory() },
                label = { Text("실행 결과") },
                modifier = Modifier.weight(1f),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.onSurface,
                    selectedLabelColor = MaterialTheme.colorScheme.surface,
                    containerColor = MaterialTheme.colorScheme.surface,
                    labelColor = MaterialTheme.colorScheme.onSurface
                ),
                border = if (!uiState.showHistory) null else BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.onSurface
                )
            )
            FilterChip(
                selected = uiState.showHistory,
                onClick = { if (!uiState.showHistory) onToggleHistory() },
                label = { Text("경기 기록") },
                modifier = Modifier.weight(1f),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.onSurface,
                    selectedLabelColor = MaterialTheme.colorScheme.surface,
                    containerColor = MaterialTheme.colorScheme.surface,
                    labelColor = MaterialTheme.colorScheme.onSurface
                ),
                border = if (uiState.showHistory) null else BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.onSurface
                )
            )
        }

        if (!uiState.showHistory) {
            if (currentRound > 0 && currentRound <= totalRounds) {
                Text(
                    text = if (uiState.isGameFinished) {
                        "경기 종료!"
                    } else {
                        "진행 중... (${currentRound}/${totalRounds})"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            if (uiState.currentRoundProgress.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    ),
                    border = BorderStroke(
                        0.5.dp,
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f)
                    ),
                    shape = MaterialTheme.shapes.large
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "${currentRound}회차",
                            style = MaterialTheme.typography.titleMedium
                        )
                        uiState.currentRoundProgress.forEach { progress ->
                            Text(progress)
                        }
                    }
                }
            }

            if (uiState.isGameFinished) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    ),
                    border = BorderStroke(
                        0.5.dp,
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f)
                    ),
                    shape = MaterialTheme.shapes.large
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "최종 우승자: ${uiState.winners.joinToString(", ")}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.2).sp
                        )
                    }
                }

                OutlinedButton(
                    onClick = onReset,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    border = BorderStroke(
                        1.5.dp,
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    ),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(
                        "다시 시작",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.3.sp
                    )
                }
            }
        } else {
            if (uiState.gameHistory.isEmpty()) {
                Text(
                    text = "경기 기록이 없습니다.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                uiState.gameHistory.forEachIndexed { index, round ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        border = BorderStroke(
                            0.5.dp,
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.15f)
                        ),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "${index + 1}회차",
                                style = MaterialTheme.typography.titleMedium
                            )
                            round.forEach { progress ->
                                Text(progress)
                            }
                        }
                    }
                }
            }
        }
    }
}

