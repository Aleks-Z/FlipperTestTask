package com.lionzxy.flippertesttask.keychoose.impl.viewmodel

import com.lionzxy.flippertesttask.dao.api.delegates.LockerApi
import com.lionzxy.flippertesttask.dao.api.model.KeyModel
import com.lionzxy.flippertesttask.dao.api.model.LockerModel
import com.flipperdevices.core.decompose.DecomposeViewModel
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class KeyViewModel @Inject constructor(
    private val lockerApi: LockerApi,
) : DecomposeViewModel() {
    private val keySet = MutableStateFlow<PersistentSet<KeyModel>>(
        persistentSetOf()
    )

    init {
        viewModelScope.launch {
            lockerApi.getFreeKeysFlow().collect {
                keySet.emit(it.toPersistentSet())
            }
        }
    }

    fun getKeysFlow(): StateFlow<PersistentSet<KeyModel>> {
        return keySet.asStateFlow()
    }

    fun getLockerById(lockerId: Long): LockerModel {
        return lockerApi.getLockerById(lockerId)
    }

    fun onSelectKey(lockerId: Long, keyId: Long) {
        viewModelScope.launch {
            lockerApi.lockKey(lockerId, keyId)
        }
    }
}