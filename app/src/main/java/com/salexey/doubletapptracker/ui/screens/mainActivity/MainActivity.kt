package com.salexey.doubletapptracker.ui.screens.mainActivity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.salexey.changes_manager.changes.ChangesRepository
import com.salexey.datamodels.consts.sharedpreference.SharedPreferenceKeys
import com.salexey.doubletapptracker.MainApplication
import com.salexey.doubletapptracker.R
import com.salexey.doubletapptracker.ui.elements.MainDrawerHeader
import com.salexey.network_tracker.network.NetworkState
import com.salexey.network_tracker.network.NetworkStatusTracker
import com.salexey.network_tracker.network.map
import com.salexey.shared_preference.preference.SharedPreferencesStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.logging.Logger
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private val logger = Logger.getLogger("MainActivity")
    private lateinit var navController: NavController

    private lateinit var toolbar: Toolbar
    private lateinit var navigationDrawerLayout: DrawerLayout
    private lateinit var navigationDrawerView: NavigationView

    @Inject
    lateinit var networkStatusTracker: NetworkStatusTracker

    @Inject
    lateinit var changesRepository: ChangesRepository

    @Inject
    lateinit var sharedPreferencesStorage: SharedPreferencesStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        (this.application as MainApplication).applicationComponent.inject(this)


        networkStatusTracker.networkStatus
            .map(
                onUnavailable = { NetworkState.Error },
                onAvailable = { NetworkState.Fetched },
            )
            .asLiveData(Dispatchers.IO).observe(this){
                when (it) {
                    NetworkState.Fetched -> {
                        try {
                            if (!sharedPreferencesStorage.getValue(SharedPreferenceKeys.IS_SYNCH)){
                                lifecycleScope.launch {
                                    changesRepository.synchChangeToServer()

                                    if (changesRepository.checkSynch()){
                                        changesRepository.synchChangeToClient()
                                    }

                                }
                            } else {
                                lifecycleScope.launch {
                                    if (changesRepository.checkSynch()){
                                        changesRepository.synchChangeToClient()
                                    }

                                }
                            }
                        } catch (e: Exception){
                            logger.warning(e.toString())
                        }




                    }
                    NetworkState.Error -> { }
                }
            }


        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24)

        navigationDrawerLayout = findViewById<DrawerLayout>(R.id.main_drawer_layout)

        navigationDrawerView = findViewById<NavigationView>(R.id.nvView);
        setupDrawerContent(navigationDrawerView)

        navController = this.findNavController(R.id.mainFragment)
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
            resId = when (menuItem.itemId) {
                R.id.habitListItem -> R.id.habitListFragment
                R.id.aboutAppItem -> R.id.aboutAppFragment
                else -> {R.id.habitListFragment}
            }
        )

        if (menuItem.itemId == R.id.aboutAppFragment){
            navController.backQueue.clear()
        }

        navigationDrawerLayout.closeDrawers()
    }
}
