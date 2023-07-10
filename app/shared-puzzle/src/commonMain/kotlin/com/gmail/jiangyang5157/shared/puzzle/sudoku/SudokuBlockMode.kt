package com.gmail.jiangyang5157.shared.puzzle.sudoku

import com.gmail.jiangyang5157.shared.common.ext.EnumWithKey

enum class SudokuBlockMode(val id: String) {
    Square("Square"),
    Irregular("Irregular");

    companion object : EnumWithKey<SudokuBlockMode, String> {
        override val SudokuBlockMode.key: String
            get() = id
    }
}
