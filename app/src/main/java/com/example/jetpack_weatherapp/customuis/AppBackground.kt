package com.example.jetpack_weatherapp.customuis

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AppBackground(
    @DrawableRes photoId: Int,
    modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(photoId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .alpha(0.7f)
            .blur(
                radius = 8.dp,
                edgeTreatment = BlurredEdgeTreatment.Unbounded
            )
    )
}