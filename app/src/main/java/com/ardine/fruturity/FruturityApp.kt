package com.ardine.fruturity

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ardine.fruturity.ui.navigation.NavigationItem
import com.ardine.fruturity.ui.navigation.Screen
import com.ardine.fruturity.ui.screen.cart.CartScreen
import com.ardine.fruturity.ui.screen.detail.DetailScreen
import com.ardine.fruturity.ui.screen.home.HomeScreen
import com.ardine.fruturity.ui.screen.profile.ProfileScreen
import com.ardine.fruturity.ui.theme.FruturityTheme

@Composable
fun FruturityApp (
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier
                .padding(innerPadding)
        ){
            composable(Screen.Home.route){
                HomeScreen(
                    navigateToDetail = { fruitId ->
                        navController.navigate(Screen.Detail.createRoute(fruitId))
                    }
                )
            }
            composable(Screen.Cart.route){
                val context = LocalContext.current
                CartScreen(
                    onOrderButtonClicked = { message ->
                        shareOrder(context, message)
                    }
                )
            }
            composable(Screen.Profile.route){
                ProfileScreen()
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("fruitId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("fruitId") ?: -1L
                DetailScreen(
                    fruitId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToCart = {
                        navController.popBackStack()
                        navController.navigate(Screen.Cart.route){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar (
        modifier = modifier
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(id = R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_cart),
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Cart
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            )
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}


private fun shareOrder(context: Context, summary:String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.fruturity_shop))
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.fruturity_shop)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AppPreview(){
    FruturityTheme {
        FruturityApp()
    }
}