package com.mutualmobile.composesensors

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * A Pose6DOF event consists of a rotation expressed as a quaternion and a translation expressed in
 * SI units. The event also contains a delta rotation and translation that show how the device's
 * pose has changed since the previous sequence numbered pose. The event uses the canonical Android
 * Sensor axes.
 *
 * For more info, please refer the [Android Documentation Reference](https://developer.android.com/reference/android/hardware/SensorEvent#sensor.type_pose_6dof)
 * @param xScaledSinValue Represents the value x*sin(θ/2)
 * @param yScaledSinValue Represents the value y*sin(θ/2)
 * @param zScaledSinValue Represents the value z*sin(θ/2)
 * @param cosValue Represents the value cos(θ/2)
 * @param xTranslation Translation along x-axis from an arbitrary origin
 * @param yTranslation Translation along y-axis from an arbitrary origin
 * @param zTranslation Translation along z-axis from an arbitrary origin
 * @param xScaledSinDeltaRotation Represents the delta quaternion rotation x*sin(θ/2)
 * @param yScaledSinDeltaRotation Represents the delta quaternion rotation y*sin(θ/2)
 * @param zScaledSinDeltaRotation Represents the delta quaternion rotation z*sin(θ/2)
 * @param cosDeltaRotation Represents the delta quaternion rotation cos(θ/2)
 * @param xDeltaTranslation Delta translation along x-axis
 * @param yDeltaTranslation Delta translation along y-axis
 * @param zDeltaTranslation Delta translation along z-axis
 * @param sequenceNumber Represents the Sequence number
 * @param isAvailable Whether the current device has a Pose6DOF sensor. Defaults to false.
 * @param accuracy Accuracy factor of the Pose6DOF sensor. Defaults to 0.
 */
@RequiresApi(Build.VERSION_CODES.N)
@Immutable
class Pose6DOFSensorState internal constructor(
    val xScaledSinValue: Float = 0f,
    val yScaledSinValue: Float = 0f,
    val zScaledSinValue: Float = 0f,
    val cosValue: Float = 0f,
    val xTranslation: Float = 0f,
    val yTranslation: Float = 0f,
    val zTranslation: Float = 0f,
    val xScaledSinDeltaRotation: Float = 0f,
    val yScaledSinDeltaRotation: Float = 0f,
    val zScaledSinDeltaRotation: Float = 0f,
    val cosDeltaRotation: Float = 0f,
    val xDeltaTranslation: Float = 0f,
    val yDeltaTranslation: Float = 0f,
    val zDeltaTranslation: Float = 0f,
    val sequenceNumber: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
    private val startListeningEvents: (() -> Unit)? = null,
    private val stopListeningEvents: (() -> Unit)? = null
) : SensorStateListener {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Pose6DOFSensorState) return false

        if (xScaledSinValue != other.xScaledSinValue) return false
        if (yScaledSinValue != other.yScaledSinValue) return false
        if (zScaledSinValue != other.zScaledSinValue) return false
        if (cosValue != other.cosValue) return false
        if (xTranslation != other.xTranslation) return false
        if (yTranslation != other.yTranslation) return false
        if (zTranslation != other.zTranslation) return false
        if (xScaledSinDeltaRotation != other.xScaledSinDeltaRotation) return false
        if (yScaledSinDeltaRotation != other.yScaledSinDeltaRotation) return false
        if (zScaledSinDeltaRotation != other.zScaledSinDeltaRotation) return false
        if (cosDeltaRotation != other.cosDeltaRotation) return false
        if (xDeltaTranslation != other.xDeltaTranslation) return false
        if (yDeltaTranslation != other.yDeltaTranslation) return false
        if (zDeltaTranslation != other.zDeltaTranslation) return false
        if (sequenceNumber != other.sequenceNumber) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false
        if (startListeningEvents != other.startListeningEvents) return false
        if (stopListeningEvents != other.stopListeningEvents) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xScaledSinValue.hashCode()
        result = 31 * result + yScaledSinValue.hashCode()
        result = 31 * result + zScaledSinValue.hashCode()
        result = 31 * result + cosValue.hashCode()
        result = 31 * result + xTranslation.hashCode()
        result = 31 * result + yTranslation.hashCode()
        result = 31 * result + zTranslation.hashCode()
        result = 31 * result + xScaledSinDeltaRotation.hashCode()
        result = 31 * result + yScaledSinDeltaRotation.hashCode()
        result = 31 * result + zScaledSinDeltaRotation.hashCode()
        result = 31 * result + cosDeltaRotation.hashCode()
        result = 31 * result + xDeltaTranslation.hashCode()
        result = 31 * result + yDeltaTranslation.hashCode()
        result = 31 * result + zDeltaTranslation.hashCode()
        result = 31 * result + sequenceNumber.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + startListeningEvents.hashCode()
        result = 31 * result + stopListeningEvents.hashCode()
        return result
    }

    override fun toString(): String {
        return "Pose6DOFSensorState(xScaledSinValue=$xScaledSinValue," +
            " yScaledSinValue=$yScaledSinValue, zScaledSinValue=$zScaledSinValue, " +
            " cosValue=$cosValue, xTranslation=$xTranslation, yTranslation=$yTranslation," +
            " zTranslation=$zTranslation, xScaledSinDeltaRotation=$xScaledSinDeltaRotation," +
            " yScaledSinDeltaRotation=$yScaledSinDeltaRotation," +
            " zScaledSinDeltaRotation=$zScaledSinDeltaRotation," +
            " cosDeltaRotation=$cosDeltaRotation, xDeltaTranslation=$xDeltaTranslation," +
            " yDeltaTranslation=$yDeltaTranslation, zDeltaTranslation=$zDeltaTranslation," +
            " sequenceNumber=$sequenceNumber, isAvailable=$isAvailable, " +
            "accuracy=$accuracy)"
    }

    override fun startListening() {
        startListeningEvents?.invoke()
    }

    override fun stopListening() {
        stopListeningEvents?.invoke()
    }
}

/**
 * Creates and [remember]s an instance of [Pose6DOFSensorState].
 * @param autoStart Start listening to sensor events as soon as sensor state is initialised.
 * Defaults to true.
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun rememberPose6DOFSensorState(
    autoStart: Boolean = true,
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {}
): Pose6DOFSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.Pose6DOF,
        sensorDelay = sensorDelay,
        autoStart = autoStart,
        onError = onError
    )
    val pose6DOFSensorState = remember { mutableStateOf(Pose6DOFSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                pose6DOFSensorState.value = Pose6DOFSensorState(
                    xScaledSinValue = sensorStateValues[0],
                    yScaledSinValue = sensorStateValues[1],
                    zScaledSinValue = sensorStateValues[2],
                    cosValue = sensorStateValues[3],
                    xTranslation = sensorStateValues[4],
                    yTranslation = sensorStateValues[5],
                    zTranslation = sensorStateValues[6],
                    xScaledSinDeltaRotation = sensorStateValues[7],
                    yScaledSinDeltaRotation = sensorStateValues[8],
                    zScaledSinDeltaRotation = sensorStateValues[9],
                    cosDeltaRotation = sensorStateValues[10],
                    xDeltaTranslation = sensorStateValues[11],
                    yDeltaTranslation = sensorStateValues[12],
                    zDeltaTranslation = sensorStateValues[13],
                    sequenceNumber = sensorStateValues[14],
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy,
                    startListeningEvents = sensorState::startListening,
                    stopListeningEvents = sensorState::stopListening
                )
            }
        }
    )

    return pose6DOFSensorState.value
}
