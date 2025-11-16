package com.woowacourse.woowacollectionapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.woowacourse.woowacollectionapp.calculator.ui.CalculatorScreen
import com.woowacourse.woowacollectionapp.navigation.NavigationRoutes
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
            // 메인 메뉴 화면 (추후 구현)
        }
        composable(NavigationRoutes.CALCULATOR) {
            CalculatorScreen(
                onNavigateBack = {
                    navController.popBackStack(NavigationRoutes.MAIN, inclusive = false)
                }
            )
        }
        composable(NavigationRoutes.RACING_CAR) {
            // 레이싱카 화면 (추후 구현)
        }
        composable(NavigationRoutes.LOTTO) {
            // 로또 화면 (추후 구현)
        }
    }
}