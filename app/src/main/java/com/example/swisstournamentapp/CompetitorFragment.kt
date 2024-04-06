package com.example.swisstournamentapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.swisstournamentapp.data.Competitor

/**
 * A fragment representing a list of Items.
 */
class CompetitorFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }


    private fun calculatePlacings(compList: List<Competitor>): List<Competitor> {
        var sortedList= compList.sortedByDescending { it.points }.toMutableList()
        var placing = 1
        var topPoints = sortedList[0].points
        for (c: Competitor in sortedList) {
            if (c.points != topPoints) {
                placing++
                topPoints = c.points
                c.placing = placing
            } else {
                c.placing = placing
            }
        }
        return sortedList.sortedBy { it.placing }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var resultsLists: List<Competitor>

        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        val args: SecondFragmentArgs by navArgs()


        val competitorArrayAsList = args.myArgs.toList()

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyCompetitorRecyclerViewAdapter(calculatePlacings(competitorArrayAsList))
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            CompetitorFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}