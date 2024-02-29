package com.lionzxy.flippertesttask.rootscreen.api

import android.view.Window
import androidx.compose.runtime.staticCompositionLocalOf

val LocalWindow = staticCompositionLocalOf<Window> {
    error("CompositionLocal LocalWindow not present")
}

