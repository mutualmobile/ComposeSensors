package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * The step detector sensor triggers an event each time the user takes a step.
 * The latency is expected to be below 2 seconds.
 * @param stepCount Number of steps taken by the user.
 * @param isAvailable Whether the current device has an step detector sensor. Defaults to false.
 * @param accuracy Accuracy factor of the accelerometer sensor. Defaults to 0.
 */
@Immutable
class StepDetectorSensorState internal constructor(
    val stepCount: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StepDetectorSensorState) return false

        if (stepCount != other.stepCount) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = stepCount.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        return result
    }

    override fun toString(): String {
        return "StepDetectorSensorState(stepCount=$stepCount isAvailable=$isAvailable, accuracy=$accuracy)"
    }
}

/**
 * Creates and [remember]s an instance of [StepDetectorSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberStepDetectorSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): StepDetectorSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.StepDetector,
        sensorDelay = sensorDelay,
        onError = onError,
    )
    val stepDetectorSensorState = remember { mutableStateOf(StepDetectorSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                stepDetectorSensorState.value = StepDetectorSensorState(
                    stepCount = sensorStateValues[0],
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy,
                )
            }
        },
    )

    return stepDetectorSensorState.value
}
