package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


/**
 * Measures a device's heading value in degrees (anti-clockwise),
 * @param degrees Indicates direction in which the device is pointing relative to true north in degrees. Defaults to 0f.
 * @param confidence Indicates the confidence of prediction, under Gaussian standard normal distribution. Defaults to 0f.
 * @param isAvailable Whether the current device has a heading sensor. Defaults to false.
 */
@Immutable
class HeadingSensorState(
    val degrees: Float = 0f,
    val confidence: Float = 0f,
    val isAvailable: Boolean = false,
) {
    override fun hashCode(): Int {
        var result = degrees.hashCode()
        result = 31 * result + confidence.hashCode()
        result = 31 * result + isAvailable.hashCode()
        return result
    }

    override fun toString(): String {
        return "HeadingSensorState(degrees=$degrees, confidence=$confidence, " +
                "isAvailable=$isAvailable)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HeadingSensorState) return false


        if (degrees != other.degrees) return false
        if (confidence != other.confidence) return false
        if (isAvailable != other.isAvailable) return false

        return true
    }
}


/**
 * Creates and [remember]s an instance of [HeadingSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberHeadingSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): HeadingSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.Heading,
        sensorDelay = sensorDelay,
        onError = onError,
    )

    val headingSensorState = remember { mutableStateOf(HeadingSensorState()) }

    LaunchedEffect(key1 = sensorState, block = {
        val sensorStateValues = sensorState.data
        if (sensorStateValues.isNotEmpty()) {
            headingSensorState.value = HeadingSensorState(
                degrees = sensorStateValues[0],
                confidence = sensorStateValues[1],
                isAvailable = sensorState.isAvailable
            )
        }
    })

    return headingSensorState.value
}