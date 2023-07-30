package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * An acceleration sensor determines the acceleration that is applied to a
 * device by measuring the forces that are applied to the sensor itself.
 * In uncalibrated sensor, there are extra biases as estimated in x, y, z
 * direction, such that uncalibrated =_value = calibrated_value + bias in
 * that particular direction
 *
 * @param xForce Acceleration force along the x axis (including gravity) in
 *     m/s^2. Defaults to 0f.
 * @param yForce Acceleration force along the y axis (including gravity) in
 *     m/s^2. Defaults to 0f.
 * @param zForce Acceleration force along the z axis (including gravity) in
 *     m/s^2. Defaults to 0f.
 * @param xBias Estimated bias in the x direction in
 *     m/s^2. Defaults to 0f.
 * @param yBias Estimated bias in the y direction in
 *     m/s^2. Defaults to 0f.
 * @param zBias Estimated bias in the z direction in
 *     m/s^2. Defaults to 0f.
 * @param isAvailable Whether the current device has an accelerometer
 *     sensor. Defaults to false.
 * @param accuracy Accuracy factor of the accelerometer sensor. Defaults
 *     to 0.
 */
@Immutable
class UncalibratedAccelerometerSensorState internal constructor(
    val xForce: Float = 0f,
    val yForce: Float = 0f,
    val zForce: Float = 0f,
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
        if (other !is UncalibratedAccelerometerSensorState) return false

        if (xForce != other.xForce) return false
        if (yForce != other.yForce) return false
        if (zForce != other.zForce) return false
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
        var result = xForce.hashCode()
        result = 31 * result + yForce.hashCode()
        result = 31 * result + zForce.hashCode()
        result = 31 * result + xBias.hashCode()
        result = 31 * result + yBias.hashCode()
        result = 31 * result + zBias.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + startListeningEvents.hashCode()
        result = 31 * result + stopListeningEvents.hashCode()
        return result
    }

    override fun toString(): String {
        return "AccelerometerSensorState(xForce=$xForce, yForce=$yForce, " +
                "zForce=$zForce, xBiased=$xBias, yBiased=$yBias, zBiased=$zBias," +
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
 * Creates and [remember]s an instance of
 * [UncalibratedAccelerometerSensorState].
 * @param autoStart Start listening to sensor events as soon as sensor state is initialised.
 * Defaults to true.
 * @param sensorDelay The rate at which the raw sensor data should be received. Defaults to
 * [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberUncalibratedAccelerometerSensorState(
    autoStart: Boolean = true,
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): UncalibratedAccelerometerSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.AccelerometerUncalibrated,
        sensorDelay = sensorDelay,
        autoStart = autoStart,
        onError = onError
    )
    val uncalibratedAccelerometerSensorState =
        remember {
            mutableStateOf(
                UncalibratedAccelerometerSensorState(
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
                uncalibratedAccelerometerSensorState.value = UncalibratedAccelerometerSensorState(
                    xForce = sensorStateValues[0],
                    yForce = sensorStateValues[1],
                    zForce = sensorStateValues[2],
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

    return uncalibratedAccelerometerSensorState.value
}
