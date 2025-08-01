package com.example.core_ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.MotionScene
import com.example.core_ui.R
import com.example.core_ui.theme.Dimensions

@Composable
fun ErrorScreen(
    errorMessage: String,
    @DrawableRes errorMessageIcon: Int = R.drawable.alert_error_icon
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(errorMessageIcon),
            contentDescription = null,
            modifier = Modifier
                .size(Dimensions.dimen_64)
        )

        Text(
            modifier = Modifier.padding(Dimensions.dimen_16),
            text = errorMessage,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

