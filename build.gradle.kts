buildscript {
    val compose_version by extra("1.7.4")
    val wear_compose_version by extra("1.4.0")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.7.1" apply false
    id("com.android.library") version "8.7.1" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
}
