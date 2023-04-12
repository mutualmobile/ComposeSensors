package com.mutualmobile.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mutualmobile.composesensors.SensorDelay
import com.mutualmobile.composesensors.rememberAccelerometerSensorState
import com.mutualmobile.composesensors.rememberMotionSensorState
import com.mutualmobile.composesensors.rememberRotationVectorSensorState
import com.mutualmobile.composesensors.rememberStationaryMotionSensorState
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
                    val stationaryMotionSensorState = rememberStationaryMotionSensorState(
                        onError = {
                            it.printStackTrace()
                        }
                    )

                    val motionDetectSensorState = rememberMotionSensorState(
                        onError = {
                            it.printStackTrace()
                        }
                    )

                    Text(
                        text = " Is StationaryMotionSensors Available : ${stationaryMotionSensorState.isAvailable} " +
                                " Is Device Stationary : ${stationaryMotionSensorState.isDeviceStationary} " +
                                " Is MotionSensor Available : ${motionDetectSensorState.isAvailable} " +
                                " Is Device in Motion : ${motionDetectSensorState.isDeviceInMotion} ",
                        modifier = Modifier.padding(16.dp)
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