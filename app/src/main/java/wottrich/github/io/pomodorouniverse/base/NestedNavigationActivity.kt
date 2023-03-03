package wottrich.github.io.pomodorouniverse.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import wottrich.github.io.pomodorouniverse.R

@AndroidEntryPoint
class NestedNavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHost = getNavHost()
        val navGraph = getNavGraph(navHost)
        val navController = navHost.navController
        setupStartDestination(navGraph)
        setupGraph(navController, navGraph)
        setupToolbarNavigation(navController)
    }

    private fun setupStartDestination(navGraph: NavGraph) {
        navGraph.setStartDestination(R.id.home_nav_graph)
    }

    private fun setupToolbarNavigation(navController: NavController) {
        val appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        val toolbar = findViewById<Toolbar>(R.id.mainToolbar)
        NavigationUI.setupWithNavController(
            toolbar,
            navController,
            appBarConfiguration
        )
    }

    private fun setupGraph(navController: NavController, navGraph: NavGraph) {
        navController.setGraph(navGraph, intent?.extras)
    }

    private fun getNavGraph(navHost: NavHostFragment) =
        navHost.navController.navInflater.inflate(NESTED_NAVIGATION_GRAPH)

    private fun getNavHost() =
        supportFragmentManager.findFragmentById(R.id.nested_nav_host_fragment) as NavHostFragment

    companion object {
        private const val NESTED_NAVIGATION_GRAPH = R.navigation.nested_nav_graph
    }
}