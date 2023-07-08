package com.gmail.jiangyang5157.demo_sudoku

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
import com.gmail.jiangyang5157.demo_sudoku.ui.SudokuView
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
    startRoute: String = MainNavDestRoute.Sudoku,
    navActions: MainNavActions = remember(navController) {
        MainNavActions(navController)
    },
) {
    val currentEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentEntry?.destination?.route ?: startRoute

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startRoute,
    ) {
        composable(
            MainNavDestRoute.Sudoku,
            arguments = listOf(
                navArgument(MainNavDestArg.blockMode) {
                    type = NavType.StringType
                    defaultValue = "square"
                },
                navArgument(MainNavDestArg.length) {
                    type = NavType.IntType
                    defaultValue = 9
                },
            ),
        ) { entry ->
            val blockMode = SudokuBlockMode.getByKey(
                entry.arguments?.getString(MainNavDestArg.blockMode)!!
            )!!
            val length = entry.arguments?.getInt(MainNavDestArg.length)!!

            val terminal = SudokuTerminal.withUniqueSolution(
                blockMode = blockMode,
                length = length,
            )
            SudokuView(text = terminal.toString())
        }
    }
}

object MainNavDestArg {
    const val blockMode = "blockMode"
    const val length = "length"
    const val minTotalGiven = "minTotalGiven"
    const val minSubGiven = "minSubGiven"
}

object MainNavDestRoute {
    const val Sudoku = "soduku"
}

class MainNavActions(private val navController: NavHostController) {

    fun navigateToSudoku(
        blockMode: SudokuBlockMode = SudokuBlockMode.Square,
        length: Int = 9,
    ) {
        navController.navigate(MainNavDestRoute.Sudoku.let {
            it +
                    "?${MainNavDestArg.blockMode}=${blockMode.key}" +
                    "?${MainNavDestArg.length}=${length}"
        }) {
            // TODO YangJ ?
        }
    }
}