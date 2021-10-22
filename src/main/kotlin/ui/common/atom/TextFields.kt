import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import ui.theme.AppColorsTheme
import ui.theme.RecipeekShapes
import ui.theme.Typography
import ui.theme.ironGrey
import androidx.compose.material.TextField as ComposeTextField

@ExperimentalAnimationApi
@Composable
fun SearchInput(
    text: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = ""
) {
    TextField(
        modifier = modifier,
        text = text,
        onTextChanged = onTextChanged,
        hint = hint,
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(resourcePath = "ic_search.png"),
                contentDescription = null
            )
        }
    )
}

@ExperimentalAnimationApi
@Composable
fun TextField(
    text: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    style: TextFieldStyle = textFieldDefaultStyle(),
    hint: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    ComposeTextField(
        value = text,
        onValueChange = onTextChanged,
        modifier = Modifier
            .fillMaxWidth(1f)
            .then(modifier),
        singleLine = singleLine,
        placeholder = {
            Text(text = hint, style = style.textStyle, color = ironGrey)
        },
        leadingIcon = leadingIcon,
        trailingIcon = {
            AnimatedVisibility(
                visible = text.isNotBlank(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(onClick = { onTextChanged("") }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close"
                    )
                }
            }
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        textStyle = style.textStyle,
        colors = style.colors,
        shape = style.shape
    )
}

data class TextFieldStyle(
    val colors: TextFieldColors,
    val shape: Shape,
    val textStyle: TextStyle
)

@Composable
fun textFieldDefaultStyle() = TextFieldStyle(
    colors = TextFieldDefaults.textFieldColors(
        textColor = AppColorsTheme.colors.text,
        disabledTextColor = AppColorsTheme.colors.text.copy(ContentAlpha.disabled),
        backgroundColor = AppColorsTheme.colors.bgTextField,
        cursorColor = AppColorsTheme.colors.mainColor,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = MaterialTheme.colors.error,
        leadingIconColor = AppColorsTheme.colors.secondaryColor.copy(alpha = TextFieldDefaults.IconOpacity),
        disabledLeadingIconColor = AppColorsTheme.colors.secondaryColor.copy(alpha = ContentAlpha.disabled),
        errorLeadingIconColor = MaterialTheme.colors.error,
        trailingIconColor = AppColorsTheme.colors.secondaryColor.copy(alpha = TextFieldDefaults.IconOpacity),
        disabledTrailingIconColor = AppColorsTheme.colors.secondaryColor.copy(alpha = ContentAlpha.disabled),
        errorTrailingIconColor = MaterialTheme.colors.error,
//        errorCursorColor = MaterialTheme.colors.error,
//        focusedLabelColor = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high),
//        unfocusedLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
//        disabledLabelColor = unfocusedLabelColor.copy(ContentAlpha.disabled),
//        errorLabelColor = MaterialTheme.colors.error,
//        placeholderColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
//        disabledPlaceholderColor = placeholderColor.copy(ContentAlpha.disabled)
    ),
    shape = RecipeekShapes.medium,
    textStyle = Typography.body1
)
