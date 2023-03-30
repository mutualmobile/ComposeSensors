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
 * @param scalar Scalar component of rotation vector.
 * @param estimatedHeadingAccuracy estimated heading Accuracy (in radians).
 * @param isAvailable Whether the current device has a rotation vector sensor. Defaults to false.
 * @param accuracy Accuracy factor of the rotation vector sensor. Defaults to 0.
 */
@Immutable
class RotationVectorSensorState internal constructor(
  val vectorX: Float = 0f,
  val vectorY: Float = 0f,
  val vectorZ: Float = 0f,
  val scalar: Float = 0f,
  val estimatedHeadingAccuracy: Float = 0f,
  val isAvailable: Boolean = false,
  val accuracy: Int = 0
) {
  override fun toString(): String {
    return "RotationVectorSensorState(vectorX=$vectorX, " +
        "vectorY=$vectorY, vectorZ=$vectorZ, scalar=$scalar, " +
        "estimatedHeadingAccuracy=$estimatedHeadingAccuracy, " +
        "isAvailable=$isAvailable, accuracy=$accuracy)"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    other as RotationVectorSensorState

    if (vectorX != other.vectorX) return false
    if (vectorY != other.vectorY) return false
    if (vectorZ != other.vectorZ) return false
    if (scalar != other.scalar) return false
    if (estimatedHeadingAccuracy != other.estimatedHeadingAccuracy) return false
    if (isAvailable != other.isAvailable) return false
    if (accuracy != other.accuracy) return false
    return true
  }

  override fun hashCode(): Int {
    var result = vectorX.hashCode()
    result = 31 * result + vectorY.hashCode()
    result = 31 * result + vectorZ.hashCode()
    result = 31 * result + scalar.hashCode()
    result = 31 * result + estimatedHeadingAccuracy.hashCode()
    result = 31 * result + isAvailable.hashCode()
    result = 31 * result + accuracy
    return result
  }
}

/**
 * Creates and [remember]s instance of [RotationVectorSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberRotationVectorSensorState(
  sensorDelay: SensorDelay = SensorDelay.Normal,
  onError: (throwable: Throwable) -> Unit = {},
): RotationVectorSensorState {
  val sensorState = rememberSensorState(
    sensorType = SensorType.RotationVector,
    sensorDelay = sensorDelay,
    onError = onError,
  )

  val rotationVectorSensorState = remember { mutableStateOf(RotationVectorSensorState()) }

  LaunchedEffect(key1 = sensorState, block = {
    val sensorStateValues = sensorState.data
    if (sensorStateValues.isNotEmpty()) {
      rotationVectorSensorState.value = RotationVectorSensorState(
        vectorX = sensorStateValues[0],
        vectorY = sensorStateValues[1],
        vectorZ = sensorStateValues[2],
        scalar = sensorStateValues[3],
        estimatedHeadingAccuracy = sensorStateValues[4],
        isAvailable = sensorState.isAvailable,
        accuracy = sensorState.accuracy
      )
    }
  })

  return rotationVectorSensorState.value
}