package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Measures the proximity of an object in cm relative to the view screen of a device.
 * @param sensorDistance Proximity of an object from the sensor (in cm).
 * @param isAvailable Whether the current device has a proximity sensor. Defaults to false.
 * @param accuracy Accuracy factor of the proximity sensor. Defaults to 0.
 */
@Immutable
class ProximitySensorState internal constructor(
  val sensorDistance: Float = 0f,
  val isAvailable: Boolean = false,
  val accuracy: Int = 0
) {
  override fun toString(): String {
    return "ProximitySensorState(sensorDistance=$sensorDistance, " +
        "isAvailable=$isAvailable, accuracy=$accuracy)"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is ProximitySensorState) return false

    if (isAvailable != other.isAvailable) return false
    if (sensorDistance != other.sensorDistance) return false
    if (accuracy != other.accuracy) return false
    return true
  }

  override fun hashCode(): Int {
    var result = isAvailable.hashCode()
    result = 31 * result + sensorDistance.hashCode()
    result = 31 * result + accuracy.hashCode()
    return result
  }
}


/**
 * Creates and [remember]s instance of [ProximitySensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberProximitySensorState(
  sensorDelay: SensorDelay = SensorDelay.Normal,
  onError: (throwable: Throwable) -> Unit = {},
): ProximitySensorState {
  val sensorState = rememberSensorState(
    sensorType = SensorType.Proximity,
    sensorDelay = sensorDelay,
    onError = onError,
  )

  val proximitySensorState = remember { mutableStateOf(ProximitySensorState()) }

  LaunchedEffect(key1 = sensorState, block = {
    val sensorStateValues = sensorState.data
    if (sensorStateValues.isNotEmpty()) {
      proximitySensorState.value = ProximitySensorState(
        sensorDistance = sensorStateValues[0],
        isAvailable = sensorState.isAvailable,
        accuracy = sensorState.accuracy
      )
    }
  })

  return proximitySensorState.value
}