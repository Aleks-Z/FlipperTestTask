package com.lionzxy.flippertesttask.lockerchoose.impl.viewmodel

import com.lionzxy.flippertesttask.dao.api.delegates.LockerApi
import com.lionzxy.flippertesttask.dao.api.model.KeyModel
import com.lionzxy.flippertesttask.dao.api.model.LockerModel
import com.lionzxy.flippertesttask.dao.api.model.LockerType
import com.flipperdevices.core.decompose.DecomposeViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentHashSetOf
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LockerViewModel @AssistedInject constructor(
    private val lockerApi: LockerApi,
    @Assisted private val lockerType: LockerType,
) : DecomposeViewModel() {

    private val lockerSet = MutableStateFlow(
        persistentHashSetOf<Pair<LockerModel, KeyModel?>>()
    )

    init {
        viewModelScope.launch {
            lockerApi.getLockerFlow(lockerType)
                .collect {
                    lockerSet.emit(it.toPersistentSet())
                }
        }
    }

    fun getLockersFlow(): StateFlow<PersistentSet<Pair<LockerModel, KeyModel?>>> {
        return lockerSet.asStateFlow()
    }

    @AssistedFactory
    fun interface Factory {
        operator fun invoke(
            lockerType: LockerType,
        ): LockerViewModel
    }
}