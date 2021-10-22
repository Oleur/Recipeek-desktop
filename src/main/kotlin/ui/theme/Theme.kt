package ui.theme

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

private val LightColorPalette = AppColors(
    mainColor = green,
    secondaryColor = black2,
    uiBackground = white,
    isDark = false,
    text = black,
    bgButtonPrimaryOff = green,
    bgButtonPrimaryOn = green2,
    bgButtonPrimaryDisabled = grey,
    bgTextField = greyLighter,
    textHint = ironGrey
)

private val DarkColorPalette = AppColors(
    mainColor = green2,
    secondaryColor = white2,
    uiBackground = black,
    isDark = true,
    text = white,
    bgButtonPrimaryOff = green,
    bgButtonPrimaryOn = green2,
    bgButtonPrimaryDisabled = grey,
    bgTextField = casal,
    textHint = brooklynWhite

    /* Other default colors to override


    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

class AppColors(
    mainColor: Color,
    secondaryColor: Color,
    uiBackground: Color,
    bgButtonPrimaryOff: Color,
    bgButtonPrimaryOn: Color,
    bgButtonPrimaryDisabled: Color,
    bgTextField: Color,
    isDark: Boolean,
    text: Color,
    textHint: Color
) {
    var mainColor by mutableStateOf(mainColor)
        private set
    var secondaryColor by mutableStateOf(secondaryColor)
        private set
    var uiBackground by mutableStateOf(uiBackground)
        private set
    var isDark by mutableStateOf(isDark)
        private set
    var text by mutableStateOf(text)
        private set
    var bgButtonPrimaryOff by mutableStateOf(bgButtonPrimaryOff)
        private set
    var bgButtonPrimaryOn by mutableStateOf(bgButtonPrimaryOn)
        private set
    var bgButtonPrimaryDisabled by mutableStateOf(bgButtonPrimaryDisabled)
        private set
    var bgTextField by mutableStateOf(bgTextField)
        private set
    var textHint by mutableStateOf(textHint)
        private set

    fun copy(): AppColors = AppColors(
        mainColor = mainColor,
        secondaryColor = secondaryColor,
        uiBackground = uiBackground,
        isDark = isDark,
        text = text,
        bgButtonPrimaryOff = bgButtonPrimaryOff,
        bgButtonPrimaryOn = bgButtonPrimaryOn,
        bgButtonPrimaryDisabled = bgButtonPrimaryDisabled,
        bgTextField = bgTextField,
        textHint = textHint
    )

    fun update(other: AppColors) {
        mainColor = other.mainColor
        secondaryColor = other.secondaryColor
        uiBackground = other.uiBackground
        isDark = other.isDark
        text = other.text
        bgButtonPrimaryOff = other.bgButtonPrimaryOff
        bgButtonPrimaryOn = other.bgButtonPrimaryOn
        bgButtonPrimaryDisabled = other.bgButtonPrimaryDisabled
        bgTextField = other.bgTextField
        textHint = other.textHint
    }
}

@Composable
fun RecipeekTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    ProvideAppColors(colors) {
        DesktopMaterialTheme(
            colors = debugColors(darkTheme = darkTheme),
            typography = Typography,
            shapes = RecipeekShapes,
            content = content
        )
    }
}

object AppColorsTheme {
    val colors: AppColors
        @Composable
        get() = LocalRecipeekColors.current
}

@Composable
fun ProvideAppColors(
    colors: AppColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colors]
        // provided, and overwrite the values set in it.
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalRecipeekColors provides colorPalette, content = content)
}

private val LocalRecipeekColors = staticCompositionLocalOf<AppColors> {
    error("No ColorPalette provided")
}

fun debugColors(
    darkTheme: Boolean,
    debugColor: Color = Color.Transparent
) = Colors(
    primary = debugColor,
    primaryVariant = debugColor,
    secondary = debugColor,
    secondaryVariant = debugColor,
    background = debugColor,
    surface = debugColor,
    error = debugColor,
    onPrimary = debugColor,
    onSecondary = debugColor,
    onBackground = debugColor,
    onSurface = debugColor,
    onError = debugColor,
    isLight = !darkTheme
)