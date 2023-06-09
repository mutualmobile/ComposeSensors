package com.mutualmobile.sample.ui.screens.sensorlist

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mutualmobile.composesensors.rememberAccelerometerSensorState
import com.mutualmobile.composesensors.rememberAmbientTemperatureSensorState
import com.mutualmobile.composesensors.rememberGameRotationVectorSensorState
import com.mutualmobile.composesensors.rememberGeomagneticRotationVectorSensorState
import com.mutualmobile.composesensors.rememberGravitySensorState
import com.mutualmobile.composesensors.rememberGyroscopeSensorState
import com.mutualmobile.composesensors.rememberHeadTrackerSensorState
import com.mutualmobile.composesensors.rememberHeadingSensorState
import com.mutualmobile.composesensors.rememberHeartBeatSensorState
import com.mutualmobile.composesensors.rememberHeartRateSensorState
import com.mutualmobile.composesensors.rememberHingeAngleSensorState
import com.mutualmobile.composesensors.rememberLightSensorState
import com.mutualmobile.composesensors.rememberLimitedAxesAccelerometerSensorState
import com.mutualmobile.composesensors.rememberLimitedAxesGyroscopeSensorState
import com.mutualmobile.composesensors.rememberLinearAccelerationSensorState
import com.mutualmobile.composesensors.rememberLowLatencyOffBodyDetectSensorState
import com.mutualmobile.composesensors.rememberMagneticFieldSensorState
import com.mutualmobile.composesensors.rememberMotionDetectSensorState
import com.mutualmobile.composesensors.rememberPressureSensorState
import com.mutualmobile.composesensors.rememberProximitySensorState
import com.mutualmobile.composesensors.rememberRotationVectorSensorState
import com.mutualmobile.composesensors.rememberStationaryDetectSensorState
import com.mutualmobile.composesensors.rememberStepCounterSensorState
import com.mutualmobile.composesensors.rememberStepDetectorSensorState
import com.mutualmobile.composesensors.rememberUncalibratedAccelerometerSensorState
import com.mutualmobile.composesensors.rememberUncalibratedGyroscopeSensorState
import com.mutualmobile.composesensors.rememberUncalibratedLimitedAxesAccelerometerSensorState
import com.mutualmobile.composesensors.rememberUncalibratedMagneticFieldSensorState
import com.mutualmobile.sample.R
import com.mutualmobile.sample.ui.screens.sensorlist.components.CSButton
import com.mutualmobile.sample.ui.screens.sensorlist.components.CSButtonPosition
import com.mutualmobile.sample.ui.screens.sensorlist.components.SensorItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SensorsListScreen() {
    var isTopBarTitleCollapsed by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    // TODO: Keep this updated until a better option is found
    val totalPageCount = rememberSaveable { 28 }
    val accelerometerState = rememberAccelerometerSensorState()
    val magneticFieldState = rememberMagneticFieldSensorState()
    val gyroscopeState = rememberGyroscopeSensorState()
    val lightState = rememberLightSensorState()
    val pressureState = rememberPressureSensorState()
    val proximityState = rememberProximitySensorState()
    val gravityState = rememberGravitySensorState()
    val linearAccelerationState = rememberLinearAccelerationSensorState()
    val rotationVectorState = rememberRotationVectorSensorState()
    val ambientTemperatureState = rememberAmbientTemperatureSensorState()
    val uncalibratedMagneticFieldState = rememberUncalibratedMagneticFieldSensorState()
    val gameRotationVectorState = rememberGameRotationVectorSensorState()
    val uncalibratedGyroscopeState = rememberUncalibratedGyroscopeSensorState()
    val stepDetectState = rememberStepDetectorSensorState()
    val stepCounterState = rememberStepCounterSensorState()
    val geomagneticRotationVectorState = rememberGeomagneticRotationVectorSensorState()
    val heartRateState = rememberHeartRateSensorState()
    val stationaryDetectState = rememberStationaryDetectSensorState()
    val motionDetectState = rememberMotionDetectSensorState()
    val heartBeatState = rememberHeartBeatSensorState()
    val lowLatencyOffBodyDetectState = rememberLowLatencyOffBodyDetectSensorState()
    val uncalibratedAccelerometerState = rememberUncalibratedAccelerometerSensorState()
    val hingeAngleState = rememberHingeAngleSensorState()
    val headTrackerState = rememberHeadTrackerSensorState()
    val limitedAxesAccelerometerState = rememberLimitedAxesAccelerometerSensorState()
    val limitedAxesGyroscopeState = rememberLimitedAxesGyroscopeSensorState()
    val uncalibratedLimitedAxesAccelerometerState =
        rememberUncalibratedLimitedAxesAccelerometerSensorState()
    val headingState = rememberHeadingSensorState()

    // Trigger TopBar animation once
    LaunchedEffect(Unit) {
        delay(2000)
        isTopBarTitleCollapsed = true
        delay(2000)
        isTopBarTitleCollapsed = false
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { isTopBarTitleCollapsed = !isTopBarTitleCollapsed }
                        ) {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.app_logo),
                                contentDescription = null
                            )
                        }
                        AnimatedVisibility(
                            visible = !isTopBarTitleCollapsed
                        ) {
                            Text(text = "ComposeSensors")
                        }
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CSButton(
                    text = "Previous",
                    icon = Icons.Default.KeyboardArrowLeft,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage - 1,
                                animationSpec = tween(durationMillis = 700)
                            )
                        }
                    },
                    onLongClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(0)
                        }
                    },
                    position = CSButtonPosition.Start,
                    enabled = pagerState.currentPage != 0
                )
                CSButton(
                    text = "Next",
                    icon = Icons.Default.KeyboardArrowRight,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage + 1,
                                animationSpec = tween(durationMillis = 700)
                            )
                        }
                    },
                    onLongClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(totalPageCount)
                        }
                    },
                    position = CSButtonPosition.End,
                    enabled = pagerState.currentPage != totalPageCount - 1
                )
            }
        }
    ) {
        HorizontalPager(
            pageCount = totalPageCount,
            state = pagerState,
            contentPadding = PaddingValues(32.dp),
            beyondBoundsPageCount = 1,
            userScrollEnabled = false
        ) { index ->
            val scrollProgress = remember {
                derivedStateOf {
                    pagerState.currentOffsetForPage(index)
                }
            }
            SensorItem(
                name = when (index) {
                    0 -> "Accelerometer"
                    1 -> "Magnetic Field"
                    2 -> "Gyroscope"
                    3 -> "Light"
                    4 -> "Pressure"
                    5 -> "Proximity"
                    6 -> "Gravity"
                    7 -> "Linear Acceleration"
                    8 -> "Rotation Vector"
                    9 -> "Ambient Temperature"
                    10 -> "Magnetic Field (Uncalibrated)"
                    11 -> "Game Rotation Vector"
                    12 -> "Gyroscope (Uncalibrated)"
                    13 -> "Step Detector"
                    14 -> "Step Counter"
                    15 -> "Geomagnetic Rotation Vector"
                    16 -> "Heart Rate"
                    17 -> "Stationary Detect"
                    18 -> "Motion Detect"
                    19 -> "Heart Beat"
                    20 -> "Low Latency Off-Body Detect"
                    21 -> "Accelerometer (Uncalibrated)"
                    22 -> "Hinge Angle"
                    23 -> "Head Tracker"
                    24 -> "Accelerometer (Limited Axes)"
                    25 -> "Gyroscope (Limited Axes)"
                    26 -> "Accelerometer (Uncalibrated - Limited Axes)"
                    27 -> "Heading"
                    else -> error("Invalid index '$index'")
                },
                scrollProgress = scrollProgress,
                imageRef = when (index) {
                    0 -> R.drawable.accelerometer
                    1 -> R.drawable.magnetic_field
                    2 -> R.drawable.gyroscope
                    3 -> R.drawable.light
                    4 -> R.drawable.pressure
                    5 -> R.drawable.proximity
                    6 -> R.drawable.gravity
                    7 -> R.drawable.linear_acceleration
                    8 -> R.drawable.rotation_vector
                    9 -> R.drawable.ambient_temperature
                    10 -> R.drawable.magnetic_field
                    11 -> R.drawable.game_rotation_vector
                    12 -> R.drawable.gyroscope
                    13 -> R.drawable.step_detection
                    14 -> R.drawable.step_counter
                    15 -> R.drawable.geomagnetic_rotation_vector
                    16 -> R.drawable.heart_rate
                    17 -> R.drawable.stationary_detect
                    18 -> R.drawable.motion_detect
                    19 -> R.drawable.heart_rate
                    20 -> R.drawable.low_latency_off_body_detect
                    21 -> R.drawable.accelerometer
                    22 -> R.drawable.hinge_angle
                    23 -> R.drawable.head_tracker
                    24 -> R.drawable.accelerometer
                    25 -> R.drawable.gyroscope
                    26 -> R.drawable.accelerometer
                    27 -> R.drawable.heading
                    else -> error("Invalid index '$index'")
                },
                sensorValues = when (index) {
                    0 -> mapOf(
                        "Force X" to accelerometerState.xForce,
                        "Force Y" to accelerometerState.yForce,
                        "Force Z" to accelerometerState.zForce
                    )

                    1 -> mapOf(
                        "Strength X" to magneticFieldState.xStrength,
                        "Strength Y" to magneticFieldState.yStrength,
                        "Strength Z" to magneticFieldState.zStrength
                    )

                    2 -> mapOf(
                        "Rotation X" to gyroscopeState.xRotation,
                        "Rotation Y" to gyroscopeState.yRotation,
                        "Rotation Z" to gyroscopeState.zRotation
                    )

                    3 -> mapOf("Illuminance" to lightState.illuminance)

                    4 -> mapOf("Pressure" to pressureState.pressure)

                    5 -> mapOf("Distance" to proximityState.sensorDistance)

                    6 -> mapOf(
                        "Force X" to gravityState.xForce,
                        "Force Y" to gravityState.yForce,
                        "Force Z" to gravityState.zForce
                    )

                    7 -> mapOf(
                        "Force X" to linearAccelerationState.xForce,
                        "Force Y" to linearAccelerationState.yForce,
                        "Force Z" to linearAccelerationState.zForce
                    )

                    8 -> mapOf(
                        "Vector X" to rotationVectorState.vectorX,
                        "Vector Y" to rotationVectorState.vectorY,
                        "Vector Z" to rotationVectorState.vectorZ,
                        "Scalar" to rotationVectorState.scalar,
                        "Heading Accuracy" to rotationVectorState.estimatedHeadingAccuracy
                    )

                    9 -> mapOf("Temperature" to ambientTemperatureState.temperature)

                    10 -> mapOf(
                        "Strength X" to uncalibratedMagneticFieldState.xStrength,
                        "Strength Y" to uncalibratedMagneticFieldState.yStrength,
                        "Strength Z" to uncalibratedMagneticFieldState.zStrength,
                        "Bias X" to uncalibratedMagneticFieldState.xBias,
                        "Bias Y" to uncalibratedMagneticFieldState.yBias,
                        "Bias Z" to uncalibratedMagneticFieldState.zBias
                    )

                    11 -> mapOf(
                        "Vector X" to gameRotationVectorState.vectorX,
                        "Vector Y" to gameRotationVectorState.vectorY,
                        "Vector Z" to gameRotationVectorState.vectorZ
                    )

                    12 -> mapOf(
                        "Rotation X" to uncalibratedGyroscopeState.xRotation,
                        "Rotation Y" to uncalibratedGyroscopeState.yRotation,
                        "Rotation Z" to uncalibratedGyroscopeState.zRotation,
                        "Bias X" to uncalibratedGyroscopeState.xBias,
                        "Bias Y" to uncalibratedGyroscopeState.yBias,
                        "Bias Z" to uncalibratedGyroscopeState.zBias
                    )

                    13 -> mapOf("Step Count" to stepDetectState.stepCount)

                    14 -> mapOf("Step Count" to stepCounterState.stepCount)

                    15 -> mapOf(
                        "Vector X" to geomagneticRotationVectorState.vectorX,
                        "Vector Y" to geomagneticRotationVectorState.vectorY,
                        "Vector Z" to geomagneticRotationVectorState.vectorZ
                    )

                    16 -> mapOf("Heart Rate" to heartRateState.heartRate)

                    17 -> mapOf("Is device stationary?" to stationaryDetectState.isDeviceStationary)
                    18 -> mapOf("Is device moving?" to motionDetectState.isDeviceInMotion)

                    19 -> mapOf("Is confident peak?" to heartBeatState.isConfidentPeak)

                    20 -> mapOf("Is On Body?" to lowLatencyOffBodyDetectState.isDeviceOnBody)

                    21 -> mapOf(
                        "Force X" to uncalibratedAccelerometerState.xForce,
                        "Force Y" to uncalibratedAccelerometerState.yForce,
                        "Force Z" to uncalibratedAccelerometerState.zForce,
                        "Bias X" to uncalibratedAccelerometerState.xBias,
                        "Bias Y" to uncalibratedAccelerometerState.yBias,
                        "Bias Z" to uncalibratedAccelerometerState.zBias
                    )

                    22 -> mapOf("Angle" to hingeAngleState.angle)

                    23 -> mapOf(
                        "Rotation X" to headTrackerState.xRotation,
                        "Rotation Y" to headTrackerState.yRotation,
                        "Rotation Z" to headTrackerState.zRotation,
                        "Velocity X" to headTrackerState.xAngularVelocity,
                        "Velocity Y" to headTrackerState.yAngularVelocity,
                        "Velocity Z" to headTrackerState.zAngularVelocity
                    )

                    24 -> mapOf(
                        "Force X" to limitedAxesAccelerometerState.xForce,
                        "Force Y" to limitedAxesAccelerometerState.yForce,
                        "Force Z" to limitedAxesAccelerometerState.zForce,
                        "X Supported?" to limitedAxesAccelerometerState.xAxisSupported,
                        "Y Supported?" to limitedAxesAccelerometerState.yAxisSupported,
                        "Z Supported?" to limitedAxesAccelerometerState.zAxisSupported
                    )

                    25 -> mapOf(
                        "Rotation X" to limitedAxesGyroscopeState.xRotation,
                        "Rotation Y" to limitedAxesGyroscopeState.yRotation,
                        "Rotation Z" to limitedAxesGyroscopeState.zRotation,
                        "X Supported?" to limitedAxesGyroscopeState.xAxisSupported,
                        "Y Supported?" to limitedAxesGyroscopeState.yAxisSupported,
                        "Z Supported?" to limitedAxesGyroscopeState.zAxisSupported
                    )

                    26 -> mapOf(
                        "Rotation X" to uncalibratedLimitedAxesAccelerometerState.xForce,
                        "Rotation Y" to uncalibratedLimitedAxesAccelerometerState.yForce,
                        "Rotation Z" to uncalibratedLimitedAxesAccelerometerState.zForce,
                        "Bias X" to uncalibratedLimitedAxesAccelerometerState.xBias,
                        "Bias Y" to uncalibratedLimitedAxesAccelerometerState.yBias,
                        "Bias Z" to uncalibratedLimitedAxesAccelerometerState.zBias,
                        "X Supported?" to uncalibratedLimitedAxesAccelerometerState.xAxisSupported,
                        "Y Supported?" to uncalibratedLimitedAxesAccelerometerState.yAxisSupported,
                        "Z Supported?" to uncalibratedLimitedAxesAccelerometerState.zAxisSupported
                    )

                    27 -> mapOf(
                        "Degrees" to headingState.degrees
                    )

                    else -> error("Invalid index '$index'")
                },
                isAvailable = when (index) {
                    0 -> accelerometerState.isAvailable
                    1 -> magneticFieldState.isAvailable
                    2 -> gyroscopeState.isAvailable
                    3 -> lightState.isAvailable
                    4 -> pressureState.isAvailable
                    5 -> proximityState.isAvailable
                    6 -> gravityState.isAvailable
                    7 -> linearAccelerationState.isAvailable
                    8 -> rotationVectorState.isAvailable
                    9 -> ambientTemperatureState.isAvailable
                    10 -> uncalibratedMagneticFieldState.isAvailable
                    11 -> gameRotationVectorState.isAvailable
                    12 -> uncalibratedGyroscopeState.isAvailable
                    13 -> stepDetectState.isAvailable
                    14 -> stepCounterState.isAvailable
                    15 -> geomagneticRotationVectorState.isAvailable
                    16 -> heartRateState.isAvailable
                    17 -> stationaryDetectState.isAvailable
                    18 -> motionDetectState.isAvailable
                    19 -> heartBeatState.isAvailable
                    20 -> lowLatencyOffBodyDetectState.isAvailable
                    21 -> uncalibratedAccelerometerState.isAvailable
                    22 -> hingeAngleState.isAvailable
                    23 -> headTrackerState.isAvailable
                    24 -> limitedAxesAccelerometerState.isAvailable
                    25 -> limitedAxesGyroscopeState.isAvailable
                    26 -> uncalibratedLimitedAxesAccelerometerState.isAvailable
                    27 -> headingState.isAvailable
                    else -> error("Invalid index '$index'")
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.currentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}
