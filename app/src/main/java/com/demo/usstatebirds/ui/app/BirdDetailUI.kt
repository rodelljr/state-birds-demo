package com.demo.usstatebirds.ui.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.demo.usstatebirds.R
import com.demo.usstatebirds.datamodel.Bird
import com.demo.usstatebirds.datamodel.getDrawableFromName


@Composable
fun BirdProfile(bird: Bird, windowSizeClass: WindowSizeClass) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints {
            Surface {
                if (windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        BirdHeader(
                            bird,
                            Modifier.weight(1f)
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                                .verticalScroll(scrollState),
                        ) {
                            BirdTitle(bird)
                            BirdInfo(stringResource(R.string.nomenclature_title), bird.nomenclature)
                            BirdInfo(stringResource(R.string.state_title), bird.state)
                            BirdInfo(stringResource(R.string.year_title), bird.year.toString())
                            BirdInfo(stringResource(R.string.description_title), bird.description)
                            BirdInfo(stringResource(R.string.male_title), bird.male)
                            BirdInfo(stringResource(R.string.female_title), bird.female)
                            BirdHistory(bird)
                        }
                    }
                } else {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                    ) {
                        BirdHeader(
                            bird,
                            Modifier.heightIn(max = this@BoxWithConstraints.maxHeight / 1.7f)
                        )
                        BirdTitle(bird)
                        BirdInfo(stringResource(R.string.nomenclature_title), bird.nomenclature)
                        BirdInfo(stringResource(R.string.state_title), bird.state)
                        BirdInfo(stringResource(R.string.year_title), bird.year.toString())
                        BirdInfo(stringResource(R.string.description_title), bird.description)
                        BirdInfo(stringResource(R.string.male_title), bird.male)
                        BirdInfo(stringResource(R.string.female_title), bird.female)
                        BirdHistory(bird)
                    }
                }
            }
        }
    }
}

@Composable
private fun BirdHeader(bird: Bird, modifier: Modifier = Modifier) {
    Image( modifier = modifier.fillMaxSize(),
        painter = painterResource(id = getDrawableFromName(bird.picture, LocalContext.current, "drawable" )),
        contentScale = ContentScale.Fit,
        contentDescription = bird.name
    )
}

@Composable
private fun BirdTitle(bird: Bird) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Text( text = bird.name, style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
fun BirdInfo(label: String, value: String) {
    if(value.isNotEmpty()) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {

            Text(
                text = label, modifier = Modifier.height(24.dp),
                style = MaterialTheme.typography.labelMedium
            )
            Divider(modifier = Modifier.padding(bottom = 4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Visible
            )
        }
    }
}

@Composable
fun BirdHistory(bird: Bird) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {

        Text(
            text = stringResource(R.string.history_title), modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.labelMedium
        )
        HorizontalDivider(
            modifier = Modifier.padding(bottom = 4.dp),
            thickness = DividerDefaults.Thickness,
            color = DividerDefaults.color
        )
        Text(
            text = bird.history1,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Visible
        )
        bird.history2.let {
            Text(
                text = it,
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Visible
            )
        }

    }
}