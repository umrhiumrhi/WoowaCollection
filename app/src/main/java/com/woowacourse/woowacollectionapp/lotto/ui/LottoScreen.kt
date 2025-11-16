package com.woowacourse.woowacollectionapp.lotto.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import com.woowacourse.woowacollectionapp.lotto.domain.Lotto
import com.woowacourse.woowacollectionapp.lotto.domain.LottoConstants
import com.woowacourse.woowacollectionapp.lotto.domain.LottoRank
import com.woowacourse.woowacollectionapp.lotto.domain.ProfitCalculator
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LottoScreen(
    onNavigateBack: () -> Unit,
    viewModel: LottoViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "로또",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text(
                            "←",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
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
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            when (uiState.currentStep) {
                LottoStep.PURCHASE -> {
                    PurchaseSection(
                        purchaseMoney = uiState.purchaseMoney,
                        onPurchaseMoneyChange = viewModel::setPurchaseMoney,
                        onPurchase = viewModel::purchaseLottos,
                        errorMessage = uiState.errorMessage
                    )
                }
                LottoStep.WIN_NUMBERS -> {
                    WinNumbersSection(
                        lottos = uiState.lottos,
                        winNumbers = uiState.winNumbers,
                        bonusNumber = uiState.bonusNumber,
                        isLottosConfirmed = uiState.isLottosConfirmed,
                        isWinNumbersValidated = uiState.isWinNumbersValidated,
                        onConfirmLottos = viewModel::confirmLottos,
                        onWinNumbersChange = viewModel::setWinNumbers,
                        onBonusNumberChange = viewModel::setBonusNumber,
                        onValidateWinNumbers = viewModel::validateWinNumbers,
                        onCalculate = viewModel::calculateResult,
                        errorMessage = uiState.errorMessage
                    )
                }
                LottoStep.RESULT -> {
                    ResultSection(
                        game = uiState.game!!,
                        lottos = uiState.lottos,
                        onReset = viewModel::reset
                    )
                }
            }
        }
    }
}

@Composable
fun PurchaseSection(
    purchaseMoney: String,
    onPurchaseMoneyChange: (String) -> Unit,
    onPurchase: () -> Unit,
    errorMessage: String?
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "구입 금액을 입력해주세요",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = purchaseMoney,
            onValueChange = onPurchaseMoneyChange,
            label = {
                Text("구입 금액 (${LottoConstants.LOTTO_PRICE}원 단위, 최대 ${LottoConstants.MAX_PURCHASE_MONEY}원)")
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("예: 5000") }
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedButton(
            onClick = onPurchase,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            border = BorderStroke(
                1.5.dp,
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
            ),
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                "구매하기",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.3.sp
            )
        }
    }
}

@Composable
fun WinNumbersSection(
    lottos: List<Lotto>,
    winNumbers: String,
    bonusNumber: String,
    isLottosConfirmed: Boolean,
    isWinNumbersValidated: Boolean,
    onConfirmLottos: () -> Unit,
    onWinNumbersChange: (String) -> Unit,
    onBonusNumberChange: (String) -> Unit,
    onValidateWinNumbers: () -> Unit,
    onCalculate: () -> Unit,
    errorMessage: String?
) {
    LaunchedEffect(lottos.size) {
        if (lottos.isNotEmpty() && !isLottosConfirmed) {
            delay(500)
            onConfirmLottos()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AnimatedVisibility(
            visible = lottos.isNotEmpty(),
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "${lottos.size}개를 구매했습니다.",
                    style = MaterialTheme.typography.titleMedium
                )

                lottos.forEachIndexed { index, lotto ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically(
                            initialOffsetY = { it / 2 }
                        ),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                            ),
                            border = BorderStroke(
                                0.5.dp,
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f)
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${index + 1}번:",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                lotto.getNumbers().sorted().forEach { number ->
                                    NumberChip(
                                        number = number,
                                        isMatched = false,
                                        isBonus = false
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = isLottosConfirmed,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Text(
                    text = "당첨 번호를 입력해주세요",
                    style = MaterialTheme.typography.titleLarge
                )

                OutlinedTextField(
                    value = winNumbers,
                    onValueChange = onWinNumbersChange,
                    label = { Text("당첨 번호 (쉼표로 구분)") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("예: 1,2,3,4,5,6") }
                )

                if (errorMessage != null && !isWinNumbersValidated) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                if (!isWinNumbersValidated) {
                    OutlinedButton(
                        onClick = onValidateWinNumbers,
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        border = BorderStroke(
                            1.5.dp,
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                        ),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text(
                            "확인",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.3.sp
                        )
                    }
                }

                AnimatedVisibility(
                    visible = isWinNumbersValidated,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "보너스 번호를 입력해주세요",
                            style = MaterialTheme.typography.titleLarge
                        )

                        OutlinedTextField(
                            value = bonusNumber,
                            onValueChange = onBonusNumberChange,
                            label = { Text("보너스 번호") },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("예: 7") }
                        )

                        if (errorMessage != null) {
                            Text(
                                text = errorMessage,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        OutlinedButton(
                            onClick = onCalculate,
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            border = BorderStroke(
                                1.5.dp,
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                            ),
                            shape = MaterialTheme.shapes.large
                        ) {
                            Text(
                                "결과 확인",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.3.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NumberChip(
    number: Int,
    isMatched: Boolean,
    isBonus: Boolean
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = when {
            isBonus -> MaterialTheme.colorScheme.onSurface
            isMatched -> MaterialTheme.colorScheme.onSurface
            else -> MaterialTheme.colorScheme.surfaceVariant
        },
        border = when {
            isBonus || isMatched -> BorderStroke(
                2.dp,
                MaterialTheme.colorScheme.onSurface
            )
            else -> null
        },
        modifier = Modifier.padding(2.dp)
    ) {
        Text(
            text = "$number",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = when {
                isBonus || isMatched -> MaterialTheme.colorScheme.surface
                else -> MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
    }
}

@Composable
fun ResultSection(
    game: com.woowacourse.woowacollectionapp.lotto.domain.LottoGame,
    lottos: List<Lotto>,
    onReset: () -> Unit
) {
    val lottoResults = game.lottoResults
    val purchaseMoney = lottos.size * Lotto.PRICE
    val totalPrize = game.rankCount.entries.sumOf { it.key.money * it.value }
    val rate = ProfitCalculator.calculate(
        game.rankCount,
        lottos.size
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "당첨 결과",
            style = MaterialTheme.typography.headlineMedium
        )

        HorizontalDivider()

        Text(
            text = "당첨 번호",
            style = MaterialTheme.typography.titleMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            border = BorderStroke(
                0.5.dp,
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.15f)
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                game.winNumbers.getNumbers().sorted().forEach { number ->
                    NumberChip(
                        number = number,
                        isMatched = false,
                        isBonus = false
                    )
                }
                Text(
                    text = "+",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                NumberChip(
                    number = game.bonusNumber,
                    isMatched = false,
                    isBonus = true
                )
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = "내 로또 번호별 당첨 결과",
            style = MaterialTheme.typography.titleMedium
        )

        lottoResults.forEachIndexed { index, result ->
            val myNumbers = result.lotto.getNumbers()
            val winNumbers = game.winNumbers.getNumbers()
            val matchedNumbers = myNumbers.filter { it in winNumbers }

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${index + 1}번",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = if (result.rank != LottoRank.NOTHING) {
                                "${result.rank.desc}"
                            } else {
                                "낙첨"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = if (result.rank != LottoRank.NOTHING) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        myNumbers.sorted().forEach { number ->
                            val isMatched = number in matchedNumbers
                            val isBonus = number == game.bonusNumber && game.bonusNumber in myNumbers
                            NumberChip(
                                number = number,
                                isMatched = isMatched,
                                isBonus = isBonus
                            )
                        }
                    }
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = "당첨 통계",
            style = MaterialTheme.typography.titleMedium
        )

        LottoRank.entries.filter { it != LottoRank.NOTHING }.reversed()
            .forEach { rank ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(rank.desc)
                    Text("${game.rankCount[rank] ?: 0}개")
                }
            }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = "수익률 계산",
            style = MaterialTheme.typography.titleMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            border = BorderStroke(
                0.5.dp,
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.15f)
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("쓴 돈:")
                    Text(
                        "${String.format("%,d", purchaseMoney)}원",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("얻은 돈:")
                    Text(
                        "${String.format("%,d", totalPrize)}원",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                HorizontalDivider()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("수익률:")
                    Text(
                        "${"%.1f".format(rate)}%",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = "수익률 = (얻은 돈 ÷ 쓴 돈) × 100",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
}
