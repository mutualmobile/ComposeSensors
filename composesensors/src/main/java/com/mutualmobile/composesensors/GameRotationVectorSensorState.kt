package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Measures the orientation of a device by providing the three elements of the device's rotation vector.
 * @param vectorX Rotation vector component along the x axis.
 * @param vectorY Rotation vector component along the y axis.
 * @param vectorZ Rotation vector component along the z axis.
 * @param isAvailable Whether the current device has a game rotation vector sensor. Defaults to false.
 * @param accuracy Accuracy factor of the game rotation vector sensor. Defaults to 0.
 */
@Immutable
class GameRotationVectorSensorState internal constructor(
    val vectorX: Float = 0f,
    val vectorY: Float = 0f,
    val vectorZ: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
    private val startListeningEvents: (() -> Unit)? = null,
    private val stopListeningEvents: (() -> Unit)? = null,
) : SensorStateListener {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        other as GameRotationVectorSensorState

        if (vectorX != other.vectorX) return false
        if (vectorY != other.vectorY) return false
        if (vectorZ != other.vectorZ) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false
        if (startListeningEvents != other.startListeningEvents) return false
        if (stopListeningEvents != other.stopListeningEvents) return false
        return true
    }

    override fun hashCode(): Int {
        var result = vectorX.hashCode()
        result = 31 * result + vectorY.hashCode()
        result = 31 * result + vectorZ.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + startListeningEvents.hashCode()
        result = 31 * result + stopListeningEvents.hashCode()
        return result
    }

    override fun toString(): String {
        return "GameRotationVectorSensorState(vectorX=$vectorX, " +
                "vectorY=$vectorY, vectorZ=$vectorZ, isAvailable=$isAvailable, " +
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
 * Creates and [remember]s instance of [GameRotationVectorSensorState].
 * @param autoStart Start listening to sensor events as soon as sensor state is initialised.
 * Defaults to true.
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Game].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberGameRotationVectorSensorState(
    autoStart: Boolean = true,
    sensorDelay: SensorDelay = SensorDelay.Game,
    onError: (throwable: Throwable) -> Unit = {},
): GameRotationVectorSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.GameRotationVector,
        sensorDelay = sensorDelay,
        autoStart = autoStart,
        onError = onError
    )

    val gameRotationVectorSensorState = remember {
        mutableStateOf(
            GameRotationVectorSensorState(
                startListeningEvents = sensorState::startListening,
                stopListeningEvents = sensorState::stopListening
            )
        )
    }

    LaunchedEffect(key1 = sensorState, block = {
        val sensorStateValues = sensorState.data
        if (sensorStateValues.isNotEmpty()) {
            gameRotationVectorSensorState.value = GameRotationVectorSensorState(
                vectorX = sensorStateValues[0],
                vectorY = sensorStateValues[1],
                vectorZ = sensorStateValues[2],
                isAvailable = sensorState.isAvailable,
                accuracy = sensorState.accuracy,
                startListeningEvents = sensorState::startListening,
                stopListeningEvents = sensorState::stopListening
            )
        }
    })

    return gameRotationVectorSensorState.value
}
