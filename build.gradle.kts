// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    id("com.google.dagger.hilt.android") version "2.55" apply false
    id("androidx.navigation.safeargs") version "2.8.6" apply false
    id("io.realm.kotlin") version "1.16.0" apply false
}