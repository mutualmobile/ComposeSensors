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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mutualmobile.composesensors.AccelerometerSensorState
import com.mutualmobile.composesensors.AmbientTemperatureSensorState
import com.mutualmobile.composesensors.GameRotationVectorSensorState
import com.mutualmobile.composesensors.GeomagneticRotationVectorSensorState
import com.mutualmobile.composesensors.GravitySensorState
import com.mutualmobile.composesensors.GyroscopeSensorState
import com.mutualmobile.composesensors.HeadTrackerSensorState
import com.mutualmobile.composesensors.HeadingSensorState
import com.mutualmobile.composesensors.HeartBeatSensorState
import com.mutualmobile.composesensors.HeartRateSensorState
import com.mutualmobile.composesensors.HingeAngleSensorState
import com.mutualmobile.composesensors.LightSensorState
import com.mutualmobile.composesensors.LimitedAxesAccelerometerSensorState
import com.mutualmobile.composesensors.LimitedAxesGyroscopeSensorState
import com.mutualmobile.composesensors.LinearAccelerationSensorState
import com.mutualmobile.composesensors.LowLatencyOffBodyDetectSensorState
import com.mutualmobile.composesensors.MagneticFieldSensorState
import com.mutualmobile.composesensors.MotionDetectSensorState
import com.mutualmobile.composesensors.PressureSensorState
import com.mutualmobile.composesensors.ProximitySensorState
import com.mutualmobile.composesensors.RotationVectorSensorState
import com.mutualmobile.composesensors.SensorStateListener
import com.mutualmobile.composesensors.SignificantMotionSensorState
import com.mutualmobile.composesensors.StationaryDetectSensorState
import com.mutualmobile.composesensors.StepCounterSensorState
import com.mutualmobile.composesensors.StepDetectorSensorState
import com.mutualmobile.composesensors.UncalibratedAccelerometerSensorState
import com.mutualmobile.composesensors.UncalibratedGyroscopeSensorState
import com.mutualmobile.composesensors.UncalibratedLimitedAxesAccelerometerSensorState
import com.mutualmobile.composesensors.UncalibratedMagneticFieldSensorState
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
import com.mutualmobile.composesensors.rememberSignificantMotionSensorState
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

private val sensorStateList: List<SensorStateListener>
    @SuppressLint("NewApi")
    @Composable
    get() = listOf(
        rememberAccelerometerSensorState(),
        rememberMagneticFieldSensorState(),
        rememberGyroscopeSensorState(),
        rememberLightSensorState(),
        rememberPressureSensorState(),
        rememberProximitySensorState(),
        rememberGravitySensorState(),
        rememberLinearAccelerationSensorState(),
        rememberRotationVectorSensorState(),
        rememberAmbientTemperatureSensorState(),
        rememberUncalibratedMagneticFieldSensorState(),
        rememberGameRotationVectorSensorState(),
        rememberUncalibratedGyroscopeSensorState(),
        rememberSignificantMotionSensorState(onMotionEvent = {}),
        rememberStepDetectorSensorState(),
        rememberStepCounterSensorState(),
        rememberGeomagneticRotationVectorSensorState(),
        rememberHeartRateSensorState(),
        rememberStationaryDetectSensorState(),
        rememberMotionDetectSensorState(),
        rememberHeartBeatSensorState(),
        rememberLowLatencyOffBodyDetectSensorState(),
        rememberUncalibratedAccelerometerSensorState(),
        rememberHingeAngleSensorState(),
        rememberHeadTrackerSensorState(),
        rememberLimitedAxesAccelerometerSensorState(),
        rememberLimitedAxesGyroscopeSensorState(),
        rememberUncalibratedLimitedAxesAccelerometerSensorState(),
        rememberHeadingSensorState()
    )

// Please note this sample app will only work on Android 13 and above because we're also consuming
// sensor data that is only available on Android 13 and above and not making API level checks
// for every sensor yet.
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SensorsListScreen() {
    var isTopBarTitleCollapsed by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val sensorStates by rememberUpdatedState(sensorStateList)
    val pagerState = rememberPagerState { sensorStates.size }

    // Trigger TopBar animation once
    LaunchedEffect(Unit) {
        delay(2000)
        isTopBarTitleCollapsed = true
        delay(2000)
        isTopBarTitleCollapsed = false
    }

    LaunchedEffect(pagerState.currentPage) {
        sensorStates.forEachIndexed { index, listener ->
            if (pagerState.currentPage == index) {
                listener.startListening()
            } else {
                listener.stopListening()
            }
        }
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
                            pagerState.scrollToPage(sensorStates.size - 1)
                        }
                    },
                    position = CSButtonPosition.End,
                    enabled = pagerState.currentPage != sensorStates.size - 1
                )
            }
        }
    ) {
        HorizontalPager(
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
            when (val state = sensorStates[index]) {
                is AccelerometerSensorState -> {
                    SensorItem(
                        name = "Accelerometer",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.accelerometer,
                        sensorValues = mapOf(
                            "Force X" to state.xForce,
                            "Force Y" to state.yForce,
                            "Force Z" to state.zForce
                        ),
                        isAvailable = state.isAvailable
                    )
                }

                is MagneticFieldSensorState -> {
                    SensorItem(
                        name = "Magnetic Field",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.magnetic_field,
                        sensorValues = mapOf(
                            "Strength X" to state.xStrength,
                            "Strength Y" to state.yStrength,
                            "Strength Z" to state.zStrength
                        ),
                        isAvailable = state.isAvailable
                    )
                }

                is GyroscopeSensorState -> {
                    SensorItem(
                        name = "Gyroscope",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.gyroscope,
                        sensorValues = mapOf(
                            "Rotation X" to state.xRotation,
                            "Rotation Y" to state.yRotation,
                            "Rotation Z" to state.zRotation
                        ),
                        isAvailable = state.isAvailable
                    )
                }

                is LightSensorState -> {
                    SensorItem(
                        name = "Light",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.light,
                        sensorValues = mapOf("Illuminance" to state.illuminance),
                        isAvailable = state.isAvailable
                    )
                }

                is PressureSensorState -> {
                    SensorItem(
                        name = "Pressure",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.pressure,
                        sensorValues = mapOf("Pressure" to state.pressure),
                        isAvailable = state.isAvailable
                    )
                }

                is ProximitySensorState -> {
                    SensorItem(
                        name = "Proximity",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.proximity,
                        sensorValues = mapOf("Distance" to state.sensorDistance),
                        isAvailable = state.isAvailable
                    )
                }

                is GravitySensorState -> {
                    SensorItem(
                        name = "Gravity",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.gravity,
                        sensorValues = mapOf(
                            "Force X" to state.xForce,
                            "Force Y" to state.yForce,
                            "Force Z" to state.zForce
                        ),
                        isAvailable = state.isAvailable
                    )
                }

                is LinearAccelerationSensorState -> {
                    SensorItem(
                        name = "Linear Acceleration",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.linear_acceleration,
                        sensorValues = mapOf(
                            "Force X" to state.xForce,
                            "Force Y" to state.yForce,
                            "Force Z" to state.zForce
                        ),
                        isAvailable = state.isAvailable
                    )
                }

                is RotationVectorSensorState -> {
                    SensorItem(
                        name = "Rotation Vector",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.rotation_vector,
                        sensorValues = mapOf(
                            "Vector X" to state.vectorX,
                            "Vector Y" to state.vectorY,
                            "Vector Z" to state.vectorZ,
                            "Scalar" to state.scalar,
                            "Heading Accuracy" to state.estimatedHeadingAccuracy
                        ),
                        isAvailable = state.isAvailable
                    )
                }

                is AmbientTemperatureSensorState -> {
                    SensorItem(
                        name = "Ambient Temperature",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.ambient_temperature,
                        sensorValues = mapOf("Temperature" to state.temperature),
                        isAvailable = state.isAvailable
                    )
                }

                is UncalibratedMagneticFieldSensorState -> {
                    SensorItem(
                        name = "Magnetic Field (Uncalibrated)",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.magnetic_field,
                        sensorValues = mapOf(
                            "Strength X" to state.xStrength,
                            "Strength Y" to state.yStrength,
                            "Strength Z" to state.zStrength,
                            "Bias X" to state.xBias,
                            "Bias Y" to state.yBias,
                            "Bias Z" to state.zBias
                        ),
                        isAvailable = state.isAvailable
                    )
                }

                is GameRotationVectorSensorState -> {
                    SensorItem(
                        name = "Game Rotation Vector",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.game_rotation_vector,
                        sensorValues = mapOf(
                            "Vector X" to state.vectorX,
                            "Vector Y" to state.vectorY,
                            "Vector Z" to state.vectorZ
                        ),
                        isAvailable = state.isAvailable
                    )
                }

                is UncalibratedGyroscopeSensorState -> {
                    SensorItem(
                        name = "Gyroscope (Uncalibrated)",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.gyroscope,
                        sensorValues = mapOf(
                            "Rotation X" to state.xRotation,
                            "Rotation Y" to state.yRotation,
                            "Rotation Z" to state.zRotation,
                            "Bias X" to state.xBias,
                            "Bias Y" to state.yBias,
                            "Bias Z" to state.zBias
                        ),
                        isAvailable = state.isAvailable
                    )
                }

                is SignificantMotionSensorState -> {
                    // Trigger motion event whenever its card is selected
                    LaunchedEffect(Unit) {
                        state.requestEventTrigger()
                    }

                    SensorItem(
                        name = "Significant Motion",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.significant_motion,
                        sensorValues = mapOf(
                            "Listening?" to state.isListening,
                            "Timestamp (ns)" to state.lastEventTimestamp
                        ),
                        isAvailable = state.isAvailable
                    )
                }

                is StepDetectorSensorState -> {
                    SensorItem(
                        name = "Step Detector",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.step_detection,
                        sensorValues = mapOf("Step Count" to state.stepCount),
                        isAvailable = state.isAvailable
                    )
                }

                is StepCounterSensorState -> {
                    SensorItem(
                        name = "Step Counter",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.step_counter,
                        sensorValues = mapOf("Step Count" to state.stepCount),
                        isAvailable = state.isAvailable
                    )
                }

                is GeomagneticRotationVectorSensorState -> {
                    SensorItem(
                        name = "Geomagnetic Rotation Vector",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.geomagnetic_rotation_vector,
                        sensorValues = mapOf(
                            "Vector X" to state.vectorX,
                            "Vector Y" to state.vectorY,
                            "Vector Z" to state.vectorZ
                        ),
                        isAvailable = state.isAvailable
                    )
                }

                is HeartRateSensorState -> {
                    SensorItem(
                        name = "Heart Rate",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.heart_rate,
                        sensorValues = mapOf("Heart Rate" to state.heartRate),
                        isAvailable = state.isAvailable
                    )
                }

                is StationaryDetectSensorState -> {
                    SensorItem(
                        name = "Stationary Detect",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.stationary_detect,
                        sensorValues = mapOf("Is device stationary?" to state.isDeviceStationary),
                        isAvailable = state.isAvailable
                    )
                }

                is MotionDetectSensorState -> {
                    SensorItem(
                        name = "Motion Detect",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.motion_detect,
                        sensorValues = mapOf("Is device moving?" to state.isDeviceInMotion),
                        isAvailable = state.isAvailable
                    )
                }

                is HeartBeatSensorState -> {
                    SensorItem(
                        name = "Heart Beat",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.heart_rate,
                        sensorValues = mapOf("Is confident peak?" to state.isConfidentPeak),
                        isAvailable = state.isAvailable
                    )
                }

                is LowLatencyOffBodyDetectSensorState -> {
                    SensorItem(
                        name = "Low Latency Off-Body Detect",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.low_latency_off_body_detect,
                        sensorValues = mapOf("Is On Body?" to state.isDeviceOnBody),
                        isAvailable = state.isAvailable
                    )
                }

                is UncalibratedAccelerometerSensorState -> {
                    SensorItem(
                        name = "Accelerometer (Uncalibrated)",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.accelerometer,
                        sensorValues = mapOf(
                            "Force X" to state.xForce,
                            "Force Y" to state.yForce,
                            "Force Z" to state.zForce,
                            "Bias X" to state.xBias,
                            "Bias Y" to state.yBias,
                            "Bias Z" to state.zBias
                        ),
                        isAvailable = state.isAvailable
                    )
                }

                is HingeAngleSensorState -> {
                    SensorItem(
                        name = "Hinge Angle",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.hinge_angle,
                        sensorValues = mapOf("Angle" to state.angle),
                        isAvailable = state.isAvailable
                    )
                }

                is HeadTrackerSensorState -> {
                    SensorItem(
                        name = "Head Tracker",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.head_tracker,
                        sensorValues = mapOf(
                            "Rotation X" to state.xRotation,
                            "Rotation Y" to state.yRotation,
                            "Rotation Z" to state.zRotation,
                            "Velocity X" to state.xAngularVelocity,
                            "Velocity Y" to state.yAngularVelocity,
                            "Velocity Z" to state.zAngularVelocity
                        ),
                        isAvailable = state.isAvailable
                    )
                }

                is LimitedAxesAccelerometerSensorState -> {
                    SensorItem(
                        name = "Accelerometer (Limited Axes)",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.accelerometer,
                        sensorValues = mapOf(
                            "Force X" to state.xForce,
                            "Force Y" to state.yForce,
                            "Force Z" to state.zForce,
                            "X Supported?" to state.xAxisSupported,
                            "Y Supported?" to state.yAxisSupported,
                            "Z Supported?" to state.zAxisSupported
                        ),
                        isAvailable = state.isAvailable
                    )
                }

                is LimitedAxesGyroscopeSensorState -> {
                    SensorItem(
                        name = "Gyroscope (Limited Axes)",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.gyroscope,
                        sensorValues = mapOf(
                            "Rotation X" to state.xRotation,
                            "Rotation Y" to state.yRotation,
                            "Rotation Z" to state.zRotation,
                            "X Supported?" to state.xAxisSupported,
                            "Y Supported?" to state.yAxisSupported,
                            "Z Supported?" to state.zAxisSupported
                        ),
                        isAvailable = state.isAvailable
                    )
                }

                is UncalibratedLimitedAxesAccelerometerSensorState -> {
                    SensorItem(
                        name = "Accelerometer (Uncalibrated - Limited Axes)",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.accelerometer,
                        sensorValues = mapOf(
                            "Rotation X" to state.xForce,
                            "Rotation Y" to state.yForce,
                            "Rotation Z" to state.zForce,
                            "Bias X" to state.xBias,
                            "Bias Y" to state.yBias,
                            "Bias Z" to state.zBias,
                            "X Supported?" to state.xAxisSupported,
                            "Y Supported?" to state.yAxisSupported,
                            "Z Supported?" to state.zAxisSupported
                        ),
                        isAvailable = state.isAvailable
                    )
                }

                is HeadingSensorState -> {
                    SensorItem(
                        name = "Heading",
                        scrollProgress = scrollProgress,
                        imageRef = R.drawable.heading,
                        sensorValues = mapOf("Degrees" to state.degrees),
                        isAvailable = state.isAvailable
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.currentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}
