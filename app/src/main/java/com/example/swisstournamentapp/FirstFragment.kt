package com.example.swisstournamentapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.swisstournamentapp.databinding.FragmentFirstBinding
import com.example.swisstournamentapp.data.Competitor
/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textBox1 = view.findViewById<EditText>(R.id.competitor1)
        val textBox2 = view.findViewById<EditText>(R.id.competitor2)
        val textBox3 = view.findViewById<EditText>(R.id.competitor3)
        val textBox4 = view.findViewById<EditText>(R.id.competitor4)
        val textBox5 = view.findViewById<EditText>(R.id.competitor5)
        val textBox6 = view.findViewById<EditText>(R.id.competitor6)
        val myButton = view.findViewById<Button>(R.id.button_first)

        fun checkAllFieldsFilled() {
            val filled = textBox1.text.toString().isNotEmpty() &&
                    textBox2.text.toString().isNotEmpty() &&
                    textBox3.text.toString().isNotEmpty()
            myButton.isEnabled = filled
        }

        val textWatcher = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkAllFieldsFilled()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        textBox1.addTextChangedListener(textWatcher)





        checkAllFieldsFilled()
        binding.buttonFirst.setOnClickListener {
            val textFromFirstOne = view.findViewById<EditText>(R.id.competitor1).text.toString();
            val textFromFirstOne2 = view.findViewById<EditText>(R.id.competitor2).text.toString();
            val textFromFirstOne3 = view.findViewById<EditText>(R.id.competitor3).text.toString();
            val textFromFirstOne4 = view.findViewById<EditText>(R.id.competitor4).text.toString();
            val textFromFirstOne5 = view.findViewById<EditText>(R.id.competitor5).text.toString();
            val textFromFirstOne6 = view.findViewById<EditText>(R.id.competitor6).text.toString();
            val simpleArrayComp = arrayOf(
                Competitor(textFromFirstOne),
                Competitor(textFromFirstOne2),
                Competitor(textFromFirstOne3),
                Competitor(textFromFirstOne4),
                Competitor(textFromFirstOne5),
                Competitor(textFromFirstOne6)
            )
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(simpleArrayComp)
            findNavController().navigate(action)

        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}