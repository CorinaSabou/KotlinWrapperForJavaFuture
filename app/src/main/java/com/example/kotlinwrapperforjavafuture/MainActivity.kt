package com.example.kotlinwrapperforjavafuture

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlinwrapperforjavafuture.ui.theme.KotlinWrapperForJavaFutureTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel = MainViewModel(DataRepository())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinWrapperForJavaFutureTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val writeEvents = viewModel.writeEvent.collectAsState()
                    Log(
                        lines = writeEvents.value,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Log(
    lines: List<String>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier
    ) {
        items(lines.size) { index ->
            Text(
                text = lines[index],
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinWrapperForJavaFutureTheme {
        Log(listOf("Android", "Second line"))
    }
}