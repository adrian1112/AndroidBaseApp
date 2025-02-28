package com.kalagui.residify.ui.screens.Register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kalagui.residify.R
import com.kalagui.residify.ui.components.AppBar
import com.kalagui.residify.ui.components.PasswordField
import com.kalagui.residify.ui.theme.ResidifyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registration(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RegistrationViewModel = viewModel(),
    ) {
//    var revenue by rememberSaveable { mutableStateOf(0) }
    val form by viewModel.form.collectAsState()
    //Get current back stack entry
    //val currentBackStackEntry by navController.currentBackStackEntryAsState()
    //Get the name of the current screen
    //val userName = currentBackStackEntry?.arguments?.getString("userName") ?: "Invitado"

    Scaffold(
        topBar = {
            AppBar(titleString = "",
            showImageHeader = false,
            onBackClick = { navController.popBackStack() },
            modifier = Modifier)
        }
    ) { it ->
        Column(
            modifier = modifier
                .statusBarsPadding()
                .padding(it)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .safeDrawingPadding()
        ) {
            Box(modifier = modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp)) {
                Text(
                    text = "Registro",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Red,
                    modifier = modifier.align(alignment = Alignment.Center),)
            }
            TextField(
                modifier = modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                value = form.name,
                onValueChange = { viewModel.setName(it) },
                singleLine = true,
                placeholder = { Text(text = "Nombre") })
            TextField(
                modifier = modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                value = form.mail,
                onValueChange = { viewModel.setMail(it) },
                singleLine = true,
                placeholder = { Text(text = "Correo") })
            PasswordField(modifier = modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
                value = form.password,
                onTextChange = {viewModel.setPassword(it)})
            PasswordField(modifier = modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
                value = form.confirmPassword,
                onTextChange = {viewModel.setConfirmPassword(it)})

            Row(modifier = modifier.padding(vertical = 35.dp)) {
                Image(modifier = modifier
                    .padding(end = 5.dp)
                    .align(alignment = Alignment.CenterVertically),
                    painter = painterResource(id = R.drawable.privacytip),
                    contentDescription = "Términos y condiciones",
                    colorFilter = ColorFilter.tint(Color.DarkGray))
                Text(text = "Al crear la cuenta indicas que estas de acuerdo con los términos y condiciones.",
                    fontSize = 12.sp)
            }
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .height(45.dp),
                enabled = form.validForm,
                onClick = { /*TODO*/ }

            ) {
                Text(text = "Registrarme")
            }
        }

    }

}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Register",
)
@Composable
fun GreetingPreview() {
    ResidifyTheme {
        val navController = rememberNavController()
        Registration(navController = navController)
    }
}