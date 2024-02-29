package com.lionzxy.flippertesttask.rootscreen.api

import androidx.compose.runtime.staticCompositionLocalOf
import com.lionzxy.flippertesttask.rootscreen.model.RootScreenConfig

val LocalRootNavigation = staticCompositionLocalOf<RootNavigationInterface> {
    error("CompositionLocal LocalRootComponent not present")
}

interface RootNavigationInterface {
    fun push(config: RootScreenConfig)
}

