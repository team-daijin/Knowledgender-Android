package dgsw.stac.knowledgender.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dgsw.stac.knowledgender.ui.feature.main.BottomNavItem
import dgsw.stac.knowledgender.ui.feature.main.CARDNEWS
import dgsw.stac.knowledgender.ui.feature.main.CARDNEWSDETAIL
import dgsw.stac.knowledgender.ui.feature.main.MainViewModel
import dgsw.stac.knowledgender.ui.feature.main.childfeature.cardnews.CardNewsScreen
import dgsw.stac.knowledgender.ui.feature.main.childfeature.cardnews.CardNewsViewModel
import dgsw.stac.knowledgender.ui.feature.main.childfeature.cardnewsdetail.CardNewsDetailScreen
import dgsw.stac.knowledgender.ui.feature.main.childfeature.cardnewsdetail.CardNewsDetailViewModel
import dgsw.stac.knowledgender.ui.feature.main.childfeature.home.HomeScreen
import dgsw.stac.knowledgender.ui.feature.main.childfeature.home.HomeViewModel
import dgsw.stac.knowledgender.ui.feature.main.childfeature.map.MapScreen
import dgsw.stac.knowledgender.ui.feature.main.childfeature.map.MapViewModel
import dgsw.stac.knowledgender.ui.feature.main.childfeature.mypage.MyPageScreen
import dgsw.stac.knowledgender.ui.feature.main.childfeature.mypage.MyPageViewModel

@Composable
fun NavigationDepth2(
    viewModel: MainViewModel,
    navController: NavHostController,
    onNavigationRequested: (String) -> Unit
) {

    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            HomeScreenDestination(navController = navController)
        }
        composable(BottomNavItem.Center.route) {
            MapScreenDestination(navController)
        }
        composable(BottomNavItem.My.route) {
            MyScreenDestination(viewModel, navController)
        }
        composable(
            "${CARDNEWS}/{category}",
            listOf(navArgument("category") {
                type = NavType.StringType
            })
        ) { backStack ->
            val category = backStack.arguments?.getString("category")

            if (category != null) {
                CardNewsDestination(category = category, navController = navController)
            }
        }
        composable(
            "$CARDNEWSDETAIL/{id}",
            listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            val id = it.arguments?.getString("id")
            if (id != null) {
                CardNewsDetailDestination(id = id, navController = navController)
            }
        }
    }
}

@Composable
fun CardNewsDestination(category: String, navController: NavHostController) {
    val viewModel: CardNewsViewModel = hiltViewModel()
    CardNewsScreen(
        category,
        viewModel = viewModel,
        onNavigtionRequested = { navController.navigate(it) }
    ) { navController.popBackStack() }
}

@Composable
fun CardNewsDetailDestination(id: String, navController: NavHostController) {
    val viewModel: CardNewsDetailViewModel = hiltViewModel()
    CardNewsDetailScreen(
        id = id,
        viewModel = viewModel,
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
fun MapScreenDestination(navController: NavHostController) {
    val viewModel: MapViewModel = hiltViewModel()
    MapScreen(viewModel = viewModel){
        navController.navigate(it)
    }
}

@Composable
fun MyScreenDestination(main: MainViewModel, navController: NavHostController) {
    val viewModel: MyPageViewModel = hiltViewModel()
    MyPageScreen(viewModel = viewModel) {
        navController.navigate(it)
    }
}