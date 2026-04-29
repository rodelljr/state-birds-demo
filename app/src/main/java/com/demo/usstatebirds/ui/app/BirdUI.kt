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

    // 1. Create a coroutine scope for suspend navigation events
    val scope = rememberCoroutineScope()

    // 2. The manual BackHandler is removed. NavigableListDetailPaneScaffold handles it internally.

    // 3. Use NavigableListDetailPaneScaffold
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                // 4. Update 'content' to 'contentKey'
                val selectedIndex = navigator.currentDestination?.contentKey

                BirdList(
                    context = context,
                    selectionState = selectedIndex?.let { SelectionVisibilityState.ShowSelection(it) } ?: SelectionVisibilityState.NoSelection,
                    onIndexClick = { index ->
                        // 5. Launch suspend navigation function within a coroutine
                        scope.launch {
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, index)
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                // 6. Update 'content' to 'contentKey'
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