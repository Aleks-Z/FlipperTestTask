plugins {
    id("flipper.android-compose")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
}

android.namespace = "com.lionzxy.flippertesttask.bridge.dao.api"

dependencies {
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.immutable.collections)

    implementation(libs.compose.ui)
}
