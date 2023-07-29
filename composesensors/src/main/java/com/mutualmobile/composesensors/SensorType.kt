package com.mutualmobile.composesensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

sealed class SensorType(val name: String) {
    data object Accelerometer : SensorType(name = "Accelerometer")
    data object MagneticField : SensorType(name = "MagneticField")
    data object Gyroscope : SensorType(name = "Gyroscope")
    data object Light : SensorType(name = "Light")
    data object Pressure : SensorType(name = "Pressure")
    data object Proximity : SensorType(name = "Proximity")
    data object Gravity : SensorType(name = "Gravity")
    data object LinearAcceleration : SensorType(name = "LinearAcceleration")
    data object RotationVector : SensorType(name = "RotationVector")
    data object RelativeHumidity : SensorType(name = "RelativeHumidity")
    data object AmbientTemperature : SensorType(name = "AmbientTemperature")
    data object MagneticFieldUncalibrated : SensorType(name = "MagneticFieldUncalibrated")
    data object GameRotationVector : SensorType(name = "GameRotationVector")
    data object GyroscopeUncalibrated : SensorType(name = "GyroscopeUncalibrated")
    data object SignificantMotion : SensorType(name = "SignificantMotion")
    data object StepDetector : SensorType(name = "StepDetector")
    data object StepCounter : SensorType(name = "StepCounter")
    data object GeomagneticRotationVector : SensorType(name = "GeomagneticRotationVector")
    data object HeartRate : SensorType(name = "HeartRate")
    data object Pose6DOF : SensorType(name = "Pose6DOF")
    data object StationaryDetect : SensorType(name = "StationaryDetect")
    data object MotionDetect : SensorType(name = "MotionDetect")
    data object HeartBeat : SensorType(name = "HeartBeat")
    data object LowLatencyOffBodyDetect : SensorType(name = "LowLatencyOffBodyDetect")
    data object AccelerometerUncalibrated : SensorType(name = "AccelerometerUncalibrated")
    data object HingeAngle : SensorType(name = "HingeAngle")
    data object HeadTracker : SensorType(name = "HeadTracker")
    data object AccelerometerLimitedAxes : SensorType(name = "AccelerometerLimitedAxes")
    data object GyroscopeLimitedAxes : SensorType(name = "GyroscopeLimitedAxes")
    data object AccelerometerLimitedAxesUncalibrated :
        SensorType(name = "AccelerometerLimitedAxesUncalibrated")

    data object GyroscopeLimitedAxesUncalibrated : SensorType(name = "GyroscopeLimitedAxesUncalibrated")
    data object Heading : SensorType(name = "Heading")

    fun toAndroidSensorType(): Int {
        return when (this) {
            is Accelerometer -> Sensor.TYPE_ACCELEROMETER
            is MagneticField -> Sensor.TYPE_MAGNETIC_FIELD
            is Gyroscope -> Sensor.TYPE_GYROSCOPE
            is Light -> Sensor.TYPE_LIGHT
            is Pressure -> Sensor.TYPE_PRESSURE
            is Proximity -> Sensor.TYPE_PROXIMITY
            is Gravity -> Sensor.TYPE_GRAVITY
            is LinearAcceleration -> Sensor.TYPE_LINEAR_ACCELERATION
            is RotationVector -> Sensor.TYPE_ROTATION_VECTOR
            is RelativeHumidity -> Sensor.TYPE_RELATIVE_HUMIDITY
            is AmbientTemperature -> Sensor.TYPE_AMBIENT_TEMPERATURE
            is MagneticFieldUncalibrated -> Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED
            is GameRotationVector -> Sensor.TYPE_GAME_ROTATION_VECTOR
            is GyroscopeUncalibrated -> Sensor.TYPE_GYROSCOPE_UNCALIBRATED
            is SignificantMotion -> Sensor.TYPE_SIGNIFICANT_MOTION
            is StepDetector -> Sensor.TYPE_STEP_DETECTOR
            is StepCounter -> Sensor.TYPE_STEP_COUNTER
            is GeomagneticRotationVector -> Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR
            is HeartRate -> Sensor.TYPE_HEART_RATE
            is Pose6DOF -> checkApi(Build.VERSION_CODES.N) { Sensor.TYPE_POSE_6DOF }
            is StationaryDetect -> checkApi(Build.VERSION_CODES.N) { Sensor.TYPE_STATIONARY_DETECT }
            is MotionDetect -> checkApi(Build.VERSION_CODES.N) { Sensor.TYPE_MOTION_DETECT }
            is HeartBeat -> checkApi(Build.VERSION_CODES.N) { Sensor.TYPE_HEART_BEAT }
            is LowLatencyOffBodyDetect -> checkApi(Build.VERSION_CODES.O) {
                Sensor.TYPE_LOW_LATENCY_OFFBODY_DETECT
            }

            is AccelerometerUncalibrated -> checkApi(Build.VERSION_CODES.O) {
                Sensor.TYPE_ACCELEROMETER_UNCALIBRATED
            }

            is HingeAngle -> checkApi(Build.VERSION_CODES.R) { Sensor.TYPE_HINGE_ANGLE }
            is HeadTracker -> checkApi(Build.VERSION_CODES.TIRAMISU) { Sensor.TYPE_HEAD_TRACKER }
            is AccelerometerLimitedAxes -> checkApi(Build.VERSION_CODES.TIRAMISU) {
                Sensor.TYPE_ACCELEROMETER_LIMITED_AXES
            }

            is GyroscopeLimitedAxes -> checkApi(Build.VERSION_CODES.TIRAMISU) {
                Sensor.TYPE_GYROSCOPE_LIMITED_AXES
            }

            is AccelerometerLimitedAxesUncalibrated -> checkApi(Build.VERSION_CODES.TIRAMISU) {
                Sensor.TYPE_ACCELEROMETER_LIMITED_AXES_UNCALIBRATED
            }

            is GyroscopeLimitedAxesUncalibrated -> checkApi(Build.VERSION_CODES.TIRAMISU) {
                Sensor.TYPE_GYROSCOPE_LIMITED_AXES_UNCALIBRATED
            }

            is Heading -> checkApi(Build.VERSION_CODES.TIRAMISU) { Sensor.TYPE_HEADING }
        }
    }

    @Composable
    internal fun rememberIsSensorAvailable(): Boolean {
        val context = LocalContext.current
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        return remember(context, sensorManager) {
            sensorManager.getDefaultSensor(this.toAndroidSensorType()) != null
        }
    }
}

private inline fun <T> checkApi(expectedApi: Int, block: () -> T): T {
    return if (Build.VERSION.SDK_INT < expectedApi) {
        throw OldApiException(
            currentApi = Build.VERSION.SDK_INT,
            expectedApi = expectedApi
        )
    } else {
        block()
    }
}

private class OldApiException(currentApi: Int, expectedApi: Int) :
    Exception(
        "The current API ($currentApi) is too low. At least API ($expectedApi) is required to use" +
            " this sensor."
    )
