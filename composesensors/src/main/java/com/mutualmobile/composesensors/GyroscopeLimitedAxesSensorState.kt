package com.mutualmobile.composesensors

import androidx.compose.runtime.*

@Immutable
class GyroscopeLimitedAxesSensorState internal constructor(
    val xAxisSupported: Float = 0f,
    val yAxisSupported: Float = 0f,
    val zAxisSupported: Float = 0f,
    val xRotation: Float = 0f,
    val yRotation: Float = 0f,
    val zRotation: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GyroscopeLimitedAxesSensorState

        if (xAxisSupported != other.xAxisSupported) return false
        if (yAxisSupported != other.yAxisSupported) return false
        if (zAxisSupported != other.zAxisSupported) return false
        if (xRotation != other.xRotation) return false
        if (yRotation != other.yRotation) return false
        if (zRotation != other.zRotation) return false
        if (isAvailable != other.isAvailable) return false
        return accuracy == other.accuracy
    }

    override fun hashCode(): Int {
        var result = xAxisSupported.hashCode()
        result = 31 * result + yAxisSupported.hashCode()
        result = 31 * result + zAxisSupported.hashCode()
        result = 31 * result + xRotation.hashCode()
        result = 31 * result + yRotation.hashCode()
        result = 31 * result + zRotation.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy
        return result
    }

    override fun toString(): String {
        return "GyroscopeLimitedAxesSensorState(xAxisSupported=$xAxisSupported, yAxisSupported=$yAxisSupported, zAxisSupported=$zAxisSupported, xRotation=$xRotation, yRotation=$yRotation, zRotation=$zRotation, isAvailable=$isAvailable, accuracy=$accuracy)"
    }

}

@Composable
fun rememberGyroscopeLimitedAxesSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): GyroscopeLimitedAxesSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.GyroscopeLimitedAxes,
        sensorDelay = sensorDelay,
        onError = onError
    )

    val gyroscopeLimitedAxesSensor = remember { mutableStateOf(GyroscopeLimitedAxesSensorState()) }

    LaunchedEffect(key1 = sensorState, block = {
        val sensorStateValues = sensorState.data
        if (sensorStateValues.isNotEmpty()) {
            gyroscopeLimitedAxesSensor.value = GyroscopeLimitedAxesSensorState(
                xAxisSupported = sensorStateValues[0],
                yAxisSupported = sensorStateValues[1],
                zAxisSupported = sensorStateValues[2],
                xRotation = sensorStateValues[3],
                yRotation = sensorStateValues[4],
                zRotation = sensorStateValues[5],
                isAvailable = sensorState.isAvailable,
                accuracy = sensorState.accuracy
            )
        }
    })

    return gyroscopeLimitedAxesSensor.value
}