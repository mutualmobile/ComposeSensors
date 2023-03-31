# Compose Sensors
<img src="https://raw.githubusercontent.com/mutualmobile/ComposeSensors/main/art/thumnail.png" width=200 />

[![Sonatype](https://img.shields.io/nexus/s/com.mutualmobile/composesensors?server=https%3A%2F%2Foss.sonatype.org)](https://oss.sonatype.org/#nexus-search;gav~com.mutualmobile~composesensors~~~)

Accessing sensor data of your Android devices just became quick and easy ⚡️

This library provides a convenience wrapper over the [Sensor APIs](https://developer.android.com/guide/topics/sensors/sensors_overview) for Android to be used with Jetpack Compose.

## WIP 🚧
This library is a work-in-progress and is subject to major changes. Our team is working hard to get it stable as soon as possible. Thank you for your patience 🌺

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

### Snapshots 📸
Add `https://oss.sonatype.org/content/repositories/snapshots` as a maven repository resource to be able to access SNAPSHOT versions of this library. Check `tags` for the latest library version.
#### Kotlin `settings.gradle.kts`
```
dependencyResolutionManagement {
    ...
    repositories {
        ...
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}
```
#### Groovy `settings.gradle`
```
dependencyResolutionManagement {
    ...
    repositories {
        ...
        maven {
            url = 'https://oss.sonatype.org/content/repositories/snapshots'
        }
    }
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
ComposeSensors plans to support the following Android sensors:
Sensor  | Status | Composable
------------- | ------------- | -------------
Accelerometer  | ✅ | rememberAccelerometerSensorState()
Magnetic Field  | ✅ | rememberMagneticFieldSensorState()
Gyroscope  | ✅ | rememberGyroscopeSensorState()
Light  | ✅️ | rememberLightSensorState()
Pressure | ✅️ | rememberPressureSensorState()
Proximity | ✅️️ | rememberProximitySensorState()
Gravity | ✅️ | rememberGravitySensorState()
Linear Acceleration | ✅️ | rememberLinearAccelerationSensorState()
Rotation Vector | ⚠️ | WIP
Relative Humidity | ⚠️ | WIP
Ambient Temperature | ⚠️ | WIP
Magnetic Field (Uncalibrated) | ⚠️ | WIP
GameRotation Vector | ⚠️ | WIP
Gyroscope (Uncalibrated) | ⚠️ | WIP
Significant Motion | ⚠️ | WIP
Step Detector | ✅️ | rememberStepDetectorSensorState()
Step Counter | ✅️ | rememberStepCounterSensorState()
Geomagnetic Rotation Vector | ⚠️ | WIP
Heart Rate | ⚠️ | WIP
Pose6DOF | ⚠️ | WIP
Stationary Detect | ⚠️ | WIP
Motion Detect | ⚠️ | WIP
Heart Beat | ⚠️ | WIP
Low Latency Off-Body Detect | ⚠️ | WIP
Accelerometer (Uncalibrated) | ⚠️ | WIP
Hinge Angle | ⚠️ | WIP
Head Tracker | ⚠️ | WIP
Accelerometer Limited Axes | ⚠️ | WIP
Gyroscope Limited Axes | ⚠️ | WIP
Accelerometer Limited Axes (Uncalibrated) | ⚠️ | WIP
Gyroscope Limited Axes (Uncalibrated) | ⚠️ | WIP
Heading | ⚠️ | WIP
All | ⚠️ | WIP

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
