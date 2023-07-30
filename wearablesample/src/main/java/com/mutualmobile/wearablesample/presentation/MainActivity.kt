/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.mutualmobile.wearablesample.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.mutualmobile.composesensors.rememberHeartRateSensorState
import com.mutualmobile.wearablesample.presentation.theme.ComposeSensorsTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSensorsTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    contentAlignment = Alignment.Center,
                ) {
                    val heartRateSensorState = rememberHeartRateSensorState(autoStart = false)
                    val lifecycleState by LocalLifecycleOwner.current.lifecycle.observeAsState()

                    var isPermissionGranted: Boolean? by remember { mutableStateOf(null) }

                    // BODY_SENSORS permission must be granted before accessing sensor
                    val permissionLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = { isGranted ->
                            isPermissionGranted = isGranted
                        }
                    )

                    LaunchedEffect(lifecycleState) {
                        if (lifecycleState == Lifecycle.Event.ON_RESUME) {
                            isPermissionGranted = isBodySensorsPermissionGranted
                            if (isPermissionGranted == true) {
                                heartRateSensorState.startListening()
                            } else {
                                permissionLauncher.launch(Manifest.permission.BODY_SENSORS)
                            }
                        }
                    }

                    AnimatedContent(
                        targetState = isPermissionGranted,
                        label = "animated container"
                    ) { animatedIsGranted ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            animatedIsGranted?.let { safeIsPermissionGranted ->
                                Text(
                                    text = if (safeIsPermissionGranted) "Heart Rate: " +
                                            "${heartRateSensorState.heartRate}" else "Please " +
                                            "grant the sensors permission first",
                                    textAlign = TextAlign.Center
                                )
                                if (!safeIsPermissionGranted) {
                                    Button(
                                        modifier = Modifier.padding(16.dp),
                                        onClick = { navigateToAppInfo() },
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(horizontal = 16.dp),
                                            text = "Grant Permission"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private val Activity.isBodySensorsPermissionGranted: Boolean
    get() {
        return checkSelfPermission(Manifest.permission.BODY_SENSORS) ==
                PackageManager.PERMISSION_GRANTED
    }

fun Activity.navigateToAppInfo() {
    startActivity(
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(Uri.fromParts("package", packageName, null))
    )
}

@Composable
fun Lifecycle.observeAsState(): State<Lifecycle.Event> {
    val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event -> state.value = event }
        this@observeAsState.addObserver(observer)
        onDispose { this@observeAsState.removeObserver(observer) }
    }
    return state
}
