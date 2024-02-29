plugins {
    id("flipper.android-lib")
    id("flipper.anvil")
}

android.namespace = "com.lionzxy.flippertesttask.bridge.dao.impl"

dependencies {
    implementation(projects.components.bridge.dao.api)
    implementation(projects.components.core.di)
    implementation(projects.components.core.log)

    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.immutable.collections)
}
