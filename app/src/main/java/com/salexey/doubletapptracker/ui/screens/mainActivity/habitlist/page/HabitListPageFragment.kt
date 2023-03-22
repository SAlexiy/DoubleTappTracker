package com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist.page

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.salexey.doubletapptracker.R
import com.salexey.doubletapptracker.consts.keys.HabitCreatorArgumentsKeys
import com.salexey.doubletapptracker.consts.keys.HabitListArgumentsKeys
import com.salexey.doubletapptracker.consts.values.TypeValues
import com.salexey.doubletapptracker.datamodel.Habit
import com.salexey.doubletapptracker.room.AppDB
import com.salexey.doubletapptracker.room.HabitRepository
import com.salexey.doubletapptracker.ui.elements.buttons.Fab
import com.salexey.doubletapptracker.ui.elements.list.habitlist.HabitList
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator.HabitCreatorFragment.Companion.newBundle
import kotlinx.coroutines.launch
import java.util.*

/**
 * фрагмент для отображения привычек
 *
 * читает Habit из таблицы habit
 */
class HabitListPageFragment : Fragment() {
    private var type: String? = null

    private lateinit var viewModel: HabitListPageViewModel
    private lateinit var db: AppDB
    private lateinit var habitRepository: HabitRepository

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[HabitListPageViewModel::class.java]

        db = AppDB.getInstance(this@HabitListPageFragment.context!!)
        habitRepository = HabitRepository(db.habitDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_habit_list_page, container, false).apply {
            findViewById<ComposeView>(R.id.habit_list_compose_view).setContent {

                Column {
                    val habitList = viewModel.habitList.collectAsState()
                    HabitList(
                        habitList = habitList.value,
                        onClick = {
                            findNavController().navigate(
                                R.id.action_habitListFragment_to_habitCreatorFragment,
                                it
                            )
                        }
                    )
                }



                Fab(onClick = {
                    findNavController().navigate(
                        R.id.action_habitListFragment_to_habitCreatorFragment,
                        newBundle()
                    )
                })

            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            type = it.getString(HabitListArgumentsKeys.type)
        }

        viewLifecycleOwner.lifecycleScope.launch {

            if (type != null){
                viewModel.setHabitList(habitRepository.getHabitsByType(type!!))
            } else  {
                viewModel.setHabitList(habitRepository.getAllHabit())
            }

        }

    }


    companion object{
        fun newInstance(type: String): HabitListPageFragment{
            val fragment = HabitListPageFragment()
            val args = Bundle()

            args.putString(HabitListArgumentsKeys.type, type)
            fragment.arguments = args

            return fragment
        }

        fun newInstance(): HabitListPageFragment{
            val fragment = HabitListPageFragment()
            val args = Bundle()

            fragment.arguments = args

            return fragment
        }
    }
}