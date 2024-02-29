plugins {
    id("flipper.android-compose")
    id("flipper.anvil")
}

android.namespace = "com.lionzxy.flippertesttask.rootscreen.impl"

dependencies {
    implementation(projects.components.rootscreen.api)

    implementation(projects.components.core.di)
    implementation(projects.components.core.log)
    implementation(projects.components.core.decompose)

    implementation(projects.components.bottombar.api)
    implementation(projects.components.keychoose.api)
    implementation(projects.components.bridge.dao.api)


    // Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material)
    implementation(libs.bundles.decompose)
    implementation(libs.bundles.essenty)
}
