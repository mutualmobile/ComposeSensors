package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * An Uncalibrated Magnetic Field sensor is similar to [SensorType.MagneticField] but the hard iron calibration is reported separately instead of being included in the measurement. Factory calibration and temperature compensation will still be applied to the "uncalibrated" measurement. Assumptions are that the magnetic field is due to the Earth's poles being avoided.
 * Hard iron - These distortions arise due to the magnetized iron, steel or permanent magnets on the device.
 * Soft iron - These distortions arise due to the interaction with the earth's magnetic field.
 * @param xStrength The measured magnetic field in X-axis. Soft iron and temperature calibrations are applied. But the hard iron calibration is not applied. The value is calculated in micro-Tesla (uT).
 * @param yStrength The measured magnetic field in Y-axis. Soft iron and temperature calibrations are applied. But the hard iron calibration is not applied. The value is calculated in micro-Tesla (uT).
 * @param zStrength The measured magnetic field in Z-axis. Soft iron and temperature calibrations are applied. But the hard iron calibration is not applied. The value is calculated in micro-Tesla (uT).
 * @param xBias The iron bias estimated in X-axis. It is a component of the estimated hard iron calibration. The value is calculated in micro-Tesla (uT).
 * @param yBias The iron bias estimated in Y-axis. It is a component of the estimated hard iron calibration. The value is calculated in micro-Tesla (uT).
 * @param zBias The iron bias estimated in Z-axis. It is a component of the estimated hard iron calibration. The value is calculated in micro-Tesla (uT).
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
    val accuracy: Int = 0,
    private val startListeningEvents: (() -> Unit)? = null,
    private val stopListeningEvents: (() -> Unit)? = null,
) : SensorStateListener {
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
        if (startListeningEvents != other.startListeningEvents) return false
        if (stopListeningEvents != other.stopListeningEvents) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xStrength.hashCode()
        result = 31 * result + yStrength.hashCode()
        result = 31 * result + zStrength.hashCode()
        result = 31 * result + xBias.hashCode()
        result = 31 * result + yBias.hashCode()
        result = 31 * result + zBias.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + startListeningEvents.hashCode()
        result = 31 * result + stopListeningEvents.hashCode()
        return result
    }

    override fun toString(): String {
        return "UncalibratedMagneticFieldSensorState(xStrength=$xStrength, yStrength=$yStrength," +
                " zStrength=$zStrength, xBias=$xBias, yBias=$yBias, zBias=$zBias, " +
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
 * Creates and [remember]s an instance of [UncalibratedMagneticFieldSensorState].
 * @param autoStart Start listening to sensor events as soon as sensor state is initialised.
 * Defaults to true.
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberUncalibratedMagneticFieldSensorState(
    autoStart: Boolean = true,
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): UncalibratedMagneticFieldSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.MagneticFieldUncalibrated,
        sensorDelay = sensorDelay,
        autoStart = autoStart,
        onError = onError
    )

    val uncalibratedMagneticFieldSensorState =
        remember {
            mutableStateOf(
                UncalibratedMagneticFieldSensorState(
                    startListeningEvents = sensorState::startListening,
                    stopListeningEvents = sensorState::stopListening
                )
            )
        }

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
                    accuracy = sensorState.accuracy,
                    startListeningEvents = sensorState::startListening,
                    stopListeningEvents = sensorState::stopListening
                )
            }
        }
    )

    return uncalibratedMagneticFieldSensorState.value
}
