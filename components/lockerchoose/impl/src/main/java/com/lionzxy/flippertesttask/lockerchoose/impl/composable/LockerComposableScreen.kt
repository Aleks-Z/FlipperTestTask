package com.lionzxy.flippertesttask.lockerchoose.impl.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lionzxy.flippertesttask.dao.api.model.KeyModel
import com.lionzxy.flippertesttask.dao.api.model.LockerModel
import com.lionzxy.flippertesttask.lockerchoose.impl.R
import kotlinx.collections.immutable.PersistentSet

@Composable
fun LockerComposableScreen(
    lockerSet: PersistentSet<Pair<LockerModel, KeyModel?>>,
    onClickLocker: (LockerModel, KeyModel?) -> Unit,
) {
    val lockerList = remember(lockerSet) { lockerSet.toList() }
    LazyColumn {
        items(
            items = lockerList,
            key = { it.first.id }
        ) { (lockerItem, keyItem) ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            Row(
                Modifier
                    .clickable {
                        onClickLocker.invoke(lockerItem, keyItem)
                    }
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding()
                        .weight(1f),
                    text = stringResource(R.string.locker, lockerItem.lockerNumber)
                )
                Text(
                    text = if (keyItem != null) {
                        stringResource(R.string.key, keyItem.keyNumber)
                    } else {
                        stringResource(R.string.key_not_found)
                    }
                )
            }
        }
    }

}