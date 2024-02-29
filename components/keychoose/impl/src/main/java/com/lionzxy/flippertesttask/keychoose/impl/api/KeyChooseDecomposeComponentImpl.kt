package com.lionzxy.flippertesttask.keychoose.impl.api

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.flipperdevices.core.decompose.DecomposeOnBackParameter
import com.lionzxy.flippertesttask.rootscreen.api.LocalWindow
import com.lionzxy.flippertesttask.core.di.AppGraph
import com.lionzxy.flippertesttask.keychoose.api.KeyChooseDecomposeComponent
import com.lionzxy.flippertesttask.keychoose.impl.R
import com.lionzxy.flippertesttask.keychoose.impl.composable.KeyComposableScreen
import com.lionzxy.flippertesttask.keychoose.impl.viewmodel.KeyViewModel
import com.squareup.anvil.annotations.ContributesBinding
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Provider

class KeyChooseDecomposeComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val onBack: DecomposeOnBackParameter,
    @Assisted private val lockerId: Long,
    private val keyViewModelProvider: Provider<KeyViewModel>
) : KeyChooseDecomposeComponent(componentContext) {
    private val keyViewModel = instanceKeeper.getOrCreate { keyViewModelProvider.get() }

    @Composable
    override fun Render() {
        val keySet by keyViewModel.getKeysFlow().collectAsState()
        val locker = keyViewModel.getLockerById(lockerId)

        val backgroundColor = colorResource(id = R.color.background_key_choose)

        val window = LocalWindow.current

        LaunchedEffect(Unit) {
            window.statusBarColor = backgroundColor.toArgb()
            window.navigationBarColor = backgroundColor.toArgb()
        }

        CompositionLocalProvider(LocalContentColor provides Color.White) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.title, locker.lockerNumber),
                    fontSize = 32.sp,
                    textAlign = TextAlign.Start
                )
                KeyComposableScreen(
                    keySet = keySet,
                    onClickKey = { key ->
                        keyViewModel.onSelectKey(locker.id, key.id)
                        onBack.invoke()
                    }
                )
            }
        }
    }

    @AssistedFactory
    @ContributesBinding(AppGraph::class, KeyChooseDecomposeComponent.Factory::class)
    interface Factory : KeyChooseDecomposeComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            onBack: DecomposeOnBackParameter,
            lockerId: Long,
        ): KeyChooseDecomposeComponentImpl
    }
}