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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mutualmobile.composesensors.rememberAccelerometerSensorState
import com.mutualmobile.composesensors.rememberGyroscopeSensorState
import com.mutualmobile.composesensors.rememberLightSensorState
import com.mutualmobile.composesensors.rememberMagneticFieldSensorState
import com.mutualmobile.composesensors.rememberPressureState
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
    val accelerometerState = rememberAccelerometerSensorState()
    val magneticFieldState = rememberMagneticFieldSensorState()
    val gyroscopeState = rememberGyroscopeSensorState()
    val lightState = rememberLightSensorState()
    val pressureState = rememberPressureState()

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
                    // TODO: Keep this updated until a better option is found
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
                    // TODO: Keep this updated until a better option is found
                    enabled = pagerState.currentPage != 4
                ) {
                    Text(text = "Next")
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
                }
            }
        }
    ) {
        HorizontalPager(
            // TODO: Keep this updated until a better option is found
            pageCount = 5,
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
                    else -> error("Invalid index '$index'")
                },
                scrollProgress = scrollProgress,
                imageRef = when (index) {
                    0 -> R.drawable.accelerometer
                    1 -> R.drawable.magnetic_field
                    2 -> R.drawable.gyroscope
                    3 -> R.drawable.light
                    4 -> R.drawable.pressure
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

                    3 -> mapOf(
                        "Illuminance" to lightState.illuminance
                    )

                    4 -> mapOf(
                        "Pressure" to pressureState.pressure
                    )

                    else -> error("Invalid index '$index'")
                },
                isAvailable = when (index) {
                    0 -> accelerometerState.isAvailable
                    1 -> magneticFieldState.isAvailable
                    2 -> gyroscopeState.isAvailable
                    3 -> lightState.isAvailable
                    4 -> pressureState.isAvailable
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
