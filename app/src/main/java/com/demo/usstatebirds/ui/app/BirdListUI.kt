package com.demo.usstatebirds.ui.app

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.demo.usstatebirds.datamodel.Bird
import com.demo.usstatebirds.datamodel.getBirds
import com.demo.usstatebirds.datamodel.getDrawableFromName

@Composable
fun BirdList(context: Context, selectionState: SelectionVisibilityState,
             onIndexClick: (Int) -> Unit, modifier: Modifier = Modifier)
{
    val birdList = remember { getBirds(context)}
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .then(
                when (selectionState) {
                    SelectionVisibilityState.NoSelection -> Modifier
                    is SelectionVisibilityState.ShowSelection -> Modifier.selectableGroup()
                }
            )
    ) {
        itemsIndexed(birdList) {
                index, bird ->
            val interactionSource = remember { MutableInteractionSource() }

            val interactionModifier = when (selectionState) {
                SelectionVisibilityState.NoSelection -> {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = ripple(),
                        onClick = { onIndexClick(index) }
                    )
                }
                is SelectionVisibilityState.ShowSelection -> {
                    Modifier.selectable(
                        selected = index == selectionState.selectedBirdIndex,
                        interactionSource = interactionSource,
                        indication = ripple(),
                        onClick = { onIndexClick(index) }
                    )
                }
            }
            BirdItem(bird, interactionModifier)
        }
    }
}


@Composable
fun BirdItem(bird: Bird, modifier: Modifier) {
    Card(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StateImage(bird)
            Text(
                text = bird.state,
                style = typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
private fun StateImage(bird: Bird) {
    Image(
        painter = painterResource(id = getDrawableFromName(bird.thumb,LocalContext.current, "mipmap" )),
        contentDescription = bird.state,
        modifier = Modifier
            .padding(8.dp)
            .size(64.dp)
    )

}

sealed interface SelectionVisibilityState {
    object NoSelection : SelectionVisibilityState
    data class ShowSelection(val selectedBirdIndex: Int) : SelectionVisibilityState

}