package com.mutualmobile.composesensors

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Measures the acceleration force in m/s2 that is applied to a device on all three physical axes
 * (x, y, and z), excluding the force of gravity.
 * @param xForce Acceleration force along the x axis (excluding gravity) in m/s^2. Defaults to 0f.
 * @param yForce Acceleration force along the y axis (excluding gravity) in m/s^2. Defaults to 0f.
 * @param zForce Acceleration force along the z axis (excluding gravity) in m/s^2. Defaults to 0f.
 * @param isAvailable Whether the current device has an LinearAcceleration sensor. Defaults to false.
 * @param accuracy Accuracy factor of the LinearAcceleration sensor. Defaults to 0.
 */
@Immutable
class LinearAccelerationSensorState internal constructor(
    val xForce: Float = 0f,
    val yForce: Float = 0f,
    val zForce: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
    private val startListeningEvents: (() -> Unit)? = null,
    private val stopListeningEvents: (() -> Unit)? = null
) : SensorStateListener {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LinearAccelerationSensorState) return false

        if (xForce != other.xForce) return false
        if (yForce != other.yForce) return false
        if (zForce != other.zForce) return false
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
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + startListeningEvents.hashCode()
        result = 31 * result + stopListeningEvents.hashCode()
        return result
    }

    override fun toString(): String {
        return "LinearAccelerationSensorState(xForce=$xForce, yForce=$yForce, zForce=$zForce, " +
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
 * Creates and [remember]s an instance of [LinearAccelerationSensorState].
 * @param autoStart Start listening to sensor events as soon as sensor state is initialised.
 * Defaults to true.
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun rememberLinearAccelerationSensorState(
    autoStart: Boolean = true,
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {}
): LinearAccelerationSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.LinearAcceleration,
        sensorDelay = sensorDelay,
        autoStart = autoStart,
        onError = onError
    )
    val linearAccelerationSensorState = remember { mutableStateOf(LinearAccelerationSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                linearAccelerationSensorState.value = LinearAccelerationSensorState(
                    xForce = sensorStateValues[0],
                    yForce = sensorStateValues[1],
                    zForce = sensorStateValues[2],
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy,
                    startListeningEvents = sensorState::startListening,
                    stopListeningEvents = sensorState::stopListening
                )
            }
        }
    )

    return linearAccelerationSensorState.value
}
