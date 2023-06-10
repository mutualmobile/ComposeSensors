package com.mutualmobile.composesensors

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * A low latency off body detect sensor that returns an event every time the device transitions from off-body to on-body and from on-body to off-body (e.g. a wearable device being removed from the wrist would trigger an event indicating an off-body transition).
 * This sensor deliver the initial on-body or off-body event representing the current device state within 5 seconds of activating the sensor.
 * This sensor must be able to detect and report an on-body to off-body transition within 1 second of the device being removed from the body, and must be able to detect and report an off-body to on-body transition within 5 seconds of the device being put back onto the body.
 * @param isDeviceOnBody This sensor produces only two values true and false where true means currently device is in on-body state and false means device is in off-body state and Defaults set to false.
 * @param isAvailable Whether the current device has an low latency off body detect sensor. Defaults to false.
 * @param accuracy Accuracy factor of the low latency off body detect sensor. Defaults to 0.
 */
@Immutable
class LowLatencyOffBodyDetectSensorState internal constructor(
    val isDeviceOnBody: Boolean = false,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
    private val startListeningEvents: (() -> Unit)? = null,
    private val stopListeningEvents: (() -> Unit)? = null
) : SensorStateListener {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LowLatencyOffBodyDetectSensorState) return false

        if (isDeviceOnBody != other.isDeviceOnBody) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false
        if (startListeningEvents != other.startListeningEvents) return false
        if (stopListeningEvents != other.stopListeningEvents) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isDeviceOnBody.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + startListeningEvents.hashCode()
        result = 31 * result + stopListeningEvents.hashCode()
        return result
    }

    override fun toString(): String {
        return "LowLatencyOffBodyDetectSensorState(isDeviceOnBody=$isDeviceOnBody," +
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
 * Creates and [remember]s an instance of [LowLatencyOffBodyDetectSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun rememberLowLatencyOffBodyDetectSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {}
): LowLatencyOffBodyDetectSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.LowLatencyOffBodyDetect,
        sensorDelay = sensorDelay,
        onError = onError
    )
    val lowLatencyOffBodyDetectSensorState =
        remember { mutableStateOf(LowLatencyOffBodyDetectSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                lowLatencyOffBodyDetectSensorState.value = LowLatencyOffBodyDetectSensorState(
                    isDeviceOnBody = sensorStateValues[0].toInt() == 1,
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy,
                    startListeningEvents = sensorState::startListening,
                    stopListeningEvents = sensorState::stopListening
                )
            }
        }
    )

    return lowLatencyOffBodyDetectSensorState.value
}
