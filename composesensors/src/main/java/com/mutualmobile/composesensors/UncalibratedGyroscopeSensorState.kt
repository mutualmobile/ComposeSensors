package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Measures a device's rate of rotation in rad/s around each of the three
 * physical axes (x, y, and z). Uncalibrated gyroscope sensor estimates an
 * extra drift on x, y, z axes, to the effect that uncalibrated_value =
 * calibrated_value + drift in that particular axis.
 *
 * @param xRotation Rate of rotation around the x axis. Defaults to 0f.
 * @param yRotation Rate of rotation around the y axis. Defaults to 0f.
 * @param zRotation Rate of rotation around the z axis. Defaults to 0f.
 * @param xBias Bias around the x axis. Defaults to 0f.
 * @param yBias Bias around the y axis. Defaults to 0f.
 * @param zBias Bias around the z axis. Defaults to 0f.
 * @param isAvailable Whether the current device has a gyroscope sensor.
 *     Defaults to false.
 * @param accuracy Accuracy factor of the gyroscope sensor. Defaults to 0.
 */
@Immutable
class UncalibratedGyroscopeSensorState internal constructor(
    val xRotation: Float = 0f,
    val yRotation: Float = 0f,
    val zRotation: Float = 0f,
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
        if (other !is UncalibratedGyroscopeSensorState) return false

        if (xRotation != other.xRotation) return false
        if (yRotation != other.yRotation) return false
        if (zRotation != other.zRotation) return false
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
        var result = xRotation.hashCode()
        result = 31 * result + yRotation.hashCode()
        result = 31 * result + zRotation.hashCode()
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
        return "GyroscopeSensorState(xRotation=$xRotation, yRotation=$yRotation, " +
                "zRotation=$zRotation," + "xBias=$xBias," + "yBias=$yBias," + "zBias=$zBias," +
                " isAvailable=$isAvailable, accuracy=$accuracy)"
    }

    override fun startListening() {
        startListeningEvents?.invoke()
    }

    override fun stopListening() {
        stopListeningEvents?.invoke()
    }
}

/**
 * Creates and [remember]s an instance of [GyroscopeSensorState].
 * @param autoStart Start listening to sensor events as soon as sensor state is initialised.
 * Defaults to true.
 * @param sensorDelay The rate at which the raw sensor data should be received. Defaults to
 * [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberUncalibratedGyroscopeSensorState(
    autoStart: Boolean = true,
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): UncalibratedGyroscopeSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.GyroscopeUncalibrated,
        sensorDelay = sensorDelay,
        autoStart = autoStart,
        onError = onError
    )
    val uncalibratedGyroscopeSensorState =
        remember {
            mutableStateOf(
                UncalibratedGyroscopeSensorState(
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
                uncalibratedGyroscopeSensorState.value = UncalibratedGyroscopeSensorState(
                    xRotation = sensorStateValues[0],
                    yRotation = sensorStateValues[1],
                    zRotation = sensorStateValues[2],
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

    return uncalibratedGyroscopeSensorState.value
}
