package com.lionzxy.flippertesttask.singleactivity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import com.lionzxy.flippertesttask.rootscreen.api.LocalRootNavigation
import com.lionzxy.flippertesttask.rootscreen.api.LocalWindow
import com.lionzxy.flippertesttask.rootscreen.api.RootDecomposeComponent
import com.lionzxy.flippertesttask.core.di.ComponentHolder
import com.lionzxy.flippertesttask.core.log.LogTagProvider
import com.lionzxy.flippertesttask.core.log.info
import com.lionzxy.flippertesttask.singleactivity.di.SingleActivityComponent
import javax.inject.Inject

class SingleActivity : AppCompatActivity(), LogTagProvider {
    override val TAG = "SingleActivity"

    @Inject
    lateinit var rootDecomposeComponentFactory: RootDecomposeComponent.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ComponentHolder.component<SingleActivityComponent>().inject(this)

        val root = rootDecomposeComponentFactory(
            componentContext = defaultComponentContext(),
            onBack = this::finish
        )

        setContent {
            CompositionLocalProvider(
                LocalRootNavigation provides root,
                LocalWindow provides window
            ) {
                root.Render(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
        info { "Create new activity with hashcode: ${this.hashCode()} " + "and intent $intent" }
    }
}