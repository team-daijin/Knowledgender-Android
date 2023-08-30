package dgsw.stac.knowledgender.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dgsw.stac.knowledgender.ui.feature.chatting.ChattingScreen
import dgsw.stac.knowledgender.ui.feature.login.LoginScreen
import dgsw.stac.knowledgender.ui.feature.login.LoginViewModel
import dgsw.stac.knowledgender.ui.feature.main.MainScreen
import dgsw.stac.knowledgender.ui.feature.main.MainViewModel
import dgsw.stac.knowledgender.ui.feature.register.RegisterScreen
import dgsw.stac.knowledgender.ui.feature.register.RegisterViewModel

@Composable
fun navigationDepth1() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.MAIN
    ) {
        composable(Route.REGISTER) {
            RegisterDestination(navController = navController)
        }
        composable(Route.LOGIN) {
            LoginDestination(navController = navController)
        }
        composable(Route.MAIN) {
            MainDestination(navController = navController)
        }
        composable(Route.CHAT) {
            ChattingDestination(navController = navController)
        }
    }
}

@Composable
private fun LoginDestination(navController: NavHostController) {
    val viewModel: LoginViewModel = hiltViewModel()
    LoginScreen(
        viewModel = viewModel,
        onNavigationRequested = {
            navController.navigate(it)
        }
    )
}

@Composable
private fun RegisterDestination(navController: NavHostController) {
    val viewModel: RegisterViewModel = hiltViewModel()
    RegisterScreen(
        viewModel = viewModel,
        onNavigationRequested = {
            navController.navigate(it)
        }
    )
}

@Composable
private fun MainDestination(navController: NavHostController) {
    val viewModel: MainViewModel = hiltViewModel()
    MainScreen(viewModel = viewModel) {
        navController.navigate(it)
    }
}

@Composable
private fun ChattingDestination(navController: NavHostController) {
    ChattingScreen()
}

object Route {
    const val LOGIN = "LoginScreen"
    const val REGISTER = "RegisterScreen"
    const val MAIN = "Main"
    const val CHAT = "Chat"
}