package com.techsultan.bookxchange

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.techsultan.bookxchange.databinding.ActivityMainBinding
import com.techsultan.bookxchange.repository.BookRepository
import com.techsultan.bookxchange.viewmodel.BookMainViewModelFactory
import com.techsultan.bookxchange.viewmodel.BookViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        hideNavBars()

        val repository = BookRepository(applicationContext)
        val viewModelFactory = BookMainViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[BookViewModel::class.java]


        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home_dest, R.id.profile_dest, R.id.search_dest, R.id.chat_dest)
        )

        setupActionBar(navController, appBarConfiguration)
        setupBottomNavMenu(navController)



    }

    // This method shows or hide the navigation menu
    private fun hideNavBars() {
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarLayout = binding.appBarLayout
        val bottomNav = binding.bottomNav


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.login_dest -> {
                    appBarLayout.visibility = View.GONE
                    bottomNav.visibility = View.GONE
                }

                R.id.signup_dest -> {
                    appBarLayout.visibility = View.GONE
                    bottomNav.visibility = View.GONE
                }

                R.id.chat_dest -> {
                    bottomNav.visibility = View.GONE
                }

                else -> {
                    appBarLayout.visibility = View.VISIBLE
                    bottomNav.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }


    private fun setupBottomNavMenu(navController: NavController) {

        val bottomNav = binding.bottomNav
        bottomNav.setupWithNavController(navController)
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }*/

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}