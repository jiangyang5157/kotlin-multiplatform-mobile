package com.gmail.jiangyang5157.demo_sudoku.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.gmail.jiangyang5157.demo_sudoku.ui.theme.AppTheme

@Composable
fun SudokuPuzzleView(
    text: String,
) {
    Text(text = text)
}

@Preview(showBackground = true)
@Composable
private fun SudokuPuzzleView_Preview() {
    AppTheme {
        SudokuPuzzleView("Hello Android!")
    }
}