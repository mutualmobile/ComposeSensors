package com.mutualmobile.sample

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.mutualmobile.composesensors.rememberAccelerometerSensorState
import com.mutualmobile.composesensors.rememberStepCounterSensorState
import com.mutualmobile.sample.ui.theme.ComposeSensorsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION),
                0,
            )
        }
        setContent {
            ComposeSensorsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val accelerometerState = rememberAccelerometerSensorState()

                    // ACTIVITY_RECOGNITION permission must be granted to fetch step counts
                    val stepCounterSensorState = rememberStepCounterSensorState()

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Force X: ${accelerometerState.xForce}" +
                                "\nForce Y: ${accelerometerState.yForce}" +
                                "\nForce Z: ${accelerometerState.zForce}" +
                                "\nIs Available?: ${accelerometerState.isAvailable}," +
                                "\nAccuracy?: ${accelerometerState.accuracy}",
                        )
                        Text(text = "No Of Steps: ${stepCounterSensorState.noOfSteps}")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeSensorsTheme {
        Greeting("Android")
    }
}
