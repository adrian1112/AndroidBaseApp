package com.kalagui.residify.ui.screens.Login

//import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kalagui.residify.R
import com.kalagui.residify.navigation.NavigationEvent
import com.kalagui.residify.navigation.ResidifyScreens
import com.kalagui.residify.ui.components.AppBar
import com.kalagui.residify.ui.components.CustomTextField
import com.kalagui.residify.ui.components.Loading
import com.kalagui.residify.ui.components.PasswordField
import com.kalagui.residify.ui.theme.ResidifyTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    ) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val form by viewModel.form.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

//    LaunchedEffect(Unit) {
//        viewModel.navigationEvents.collectLatest { event ->
//            when (event) {
//                NavigationEvent.NavigateToHome -> {
//                    navController.navigate(ResidifyScreens.Home) {
//                        popUpTo(ResidifyScreens.Login.name) { inclusive = true }
//                    }
//                }
//                else -> {
//                    //TODO
//                }
//            }
//        }
//    }

    Scaffold(
        topBar = {
            AppBar(titleString = "",
                showImageHeader = false,
                onBackClick = { println("presionado btn back") },
                false,
                modifier = Modifier)
        }
    ) { it ->


        Box(
            modifier = modifier
                .statusBarsPadding()
                .padding(it)
                .padding(horizontal = 20.dp)
                .fillMaxSize()
                .safeDrawingPadding()
        ) {
            Column(
                modifier = modifier
                    .padding(bottom = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.25f)
                ) {
                    Image(
                        modifier = modifier.align(alignment = Alignment.Center),
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = ""
                    )
                }

                Box(modifier = modifier.fillMaxWidth()) {
                    Text(
                        modifier = modifier
                            .padding(top = 20.dp)
                            .align(alignment = Alignment.Center),
                        text = "Residify",
                        fontSize = 25.sp,
                        color = Color.Red,
                    )
                }

                Text(
                    modifier = modifier.padding(top = 5.dp),
                    text = "La App que tiene todo lo que un conjunto residencial necesita.",
                    fontSize = 18.sp,
                )
//                TextField(
//                    modifier = modifier
//                        .padding(top = 20.dp)
//                        .fillMaxWidth(),
//                    value = form.mail,
//                    onValueChange = { viewModel.setMail(it) },
//                    singleLine = true,
//                    placeholder = { Text(text = "Correo") })
                CustomTextField(
                    modifier = modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                    value = form.mail,
                    onValueChange = { viewModel.setMail(it) },
                    placeholder = "Correo",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                PasswordField(modifier = modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                    value = form.password,
                    onValueChange = { viewModel.setPassword(it) },
                    placeholder = "Contraseña"
                )
                errorMessage?.let { message ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                TextButton(modifier = modifier.padding(top = 20.dp), onClick = { /*TODO*/ }) {
                    Text(text = "Recuperar mi contraseña")
                }
                Button(
                    modifier = modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .height(45.dp),
                    onClick = {
                        keyboardController?.hide()
                        viewModel.login(){
                            navController.navigate(ResidifyScreens.Registration.name)
                        }
                    }
                ) {
                    Text(text = "Continuar")
                }
            }
            Box(
                modifier = modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.BottomEnd)

            ) {
                TextButton(
                    modifier = modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .height(45.dp),
                    onClick = {
                        keyboardController?.hide()
                        navController.navigate(ResidifyScreens.Registration.name)
                    }
                ) {
                    Row {
                        Text(text = "¿Aún no tienes una cuenta?")
                        Spacer(modifier = modifier.width(10.dp))
                        Text(text = "Regístrate", fontSize = 16.sp)
                    }

                }
            }
        }
    }
    if( isLoading ) {
        Loading()
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Login",
)
@Composable
fun GreetingPreview() {
    ResidifyTheme {
        val navController = rememberNavController()
        Login(navController = navController)
    }
}