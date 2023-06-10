package com.mutualmobile.composesensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

/**
 * Most Android-powered devices have built-in sensors that measure motion, orientation, and various
 * environmental conditions. These sensors are capable of providing raw data with high precision
 * and accuracy, and are useful if you want to monitor three-dimensional device movement or
 * positioning, or you want to monitor changes in the ambient environment near a device.
 *
 * SensorState provides the availability status and the current data about a sensor.
 * @param data Data from the sensor. Defaults to an empty list.
 * @param isAvailable Whether the required sensor is available in the current device. Defaults to
 * false.
 * @param accuracy Accuracy factor of the current sensor. Defaults to 0.
 */
@Immutable
internal class SensorState(
    val data: List<Float> = emptyList(),
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
    private val triggerEvent: () -> Unit,
    private val isListenerStarted: MutableState<Boolean>
) : SensorStateListener {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SensorState) return false

        if (data != other.data) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false
        if (triggerEvent != other.triggerEvent) return false
        if (isListenerStarted != other.isListenerStarted) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + triggerEvent.hashCode()
        result = 31 * result + isListenerStarted.hashCode()
        return result
    }

    override fun toString(): String {
        return "SensorState(isAvailable=$isAvailable, data=${data.joinToString()}, " +
            "accuracy=$accuracy)"
    }

    /**
     * Use this function to trigger the event request for sensors that emit One-Shot events (For
     * example: [SignificantMotionSensorState]).
     */
    internal fun requestEventTrigger() = triggerEvent()

    override fun startListening() {
        isListenerStarted.value = true
    }

    override fun stopListening() {
        isListenerStarted.value = false
    }
}

@Composable
internal fun rememberSensorState(
    sensorType: SensorType,
    sensorDelay: SensorDelay? = null,
    onError: (throwable: Throwable) -> Unit,
    onMotionEvent: (Long) -> Unit = {}
): SensorState {
    val isListenerStarted = remember { mutableStateOf(false) }
    val isSensorAvailable = sensorType.rememberIsSensorAvailable()
    val sensorData: MutableState<List<Float>> = remember { mutableStateOf(emptyList()) }
    val sensorAccuracy: MutableState<Int> = remember { mutableStateOf(0) }
    val sensorManager =
        LocalContext.current.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val sensorState = remember {
        derivedStateOf {
            SensorState(
                isAvailable = isSensorAvailable,
                data = sensorData.value,
                accuracy = sensorAccuracy.value,
                triggerEvent = {
                    runCatching {
                        val sensor =
                            sensorManager.getDefaultSensor(sensorType.toAndroidSensorType())
                                ?: throw SensorNotFoundException(sensorName = sensorType.name)

                        if (sensorType == SensorType.SignificantMotion) {
                            val triggerEventListener = object : TriggerEventListener() {
                                override fun onTrigger(event: TriggerEvent?) {
                                    onMotionEvent(event?.timestamp ?: 0L)
                                }
                            }
                            sensorManager.requestTriggerSensor(triggerEventListener, sensor)
                        }
                    }.onFailure(onError)
                },
                isListenerStarted = isListenerStarted
            )
        }
    }

    runCatching {
        val sensor = sensorManager.getDefaultSensor(sensorType.toAndroidSensorType())
            ?: throw SensorNotFoundException(sensorName = sensorType.name)

        DisposableEffect(
            key1 = sensor,
            key2 = sensorDelay,
            key3 = isListenerStarted,
            effect = {
                var sensorEventListener: SensorEventListener? = null
                if (isListenerStarted.value) {
                    sensorEventListener = object : SensorEventListener {
                        override fun onSensorChanged(event: SensorEvent?) {
                            event?.let { nnEvent ->
                                nnEvent.values?.let { nnValues ->
                                    sensorData.value = nnValues.toList()
                                }
                            }
                        }

                        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                            sensorAccuracy.value = accuracy
                        }
                    }

                    sensorManager.registerListener(
                        sensorEventListener,
                        sensor,
                        (sensorDelay ?: SensorDelay.Normal).toAndroidSensorDelay()
                    )
                }

                onDispose {
                    sensorManager.unregisterListener(sensorEventListener)
                }
            }
        )
    }.onFailure(onError)

    return sensorState.value
}

interface SensorStateListener {
    /**
     * Start listening to this sensor's values
     */
    fun startListening()

    /**
     * Stop listening to this sensor's values
     */
    fun stopListening()
}

private class SensorNotFoundException(sensorName: String) :
    Exception("The required sensor '$sensorName' was not found on the current device.")
