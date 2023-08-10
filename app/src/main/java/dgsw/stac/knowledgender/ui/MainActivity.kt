package dgsw.stac.knowledgender.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dgsw.stac.knowledgender.ui.feature.login.LoginScreen
import dgsw.stac.knowledgender.ui.feature.login.LoginViewModel
import dgsw.stac.knowledgender.ui.feature.register.RegisterScreen
import dgsw.stac.knowledgender.ui.feature.register.RegisterViewModel
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KnowledgenderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    KnowledgenderScreens()
                }

            }
        }
    }
}
@Composable
private fun KnowledgenderScreens() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.LOGIN
    ) {
        composable(
            "LoginScreen",

        ) {
            Column {
                LoginDestination(navController = navController)
            }
        }
        composable("RegisterScreen") {
            Column {
                RegisterDestination(navController = navController)
            }
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

object Route {
    const val LOGIN = "LoginScreen"
    const val REGISTER = "RegisterScreen"
}