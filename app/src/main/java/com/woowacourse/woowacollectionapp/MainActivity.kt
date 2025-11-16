package com.woowacourse.woowacollectionapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.woowacourse.woowacollectionapp.R
import com.woowacourse.woowacollectionapp.calculator.ui.CalculatorScreen
import com.woowacourse.woowacollectionapp.lotto.ui.LottoScreen
import com.woowacourse.woowacollectionapp.navigation.NavigationRoutes
import com.woowacourse.woowacollectionapp.racingcar.ui.RacingCarScreen
import com.woowacourse.woowacollectionapp.ui.theme.WoowaCollectionAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WoowaCollectionAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.MAIN
    ) {
        composable(NavigationRoutes.MAIN) {
            MainMenuScreen(
                onNavigateToRacingCar = { navController.navigate(NavigationRoutes.RACING_CAR) },
                onNavigateToCalculator = { navController.navigate(NavigationRoutes.CALCULATOR) },
                onNavigateToLotto = { navController.navigate(NavigationRoutes.LOTTO) }
            )
        }
        composable(NavigationRoutes.CALCULATOR) {
            CalculatorScreen(
                onNavigateBack = {
                    navController.popBackStack(NavigationRoutes.MAIN, inclusive = false)
                }
            )
        }
        composable(NavigationRoutes.RACING_CAR) {
            RacingCarScreen(
                onNavigateBack = {
                    navController.popBackStack(NavigationRoutes.MAIN, inclusive = false)
                }
            )
        }
        composable(NavigationRoutes.LOTTO) {
            LottoScreen(
                onNavigateBack = {
                    navController.popBackStack(NavigationRoutes.MAIN, inclusive = false)
                }
            )
        }
    }
}

@Composable
fun MainMenuScreen(
    onNavigateToRacingCar: () -> Unit,
    onNavigateToCalculator: () -> Unit,
    onNavigateToLotto: () -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "우아한테크코스 로고",
                    modifier = Modifier.size(60.dp)
                )
                
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "우아한테크코스",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.3).sp
                    )
                    Text(
                        text = "프리코스 몰아보기",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        letterSpacing = 0.3.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                MenuButton(
                    text = "계산기",
                    onClick = onNavigateToCalculator
                )

                MenuButton(
                    text = "레이싱카",
                    onClick = onNavigateToRacingCar
                )

                MenuButton(
                    text = "로또",
                    onClick = onNavigateToLotto
                )
            }
        }
    }
}

@Composable
fun MenuButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(68.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(
            1.5.dp,
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.2.sp
        )
    }
}