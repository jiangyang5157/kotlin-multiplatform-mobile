package com.gmail.jiangyang5157.demo_sudoku

import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gmail.jiangyang5157.demo_sudoku.ui.SudokuBuilderView
import com.gmail.jiangyang5157.demo_sudoku.ui.SudokuPuzzleView
import com.gmail.jiangyang5157.shared.common.ext.getByKey
import com.gmail.jiangyang5157.shared.puzzle.sudoku.SudokuBlockMode
import com.gmail.jiangyang5157.shared.puzzle.sudoku.SudokuBlockMode.Companion.key
import com.gmail.jiangyang5157.shared.puzzle.sudoku.SudokuTerminal
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    startRoute: String = MainNavDestRoute.SudokuBuilder.name,
    navActions: MainNavActions = remember(navController) {
        MainNavActions(navController)
    },
) {
    val currentEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentEntry?.destination?.route ?: startRoute
    Log.d("####", "currentRoute=$currentRoute")

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startRoute,
    ) {
        composable(
            MainNavDestRoute.SudokuBuilder.name,
            arguments = listOf(
                navArgument(MainNavDestRoute.SudokuBuilder.argBlockMode) {
                    type = NavType.StringType
                    defaultValue = SudokuBlockMode.Square.id
                },
                navArgument(MainNavDestRoute.SudokuBuilder.argLength) {
                    type = NavType.IntType
                    defaultValue = 9
                },
            ),
        ) { entry ->
            Log.d("####", "SudokuBuilder entry.arguments=${entry.arguments}")
            val startBlockMode = SudokuBlockMode.getByKey(
                entry.arguments?.getString(MainNavDestRoute.SudokuBuilder.argBlockMode)!!
            )!!
            val startLength = entry.arguments?.getInt(MainNavDestRoute.SudokuBuilder.argLength)!!

            SudokuBuilderView(
                startBlockMode = startBlockMode,
                startLength = startLength,
            ) { blockMode, length ->
                navActions.navigateToSudokuPuzzle(blockMode, length)
            }
        }
        composable(
            MainNavDestRoute.SudokuPuzzle.name,
            arguments = listOf(
                navArgument(MainNavDestRoute.SudokuPuzzle.argBlockMode) {
                    type = NavType.StringType
                    defaultValue = SudokuBlockMode.Square.id
                },
                navArgument(MainNavDestRoute.SudokuPuzzle.argLength) {
                    type = NavType.IntType
                    defaultValue = 9
                },
            ),
        ) { entry ->
            Log.d("####", "SudokuPuzzle entry.arguments=${entry.arguments}")
            val blockMode = SudokuBlockMode.getByKey(
                entry.arguments?.getString(MainNavDestRoute.SudokuPuzzle.argBlockMode)!!
            )!!
            val length = entry.arguments?.getInt(MainNavDestRoute.SudokuPuzzle.argLength)!!

            val terminal = SudokuTerminal.withUniqueSolution(
                blockMode = blockMode,
                length = length,
            )
            SudokuPuzzleView(text = terminal.toString())
        }
    }
}

interface MainNavDestRoute {
    val name: String

    object SudokuBuilder : MainNavDestRoute {
        override val name: String = "SudokuBuilder"
        val argBlockMode = "BlockMode"
        val argLength = "Length"
    }

    object SudokuPuzzle : MainNavDestRoute {
        override val name: String = "SudokuPuzzle"
        val argBlockMode = "BlockMode"
        val argLength = "Length"
    }
}

class MainNavActions(private val navController: NavHostController) {

    fun navigateToSudokuBuilder(
        blockMode: SudokuBlockMode = SudokuBlockMode.Square,
        length: Int = 9,
    ) {
        navController.navigate(
            MainNavDestRoute.SudokuBuilder.name +
                    "?${MainNavDestRoute.SudokuBuilder.argBlockMode}=${blockMode.key}" +
                    "?${MainNavDestRoute.SudokuBuilder.argLength}=${length}"
        )
    }

    fun navigateToSudokuPuzzle(
        blockMode: SudokuBlockMode = SudokuBlockMode.Square,
        length: Int = 9,
    ) {
        navController.navigate(
            MainNavDestRoute.SudokuPuzzle.name +
                    "?${MainNavDestRoute.SudokuPuzzle.argBlockMode}=${blockMode.key}" +
                    "?${MainNavDestRoute.SudokuPuzzle.argLength}=${length}"
        )
    }
}