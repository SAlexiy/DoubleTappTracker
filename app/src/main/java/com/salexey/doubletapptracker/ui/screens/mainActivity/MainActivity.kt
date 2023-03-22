package com.salexey.doubletapptracker.ui.screens.mainActivity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import com.salexey.doubletapptracker.R
import com.salexey.doubletapptracker.ui.elements.MainDrawerHeader
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator.HabitCreatorViewModel
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist.page.HabitListPageViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    private lateinit var toolbar: Toolbar
    private lateinit var navigationDrawerLayout: DrawerLayout
    private lateinit var navigationDrawerView: NavigationView

    private lateinit var habitCreatorViewModel: HabitCreatorViewModel
    private lateinit var habitListPageViewModel: HabitListPageViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24)

        navigationDrawerLayout = findViewById<DrawerLayout>(R.id.main_drawer_layout)

        navigationDrawerView = findViewById<NavigationView>(R.id.nvView);
        setupDrawerContent(navigationDrawerView)

        navController = this.findNavController(R.id.mainFragment)


        if(savedInstanceState == null){

            habitCreatorViewModel = ViewModelProvider(this)[HabitCreatorViewModel::class.java]
            habitListPageViewModel = ViewModelProvider(this)[HabitListPageViewModel::class.java]

        }
    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navigationDrawerLayout.openDrawer(GravityCompat.START)

                findViewById<ComposeView>(R.id.main_drawer_compose_view).setContent {
                    MainDrawerHeader()
                }

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }


    private fun selectDrawerItem(menuItem: MenuItem) {

        navController.navigate(
            when (menuItem.itemId) {
                R.id.habitListItem -> R.id.habitListFragment
                R.id.aboutAppItem -> R.id.aboutAppFragment
                else -> {R.id.habitListFragment}
            }
        )

        navigationDrawerLayout.closeDrawers()
    }

}
