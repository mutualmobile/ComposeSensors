package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Immutable
class StationaryMotionSensorState internal constructor(
    val isDeviceStationary: Float = 0f,
    val isAvailable: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StationaryMotionSensorState) return false

        if (isDeviceStationary != other.isDeviceStationary) return false
        if (isAvailable != other.isAvailable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isDeviceStationary.hashCode()
        result = 31 * result + isAvailable.hashCode()
        return result
    }

    override fun toString(): String {
        return "StationaryMotionSensorState(isDeviceStationary : $isDeviceStationary, isAvailable : $isAvailable)"
    }
}

@Composable
fun rememberStationaryMotionSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): StationaryMotionSensorState {

    val sensorState = rememberSensorState(
        sensorType = SensorType.StationaryDetect,
        sensorDelay = sensorDelay,
        onError = onError,
    )

    val stationaryMotionSensorState = remember { mutableStateOf(StationaryMotionSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val motionSensorData = sensorState.data
            if (motionSensorData.isNotEmpty()) {
                stationaryMotionSensorState.value = StationaryMotionSensorState(
                    isDeviceStationary = motionSensorData[0],
                    isAvailable = sensorState.isAvailable
                )
            }
        },
    )

    return stationaryMotionSensorState.value
}