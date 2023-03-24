package com.mutualmobile.composesensors

import android.hardware.SensorManager

enum class SensorDelay {
    /**
     * Get sensor data as fast as possible
     */
    Fastest,

    /**
     * Rate suitable for games
     */
    Game,

    /**
     * Rate suitable for the user interface
     */
    UI,

    /**
     * Rate (default) suitable for screen orientation changes
     */
    Normal;

    internal fun toAndroidSensorDelay(): Int {
        return when (this) {
            Fastest -> SensorManager.SENSOR_DELAY_FASTEST
            Game -> SensorManager.SENSOR_DELAY_GAME
            UI -> SensorManager.SENSOR_DELAY_UI
            Normal -> SensorManager.SENSOR_DELAY_NORMAL
        }
    }
}
