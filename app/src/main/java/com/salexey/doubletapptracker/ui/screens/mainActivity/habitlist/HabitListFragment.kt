package com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.salexey.doubletapptracker.R
import com.salexey.doubletapptracker.datamodel.Habit
import com.salexey.doubletapptracker.room.AppDB
import com.salexey.doubletapptracker.room.HabitRepository
import com.salexey.doubletapptracker.ui.elements.buttons.fab
import com.salexey.doubletapptracker.ui.elements.list.habitlist.habitList
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator.HabitCreatorFragment.Companion.newInstanceHabitCreatorFragment
import kotlinx.coroutines.launch
import java.util.*

/**
 * фрагмент для отображения привычек
 *
 * читает Habit из таблицы habit
 */
class HabitListFragment : Fragment() {

    private lateinit var viewModel: HabitListViewModel
    private lateinit var db: AppDB
    private lateinit var habitRepository: HabitRepository

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[HabitListViewModel::class.java]

        db = AppDB.getInstance(this@HabitListFragment.context!!)
        habitRepository = HabitRepository(db.habitDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_habit_list, container, false).apply {
            findViewById<ComposeView>(R.id.habit_list_compose_view).setContent {

                habitList(viewModel){
                    findNavController().navigate(
                        R.id.action_habitListFragment_to_habitCreatorFragment,
                        viewModel.bundle
                    )
                }

                fab(){
                    findNavController().navigate(
                        R.id.action_habitListFragment_to_habitCreatorFragment,
                        newInstanceHabitCreatorFragment().arguments
                    )
                }

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.setHabitList(habitRepository.getAllHabit())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }


    companion object{

        fun newInstanceHabitListFragment(habit: Habit) : HabitListFragment {
            val fragment = HabitListFragment()
            val bundle = Bundle()


            bundle.putSerializable("habit", habit)


            fragment.arguments = bundle

            return fragment
        }

    }
}