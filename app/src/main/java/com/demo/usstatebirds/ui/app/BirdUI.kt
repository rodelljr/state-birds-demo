package com.demo.usstatebirds.ui.app

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.demo.usstatebirds.datamodel.Bird
import com.demo.usstatebirds.datamodel.getBirds
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun BirdUI(
    windowSizeClass: WindowSizeClass
) {
    val context = LocalContext.current
    val birds: List<Bird> = getBirds(context)
    val navigator = rememberListDetailPaneScaffoldNavigator<Int>()
    val scope = rememberCoroutineScope()

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                val selectedIndex = navigator.currentDestination?.contentKey

                BirdList(
                    context = context,
                    selectionState = selectedIndex?.let { SelectionVisibilityState.ShowSelection(it) } ?: SelectionVisibilityState.NoSelection,
                    onIndexClick = { index ->
                        scope.launch {
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, index)
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                val selectedIndex = navigator.currentDestination?.contentKey
                if (selectedIndex != null) {
                    val selectedBird = birds[selectedIndex]
                    BirdProfile(
                        bird = selectedBird,
                        windowSizeClass = windowSizeClass
                    )
                }
            }
        }
    )
}