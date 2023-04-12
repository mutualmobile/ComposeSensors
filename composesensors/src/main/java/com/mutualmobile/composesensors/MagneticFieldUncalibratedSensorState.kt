package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * An Magnetic Field Uncalibrated sensor is similar to Sensor.TYPE_MAGNETIC_FIELD here the hard iron calibration is reported separately instead of being included in the measurement.
 * Factory calibration and temperature compensation will still be applied to the "uncalibrated" measurement.
 * Assumptions that the magnetic field is due to the Earth's poles is avoided
 * @param xUncalib Magnetic field in x axes (Including Soft Iron and temperature calibration) measured in micro tesla (uT).
 * @param yUncalib Magnetic field in y axes (Including soft iron and temperature calibration) measured in micro tesla (uT).
 * @param zUncalib Magnetic field in z axes (Including soft iron and temperature calibration) measured in micro tesla (uT).
 * @param xBias Magnetic field in x axes (Including Hard iron calibration) measured in micro tesla (uT).
 * @param yBias Magnetic field in y axes (Including Hard iron calibration) measured in micro tesla (uT).
 * @param zBias Magnetic field in z axes (Including Hard iron calibration) measured in micro tesla (uT).
 * @param isAvailable Whether the current device has an accelerometer sensor. Defaults to false.
 * @param accuracy Accuracy factor of the accelerometer sensor. Defaults to 0.
 */
@Immutable
class MagneticFieldUncalibratedSensorState internal constructor(
    val xUncalib: Float = 0f,
    val yUncalib: Float = 0f,
    val zUncalib: Float = 0f,
    val xBias: Float = 0f,
    val yBias: Float = 0f,
    val zBias: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MagneticFieldUncalibratedSensorState) return false

        if (xUncalib != other.xUncalib) return false
        if (yUncalib != other.yUncalib) return false
        if (zUncalib != other.zUncalib) return false
        if (xBias != other.xBias) return false
        if (yBias != other.yBias) return false
        if (zBias != other.zBias) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xUncalib.hashCode()
        result = 31 * result + yUncalib.hashCode()
        result = 31 * result + zUncalib.hashCode()
        result = 31 * result + xBias.hashCode()
        result = 31 * result + yBias.hashCode()
        result = 31 * result + zBias.hashCode()
        result = 31 * result + accuracy.hashCode()
        return result
    }

    override fun toString(): String {
        return "MagneticFieldUncalibratedSensorState(xUncalib=$xUncalib, yUncalib=$yUncalib, zUncalib=$zUncalib, " +
            "xBias=$xBias, yBias=$yBias, zBias=$zBias, " +
            "isAvailable=$isAvailable, accuracy=$accuracy)"
    }
}

/**
 * Creates and [remember]s an instance of [MagneticFieldUncalibratedSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberMagneticFieldUncalibratedSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): MagneticFieldUncalibratedSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.MagneticFieldUncalibrated,
        sensorDelay = sensorDelay,
        onError = onError,
    )
    val magneticFieldUncalibratedSensorState =
        remember { mutableStateOf(MagneticFieldUncalibratedSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                magneticFieldUncalibratedSensorState.value = MagneticFieldUncalibratedSensorState(
                    xUncalib = sensorStateValues[0],
                    yUncalib = sensorStateValues[1],
                    zUncalib = sensorStateValues[2],
                    xBias = sensorStateValues[3],
                    yBias = sensorStateValues[4],
                    zBias = sensorStateValues[5],
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy,
                )
            }
        },
    )

    return magneticFieldUncalibratedSensorState.value
}
