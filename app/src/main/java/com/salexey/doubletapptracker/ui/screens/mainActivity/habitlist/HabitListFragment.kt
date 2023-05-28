package com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.salexey.doubletapptracker.MainApplication
import com.salexey.doubletapptracker.R
import com.salexey.doubletapptracker.consts.values.TypeValue
import com.salexey.doubletapptracker.ui.elements.buttons.Fab
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator.HabitCreatorFragment
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist.page.HabitListPagerAdapter
import com.salexey.habit_manager.HabitRepository
import java.util.logging.Logger
import javax.inject.Inject


class HabitListFragment : Fragment() {
    lateinit var pager: ViewPager2
    private lateinit var pageAdapter: HabitListPagerAdapter

    lateinit var tabLayout: TabLayout
    private lateinit var fab: ComposeView
    private lateinit var viewModel: HabitListViewModel

    @Inject
    lateinit var habitRepository: HabitRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().application as MainApplication).applicationComponent.inject(this)

        viewModel = HabitListViewModel.getViewModel(habitRepository)
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_habit_list, container, false)

        pager = view.findViewById(R.id.fragment_habit_list_viewpager)
        tabLayout = view.findViewById(R.id.tabLayout)
        fab = view.findViewById(R.id.fab)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getHabits()

        fab.setContent {
            Box {
                val isFabView = viewModel.isFabView.value.collectAsState()

                if (isFabView.value){
                    Fab(onClick = {
                        findNavController().navigate(
                            R.id.action_habitListFragment_to_habitCreatorFragment,
                            HabitCreatorFragment.newBundle()
                        )
                    })
                }
            }
        }

        pageAdapter = HabitListPagerAdapter(this)
        pager.adapter = pageAdapter


        tabLayout.isSmoothScrollingEnabled = true

        for (i in 0 until pageAdapter.itemCount){
            tabLayout.addTab(tabLayout.newTab().setText(

                resources.getString(TypeValue.values()[i].strId)

            ))
        }


        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                viewModel.filterTypeValue.setValue(tab.position)

                pager.setCurrentItem(tab.position, false)
                viewModel.isFabView.setValue(true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


        pager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
}
