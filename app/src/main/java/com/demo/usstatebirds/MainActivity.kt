package com.demo.usstatebirds

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.demo.usstatebirds.ui.theme.USStateBirdsComposeTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.demo.usstatebirds.ui.app.BirdUI

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            USStateBirdsComposeTheme {
                MainScreen(calculateWindowSizeClass(this))
            }
        }

    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(sizeClass: WindowSizeClass) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "US State Birds"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        content = {
            padding ->
            Surface(modifier = Modifier.fillMaxSize().padding(padding)) {
                BirdUI(
                    windowSizeClass = sizeClass
                )
            }


        })
}

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        USStateBirdsComposeTheme {}
    }