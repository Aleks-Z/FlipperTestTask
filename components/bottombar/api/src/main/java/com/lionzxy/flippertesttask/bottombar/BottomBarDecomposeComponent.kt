package com.lionzxy.flippertesttask.bottombar

import com.arkivanov.decompose.ComponentContext
import com.flipperdevices.core.decompose.ScreenDecomposeComponent

abstract class BottomBarDecomposeComponent(
    componentContext: ComponentContext
) : ScreenDecomposeComponent(
    componentContext
) {
    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext
        ): BottomBarDecomposeComponent
    }
}