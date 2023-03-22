package com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.salexey.doubletapptracker.consts.values.TypeValues
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist.page.HabitListPageFragment

class HabitListPagerAdapter(
    fragment: Fragment
): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HabitListPageFragment.newInstance(TypeValues.positive)
            1 -> HabitListPageFragment.newInstance(TypeValues.negative)
            else -> {HabitListPageFragment.newInstance()}
        }
    }

    fun getItemViewTitle(position: Int): String {
        return when(position){
            0 -> TypeValues.positive
            1 -> TypeValues.negative
            else -> ""
        }
    }

}
