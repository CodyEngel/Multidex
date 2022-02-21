package dev.multidex.android.common.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

@Composable
fun Chip(text: String, colorHex: String) {
    Surface(
        modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(4.dp),
        color = Color(colorHex.toColorInt())
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = text
        )
    }
}

@Preview
@Composable
fun ChipPreview() {
    Chip(text = "Grass", colorHex = "#FF0000")
}