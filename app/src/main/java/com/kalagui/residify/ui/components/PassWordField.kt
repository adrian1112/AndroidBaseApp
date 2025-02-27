package com.kalagui.residify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.kalagui.residify.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(modifier: Modifier = Modifier,
                  value: String,
                  onTextChange: (String) -> Unit) {

    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onTextChange,
        singleLine = true,
        placeholder = { Text("Contraseña") },
        visualTransformation = if(!passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible) R.drawable.visibilityoff else R.drawable.visibility
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Image(modifier = modifier.width(25.dp).height(25.dp), painter = painterResource(id = image),
                    contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                    colorFilter = ColorFilter.tint(Color.DarkGray))
            }
        }
    )
}