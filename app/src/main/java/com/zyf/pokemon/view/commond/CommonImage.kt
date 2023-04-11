package com.zyf.pokemon.view.commond

import androidx.compose.foundation.Image
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.Transformation
import com.zyf.pokemon.R


@Composable
fun CommonNetworkImage(
    url: Any?,
    placeholder: Int = R.drawable.ic_pokeball,
    error: Int = R.drawable.ic_pokeball,
    contentScale: ContentScale = ContentScale.Crop,
    allowHardware: Boolean = false,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    transformations: List<Transformation>? = null
) {
    val modelBuilder = ImageRequest.Builder(LocalContext.current)
        .data(url ?: "")
        .crossfade(false)
        .allowHardware(allowHardware)
        .transformations()

    if (placeholder != -1) {
        modelBuilder.placeholder(placeholder)
    }
    if (error != -1) {
        modelBuilder.error(error)
    }

    if (transformations != null) {
        modelBuilder.transformations(transformations)
    }

    AsyncImage(
        model = modelBuilder.build(),
        contentDescription = "头像",
        contentScale = contentScale,
        modifier = modifier,
        colorFilter = colorFilter,
    )
}


@Composable
fun CommonLocalImage(
    resId: Int,
    allowHardware: Boolean = false,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null
) {

    Image(
        rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current).data(resId).apply(block = fun ImageRequest.Builder.() {
            allowHardware(allowHardware)
        }).build()),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        colorFilter = colorFilter
    )
}

@Composable
fun CommonIcon(
    resId: Int,
    modifier: Modifier = Modifier,
    tint: Color = Color.White
) {

    Icon(
        painter = painterResource(resId),
        contentDescription = null,
        modifier = modifier,
        tint = tint
    )
}