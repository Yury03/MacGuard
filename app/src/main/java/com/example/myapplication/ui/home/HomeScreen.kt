package com.example.myapplication.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.domain.models.MacStatus

@Composable
fun getPainter(state: HomeUiState): Painter {
    return when (state.macStatus) {
        MacStatus.NONE, MacStatus.WAIT, MacStatus.UNLOCKED -> painterResource(id = R.drawable.unlock)

        MacStatus.LOCKED -> painterResource(id = R.drawable.lock)
    }
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background)),
        verticalArrangement = Arrangement.Center,
    ) {
        IconButton(modifier = Modifier
            .width(114.dp)
            .height(168.dp)
            .align(Alignment.CenterHorizontally),
            onClick = { viewModel.sendEvent(HomeEvent.ButtonIsClicked) }) {
            Image(
                modifier = Modifier
                    .padding(14.dp),
                painter = getPainter(state),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = getText(state),
            fontSize = 18.sp
        )
    }
}

@Composable
fun getText(state: HomeUiState): String {
    return when (state.macStatus) {
        MacStatus.NONE -> stringResource(id = R.string.state_is_none)
        MacStatus.WAIT -> stringResource(id = R.string.state_is_wait)
        MacStatus.UNLOCKED -> stringResource(id = R.string.state_is_unlocked)
        MacStatus.LOCKED -> stringResource(id = R.string.state_is_locked)
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
