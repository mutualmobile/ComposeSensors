package com.mutualmobile.composesensors

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * The HeadTracker sensor measures the orientation of a user's head relative to an arbitrary
 * reference frame, as well as the rate of rotation.
 * @param xRotation X component of Euler vector representing rotation. Defaults to 0f.
 * @param yRotation Y component of Euler vector representing rotation. Defaults to 0f.
 * @param zRotation Z component of Euler vector representing rotation. Defaults to 0f.
 * @param xAngularVelocity X component of Euler vector representing angular velocity (if supported,
 * otherwise 0). Defaults to 0f.
 * @param yAngularVelocity Y component of Euler vector representing angular velocity (if supported,
 * otherwise 0). Defaults to 0f.
 * @param zAngularVelocity Z component of Euler vector representing angular velocity (if supported,
 * otherwise 0). Defaults to 0f.
 * @param isAvailable Whether the current device has a gyroscope sensor. Defaults to false.
 * @param accuracy Accuracy factor of the gyroscope sensor. Defaults to 0.
 **/
@Immutable
class HeadTrackerSensorState internal constructor(
    val xRotation: Float = 0f,
    val yRotation: Float = 0f,
    val zRotation: Float = 0f,
    val xAngularVelocity: Float = 0f,
    val yAngularVelocity: Float = 0f,
    val zAngularVelocity: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
    private val startListeningEvents: (() -> Unit)? = null,
    private val stopListeningEvents: (() -> Unit)? = null
) : SensorStateListener {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HeadTrackerSensorState

        if (xRotation != other.xRotation) return false
        if (yRotation != other.yRotation) return false
        if (zRotation != other.zRotation) return false
        if (xAngularVelocity != other.xAngularVelocity) return false
        if (yAngularVelocity != other.yAngularVelocity) return false
        if (zAngularVelocity != other.zAngularVelocity) return false
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
        result = 31 * result + xAngularVelocity.hashCode()
        result = 31 * result + yAngularVelocity.hashCode()
        result = 31 * result + zAngularVelocity.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + startListeningEvents.hashCode()
        result = 31 * result + stopListeningEvents.hashCode()
        return result
    }

    override fun toString(): String {
        return "HeadTrackerSensorState(xRotation=$xRotation, yRotation=$yRotation," +
            " zRotation=$zRotation, xAngularVelocity=$xAngularVelocity," +
            " yAngularVelocity=$yAngularVelocity, zAngularVelocity=$zAngularVelocity," +
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
 * Creates and [remember]s an instance of [HeadTrackerSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun rememberHeadTrackerSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {}
): HeadTrackerSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.HeadTracker,
        sensorDelay = sensorDelay,
        onError = onError
    )

    val headTrackerSensor = remember { mutableStateOf(HeadTrackerSensorState()) }

    LaunchedEffect(key1 = sensorState, block = {
        val sensorStateValues = sensorState.data
        if (sensorStateValues.isNotEmpty()) {
            headTrackerSensor.value = HeadTrackerSensorState(
                xRotation = sensorStateValues[0],
                yRotation = sensorStateValues[1],
                zRotation = sensorStateValues[2],
                xAngularVelocity = sensorStateValues[3],
                yAngularVelocity = sensorStateValues[4],
                zAngularVelocity = sensorStateValues[5],
                isAvailable = sensorState.isAvailable,
                accuracy = sensorState.accuracy,
                startListeningEvents = sensorState::startListening,
                stopListeningEvents = sensorState::stopListening
            )
        }
    })

    return headTrackerSensor.value
}
