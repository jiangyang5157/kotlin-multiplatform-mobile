package com.gmail.jiangyang5157.demo_camera

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.gmail.jiangyang5157.demo_camera.ui.theme.AppTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            MyDialogFragment().show(
                supportFragmentManager,
                MyDialogFragment::class.simpleName,
            )
        }, 0)

//        setContent {
//            AppTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                ) {
//                    MainScreen()
//                }
//            }
//        }
    }
}

@Composable
fun MainScreen() {
    val showDialog = remember { mutableStateOf(true) }

    MyDialog(showDialog)
}

@Composable
fun MyCustomDialog(onDismissRequest: () -> Unit, content: @Composable () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        content()
    }
}

@Composable
fun MyDialog(showDialog: MutableState<Boolean>) {
    if (showDialog.value) {
        MyCustomDialog(onDismissRequest = { showDialog.value = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
//                    .fillMaxWidth()
//                    .heightIn(max = 200.dp)
                    .clip(RoundedCornerShape(16.dp)),
//                color = Color.Transparent,
                shape = RoundedCornerShape(16.dp),
            ) {
                SimpleCameraPreview()
            }
        }
    }
}
