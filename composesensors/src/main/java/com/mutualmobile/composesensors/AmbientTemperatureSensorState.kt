package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Measures the ambient room temperature in degrees Celsius (°C)
 * @param temperature Ambient temperature as determined by sensor (in °C).
 * @param isAvailable Whether the current device has an ambient temperature sensor. Defaults to false.
 * @param accuracy Accuracy factor of the ambient temperature sensor. Defaults to 0.
 */
@Immutable
class AmbientTemperatureSensorState internal constructor(
  val temperature: Float = 0f,
  val isAvailable: Boolean = false,
  val accuracy: Int = 0
) {
  override fun toString(): String {
    return "AmbientTemperatureSensorState(temperature=$temperature, " +
        "isAvailable=$isAvailable, accuracy=$accuracy)"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    other as AmbientTemperatureSensorState

    if (temperature != other.temperature) return false
    if (isAvailable != other.isAvailable) return false
    if (accuracy != other.accuracy) return false
    return true
  }

  override fun hashCode(): Int {
    var result = temperature.hashCode()
    result = 31 * result + isAvailable.hashCode()
    result = 31 * result + accuracy
    return result
  }
}


/**
 * Creates and [remember]s instance of [AmbientTemperatureSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberAmbientTemperatureSensorState(
  sensorDelay: SensorDelay = SensorDelay.Normal,
  onError: (throwable: Throwable) -> Unit = {},
): AmbientTemperatureSensorState {
  val sensorState = rememberSensorState(
    sensorType = SensorType.AmbientTemperature,
    sensorDelay = sensorDelay,
    onError = onError,
  )

  val ambientTemperatureSensorState = remember { mutableStateOf(AmbientTemperatureSensorState()) }

  LaunchedEffect(key1 = sensorState, block = {
    val sensorStateValues = sensorState.data
    if (sensorStateValues.isNotEmpty()) {
      ambientTemperatureSensorState.value = AmbientTemperatureSensorState(
        temperature = sensorStateValues[0],
        isAvailable = sensorState.isAvailable,
        accuracy = sensorState.accuracy
      )
    }
  })

  return ambientTemperatureSensorState.value
}