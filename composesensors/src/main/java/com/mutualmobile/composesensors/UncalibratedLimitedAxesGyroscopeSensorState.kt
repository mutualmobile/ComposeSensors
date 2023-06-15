package com.mutualmobile.composesensors

import android.os.Build
import androidx.annotation.RequiresApi
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
 * Equivalent to [UncalibratedGyroscopeSensorState], but supporting cases where one or two axes are
 * not supported. The last three values represent whether the angular speed value for a given axis
 * is supported. The supported axes should be determined at build time and these values do not
 * change during runtime. The angular speed values and drift values for axes that are not supported
 * are set to 0.
 *
 * For more info, please refer the [Android Documentation Reference](https://developer.android.com/reference/android/hardware/SensorEvent#sensor.type_gyroscope_limited_axes_uncalibrated:)
 *
 * @param xRotation Angular speed (w/o drift compensation) around the X axis (if supported).
 * Defaults to 0f.
 * @param yRotation Angular speed (w/o drift compensation) around the Y axis (if supported).
 * Defaults to 0f.
 * @param zRotation Angular speed (w/o drift compensation) around the Z axis (if supported).
 * Defaults to 0f.
 * @param xBias estimated drift around X axis (if supported). Defaults to 0f.
 * @param yBias estimated drift around Y axis (if supported). Defaults to 0f.
 * @param zBias estimated drift around Z axis (if supported). Defaults to 0f.
 * @param xAxisSupported Whether angular speed supported for x-axis. Defaults to false.
 * @param yAxisSupported Whether angular speed supported for y-axis. Defaults to false.
 * @param zAxisSupported Whether angular speed supported for z-axis. Defaults to false.
 * @param isAvailable Whether the current device has a gyroscope sensor.
 *     Defaults to false.
 * @param accuracy Accuracy factor of the gyroscope sensor. Defaults to 0.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Immutable
class UncalibratedLimitedAxesGyroscopeSensorState internal constructor(
    val xRotation: Float = 0f,
    val yRotation: Float = 0f,
    val zRotation: Float = 0f,
    val xBias: Float = 0f,
    val yBias: Float = 0f,
    val zBias: Float = 0f,
    val xAxisSupported: Boolean = false,
    val yAxisSupported: Boolean = false,
    val zAxisSupported: Boolean = false,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
    private val startListeningEvents: (() -> Unit)? = null,
    private val stopListeningEvents: (() -> Unit)? = null
) : SensorStateListener {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UncalibratedLimitedAxesGyroscopeSensorState) return false

        if (xRotation != other.xRotation) return false
        if (yRotation != other.yRotation) return false
        if (zRotation != other.zRotation) return false
        if (xBias != other.xBias) return false
        if (yBias != other.yBias) return false
        if (zBias != other.zBias) return false
        if (xAxisSupported != other.xAxisSupported) return false
        if (yAxisSupported != other.yAxisSupported) return false
        if (zAxisSupported != other.zAxisSupported) return false
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
        result = 31 * result + xAxisSupported.hashCode()
        result = 31 * result + yAxisSupported.hashCode()
        result = 31 * result + zAxisSupported.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + startListeningEvents.hashCode()
        result = 31 * result + stopListeningEvents.hashCode()
        return result
    }

    override fun toString(): String {
        return "UncalibratedGyroscopeSensorState(xRotation=$xRotation, yRotation=$yRotation," +
            " zRotation=$zRotation," + "xBias=$xBias," + "yBias=$yBias," + "zBias=$zBias," +
            " xAxisSupported=$xAxisSupported, yAxisSupported=$yAxisSupported," +
            " zAxisSupported=$zAxisSupported, isAvailable=$isAvailable, accuracy=$accuracy)"
    }

    override fun startListening() {
        startListeningEvents?.invoke()
    }

    override fun stopListening() {
        stopListeningEvents?.invoke()
    }
}

/**
 * Creates and [remember]s an instance of [UncalibratedLimitedAxesGyroscopeSensorState].
 * @param autoStart Start listening to sensor events as soon as sensor state is initialised.
 * Defaults to true.
 * @param sensorDelay The rate at which the raw sensor data should be received. Defaults to
 * [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun rememberUncalibratedLimitedAxesGyroscopeSensorState(
    autoStart: Boolean = true,
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {}
): UncalibratedLimitedAxesGyroscopeSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.GyroscopeLimitedAxesUncalibrated,
        sensorDelay = sensorDelay,
        autoStart = autoStart,
        onError = onError
    )
    val uncalibratedLimitedAxesGyroscopeSensorState =
        remember { mutableStateOf(UncalibratedLimitedAxesGyroscopeSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                uncalibratedLimitedAxesGyroscopeSensorState.value =
                    UncalibratedLimitedAxesGyroscopeSensorState(
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

    return uncalibratedLimitedAxesGyroscopeSensorState.value
}
