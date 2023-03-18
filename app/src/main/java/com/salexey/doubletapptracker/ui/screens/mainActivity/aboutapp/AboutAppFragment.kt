package com.salexey.doubletapptracker.ui.screens.mainActivity.aboutapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.salexey.doubletapptracker.R

class AboutAppFragment : Fragment() {

    companion object {
        fun newInstance() = AboutAppFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_app, container, false).apply {
            findViewById<ComposeView>(R.id.habit_creator_compose_view).setContent {

            }
        }
    }


}