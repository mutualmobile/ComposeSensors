package com.mutualmobile.composesensors

import android.hardware.Sensor
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * The Stationary Detect sensor ([Sensor.TYPE_MOTION_DETECT]) is used to detect whether the
 * device is stationary or in motion. This sensor produces an event if the device has been in motion
 * for at least 5 seconds with a maximal latency of 5 additional seconds. ie: it may take up
 * anywhere from 5 to 10 seconds after the device has been at rest to trigger this event. The only
 * allowed value is 1.0 (true).
 *
 * For more info, please refer the [Android Documentation Reference](https://developer.android.com/reference/android/hardware/SensorEvent#sensor.type_motion_detect:)
 *
 * @param isDeviceInMotion Whether the device is in motion or not. Defaults to false.
 * @param isAvailable Whether the current device has a heart beat sensor. Defaults to false.
 * @param accuracy Accuracy factor of the heart beat sensor. Defaults to 0.
 */
@RequiresApi(Build.VERSION_CODES.N)
@Immutable
class MotionDetectSensorState internal constructor(
    val isDeviceInMotion: Boolean = false,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
    private val startListeningEvents: (() -> Unit)? = null,
    private val stopListeningEvents: (() -> Unit)? = null,
) : SensorStateListener {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MotionDetectSensorState) return false

        if (isDeviceInMotion != other.isDeviceInMotion) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false
        if (startListeningEvents != other.startListeningEvents) return false
        if (stopListeningEvents != other.stopListeningEvents) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isDeviceInMotion.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + startListeningEvents.hashCode()
        result = 31 * result + stopListeningEvents.hashCode()
        return result
    }

    override fun toString(): String {
        return "MotionDetectSensorState(isDeviceInMotion=$isDeviceInMotion," +
                " isAvailable=$isAvailable, accuracy=$accuracy)"
    }

    override fun startListening() {
        startListeningEvents?.invoke()
    }

    override fun stopListening() {
        stopListeningEvents?.invoke()
    }
}

/**
 * Creates and [remember]s an instance of [MotionDetectSensorState].
 * @param autoStart Start listening to sensor events as soon as sensor state is initialised.
 * Defaults to true.
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun rememberMotionDetectSensorState(
    autoStart: Boolean = true,
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): MotionDetectSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.MotionDetect,
        sensorDelay = sensorDelay,
        autoStart = autoStart,
        onError = onError
    )
    val confidenceSensorState = remember {
        mutableStateOf(
            MotionDetectSensorState(
                startListeningEvents = sensorState::startListening,
                stopListeningEvents = sensorState::stopListening
            )
        )
    }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                confidenceSensorState.value = MotionDetectSensorState(
                    isDeviceInMotion = sensorStateValues[0] == 1f,
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy,
                    startListeningEvents = sensorState::startListening,
                    stopListeningEvents = sensorState::stopListening
                )
            }
        }
    )

    return confidenceSensorState.value
}
