package com.gmail.jiangyang5157.demo_sudoku.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.jiangyang5157.demo_sudoku.ui.theme.AppTheme
import com.gmail.jiangyang5157.shared.puzzle.sudoku.SudokuBlockMode
import com.gmail.jiangyang5157.shared.puzzle.sudoku.SudokuBlockMode.Companion.key

@Composable
fun SudokuBuilderView(
    blockModes: Array<SudokuBlockMode> = SudokuBlockMode.values(),
    startBlockMode: SudokuBlockMode = SudokuBlockMode.Square,
    lengths: Array<Int> = arrayOf(9, 16),
    startLength: Int = 9,
    onBuildClicked: (blockMode: SudokuBlockMode, length: Int) -> Unit,
) {
    val (selectedBlockMode, onBlockModeSelected) = remember { mutableStateOf(startBlockMode) }
    val (selectedLength, onLengthSelected) = remember { mutableStateOf(startLength) }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = "Block mode:")
        blockModes.forEach { blockMode ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (blockMode == selectedBlockMode),
                        onClick = {
                            onBlockModeSelected(blockMode)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = (blockMode == selectedBlockMode),
                    onClick = { onBlockModeSelected(blockMode) }
                )
                Text(text = blockMode.key)
            }
        }

        Text(text = "Length:")
        lengths.forEach { length ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (length == selectedLength),
                        onClick = {
                            onLengthSelected(length)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = (length == selectedLength),
                    onClick = { onLengthSelected(length) }
                )
                Text(text = "$length")
            }
        }

        Button(onClick = {
            onBuildClicked(selectedBlockMode, selectedLength)
        }) {
            Text(text = "Build")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SudokuBuilderView_Preview() {
    AppTheme {
        SudokuBuilderView { blockMode, length ->

        }
    }
}