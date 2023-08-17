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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.ui.feature.main.childfeature.cardnews.CardNewsScreen
import dgsw.stac.knowledgender.ui.feature.main.childfeature.cardnews.CardNewsViewModel
import dgsw.stac.knowledgender.ui.feature.main.childfeature.cardnewsdetail.CardNewsDetailScreen
import dgsw.stac.knowledgender.ui.feature.main.childfeature.cardnewsdetail.CardNewsDetailViewModel
import dgsw.stac.knowledgender.ui.feature.main.childfeature.home.HomeScreen
import dgsw.stac.knowledgender.ui.feature.main.childfeature.home.HomeViewModel
import dgsw.stac.knowledgender.ui.feature.main.childfeature.map.MapScreen
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
    object Center : BottomNavItem(name = "Center", icon = R.drawable.center, CENTER)
    object Home : BottomNavItem(name = "Home", icon = R.drawable.home, HOME)
    object My : BottomNavItem(name = "My", icon = R.drawable.my, MY)
}

const val HOME = "HOME"
const val CENTER = "CENTER"
const val MY = "MY"
const val CARDNEWS = "CARDNEWS"
const val CARDNEWSDETAIL = "CARDNEWSDETAIL"

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
            MapScreenDestination()
        }
        composable(BottomNavItem.My.route) {
            MyScreenDestination()
        }
        composable(
            "$CARDNEWS/{category}",
            listOf(navArgument("category") {
                type = NavType.StringType
            })
        ) {
            val category = it.arguments?.getString("category")
            CardNewsDestination(category = category!!, navController = navController)
        }
        composable(
            "$CARDNEWSDETAIL/{id}",
            listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            val id = it.arguments?.getString("id")
            CardNewsDetailDestination(id = id!!, navController = navController)
        }
    }
}

@Composable
fun CardNewsDestination(category: String, navController: NavHostController) {
    val viewModel: CardNewsViewModel = hiltViewModel()
    CardNewsScreen(category, viewModel = viewModel, backRequested = {navController.popBackStack()}, onNavigateRequested = { navController.navigate(it) })
}

@Composable
fun CardNewsDetailDestination(id: String, navController: NavHostController) {
    val viewModel: CardNewsDetailViewModel = hiltViewModel()
    CardNewsDetailScreen(
        id = id,
        viewModel = viewModel,
        onNavigationRequested = { navController.navigate(it) },
        backRequested = {
            navController.popBackStack()
        }
    )
}

@Composable
fun HomeScreenDestination(navController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    HomeScreen(viewModel = viewModel) {
        navController.navigate(it)
    }
}

@Composable
fun MapScreenDestination() {
    MapScreen()
}

@Composable
fun MyScreenDestination() {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    KnowledgenderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            MainScreen()
        }
    }
}