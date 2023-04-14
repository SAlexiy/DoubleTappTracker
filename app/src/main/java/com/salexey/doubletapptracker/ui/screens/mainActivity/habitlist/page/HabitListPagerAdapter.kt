package com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist.page

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.salexey.doubletapptracker.consts.values.TypeValue
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist.page.HabitListPageFragment

class HabitListPagerAdapter(
    fragment: Fragment
): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = TypeValue.values().size

    override fun createFragment(position: Int): Fragment {
        return HabitListPageFragment.newInstance(TypeValue.values()[position])
    }

}
