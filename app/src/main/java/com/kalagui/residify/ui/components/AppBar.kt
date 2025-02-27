package com.kalagui.residify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.kalagui.residify.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
           titleString: String = "",
           showImageHeader: Boolean = false,
           onBackClick: () -> Unit,
           showBackButton: Boolean = true,
           modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(showImageHeader) {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = null
                    )
                }
                Text(
                    text = titleString,
                )
            }
        },
        navigationIcon = {
            if( showBackButton){
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Volver")
                }
            }
        },
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTopAppBarWithBackButton() {
    AppBar(titleString = "Titulo", showImageHeader = true, onBackClick = { println("presionado btn back") }, modifier = Modifier)
}