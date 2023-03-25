package com.mutualmobile.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mutualmobile.composesensors.rememberAccelerometerSensorState
import com.mutualmobile.sample.ui.theme.ComposeSensorsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSensorsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val accelerometerState = rememberAccelerometerSensorState()
                    Text(
                        text = "Force X: ${accelerometerState.xForce}" +
                                "\nForce Y: ${accelerometerState.yForce}" +
                                "\nForce Z: ${accelerometerState.zForce}" +
                                "\nIs Available?: ${accelerometerState.isAvailable}," +
                                "\nAccuracy?: ${accelerometerState.accuracy}"
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeSensorsTheme {
        Greeting("Android")
    }
}