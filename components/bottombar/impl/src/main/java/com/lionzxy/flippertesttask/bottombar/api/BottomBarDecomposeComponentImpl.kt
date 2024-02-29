package com.lionzxy.flippertesttask.bottombar.api

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.lionzxy.flippertesttask.dao.api.model.LockerType
import com.flipperdevices.core.decompose.DecomposeComponent
import com.lionzxy.flippertesttask.rootscreen.api.LocalWindow
import com.lionzxy.flippertesttask.bottombar.BottomBarDecomposeComponent
import com.lionzxy.flippertesttask.bottombar.composable.ComposableBottomBarScreen
import com.lionzxy.flippertesttask.bottombar.config.BottomBarConfig
import com.lionzxy.flippertesttask.bottombar.config.BottomBarEnum
import com.lionzxy.flippertesttask.bottombar.impl.R
import com.lionzxy.flippertesttask.core.di.AppGraph
import com.lionzxy.flippertesttask.lockerchoose.api.LockerChooseDecomposeComponent
import com.squareup.anvil.annotations.ContributesBinding
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class BottomBarDecomposeComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    private val lockerChooseDecomposeComponentFactory: LockerChooseDecomposeComponent.Factory
) : BottomBarDecomposeComponent(componentContext), ComponentContext by componentContext {
    private val navigation = StackNavigation<BottomBarConfig>()

    private val stack: Value<ChildStack<BottomBarConfig, DecomposeComponent>> =
        childStack(
            source = navigation,
            serializer = BottomBarConfig.serializer(),
            initialConfiguration = BottomBarConfig.Device,
            childFactory = ::child,
        )

    @Composable
    @Suppress("NonSkippableComposable")
    override fun Render() {
        val childStack by stack.subscribeAsState()

        val window = LocalWindow.current

        val statusBarColor = colorResource(id = R.color.background).toArgb()
        val navigationBarColor = colorResource(id = R.color.bottombar_background_color).toArgb()

        LaunchedEffect(Unit) {
            window.statusBarColor = statusBarColor
            window.navigationBarColor = navigationBarColor
        }

        ComposableBottomBarScreen(
            childStack = childStack,
            modifier = Modifier.fillMaxSize(),
            onSelect = {
                when (it) {
                    BottomBarEnum.DEVICE -> navigation.bringToFront(BottomBarConfig.Device)
                    BottomBarEnum.ARCHIVE -> navigation.bringToFront(BottomBarConfig.Archive)
                    BottomBarEnum.HUB -> navigation.bringToFront(BottomBarConfig.Hub)
                }
            }
        )
    }


    private fun child(
        config: BottomBarConfig,
        componentContext: ComponentContext
    ): DecomposeComponent = when (config) {

        BottomBarConfig.Archive -> lockerChooseDecomposeComponentFactory(
            componentContext = componentContext,
            tabName = config.enum.tabName,
            lockerType = LockerType.ARCHIVE
        )

        BottomBarConfig.Device -> lockerChooseDecomposeComponentFactory(
            componentContext = componentContext,
            tabName = config.enum.tabName,
            lockerType = LockerType.DEVICE
        )

        is BottomBarConfig.Hub -> lockerChooseDecomposeComponentFactory(
            componentContext = componentContext,
            tabName = config.enum.tabName,
            lockerType = LockerType.HUB
        )
    }

    @AssistedFactory
    @ContributesBinding(AppGraph::class, BottomBarDecomposeComponent.Factory::class)
    fun interface Factory : BottomBarDecomposeComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext
        ): BottomBarDecomposeComponentImpl
    }
}