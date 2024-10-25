package com.mutualmobile.sample.ui.screens.sensorlist.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SensorValue(
    title: String,
    value: Any,
    scrollProgress: State<Float>
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(bottom = 4.dp)
            .fillMaxWidth(0.9f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val dashedLineColor = MaterialTheme.colorScheme.primary
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = title,
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(modifier = Modifier.weight(scrollProgress.value.coerceAtLeast(0.01f)))
        repeat(3) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = dashedLineColor
            )
        }
        Spacer(modifier = Modifier.weight(1f - scrollProgress.value.coerceAtMost(0.99f)))
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = value.toString(),
            style = MaterialTheme.typography.labelLarge
        )
    }
}
