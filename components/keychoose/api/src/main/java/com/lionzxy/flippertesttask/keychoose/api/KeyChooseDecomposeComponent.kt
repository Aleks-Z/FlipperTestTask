package com.lionzxy.flippertesttask.keychoose.api

import com.arkivanov.decompose.ComponentContext
import com.flipperdevices.core.decompose.DecomposeOnBackParameter
import com.flipperdevices.core.decompose.ScreenDecomposeComponent

abstract class KeyChooseDecomposeComponent(
    componentContext: ComponentContext
) : ScreenDecomposeComponent(componentContext) {
    fun interface Factory {
        fun invoke(
            componentContext: ComponentContext,
            onBack: DecomposeOnBackParameter,
            lockerId: Long,
        ): KeyChooseDecomposeComponent
    }
}