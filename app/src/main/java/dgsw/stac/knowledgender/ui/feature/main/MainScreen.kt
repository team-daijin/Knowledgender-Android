package dgsw.stac.knowledgender.ui.feature.main


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.ui.feature.main.ui.theme.KnowledgenderAndroidTheme
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme
import dgsw.stac.knowledgender.ui.theme.LightPurple
import dgsw.stac.knowledgender.ui.theme.LighterBlack

@AndroidEntryPoint
class MainScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KnowledgenderTheme {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
//        Header()
                    MainBottomNav()
                }
            }
        }
    }
}

sealed class BottomNavItem(val name: String, val icon: Int, val route: String) {
    object Home : BottomNavItem(name = "Home", icon = R.drawable.home, HOME)
    object Center : BottomNavItem(name = "Center", icon = R.drawable.center, CENTER)
    object My : BottomNavItem(name = "My", icon = R.drawable.my, MY)
}

const val HOME = "HOME"
const val CENTER = "CENTER"
const val MY = "MY"
@Composable
fun MainBottomNav() {
    val navController = rememberNavController()
    Scaffold(
        Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationView(navController = navController) }
    )
    {
        Box(modifier = Modifier.padding(it)) {
            Navigation(navController = navController)
        }
    }
}

@Composable
fun BottomNavigationView(navController: NavHostController) {

    val items = listOf<BottomNavItem>(
        BottomNavItem.Home,
        BottomNavItem.Center,
        BottomNavItem.My
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(60.dp)
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    androidx.compose.material.Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.name,
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                    )
                },
                label = { Text(text = item.name, style = TextStyle(fontSize = 9.sp)) },
                selectedContentColor = LightPurple,
                unselectedContentColor = LighterBlack,
                selected = currentRoute == item.route,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            HomeScreenDestination(navController = navController)
        }
        composable(BottomNavItem.Center.route) {
            CenterScreenDestination()
        }
        composable(BottomNavItem.My.route) {
            MyScreenDestination()
        }
    }
}

@Composable
fun HomeScreenDestination(navController: NavHostController) {
    val viewModel: MainViewModel = hiltViewModel()
    Column {
        HomeScreen(viewModel = viewModel)
    }
}

@Composable
fun CenterScreenDestination() {
    Column {

    }
}

@Composable
fun MyScreenDestination() {
    Column {

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    KnowledgenderAndroidTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            MainScreen()
        }
    }
}