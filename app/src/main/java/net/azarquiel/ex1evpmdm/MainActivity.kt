package net.azarquiel.ex1evpmdm

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.azarquiel.ex1evpmdm.ui.screens.DetalleScreen
import net.azarquiel.ex1evpmdm.ui.screens.MainScreen
import net.azarquiel.ex1evpmdm.ui.theme.Ex1EvPMDMTheme
import net.azarquiel.ex1evpmdm.viewModel.RecetaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ex1EvPMDMTheme {
                RecetasApp()
            }
        }
    }
}

@Composable
fun RecetasApp() {
    val navController = rememberNavController()

    // ViewModel con factory
    val viewModel: RecetaViewModel = viewModel(
        factory = RecetaViewModelFactory(LocalContext.current.applicationContext as Application)
    )

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(
                onRecetaClick = { recetaId ->
                    navController.navigate("detalle/$recetaId")
                },
                viewModel = viewModel
            )
        }
        composable("detalle/{recetaId}") { backStackEntry ->
            val recetaId = backStackEntry.arguments?.getString("recetaId")?.toIntOrNull() ?: 0
            DetalleScreen(
                recetaId = recetaId,
                viewModel = viewModel
            )
        }
    }
}

class RecetaViewModelFactory(private val application: Application) :
    androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        return RecetaViewModel(application) as T
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ex1EvPMDMTheme {
        RecetasApp()
    }
}