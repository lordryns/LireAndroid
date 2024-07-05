package com.radar.lire


import android.graphics.ImageDecoder
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.system.exitProcess

@Composable
fun Navigator(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screen.LibraryScreen.route ){
        composable(Screen.LibraryScreen.route){
            LibraryScreen()
        }

        composable(Screen.UpdatesScreen.route){
            UpdatesScreen()
        }
        composable(Screen.TrendingScreen.route){
            TrendingScreen()
        }


    }
}


@Composable
fun AppBase(){
    val navController = rememberNavController()
    var currentBottomBarIndex by rememberSaveable {
        mutableStateOf(0)
    }

    val scope = rememberCoroutineScope()

    val routeToIndex = mapOf(
        Screen.LibraryScreen.route to 0,
        Screen.UpdatesScreen.route to 1,
        Screen.TrendingScreen.route to 2
    )

    val bottomNavItems = listOf(
        BottomNavItem(
            title="Library",
            unselectedIcon = Icons.Default.LibraryBooks,
            selectedIcon = Icons.Filled.LibraryBooks,
            route = Screen.LibraryScreen.route
        ),
        BottomNavItem(
            title="Updates",
            unselectedIcon = Icons.Default.TipsAndUpdates,
            selectedIcon = Icons.Filled.TipsAndUpdates,
            route = Screen.UpdatesScreen.route
        ),
        BottomNavItem(
            title="Trending",
            unselectedIcon = Icons.Default.TrendingUp,
            selectedIcon = Icons.Filled.TrendingUp,
            route = Screen.TrendingScreen.route
        )
    )


    Scaffold(
        bottomBar = {
            BottomAppBar {
                LaunchedEffect(navController) {
                    navController.currentBackStackEntryFlow.collect { backStackEntry ->
                        routeToIndex[backStackEntry.destination.route]?.let { index ->
                            currentBottomBarIndex = index
                        }
                    }
                }

                bottomNavItems.forEachIndexed {
                        index, item ->
                    NavigationBarItem(
                        alwaysShowLabel = false,
                        label = {
                            Text(text=item.title)
                        },
                        icon = {
                            Icon(imageVector = if (index == currentBottomBarIndex){item.selectedIcon} else item.unselectedIcon,
                                contentDescription = item.title)
                        },
                        selected = currentBottomBarIndex == index,
                        onClick = {
                            if (currentBottomBarIndex != index){
                                navController.navigate(item.route)
                            }
                            currentBottomBarIndex = index
                        }
                    )
                }
            }
        }
    ) {
            paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)){
            Navigator(navController = navController)
        }


    }
}


data class BottomNavItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val route: String
)