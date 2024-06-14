package dgsw.stac.knowledgender.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dgsw.stac.knowledgender.navigation.Route.CARDNEWS
import dgsw.stac.knowledgender.navigation.Route.CARDNEWSDETAIL
import dgsw.stac.knowledgender.navigation.Route.CENTER
import dgsw.stac.knowledgender.navigation.Route.CHAT
import dgsw.stac.knowledgender.navigation.Route.HOME
import dgsw.stac.knowledgender.navigation.Route.LOGIN
import dgsw.stac.knowledgender.navigation.Route.MY
import dgsw.stac.knowledgender.navigation.Route.REGISTER
import dgsw.stac.knowledgender.navigation.Route.SPLASH
import dgsw.stac.knowledgender.ui.components.BaseDialog
import dgsw.stac.knowledgender.ui.feature.appbar.AppbarViewModel
import dgsw.stac.knowledgender.ui.feature.appbar.BottomAppbar
import dgsw.stac.knowledgender.ui.feature.appbar.TopBar
import dgsw.stac.knowledgender.ui.feature.cardnews.CardNewsScreen
import dgsw.stac.knowledgender.ui.feature.cardnews.CardNewsViewModel
import dgsw.stac.knowledgender.ui.feature.cardnewsdetail.CardNewsDetailScreen
import dgsw.stac.knowledgender.ui.feature.cardnewsdetail.CardNewsDetailViewModel
import dgsw.stac.knowledgender.ui.feature.chatting.ChattingScreen
import dgsw.stac.knowledgender.ui.feature.chatting.ChattingViewModel
import dgsw.stac.knowledgender.ui.feature.home.HomeScreen
import dgsw.stac.knowledgender.ui.feature.home.HomeViewModel
import dgsw.stac.knowledgender.ui.feature.login.LoginScreen
import dgsw.stac.knowledgender.ui.feature.login.LoginViewModel
import dgsw.stac.knowledgender.ui.feature.map.MapScreen
import dgsw.stac.knowledgender.ui.feature.map.MapViewModel
import dgsw.stac.knowledgender.ui.feature.mypage.MyPageScreen
import dgsw.stac.knowledgender.ui.feature.mypage.MyPageViewModel
import dgsw.stac.knowledgender.ui.feature.register.RegisterScreen
import dgsw.stac.knowledgender.ui.feature.register.RegisterViewModel
import dgsw.stac.knowledgender.ui.feature.splash.SplashScreen
import dgsw.stac.knowledgender.util.Category
import dgsw.stac.knowledgender.util.categoryConverter

@Composable
fun Navigation() {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val mapViewModel: MapViewModel = hiltViewModel()
    val myPageViewModel: MyPageViewModel = hiltViewModel()
    val cardNewsViewModel: CardNewsViewModel = hiltViewModel()
    val cardNewsDetailViewModel: CardNewsDetailViewModel = hiltViewModel()
    val registerViewModel: RegisterViewModel = hiltViewModel()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val chattingViewModel: ChattingViewModel = hiltViewModel()


    val appbarViewModel: AppbarViewModel = hiltViewModel()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val onLoginRequested = remember {
        mutableStateOf(false)
    }
    val positionChecked = remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (onLoginRequested.value) {
            BaseDialog(
                onLoginRequested = {
                    navController.navigate(LOGIN)
                },
                openDialogCustom = onLoginRequested,
                title = "로그인 하시겠습니까?",
                description = "로그인이 필요한 기능입니다."
            )
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            when (currentDestination?.route) {
                CENTER, HOME, MY, "$CARDNEWS/{category}", "$CARDNEWSDETAIL/{id}" -> BottomAppbar(
                navController = navController,
                viewModel = appbarViewModel,
                context = LocalContext.current
            )

                else -> {}
            }

        },
        topBar = {
            when (currentDestination?.route) {
                CENTER, HOME, MY, "$CARDNEWS/{category}", "$CARDNEWSDETAIL/{id}" -> TopAppBar(
                    elevation = 0.dp,
                    backgroundColor = if (currentDestination.route == HOME && !positionChecked.value) {
                        Color.Transparent
                    } else {
                        Color.White
                    }
                ) {
                    TopBar(
                        route = currentDestination.route == HOME && !positionChecked.value,
                        context = LocalContext.current,
                        viewModel = appbarViewModel
                    )
                }

                else -> {}
            }
        }
    ) {
        NavHost(
            modifier = when (currentDestination?.route) {
                HOME, CENTER, MY -> Modifier.padding(bottom = it.calculateBottomPadding())
                "$CARDNEWS/{category}", "$CARDNEWSDETAIL/{id}" -> Modifier.padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                else -> Modifier
            },
            navController = navController,
            startDestination = SPLASH,
            enterTransition = { EnterTransition.None },
            popEnterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            composable(SPLASH) {
                SplashDestination(navController = navController)
            }
            composable(
                HOME,
                enterTransition = { fadeIn(animationSpec = tween(500, easing = LinearEasing)) },
                exitTransition = { fadeOut(animationSpec = tween(500, easing = LinearEasing)) },
                popEnterTransition = { fadeIn(animationSpec = tween(500, easing = LinearEasing)) },
                popExitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            500,
                            easing = LinearEasing
                        )
                    )
                }
            ) {
                HomeScreenDestination(
                    viewModel = homeViewModel,
                    navController = navController,
                    positionChecked = positionChecked
                )
            }
            composable(
                CENTER,
                enterTransition = { fadeIn(animationSpec = tween(500, easing = LinearEasing)) },
                exitTransition = { fadeOut(animationSpec = tween(500, easing = LinearEasing)) },
                popEnterTransition = { fadeIn(animationSpec = tween(500, easing = LinearEasing)) },
                popExitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            500,
                            easing = LinearEasing
                        )
                    )
                }) {
                MapScreenDestination(viewModel = mapViewModel, navController = navController)
            }
            composable(
                MY,
                enterTransition = { fadeIn(animationSpec = tween(100, easing = LinearEasing)) },
                exitTransition = { fadeOut(animationSpec = tween(100, easing = LinearEasing)) },
                popEnterTransition = { fadeIn(animationSpec = tween(100, easing = LinearEasing)) },
                popExitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            100,
                            easing = LinearEasing
                        )
                    )
                }) {
                MyScreenDestination(myPageViewModel, navController)
            }
            composable(
                "${CARDNEWS}/{category}",
                listOf(navArgument("category") {
                    type = NavType.StringType
                }), enterTransition = { fadeIn(animationSpec = tween(500, easing = LinearEasing)) },
                exitTransition = { fadeOut(animationSpec = tween(500, easing = LinearEasing)) },
                popEnterTransition = { fadeIn(animationSpec = tween(500, easing = LinearEasing)) },
                popExitTransition = { fadeOut(animationSpec = tween(500, easing = LinearEasing)) }
            ) { backStack ->
                val category = categoryConverter(backStack.arguments?.getString("category"))

                CardNewsDestination(
                    currentDestination = currentDestination,
                    category = category,
                    viewModel = cardNewsViewModel,
                    navController = navController
                )
            }
            composable(
                "$CARDNEWSDETAIL/{id}",
                listOf(navArgument("id") {
                    type = NavType.StringType
                }),
                enterTransition = { fadeIn(animationSpec = tween(100, easing = LinearEasing)) },
                exitTransition = { fadeOut(animationSpec = tween(100, easing = LinearEasing)) },
                popEnterTransition = { fadeIn(animationSpec = tween(100, easing = LinearEasing)) },
                popExitTransition = { fadeOut(animationSpec = tween(100, easing = LinearEasing)) }
            ) {
                val id = it.arguments?.getString("id")
                if (id != null) {
                    CardNewsDetailDestination(
                        id = id,
                        viewModel = cardNewsDetailViewModel,
                        navController = navController
                    )
                }
            }
            composable(
                REGISTER,
                enterTransition = { fadeIn(animationSpec = tween(100, easing = LinearEasing)) },
                exitTransition = { fadeOut(animationSpec = tween(100, easing = LinearEasing)) },
                popEnterTransition = { fadeIn(animationSpec = tween(100, easing = LinearEasing)) },
                popExitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            100,
                            easing = LinearEasing
                        )
                    )
                }) {
                RegisterDestination(viewModel = registerViewModel, navController = navController)
            }
            composable(
                LOGIN,
                enterTransition = { fadeIn(animationSpec = tween(100, easing = LinearEasing)) },
                exitTransition = { fadeOut(animationSpec = tween(100, easing = LinearEasing)) },
                popEnterTransition = { fadeIn(animationSpec = tween(100, easing = LinearEasing)) },
                popExitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            100,
                            easing = LinearEasing
                        )
                    )
                }) {
                LoginDestination(viewModel = loginViewModel, navController = navController)
            }
            composable(
                CHAT,
                enterTransition = { fadeIn(animationSpec = tween(100, easing = LinearEasing)) },
                exitTransition = { fadeOut(animationSpec = tween(100, easing = LinearEasing)) },
                popEnterTransition = { fadeIn(animationSpec = tween(100, easing = LinearEasing)) },
                popExitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            100,
                            easing = LinearEasing
                        )
                    )
                }) {
                ChattingDestination(viewModel = chattingViewModel, navController = navController)
            }
        }
    }
}

@Composable
fun CardNewsDestination(
    currentDestination: NavDestination?,
    category: Category,
    viewModel: CardNewsViewModel,
    navController: NavHostController
) {
    CardNewsScreen(
        currentRoute = currentDestination,
        category = category,
        viewModel = viewModel,
        onNavigtionRequested = {
            navController.navigate(it) {
                navController.graph.id.let {
                    popUpTo(it) {
                        saveState = true
                        inclusive = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    ) {
        navController.navigate(HOME) {
            navController.graph.id.let {
                popUpTo(it) {
                    saveState = true
                    inclusive = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

@Composable
fun CardNewsDetailDestination(
    id: String,
    viewModel: CardNewsDetailViewModel,
    navController: NavHostController
) {
    CardNewsDetailScreen(
        id = id,
        viewModel = viewModel,
        backRequested = {
            navController.popBackStack()
        }
    )
}

@Composable
fun SplashDestination(
    navController: NavHostController
) {
    SplashScreen {
        navController.navigate(it)
    }
}

@Composable
fun HomeScreenDestination(
    viewModel: HomeViewModel,
    positionChecked: MutableState<Boolean>,
    navController: NavHostController,
) {
    HomeScreen(viewModel = viewModel, positionChecked = positionChecked) {
        navController.navigate(it)
    }
}

@Composable
fun MapScreenDestination(viewModel: MapViewModel, navController: NavHostController) {
    MapScreen(viewModel = viewModel) {
        navController.navigate(it)
    }
}

@Composable
private fun LoginDestination(viewModel: LoginViewModel, navController: NavHostController) {
    LoginScreen(
        viewModel = viewModel,
        onNavigationRequested = {
            navController.navigate(it)
        }
    )
}

@Composable
private fun RegisterDestination(viewModel: RegisterViewModel, navController: NavHostController) {
    RegisterScreen(
        viewModel = viewModel,
        onNavigationRequested = {
            navController.navigate(it)
        }
    )
}

@Composable
private fun ChattingDestination(viewModel: ChattingViewModel, navController: NavHostController) {
    ChattingScreen(viewModel = viewModel)
}

@Composable
fun MyScreenDestination(viewModel: MyPageViewModel, navController: NavHostController) {
    MyPageScreen(viewModel = viewModel) {
        navController.navigate(it)
    }
}

object Route {
    const val SPLASH = "SPLASH"
    const val LOGIN = "LOGIN"
    const val REGISTER = "REGISTER"
    const val CHAT = "CHAT"
    const val HOME = "HOME"
    const val CENTER = "CENTER"
    const val MY = "MY"
    const val CARDNEWS = "CARDNEWS"
    const val CARDNEWSDETAIL = "CARDNEWSDETAIL"
}