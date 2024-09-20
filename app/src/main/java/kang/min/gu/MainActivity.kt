package kang.min.gu

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import kang.min.gu.ui.theme.MVI_SampleTheme
import kang.min.gu.utils.rememberFlowWithLifecycle

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVI_SampleTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var textField by remember { mutableStateOf("") }

    val state by mainViewModel.state.collectAsStateWithLifecycle()
    val effect = rememberFlowWithLifecycle(mainViewModel.effect)

    LaunchedEffect(effect) {
        effect.collect { action ->
            when (action) {
                is MainScreenReducer.MainScreenEffect.ShowToast -> {
                    Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                TextField(
                    value = textField,
                    onValueChange = { textField = it },
                    label = { Text("텍스트 입력") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus(true)
                        }
                    )
                )

                if (state.isShowSavedText) {
                    Text("현재 저장된 값은 \"${state.storageText}\" 입니다.")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { mainViewModel.saveText(textField) }
                    ) {
                        Text("저장")
                    }

                    Button(
                        onClick = { mainViewModel.loadText() }
                    ) {
                        Text("현재 저장된 값 표시")
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun MainScreenPreview() {
    MVI_SampleTheme {
        MainScreen()
    }
}