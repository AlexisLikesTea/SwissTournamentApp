package com.example.swisstournamentapp

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.swisstournamentapp.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var roundCount = binding.currentRound.text.toString().toInt();
        val args: SecondFragmentArgs by navArgs()
        var competitorList = args.myArgs.toList()

        //Binding buttons
        val button1 = binding.buttonComp1
        val button2 = binding.buttonComp2
        val button3 = binding.buttonComp3
        val button4 = binding.buttonComp4
        val button5 = binding.buttonComp5
        val button6 = binding.buttonComp6
        val submitButton = binding.submitButton
        val randomiseButton = binding.randomiseButton
        //Setup original list
        val initialScores = mutableListOf<Int>()
        repeat(6) {
            initialScores.add(0)
        }

        binding.randomiseButton.setOnClickListener {
            roundCount++;

        }

        val buttonsArray = arrayOf(button1, button2, button3, button4, button5, button6)

        val mainColor = Color.rgb(207, 187, 254)
        super.onViewCreated(view, savedInstanceState)
        val textViewList =
            listOf("textView1", "textView2", "textView3", "textView4", "textView5", "textView6")

        fun renderNames() {
            for ((index, competitor) in competitorList.withIndex()) {
                val textViewId =
                    resources.getIdentifier(textViewList[index], "id", requireContext().packageName)
                val textView: TextView = view.findViewById(textViewId)
                textView.text = competitor.name
            }
        }
        renderNames()

        binding.results.setOnClickListener {
            val action =
                SecondFragmentDirections.actionSecondFragmentToCompetitorFragment3(args.myArgs)
            findNavController().navigate(action)
        }


        fun testForGroup1(): Boolean {
            return (button1.text.toString().toInt() + button2.text.toString().toInt() != 4)
        }

        fun testForGroup2(): Boolean {
            return (button3.text.toString().toInt() + button4.text.toString().toInt() != 4)
        }

        fun testForGroup3(): Boolean {
            return (button5.text.toString().toInt() + button6.text.toString().toInt() != 4)
        }


        fun illegalScoreDetection() {
            submitButton.isEnabled = testForGroup1() && testForGroup2() && testForGroup3()
        }


        fun handleButtonClick(button: Button, index: Int) {
            val buttonText = button.text.toString().toInt()
            val buttonValue: Int
            if (buttonText == 2) {
                buttonValue = 0
                button.setBackgroundColor(mainColor)
                button.text = buttonValue.toString()
            } else {
                buttonValue = buttonText + 1
                button.text = buttonValue.toString()
                if (buttonValue == 2) {
                    button.setBackgroundColor(Color.YELLOW)
                }
            }
            initialScores[index] = buttonValue
            illegalScoreDetection()

        }

        button1.setOnClickListener {
            handleButtonClick(binding.buttonComp1, 0)
        }

        button2.setOnClickListener {
            handleButtonClick(binding.buttonComp2, 1)
        }

        button3.setOnClickListener {
            handleButtonClick(binding.buttonComp3, 2)
        }

        button4.setOnClickListener {
            handleButtonClick(binding.buttonComp4, 3)
        }

        button5.setOnClickListener {
            handleButtonClick(binding.buttonComp5, 4)
        }

        button6.setOnClickListener {
            handleButtonClick(binding.buttonComp6, 5)
        }



        fun calculatePoints(score1: Int, score2: Int): Double {
            return when {
                score1 - score2 == 0 -> 0.5
                score1 - score2 < 0 -> 0.0
                else -> 1.0
            }
        }

        fun resetScores() {
            initialScores.replaceAll { 0 }
            for (button in buttonsArray) {
                button.text = "0"
                button.setBackgroundColor(mainColor)
            }

        }

        fun calculateNewOpponents() {
            var sortedList= competitorList.sortedByDescending { it.points }.toMutableList()
            competitorList = sortedList
            var index = 0
            while (index < sortedList.size - 1) {
                val competitor1 = sortedList[index]
                val competitor2 = sortedList[index + 1]

                if (competitor1.matchHistory.contains(competitor2.name)) {
                    val temp = sortedList.getOrNull(index + 2)
                    if (temp != null) {
                        sortedList[index+2] = sortedList[index + 1]
                        sortedList[index + 1] = temp
                        index = 0
                    } else {
                        break
                    }
                } else {
                    index++
                }
            }
            renderNames()
        }

        fun nextRound() {
            var index = 1
            var pointsIndex = 0
            for (comp in competitorList) {
                if (index % 2 != 0) {
                    comp.points += calculatePoints(
                        initialScores[pointsIndex],
                        initialScores[pointsIndex + 1]
                    )
                } else {
                    comp.points += calculatePoints(
                        initialScores[pointsIndex],
                        initialScores[pointsIndex - 1]
                    )
                }
                index++
                pointsIndex++
            }
            resetScores()
        }

            submitButton.setOnClickListener() {
            nextRound()
            var compIndex = 1
            var index = 0
            for (comp in competitorList) {
                if (compIndex % 2 != 0) {
                    competitorList[index].matchHistory.add(competitorList[index + 1].name)
                } else {
                    competitorList[index].matchHistory.add(competitorList[index - 1].name)
                }
                index += 1
                compIndex += 1
            }
            calculateNewOpponents()
            if (roundCount == 3) {
                val action =
                    SecondFragmentDirections.actionSecondFragmentToCompetitorFragment3(args.myArgs)
                findNavController().navigate(action)
            }
            roundCount++
            binding.currentRound.text = roundCount.toString();
            if (roundCount == 3) {
                submitButton.setBackgroundColor(Color.YELLOW)
                submitButton.text = "Results";
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}