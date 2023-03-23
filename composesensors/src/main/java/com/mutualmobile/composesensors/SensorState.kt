package com.mutualmobile.composesensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

interface SensorState {
    /**
     * States whether this sensor is available for the current device.
     */
    val isAvailable: Boolean @Composable get
}

@Composable
internal fun rememberSensorState(sensorType: SensorType): State<List<Float>> {
    val sensorData: MutableState<List<Float>> = remember { mutableStateOf(emptyList()) }

    val sensorManager =
        LocalContext.current.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    val sensor = sensorManager.getDefaultSensor(sensorType.toAndroidSensorType())
        ?: throw SensorNotFoundException(sensorName = sensorType.name)

    DisposableEffect(
        key1 = sensor,
        effect = {

            val sensorEventListener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    event?.let { nnEvent ->
                        nnEvent.values?.let { nnValues ->
                            sensorData.value = nnValues.toList()
                        }
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }

            sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )

            onDispose {
                sensorManager.unregisterListener(sensorEventListener)
            }
        }
    )

    return sensorData
}

private class SensorNotFoundException(sensorName: String) :
    Exception("The required sensor '$sensorName' was not found.")
