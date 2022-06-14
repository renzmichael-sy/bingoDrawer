package com.sy.renz.bingo.ui.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sy.renz.bingo.pattern_add_edit.PatternAddEditScreen
import com.sy.renz.bingo.ui.history.HistoryScreen
import com.sy.renz.bingo.ui.main_bingo.MainBingoScreen
import com.sy.renz.bingo.ui.pattern_list.PatternListScreen
import com.sy.renz.bingo.ui.theme.*
import com.sy.renz.bingo.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BingoTheme() {
                val navController = rememberNavController()
                NavHost(navController = navController,
                    startDestination = Routes.MAIN
                ) {
                    composable(Routes.MAIN) {
                        MainBingoScreen(
                        onNavigate = {
                            navController.navigate(it.route)
                        }
                    )
                    }
                    composable(
                        route = Routes.ADD_EDIT_PATTERN + "?pattern={pattern}",
                        arguments = listOf(
                            navArgument(name= "pattern") {
                                type = NavType.StringType
                                defaultValue = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0"
                            }
                        )
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
                            }
                        )
                    }
                    composable(Routes.PATTERN_LIST){
                        PatternListScreen(
                            onPopBackStack = {
                                navController.popBackStack()
                            },
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                }
            }
        }
    }
}