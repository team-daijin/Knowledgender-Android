package dgsw.stac.knowledgender.ui.feature.main


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.navigation.NavigationDepth2
import dgsw.stac.knowledgender.navigation.Route
import dgsw.stac.knowledgender.navigation.Route.LOGIN
import dgsw.stac.knowledgender.ui.components.BaseText
import dgsw.stac.knowledgender.ui.components.NoLoginDialog
import dgsw.stac.knowledgender.ui.components.NotReadyDialog
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.LightPurple
import dgsw.stac.knowledgender.ui.theme.LighterBlack
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.BackOnPressed
import dgsw.stac.knowledgender.util.dpToSp

sealed class BottomNavItem(val name: String, val icon: Int, val route: String) {
    object Center: BottomNavItem(name = "상담센터",icon = R.drawable.knowledgender_center, CENTER)
    object Home : BottomNavItem(name = "홈", icon = R.drawable.knowledgender_home, HOME)
    object My : BottomNavItem(name = "마이", icon = R.drawable.knowledgender_my, MY)
}

const val HOME = "HOME"
const val CENTER = "CENTER"
const val MY = "MY"
const val CARDNEWS = "CARDNEWS"
const val CARDNEWSDETAIL = "CARDNEWSDETAIL"


@Composable
fun MainScreen(viewModel: MainViewModel, onNavigationRequested: (String) -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isLogin by viewModel.isLogin.collectAsState()
    val onLoginRequested = remember { mutableStateOf(false) }
    val notReady = remember { mutableStateOf(false) }
    BackOnPressed()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationView(
                onLoginRequested,
                navController = navController,
                currentRoute = currentRoute,
                isLogin = isLogin
            )
        }
    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            if (onLoginRequested.value) {
                NoLoginDialog(
                    onLoginRequested = {
                        onNavigationRequested(LOGIN)
                    },
                    openDialogCustom = onLoginRequested
                )
            }
            if (notReady.value) {
                NotReadyDialog(openDialogCustom = notReady)
            }
            NavigationDepth2(
                navController = navController,
                viewModel = viewModel,
                onNavigationRequested = onNavigationRequested
            )
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = if (currentRoute == HOME) {
                    Color.Transparent
                } else {
                    Color.White
                }
            ) {
                TopBar(
                    onLoginRequested,
                    onNavigationRequested,
                    currentRoute = currentRoute,
                    isLogin,
                    notReady = notReady
                )
            }
        }
    }
}

@Composable
fun TopBar(
    onLoginRequested: MutableState<Boolean>,
    onNavigationRequested: (String) -> Unit,
    currentRoute: String?,
    isLogin: Boolean,
    notReady: MutableState<Boolean>
) {

    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.knowledgender_logo),
                contentDescription = "TopAppBar",
                modifier = Modifier
                    .height(22.dp)
                    .wrapContentWidth()
                    .padding(end = 4.dp),
                contentScale = ContentScale.Crop,
                colorFilter = if (currentRoute == HOME) {
                    ColorFilter.tint(Color.White)
                } else {
                    ColorFilter.tint(BasePurple)
                }
            )
            BaseText(
                text = stringResource(id = R.string.title),
                color = if (currentRoute == HOME) {
                    Color.White
                } else {
                    BasePurple
                },
                style = TextStyle(
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dpToSp(16.dp)
                )
            )
        }
        Image(
            painter = painterResource(id = R.drawable.knowledgender_chat),
            contentDescription = "chat",
            modifier = Modifier
                .width(22.5.dp)
                .height(22.5.dp)
                .clickable {
                    notReady.value = true
//                    if (!isLogin) {
//                        onLoginRequested.value = true
//                    } else {
//                        onNavigationRequested(Route.CHAT)
//                    }
                },
            colorFilter = if (currentRoute == HOME) {
                ColorFilter.tint(Color.White)
            } else {
                ColorFilter.tint(BasePurple)
            }
        )
    }


}

@Composable
fun BottomNavigationView(
    onLoginRequested: MutableState<Boolean>,
    navController: NavHostController,
    currentRoute: String?,
    isLogin: Boolean
) {
    val items = listOf(
        BottomNavItem.Center,
        BottomNavItem.Home,
        BottomNavItem.My
    )

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        backgroundColor = Color.White
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.name,
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp),
                        tint = if (currentRoute == item.route) LightPurple else LighterBlack
                    )
                },
                label = {
                    Text(
                        text = item.name,
                        style = TextStyle(fontSize = dpToSp(9.dp)),
                        color = when {
                            currentRoute == item.route && currentRoute == HOME -> LightPurple
                            else -> LighterBlack
                        }

                    )
                },
                selectedContentColor = LightPurple,
                unselectedContentColor = LighterBlack,
                selected = currentRoute == item.route,
                alwaysShowLabel = true,
                onClick = {
                    if (!isLogin && item.route != HOME) {
                        onLoginRequested.value = true
                    } else {
                        navController.navigate(item.route) {
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
            )
        }
    }
}