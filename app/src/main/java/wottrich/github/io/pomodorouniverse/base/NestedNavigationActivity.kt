package wottrich.github.io.pomodorouniverse.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import wottrich.github.io.pomodorouniverse.R

@AndroidEntryPoint
class NestedNavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHost = supportFragmentManager.findFragmentById(R.id.nested_nav_host_fragment) as NavHostFragment
        val navGraph = navHost.navController.navInflater.inflate(R.navigation.nested_nav_graph)
        navGraph.setStartDestination(R.id.home_nav_graph)
        navHost.navController.setGraph(navGraph, intent?.extras)
    }
}