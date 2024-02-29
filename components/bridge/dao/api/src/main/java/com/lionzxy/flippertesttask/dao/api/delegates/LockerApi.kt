package com.lionzxy.flippertesttask.dao.api.delegates

import com.lionzxy.flippertesttask.dao.api.model.KeyModel
import com.lionzxy.flippertesttask.dao.api.model.LockerModel
import com.lionzxy.flippertesttask.dao.api.model.LockerType
import kotlinx.coroutines.flow.Flow

interface LockerApi {
    fun getLockerFlow(lockerType: LockerType): Flow<Set<Pair<LockerModel, KeyModel?>>>
    suspend fun getFreeKeysFlow(): Flow<Set<KeyModel>>
    suspend fun lockKey(lockerId: Long, keyId: Long)
    fun getLockerById(lockerId: Long): LockerModel
}

