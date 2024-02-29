package com.lionzxy.flippertesttask.rootscreen.impl.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.flipperdevices.core.decompose.DecomposeComponent
import com.flipperdevices.core.decompose.DecomposeOnBackParameter
import com.flipperdevices.core.decompose.popOr
import com.lionzxy.flippertesttask.rootscreen.api.RootDecomposeComponent
import com.lionzxy.flippertesttask.rootscreen.model.RootScreenConfig
import com.lionzxy.flippertesttask.bottombar.BottomBarDecomposeComponent
import com.lionzxy.flippertesttask.core.di.AppGraph
import com.lionzxy.flippertesttask.keychoose.api.KeyChooseDecomposeComponent
import com.squareup.anvil.annotations.ContributesBinding
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@Suppress("LongParameterList")
class RootDecomposeComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val onBack: DecomposeOnBackParameter,
    private val bottomBarFactory: BottomBarDecomposeComponent.Factory,
    private val keysFactory: KeyChooseDecomposeComponent.Factory,
) : RootDecomposeComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<RootScreenConfig>()

    private val stack: Value<ChildStack<RootScreenConfig, DecomposeComponent>> = childStack(
        source = navigation,
        serializer = RootScreenConfig.serializer(),
        initialStack = { getInitialConfiguration() },
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(
        config: RootScreenConfig,
        componentContext: ComponentContext
    ): DecomposeComponent = when (config) {
        is RootScreenConfig.KeyChoose -> {
            keysFactory.invoke(
                componentContext = componentContext,
                lockerId = config.lockerId,
                onBack = this::internalOnBack
            )
        }

        RootScreenConfig.BottomBar -> {
            bottomBarFactory(
                componentContext = componentContext,
            )
        }
    }

    private fun getInitialConfiguration(): List<RootScreenConfig> {
        return listOf(RootScreenConfig.BottomBar)
    }

    private fun internalOnBack() {
        navigation.popOr(onBack::invoke)
    }

    override fun push(config: RootScreenConfig) {
        navigation.push(config)
    }

    @Composable
    @Suppress("NonSkippableComposable")
    override fun Render(modifier: Modifier) {
        val childStack by stack.subscribeAsState()

        Children(
            modifier = modifier,
            stack = childStack,
        ) {
            it.instance.Render()
        }
    }

    @AssistedFactory
    @ContributesBinding(AppGraph::class, RootDecomposeComponent.Factory::class)
    interface Factory : RootDecomposeComponent.Factory {
        override operator fun invoke(
            componentContext: ComponentContext,
            onBack: DecomposeOnBackParameter
        ): RootDecomposeComponentImpl
    }
}
