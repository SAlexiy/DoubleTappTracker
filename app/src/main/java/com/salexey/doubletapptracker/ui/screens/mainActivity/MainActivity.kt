package com.salexey.doubletapptracker.ui.screens.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.salexey.doubletapptracker.R
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator.HabitCreatorViewModel
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist.HabitListViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    private lateinit var habitCreatorViewModel: HabitCreatorViewModel
    private lateinit var habitListViewModel: HabitListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)



        navController = this.findNavController(R.id.mainFragment)

        if(savedInstanceState == null){

            habitCreatorViewModel = ViewModelProvider(this)[HabitCreatorViewModel::class.java]
            habitListViewModel = ViewModelProvider(this)[HabitListViewModel::class.java]

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
