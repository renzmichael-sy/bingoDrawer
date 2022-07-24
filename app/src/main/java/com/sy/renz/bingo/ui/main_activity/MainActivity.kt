package com.sy.renz.bingo.ui.main_activity

import android.os.Bundle
import android.window.SplashScreenView
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sy.renz.bingo.ui.pattern_add_edit.PatternAddEditScreen
import com.sy.renz.bingo.ui.history.HistoryScreen
import com.sy.renz.bingo.ui.main_bingo.MainBingoScreen
import com.sy.renz.bingo.ui.main_bingo.MainViewModel
import com.sy.renz.bingo.ui.pattern_list.PatternListScreen
import com.sy.renz.bingo.ui.settings_screen.SettingsScreen
import com.sy.renz.bingo.ui.splash_screen.SplashScreen
import com.sy.renz.bingo.ui.theme.*
import com.sy.renz.bingo.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BingoTheme {
                val viewModel: MainViewModel = viewModel()
                val navController = rememberNavController()
                NavHost(navController = navController,
                    startDestination = Routes.SPLASH_SCREEN
                ) {
                    composable(Routes.SPLASH_SCREEN) {
                        SplashScreen(
                            onNavigate= {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(Routes.MAIN) {
                        BackHandler(true) {

                        }
                        MainBingoScreen(
                        onNavigate = {
                            navController.navigate(it.route)
                        },
                        viewModel
                    )
                    }
                    composable(
                        route = Routes.ADD_EDIT_PATTERN + "?patternId={patternId}",
                        arguments = listOf(navArgument("patternId"){
                            type = NavType.IntType
                            defaultValue = -1
                        })
                    ){
                        PatternAddEditScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                    composable(Routes.HISTORY){
                        HistoryScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            },
                            onPopBackStack = {
                                navController.popBackStack()
                            },
                            viewModel
                        )
                    }
                    composable(Routes.PATTERN_LIST){
                        PatternListScreen(
                            onPopBackStack = {
                                navController.popBackStack()
                            },
                            onNavigate = {
                                navController.navigate(it.route)
                            },
                            mainViewModel = viewModel
                        )
                    }

                    composable(
                        route =Routes.SETTINGS + "?settingsId={settingsId}",
                        arguments = listOf(navArgument("settingsId"){
                            type = NavType.LongType
                            defaultValue = -1
                        })) {
                        SettingsScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}