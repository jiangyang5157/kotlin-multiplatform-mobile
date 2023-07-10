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
    startRoute: String = MainNavDestRoute.SudokuPuzzle.name,
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
            MainNavDestRoute.SudokuPuzzle.name,
            arguments = listOf(
                navArgument(MainNavDestRoute.SudokuPuzzle.argBlockMode) {
                    type = NavType.StringType
                    defaultValue = "square"
                },
                navArgument(MainNavDestRoute.SudokuPuzzle.argLength) {
                    type = NavType.IntType
                    defaultValue = 9
                },
            ),
        ) { entry ->
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
    }

    object SudokuPuzzle : MainNavDestRoute {
        override val name: String = "SudokuPuzzle"
        val argBlockMode = "BlockMode"
        val argLength = "Length"
    }
}

class MainNavActions(private val navController: NavHostController) {

    fun navigateToSudokuBuilder() {
        navController.navigate(MainNavDestRoute.SudokuBuilder.name)
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