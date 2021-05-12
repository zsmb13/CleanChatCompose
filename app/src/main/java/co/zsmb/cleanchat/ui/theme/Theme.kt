package co.zsmb.cleanchat.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import co.zsmb.cleanchat.R

private val Metropolis = FontFamily(
    Font(R.font.metropolis_light, FontWeight.Light),
    Font(R.font.metropolis_regular, FontWeight.Normal),
    Font(R.font.metropolis_regular_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.metropolis_medium, FontWeight.Medium),
    Font(R.font.metropolis_bold, FontWeight.Bold)
)

private val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

@Composable
fun CleanChatTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = Typography,
        content = content
    )
}
