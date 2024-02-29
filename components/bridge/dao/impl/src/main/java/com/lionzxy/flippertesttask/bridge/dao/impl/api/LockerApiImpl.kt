package com.lionzxy.flippertesttask.bridge.dao.impl.api

import com.lionzxy.flippertesttask.dao.api.delegates.LockerApi
import com.lionzxy.flippertesttask.dao.api.model.KeyModel
import com.lionzxy.flippertesttask.dao.api.model.LockerModel
import com.lionzxy.flippertesttask.dao.api.model.LockerType
import com.lionzxy.flippertesttask.core.di.AppGraph
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ContributesBinding(AppGraph::class, LockerApi::class)
class LockerApiImpl @Inject constructor() : LockerApi {
    private val lockerSetFlow = MutableStateFlow(
        emptySet<LockerModel>()
    )

    private val keySetFlow = MutableStateFlow(
        emptySet<KeyModel>()
    )

    private val lockerTypeMapFlow = MutableStateFlow(
        emptyMap<LockerType, List<Long>>()
    )

    private val lockerKeyMapFlow = MutableStateFlow(
        emptyMap<Long, Long>()
    )

    private val freeKeySetFlow = combine(
        keySetFlow,
        lockerKeyMapFlow
    ) { keySet, lockerKeyMap ->
        keySet.filterNot {
            lockerKeyMap.map { it.value }.contains(it.id)
        }.toSet()
    }

    init {
        val deviceLockerSet = (0..15).map {
            val id = (it + 100).toLong()
            LockerModel(id, it)
        }.toSet()

        val archiveLockerSet = (0..15).map {
            val id = (it + 200).toLong()
            LockerModel(id, it)
        }.toSet()

        val hubLockerSet = (0..15).map {
            val id = (it + 300).toLong()
            LockerModel(id, it)
        }.toSet()

        val keySet = (0..15).map {
            KeyModel(it.toLong(), it)
        }.toSet()

        lockerSetFlow.tryEmit(
            deviceLockerSet + archiveLockerSet + hubLockerSet
        )

        lockerTypeMapFlow.tryEmit(
            mapOf(
                LockerType.DEVICE to deviceLockerSet.map { it.id },
                LockerType.ARCHIVE to archiveLockerSet.map { it.id },
                LockerType.HUB to hubLockerSet.map { it.id },
            )
        )

        keySetFlow.tryEmit(
            keySet
        )
    }

    override fun getLockerFlow(lockerType: LockerType): Flow<Set<Pair<LockerModel, KeyModel?>>> {
        val output = combine(
            lockerSetFlow,
            keySetFlow,
            lockerKeyMapFlow,
            lockerTypeMapFlow
        ) { lockerSet,
            keySet,
            lockerKeyMap,
            lockerTypeMap
            ->

            lockerTypeMap.getValue(lockerType)
                .map { targetLockerId ->
                    val targetKeyIdOrNull = lockerKeyMap[targetLockerId]
                    lockerSet.first { it.id == targetLockerId } to keySet.find { it.id == targetKeyIdOrNull }
                }.toSet()
        }

        return output
    }

    override suspend fun getFreeKeysFlow(): Flow<Set<KeyModel>> {
        return freeKeySetFlow
    }

    override suspend fun lockKey(lockerId: Long, keyId: Long) {
        lockerKeyMapFlow.update {
            it.toMutableMap()
                .apply {
                    put(lockerId, keyId)
                }
                .toMap()
        }
    }

    override fun getLockerById(lockerId: Long): LockerModel {
        return lockerSetFlow.value.first { it.id == lockerId }
    }
}