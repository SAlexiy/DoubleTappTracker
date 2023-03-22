package com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.salexey.doubletapptracker.R
import com.salexey.doubletapptracker.consts.keys.HabitListArgumentsKeys
import kotlin.properties.Delegates


class HabitListFragment : Fragment() {
    lateinit var pager: ViewPager2
    private lateinit var pageAdapter: HabitListPagerAdapter

    lateinit var tabLayout: TabLayout

    private var tabNum by Delegates.notNull<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_habit_list, container, false)

        pager = view.findViewById(R.id.fragment_habit_list_viewpager)
        tabLayout = view.findViewById(R.id.tabLayout)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabNum = savedInstanceState?.getInt(HabitListArgumentsKeys.tabNum) ?: 0


        pageAdapter = HabitListPagerAdapter(this)
        pager.adapter = pageAdapter


        tabLayout.isSmoothScrollingEnabled = true

        for (i in 0 until pageAdapter.itemCount){
            tabLayout.addTab(tabLayout.newTab().setText(
                pageAdapter.getItemViewTitle(i)
            ))
        }

        pager.currentItem = tabNum
        tabLayout.selectTab(tabLayout.getTabAt(tabNum))


        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tabNum = tab.position
                pager.setCurrentItem(tab.position, false)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(HabitListArgumentsKeys.tabNum, tabNum)
    }

    companion object { }
}
