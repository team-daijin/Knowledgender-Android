package dgsw.stac.knowledgender.ui.feature.appbar


import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.navigation.Route.CARDNEWS
import dgsw.stac.knowledgender.navigation.Route.CARDNEWSDETAIL
import dgsw.stac.knowledgender.navigation.Route.CENTER
import dgsw.stac.knowledgender.navigation.Route.HOME
import dgsw.stac.knowledgender.navigation.Route.MY
import dgsw.stac.knowledgender.ui.components.BaseText
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.LightPurple
import dgsw.stac.knowledgender.ui.theme.LighterBlack
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.BackOnPressed
import dgsw.stac.knowledgender.util.dpToSp

sealed class BottomNavItem(val name: String, val icon: Int, val route: String) {
    object Center : BottomNavItem(name = "상담센터", icon = R.drawable.knowledgender_center, CENTER)
    object Home : BottomNavItem(name = "홈", icon = R.drawable.knowledgender_home, HOME)
    object My : BottomNavItem(name = "정보", icon = R.drawable.questionmark_1, MY)
}


@Composable
fun BottomAppbar(
    navController: NavHostController,
    viewModel: AppbarViewModel,
    context: Context
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BackOnPressed()
    BottomNavigationView(
        navController = navController,
        currentRoute = currentRoute,
        context = context
    )

}

@Composable
fun TopBar(
    route: Boolean,
    context: Context,
    viewModel: AppbarViewModel
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
                colorFilter = if (route) ColorFilter.tint(Color.White) else ColorFilter.tint(
                    BasePurple
                )

            )
            BaseText(
                text = stringResource(id = R.string.title),
                color = if (route) Color.White else BasePurple,
                style = TextStyle(
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dpToSp(16.dp)
                )
            )
        }
        Row {
            Image(
                painter = painterResource(id = R.drawable.gamecontroller),
                contentDescription = "chat",
                modifier = Modifier
                    .width(22.5.dp)
                    .height(22.5.dp)
                    .clickable {
                        val urlIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.waveon.io/apps/36525")
                        )
                        context.startActivity(urlIntent)
                    },
                colorFilter = if (route) ColorFilter.tint(Color.White) else ColorFilter.tint(
                    BasePurple
                )
            )
        }
    }
}

@Composable
fun BottomNavigationView(
    navController: NavHostController,
    currentRoute: String?,
    context: Context
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
                            .size(26.dp),
                        tint = when {
                            currentRoute == "$CARDNEWS/{category}" && item.route == HOME -> LightPurple
                            currentRoute == "$CARDNEWSDETAIL/{id}" && item.route == HOME -> LightPurple
                            currentRoute == item.route -> LightPurple
                            else -> LighterBlack
                        }
                    )
                },
                label = {
                    Text(
                        text = item.name,
                        style = TextStyle(fontSize = dpToSp(9.dp)),
                        color = when {
                            currentRoute == "$CARDNEWS/{category}" && item.route == HOME -> LightPurple
                            currentRoute == "$CARDNEWSDETAIL/{id}" && item.route == HOME -> LightPurple
                            currentRoute == item.route -> LightPurple
                            else -> LighterBlack
                        }
                    )
                },
                selectedContentColor = LightPurple,
                unselectedContentColor = LighterBlack,
                selected = currentRoute == item.route,
                alwaysShowLabel = true,
                onClick = {
                    if (item.route == MY) {
                        val urlIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://bento.me/knowledgender")
                        )
                        context.startActivity(urlIntent)
                    } else if (currentRoute != item.route) {
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