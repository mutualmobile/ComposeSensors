package com.mutualmobile.composesensors.wearablesample.presentation

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Typography

@Composable
fun ComposeSensorsTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = Colors(),
        typography = Typography(),
        // For shapes, we generally recommend using the default Material Wear shapes which are
        // optimized for round and non-round devices.
        content = content,
    )
}
