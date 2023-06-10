package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Measures a device's heading value in degrees (anti-clockwise),
 *
 * @param degrees Indicates direction in which the device is pointing relative to true north in
 * degrees. Defaults to 0f.
 * @param headingAccuracy Indicates the confidence of prediction, under Gaussian standard normal
 * distribution. Defaults to 0f.
 * @param isAvailable Whether the current device has a heading sensor. Defaults to false.
 * @param accuracy Accuracy factor of the heading sensor. Defaults to 0.
 */
@Immutable
class HeadingSensorState internal constructor(
    val degrees: Float = 0f,
    val headingAccuracy: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
    private val startListeningEvents: (() -> Unit)? = null,
    private val stopListeningEvents: (() -> Unit)? = null
) : SensorStateListener {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HeadingSensorState) return false

        if (degrees != other.degrees) return false
        if (headingAccuracy != other.headingAccuracy) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false
        if (startListeningEvents != other.startListeningEvents) return false
        if (stopListeningEvents != other.stopListeningEvents) return false

        return true
    }

    override fun hashCode(): Int {
        var result = degrees.hashCode()
        result = 31 * result + headingAccuracy.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + startListeningEvents.hashCode()
        result = 31 * result + stopListeningEvents.hashCode()
        return result
    }

    override fun toString(): String {
        return "HeadingSensorState(degrees=$degrees, headingAccuracy=$headingAccuracy, " +
            "isAvailable=$isAvailable, accuracy=$accuracy)"
    }

    override fun startListening() {
        startListeningEvents?.invoke()
    }

    override fun stopListening() {
        stopListeningEvents?.invoke()
    }
}

/**
 * Creates and [remember]s an instance of [HeadingSensorState].
 *
 * @param sensorDelay The rate at which the raw sensor data should be
 *     received. Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberHeadingSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {}
): HeadingSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.Heading,
        sensorDelay = sensorDelay,
        onError = onError
    )

    val headingSensorState = remember { mutableStateOf(HeadingSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                headingSensorState.value = HeadingSensorState(
                    degrees = sensorStateValues[0],
                    headingAccuracy = sensorStateValues[1],
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy,
                    startListeningEvents = sensorState::startListening,
                    stopListeningEvents = sensorState::stopListening
                )
            }
        }
    )

    return headingSensorState.value
}
