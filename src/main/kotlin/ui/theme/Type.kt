package ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp

val Fellix = FontFamily(
    Font("fellix_light.otf", FontWeight.Light),
    Font("fellix_regular.otf", FontWeight.Normal),
    Font("fellix_medium.otf", FontWeight.Medium),
    Font("fellix_semibold.otf", FontWeight.SemiBold),
    Font("fellix_bold.otf", FontWeight.Bold),
)

private val defaultTextStyle = TextStyle(
    fontFamily = Fellix,
    fontWeight = FontWeight.Normal,
    letterSpacing = 0.sp
)

private val UNDEFINED_SIZE = 4.sp

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = defaultTextStyle.copy(fontSize = 32.sp),
    h2 = defaultTextStyle.copy(fontSize = 22.sp),
    h3 = defaultTextStyle.copy(fontSize = 20.sp),
    h4 = defaultTextStyle.copy(fontSize = 18.sp, fontWeight = FontWeight.Medium),
    h5 = defaultTextStyle.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold),
    h6 = defaultTextStyle.copy(fontSize = 14.sp, fontWeight = FontWeight.Bold),
    subtitle1 = defaultTextStyle.copy(fontSize = UNDEFINED_SIZE),
    subtitle2 = defaultTextStyle.copy(fontSize = UNDEFINED_SIZE),
    body1 = defaultTextStyle.copy(fontSize = 16.sp),
    body2 = defaultTextStyle.copy(fontSize = 14.sp),
    button = defaultTextStyle.copy(fontSize = 16.sp),
    caption = defaultTextStyle.copy(fontSize = 14.sp),
    overline = defaultTextStyle.copy(fontSize = 12.sp)
)