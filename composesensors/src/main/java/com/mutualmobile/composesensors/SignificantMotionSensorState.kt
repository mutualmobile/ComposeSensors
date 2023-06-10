package com.mutualmobile.composesensors

import android.hardware.Sensor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * A significant motion ([Sensor.TYPE_SIGNIFICANT_MOTION]) detector triggers when detecting a
 * significant motion: a motion that might lead to a change in the user location.
 *
 * Examples of such significant motions are:
 * - Walking or biking
 * - Sitting in a moving car, coach, or train
 *
 * Examples of situations that don't trigger significant motion:
 * - Phone in pocket and person isn't moving
 * - Phone is on a table and the table shakes a bit due to nearby traffic or washing machine
 *
 * For more info, please refer the [Android Documentation Reference](https://source.android.com/docs/core/interaction/sensors/sensor-types#significant_motion)
 *
 * @param isListening Whether the app is currently listening to the motion event. Defaults to false.
 * @param lastEventTimestamp Timestamp (in nanoseconds) of the last motion event. Defaults to 0L.
 * @param isAvailable Whether the current device has a heart beat sensor. Defaults to false.
 * @param accuracy Accuracy factor of the heart beat sensor. Defaults to 0.
 */
@Immutable
class SignificantMotionSensorState internal constructor(
    private val triggerEvent: () -> Unit = {},
    val isListening: Boolean = false,
    val lastEventTimestamp: Long = 0L,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
    private val startListeningEvents: () -> Unit,
    private val stopListeningEvents: () -> Unit
) : SensorStateListener {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SignificantMotionSensorState) return false

        if (isListening != other.isListening) return false
        if (lastEventTimestamp != other.lastEventTimestamp) return false
        if (triggerEvent != other.triggerEvent) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false
        if (startListeningEvents != other.startListeningEvents) return false
        if (stopListeningEvents != other.stopListeningEvents) return false

        return true
    }

    override fun hashCode(): Int {
        var result = triggerEvent.hashCode()
        result = 31 * result + isListening.hashCode()
        result = 31 * result + lastEventTimestamp.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + startListeningEvents.hashCode()
        result = 31 * result + stopListeningEvents.hashCode()
        return result
    }

    override fun toString(): String {
        return "SignificantMotionSensorState(triggerEvent=$triggerEvent, " +
            "isListening=$isListening, lastEventTimestamp=$lastEventTimestamp, " +
            "isAvailable=$isAvailable, accuracy=$accuracy)"
    }

    /**
     * Since this sensor's reporting mode is One-Shot, therefore the user has to manually request a
     * one-time event trigger and subscribe to it. Use this function to trigger such event and use
     * onMotionEvent to subscribe to the event.
     */
    fun requestEventTrigger() = triggerEvent()

    override fun startListening() = startListeningEvents()

    override fun stopListening() = stopListeningEvents()
}

/**
 * Creates and [remember]s an instance of [SignificantMotionSensorState].
 * @param onMotionEvent Callback invoked every time the sensor detects a significant amount of
 * device motion. Since this sensor's reporting mode is One-Shot, use
 * [SignificantMotionSensorState.requestEventTrigger] to trigger the event emission and collect
 * the value (timestamp in nanoseconds - defaults to 0L) of the event in this callback.
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberSignificantMotionSensorState(
    onMotionEvent: (timestamp: Long) -> Unit,
    onError: (throwable: Throwable) -> Unit = {}
): SignificantMotionSensorState {
    val isListening = remember { mutableStateOf(false) }
    val lastEventTimestamp = remember { mutableStateOf(0L) }

    val sensorState = rememberSensorState(
        sensorType = SensorType.SignificantMotion,
        onError = onError,
        onMotionEvent = { timestampNs ->
            onMotionEvent(timestampNs)
            isListening.value = false
            lastEventTimestamp.value = timestampNs
        }
    )

    val significantMotionSensorState = remember(
        sensorState,
        isListening.value,
        lastEventTimestamp.value
    ) {
        mutableStateOf(
            SignificantMotionSensorState(
                triggerEvent = {
                    sensorState.requestEventTrigger()
                    isListening.value = true
                },
                isAvailable = sensorState.isAvailable,
                accuracy = sensorState.accuracy,
                isListening = isListening.value,
                lastEventTimestamp = lastEventTimestamp.value,
                startListeningEvents = sensorState::startListening,
                stopListeningEvents = sensorState::stopListening
            )
        )
    }

    return significantMotionSensorState.value
}
