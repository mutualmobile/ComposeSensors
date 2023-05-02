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
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.mutualmobile.composesensors.rememberGravitySensorState
import com.mutualmobile.composesensors.rememberGyroscopeSensorState
import com.mutualmobile.composesensors.rememberHeartRateSensorState
import com.mutualmobile.composesensors.rememberHingeAngleSensorState
import com.mutualmobile.composesensors.rememberLightSensorState
import com.mutualmobile.composesensors.rememberLimitedAxesGyroscopeSensorState
import com.mutualmobile.composesensors.rememberLinearAccelerationSensorState
import com.mutualmobile.composesensors.rememberLowLatencyOffBodyDetectSensorState
import com.mutualmobile.composesensors.rememberMagneticFieldSensorState
import com.mutualmobile.composesensors.rememberPressureSensorState
import com.mutualmobile.composesensors.rememberProximitySensorState
import com.mutualmobile.composesensors.rememberRotationVectorSensorState
import com.mutualmobile.composesensors.rememberUncalibratedMagneticFieldSensorState
import com.mutualmobile.sample.R
import com.mutualmobile.sample.ui.screens.sensorlist.components.SensorItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SensorsListScreen() {
    var isTopBarTitleCollapsed by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    // TODO: Keep this updated until a better option is found
    val totalPageCount = rememberSaveable { 16 }
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
    val heartRateState = rememberHeartRateSensorState()
    val lowLatencyOffBodyDetectState = rememberLowLatencyOffBodyDetectSensorState()
    val hingeAngleState = rememberHingeAngleSensorState()
    val limitedAxesGyroscopeState = rememberLimitedAxesGyroscopeSensorState()

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
                Button(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage - 1,
                                animationSpec = tween(durationMillis = 700)
                            )
                        }
                    },
                    shape = MaterialTheme.shapes.small,
                    contentPadding = PaddingValues(start = 4.dp, end = 16.dp),
                    enabled = pagerState.currentPage != 0
                ) {
                    Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
                    Text(text = "Previous")
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage + 1,
                                animationSpec = tween(durationMillis = 700)
                            )
                        }
                    },
                    shape = MaterialTheme.shapes.small,
                    contentPadding = PaddingValues(start = 16.dp, end = 8.dp),
                    enabled = pagerState.currentPage != totalPageCount - 1
                ) {
                    Text(text = "Next")
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
                }
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
                    12 -> "Heart Rate"
                    13 -> "Low Latency Off-Body Detection"
                    14 -> "Hinge Angle"
                    15 -> "Gyroscope (Limited Axes)"
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
                    12 -> R.drawable.heart_rate
                    13 -> R.drawable.low_latency_off_body_detect
                    14 -> R.drawable.hinge_angle
                    15 -> R.drawable.gyroscope
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

                    12 -> mapOf("Heart Rate" to heartRateState.heartRate)

                    13 -> mapOf("Is On Body?" to lowLatencyOffBodyDetectState.isDeviceOnBody)

                    14 -> mapOf("Angle" to hingeAngleState.angle)

                    15 -> mapOf(
                        "Rotation X" to limitedAxesGyroscopeState.xRotation,
                        "Rotation Y" to limitedAxesGyroscopeState.yRotation,
                        "Rotation Z" to limitedAxesGyroscopeState.zRotation,
                        "X Supported?" to limitedAxesGyroscopeState.xAxisSupported,
                        "Y Supported?" to limitedAxesGyroscopeState.yAxisSupported,
                        "Z Supported?" to limitedAxesGyroscopeState.zAxisSupported
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
                    12 -> heartRateState.isAvailable
                    13 -> lowLatencyOffBodyDetectState.isAvailable
                    14 -> hingeAngleState.isAvailable
                    15 -> limitedAxesGyroscopeState.isAvailable
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
