# Compose Sensors
<img src="https://raw.githubusercontent.com/mutualmobile/ComposeSensors/main/art/thumnail.png" width=200 />

[![Sonatype](https://img.shields.io/nexus/r/com.mutualmobile/composesensors?server=https%3A%2F%2Foss.sonatype.org%2F)](https://oss.sonatype.org/#nexus-search;gav~com.mutualmobile~composesensors~~~)

Accessing sensor data of your Android devices just became quick and easy ⚡️

This library provides a convenience wrapper over the [Sensor APIs](https://developer.android.com/guide/topics/sensors/sensors_overview) for Android to be used with Jetpack Compose.

## Demo ❤️
![Screen Recording 2023-04-03 at 1 00 08 PM](https://user-images.githubusercontent.com/89389061/229441943-6339d18f-c704-4d92-9fe8-28c2fd94fdeb.gif)

## Usage 🚀
### Install dependency 📲
#### Kotlin `build.gradle.kts (:module-name)`
```
dependencies {
    ...
    implementation("com.mutualmobile:composesensors:x.y.z")
}
```
#### Groovy `build.gradle (:module-name)`
```
dependencies {
    ...
    implementation 'com.mutualmobile:composesensors:x.y.z'
}
```

The library provides straightforward state methods for multiple sensors like Accelerometer, Gyroscope, etc (more mentioned below). Following is an example on how to get the current values from the `Accelerometer`:
```
val accelerometerState = rememberAccelerometerSensorState()
```
Use it in an example:
```
val accelerometerState = rememberAccelerometerSensorState()
// Optional: You could also write: rememberAccelerometerSensorState(sensorDelay = SensorDelay.Fastest) for fetching sensor data faster

Text(
    text = "Force X: ${accelerometerState.xForce}" +
    "\nForce Y: ${accelerometerState.yForce}" +
    "\nForce Z: ${accelerometerState.zForce}" +
    "\nIs Available?: ${accelerometerState.isAvailable}"
)
```

## Sensors Supported 📱
ComposeSensors supports the following Android sensors:
Sensor | Composable
------------- | -------------
Accelerometer | rememberAccelerometerSensorState()
Magnetic Field | rememberMagneticFieldSensorState()
Gyroscope | rememberGyroscopeSensorState()
Light  | rememberLightSensorState()
Pressure | rememberPressureSensorState()
Proximity | rememberProximitySensorState()
Gravity | rememberGravitySensorState()
Linear Acceleration | rememberLinearAccelerationSensorState()
Rotation Vector | rememberRotationVectorSensorState()
Relative Humidity | rememberRelativeHumiditySensorState()
Ambient Temperature | rememberAmbientTemperatureSensorState()
Magnetic Field (Uncalibrated)️ | rememberUncalibratedMagneticFieldSensorState()
GameRotation Vector | rememberGameRotationVectorSensorState()
Gyroscope (Uncalibrated) | rememberUncalibratedGyroscopeSensorState()
Significant Motion| rememberSignificantMotionSensorState(onMotionEvent = {})
Step Detector | rememberStepDetectorSensorState()
Step Counter | rememberStepCounterSensorState()
Geomagnetic Rotation Vector | rememberGeomagneticRotationVectorSensorState()
Heart Rate | rememberHeartRateSensorState()
Pose6DOF| rememberPose6DOFSensorState()
Stationary Detect | rememberStationaryDetectSensorState()
Motion Detect | rememberMotionDetectSensorState()
Heart Beat| rememberHeartBeatSensorState()
Low Latency Off-Body Detect | rememberLowLatencyOffBodyDetectSensorState()
Accelerometer (Uncalibrated) | rememberUncalibratedAccelerometerSensorState()
Hinge Angle | rememberHingeAngleSensorState()
Head Tracker | rememberHeadTrackerSensorState()
Accelerometer Limited Axes | rememberLimitedAxesAccelerometerSensorState()
Gyroscope Limited Axes | rememberLimitedAxesGyroscopeSensorState()
Accelerometer Limited Axes (Uncalibrated) | rememberUncalibratedLimitedAxesAccelerometerSensorState()
Gyroscope Limited Axes (Uncalibrated) | rememberUncalibratedLimitedAxesGyroscopeSensorState()
Heading | rememberHeadingSensorState()

## License 🔖
```
Copyright 2023 MutualMobile

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
