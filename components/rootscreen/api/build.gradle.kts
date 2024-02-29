plugins {
    id("flipper.android-compose")
    id("kotlinx-serialization")
}

android.namespace = "com.lionzxy.flippertesttask.rootscreen.api"

dependencies {
    implementation(projects.components.bridge.dao.api)
    implementation(projects.components.core.decompose)

    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.decompose)


}
