package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Measures the ambient geomagnetic field for all three physical axes (x, y, z) in μT.
 * @param xStrength Geomagnetic field strength along the x axis.
 * @param yStrength Geomagnetic field strength along the y axis.
 * @param zStrength Geomagnetic field strength along the z axis.
 */
@Immutable
class MagneticFieldState internal constructor(
    val xStrength: Float = 0f,
    val yStrength: Float = 0f,
    val zStrength: Float = 0f,
) : SensorState {
    override val isAvailable: Boolean
        @Composable
        get() = SensorType.MagneticField.isSensorAvailable()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MagneticFieldState) return false

        if (xStrength != other.xStrength) return false
        if (yStrength != other.yStrength) return false
        if (zStrength != other.zStrength) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xStrength.hashCode()
        result = 31 * result + yStrength.hashCode()
        result = 31 * result + zStrength.hashCode()
        return result
    }

    override fun toString(): String {
        return "MagneticFieldState(xStrength=$xStrength, yStrength=$yStrength, " +
                "zStrength=$zStrength)"
    }
}

/**
 * Creates and remembers an instance of [MagneticFieldState].
 */
@Composable
fun rememberMagneticFieldState(): State<MagneticFieldState> {
    val sensorState = rememberSensorState(sensorType = SensorType.MagneticField)
    val magneticFieldState = remember { mutableStateOf(MagneticFieldState()) }

    LaunchedEffect(
        key1 = sensorState.value,
        block = {
            val sensorStateValues = sensorState.value
            if (sensorStateValues.isNotEmpty()) {
                magneticFieldState.value = MagneticFieldState(
                    xStrength = sensorStateValues[0],
                    yStrength = sensorStateValues[1],
                    zStrength = sensorStateValues[2]
                )
            }
        }
    )

    return magneticFieldState
}
