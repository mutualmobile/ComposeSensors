package com.mutualmobile.sample.ui.screens.sensorlist.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue

@Composable
fun SensorItem(
    name: String,
    scrollProgress: State<Float>,
    @DrawableRes imageRef: Int,
    sensorValues: Map<String, Any>,
    isAvailable: Boolean
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.scale(1f - scrollProgress.value.times(0.2f)),
            tonalElevation = 8.dp,
            shadowElevation = 16.dp,
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Column(
                modifier = Modifier.padding(16.dp).alpha(if (isAvailable) 1f else 0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    if (isAvailable) "(Available)" else "(Unavailable)",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isAvailable) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    }
                )
                Box(
                    modifier = Modifier
                        .padding(24.dp)
                        .clip(MaterialTheme.shapes.large)
                        .fillMaxHeight(0.4f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(1.25f + scrollProgress.value.absoluteValue.times(0.25f))
                            .paint(
                                painter = painterResource(id = imageRef),
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.colorMatrix(
                                    ColorMatrix().apply {
                                        if (!isAvailable) {
                                            setToSaturation(0f)
                                        }
                                    }
                                )
                            )
                    )
                }
                LazyColumn(
                    modifier = Modifier.heightIn(
                        max = LocalConfiguration.current.screenHeightDp.dp.times(0.1f)
                    )
                ) {
                    val sensorValueList = sensorValues.entries.toList()
                    if (sensorValueList.size > 3) {
                        item {
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = "Scroll down to see more",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    items(sensorValueList.size) { index ->
                        val sensorValue: Map.Entry<String, Any> = sensorValueList[index]
                        SensorValue(
                            title = sensorValue.key,
                            value = sensorValue.value,
                            scrollProgress = scrollProgress
                        )
                    }
                }
            }
        }
    }
}
