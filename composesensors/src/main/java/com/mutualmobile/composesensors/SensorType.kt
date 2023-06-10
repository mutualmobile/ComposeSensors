package com.mutualmobile.composesensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

sealed class SensorType(val name: String) {
    object Accelerometer : SensorType(name = "Accelerometer")
    object MagneticField : SensorType(name = "MagneticField")
    object Gyroscope : SensorType(name = "Gyroscope")
    object Light : SensorType(name = "Light")
    object Pressure : SensorType(name = "Pressure")
    object Proximity : SensorType(name = "Proximity")
    object Gravity : SensorType(name = "Gravity")
    object LinearAcceleration : SensorType(name = "LinearAcceleration")
    object RotationVector : SensorType(name = "RotationVector")
    object RelativeHumidity : SensorType(name = "RelativeHumidity")
    object AmbientTemperature : SensorType(name = "AmbientTemperature")
    object MagneticFieldUncalibrated : SensorType(name = "MagneticFieldUncalibrated")
    object GameRotationVector : SensorType(name = "GameRotationVector")
    object GyroscopeUncalibrated : SensorType(name = "GyroscopeUncalibrated")
    object SignificantMotion : SensorType(name = "SignificantMotion")
    object StepDetector : SensorType(name = "StepDetector")
    object StepCounter : SensorType(name = "StepCounter")
    object GeomagneticRotationVector : SensorType(name = "GeomagneticRotationVector")
    object HeartRate : SensorType(name = "HeartRate")
    object Pose6DOF : SensorType(name = "Pose6DOF")
    object StationaryDetect : SensorType(name = "StationaryDetect")
    object MotionDetect : SensorType(name = "MotionDetect")
    object HeartBeat : SensorType(name = "HeartBeat")
    object LowLatencyOffBodyDetect : SensorType(name = "LowLatencyOffBodyDetect")
    object AccelerometerUncalibrated : SensorType(name = "AccelerometerUncalibrated")
    object HingeAngle : SensorType(name = "HingeAngle")
    object HeadTracker : SensorType(name = "HeadTracker")
    object AccelerometerLimitedAxes : SensorType(name = "AccelerometerLimitedAxes")
    object GyroscopeLimitedAxes : SensorType(name = "GyroscopeLimitedAxes")
    object AccelerometerLimitedAxesUncalibrated :
        SensorType(name = "AccelerometerLimitedAxesUncalibrated")

    object GyroscopeLimitedAxesUncalibrated : SensorType(name = "GyroscopeLimitedAxesUncalibrated")
    object Heading : SensorType(name = "Heading")

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
            "this sensor."
    )
