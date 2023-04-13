package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * An Uncalibrated Magnetic Field sensor is similar to [SensorType.MagneticField] but the hard iron calibration is reported separately instead of being included in the measurement.
 * Factory calibration and temperature compensation will still be applied to the "uncalibrated" measurement.
 * Assumptions are that the magnetic field is due to the Earth's poles being avoided.
 * @param xStrength Magnetic field in x axes (Including Soft Iron and temperature calibration) measured in micro tesla (uT).
 * @param yStrength Magnetic field in y axes (Including soft iron and temperature calibration) measured in micro tesla (uT).
 * @param zStrength Magnetic field in z axes (Including soft iron and temperature calibration) measured in micro tesla (uT).
 * @param xBias Magnetic field in x axes (Including Hard iron calibration) measured in micro tesla (uT).
 * @param yBias Magnetic field in y axes (Including Hard iron calibration) measured in micro tesla (uT).
 * @param zBias Magnetic field in z axes (Including Hard iron calibration) measured in micro tesla (uT).
 * @param isAvailable Whether the current device has an uncalibrated magnetic field sensor. Defaults to false.
 * @param accuracy Accuracy factor of the uncalibrated magnetic field sensor. Defaults to 0.
 */
@Immutable
class UncalibratedMagneticFieldSensorState internal constructor(
    val xStrength: Float = 0f,
    val yStrength: Float = 0f,
    val zStrength: Float = 0f,
    val xBias: Float = 0f,
    val yBias: Float = 0f,
    val zBias: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UncalibratedMagneticFieldSensorState) return false

        if (xStrength != other.xStrength) return false
        if (yStrength != other.yStrength) return false
        if (zStrength != other.zStrength) return false
        if (xBias != other.xBias) return false
        if (yBias != other.yBias) return false
        if (zBias != other.zBias) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xStrength.hashCode()
        result = 31 * result + yStrength.hashCode()
        result = 31 * result + zStrength.hashCode()
        result = 31 * result + xBias.hashCode()
        result = 31 * result + yBias.hashCode()
        result = 31 * result + zBias.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + isAvailable.hashCode()
        return result
    }

    override fun toString(): String {
        return "UncalibratedMagneticFieldSensorState(xStrength=$xStrength, yStrength=$yStrength, zStrength=$zStrength, " +
            "xBias=$xBias, yBias=$yBias, zBias=$zBias, " +
            "isAvailable=$isAvailable, accuracy=$accuracy)"
    }
}

/**
 * Creates and [remember]s an instance of [UncalibratedMagneticFieldSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberUncalibratedMagneticFieldSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {}
): UncalibratedMagneticFieldSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.MagneticFieldUncalibrated,
        sensorDelay = sensorDelay,
        onError = onError
    )
    val uncalibratedMagneticFieldSensorState =
        remember { mutableStateOf(UncalibratedMagneticFieldSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                uncalibratedMagneticFieldSensorState.value = UncalibratedMagneticFieldSensorState(
                    xStrength = sensorStateValues[0],
                    yStrength = sensorStateValues[1],
                    zStrength = sensorStateValues[2],
                    xBias = sensorStateValues[3],
                    yBias = sensorStateValues[4],
                    zBias = sensorStateValues[5],
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy
                )
            }
        }
    )

    return uncalibratedMagneticFieldSensorState.value
}
