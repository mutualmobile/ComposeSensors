package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Immutable
class MotionSensorState internal constructor(
    val isDeviceInMotion: Float = 0f,
    val isAvailable: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MotionSensorState) return false
        if (isDeviceInMotion != other.isDeviceInMotion) return false
        if (isAvailable != other.isAvailable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isDeviceInMotion.hashCode()
        result = 31 * result + isAvailable.hashCode()
        return result
    }

    override fun toString(): String {
        return "StationaryMotionSensorState(isDeviceStationary : $isDeviceInMotion, isAvailable : $isAvailable)"
    }
}

@Composable
fun rememberMotionSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): MotionSensorState {

    val sensorState = rememberSensorState(
        sensorType = SensorType.MotionDetect,
        sensorDelay = sensorDelay,
        onError = onError,
    )

    val motionSensorState = remember { mutableStateOf(MotionSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val motionSensorData = sensorState.data
            if (motionSensorData.isNotEmpty()) {
                motionSensorState.value = MotionSensorState(
                    isDeviceInMotion = motionSensorData[0],
                    isAvailable = sensorState.isAvailable
                )
            }
        },
    )

    return motionSensorState.value
}