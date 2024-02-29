package com.lionzxy.flippertesttask.rootscreen.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.flipperdevices.core.decompose.DecomposeOnBackParameter

interface RootDecomposeComponent :
    ComponentContext,
    RootNavigationInterface {
    @Composable
    fun Render(modifier: Modifier)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            onBack: DecomposeOnBackParameter
        ): RootDecomposeComponent
    }
}
