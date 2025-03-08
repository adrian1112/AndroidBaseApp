package com.kalagui.residify.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalagui.residify.R


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(modifier: Modifier,
                  value: String,
                  onValueChange: (String) -> Unit,
                  placeholder: String = "") {

    var passwordVisible by remember { mutableStateOf(false) }
    val image = if (passwordVisible) R.drawable.visibilityoff else R.drawable.visibility


    Box(
        modifier = modifier
            .background(Color(0xFFF2F2F7), shape = RoundedCornerShape(12.dp))
            .border(0.5.dp, Color(0xFFE5E5EA), RoundedCornerShape(12.dp))
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        fontSize = 18.sp,
                        modifier = Modifier.alpha(0.6f)
                    )
                }
                innerTextField()
            },
            visualTransformation = if (!passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )
        Box(
            modifier =
                Modifier
            .padding(end = 5.dp)
            .size(40.dp)
            .align(Alignment.CenterEnd)
        ) {

            IconButton(modifier = Modifier.fillMaxSize(), onClick = { passwordVisible = !passwordVisible }) {
                Image(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = image),
                    contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                    colorFilter = ColorFilter.tint(Color.DarkGray)
                )
            }
        }
    }
}
//    TextField(
//        modifier = modifier,
//        value = value,
//        onValueChange = onTextChange,
//        singleLine = true,
//        placeholder = { Text("Contraseña") },
//        visualTransformation = if(!passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//        trailingIcon = {
//            val image = if (passwordVisible) R.drawable.visibilityoff else R.drawable.visibility
//            IconButton(onClick = { passwordVisible = !passwordVisible }) {
//                Image(modifier = modifier.width(25.dp).height(25.dp), painter = painterResource(id = image),
//                    contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
//                    colorFilter = ColorFilter.tint(Color.DarkGray))
//            }
//        }
//    )
//}