package com.lionzxy.flippertesttask.rootscreen.model

import kotlinx.serialization.Serializable

@Serializable
sealed class RootScreenConfig {
    @Serializable
    data object BottomBar : RootScreenConfig()

    @Serializable
    data class KeyChoose(
        val lockerId: Long,
    ) : RootScreenConfig()

}
