package com.example.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.core_ui.theme.Dimensions

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CircularProfileImage(
    modifier: Modifier,
    imageSource: String,
    size: Dp = Dimensions.dimen_32
) {
    GlideImage(
        model = imageSource,
        contentDescription = null,
        modifier =
            modifier.size(size)
                .background(
                    color = Color.Gray,
                    shape = CircleShape
                )
                .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}