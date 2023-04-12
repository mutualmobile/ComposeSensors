package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * A hinge angle sensor measures the angle, in degrees, between two integral parts of the device.
 * Movement of a hinge measured by this sensor type is expected to alter the ways in which the user
 * can interact with the device, for example, by unfolding or revealing a display.
 * @param angle Angle between two integral parts of the device (in degrees)
 * @param isAvailable Whether the current device has a HingeAngle sensor. Defaults to false.
 * @param accuracy Accuracy factor of the HingeAngle sensor. Defaults to 0.
 * @see [rememberHingeAngleSensorState]
 */
@Immutable
class HingeAngleSensorState internal constructor(
    val angle: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HingeAngleSensorState) return false

        if (angle != other.angle) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = angle.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        return result
    }

    override fun toString(): String {
        return "HingeAngleSensorState(angle=$angle, isAvailable=$isAvailable, accuracy=$accuracy)"
    }
}

/**
 * Creates and [remember]s an instance of [HingeAngleSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberHingeAngleSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {}
): HingeAngleSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.HingeAngle,
        sensorDelay = sensorDelay,
        onError = onError
    )
    val hingeAngleSensorState = remember { mutableStateOf(HingeAngleSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                hingeAngleSensorState.value = HingeAngleSensorState(
                    angle = sensorStateValues[0],
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy
                )
            }
        }
    )

    return hingeAngleSensorState.value
}
