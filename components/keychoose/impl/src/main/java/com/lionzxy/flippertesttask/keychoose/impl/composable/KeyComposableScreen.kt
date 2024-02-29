package com.lionzxy.flippertesttask.keychoose.impl.composable

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
import com.lionzxy.flippertesttask.keychoose.impl.R
import kotlinx.collections.immutable.PersistentSet

@Composable
fun KeyComposableScreen(
    keySet: PersistentSet<KeyModel>,
    onClickKey: (KeyModel) -> Unit,
) {
    val keyList = remember(keySet) { keySet.toList() }
    LazyColumn {
        items(
            items = keyList,
            key = { it.keyNumber }
        ) { keyItem ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            Row(
                Modifier
                    .clickable {
                        onClickKey.invoke(keyItem)
                    }
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding()
                        .weight(1f),
                    text = stringResource(R.string.key, keyItem.keyNumber)
                )
//                Text(
//                    text = if (lockerItem.keyNumber != null) {
//                        "Key #${lockerItem.keyNumber}"
//                    } else {
//                        "Key not found"
//                    }
//                )
            }
        }
    }

}