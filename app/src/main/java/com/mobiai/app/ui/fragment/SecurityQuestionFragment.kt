package com.mobiai.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.mobiai.R
import com.mobiai.app.db.question.Question
import com.mobiai.app.viewmodel.NoteViewModel
import com.mobiai.app.viewmodel.QuestionViewModel
import com.mobiai.base.basecode.ui.fragment.BaseFragment
import com.mobiai.databinding.FragmentNotesBinding
import com.mobiai.databinding.FragmentSecurityQuestionBinding

class SecurityQuestionFragment : BaseFragment<FragmentSecurityQuestionBinding>() {
    private val viewModel: QuestionViewModel by viewModels()
    companion object {
        fun instance(): SecurityQuestionFragment {
            return newInstance(SecurityQuestionFragment::class.java)
        }
    }
    override fun initView() {
        val popupMenu = PopupMenu(requireContext(),binding.menuPopup)
        popupMenu.menuInflater.inflate(R.menu.menu_popup,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.question_1 -> {
                    binding.menuPopup.text = "What is your favorite food?"
                }
                R.id.question_2 -> {
                    binding.menuPopup.text = "What is the name of your favorite pet?"
                }
                R.id.question_3 -> {
                    binding.menuPopup.text = "What is your mother's name?"
                }
                R.id.question_4 -> {
                    binding.menuPopup.text = "What is the name of your first school?"
                }
                R.id.question_5 -> {
                    binding.menuPopup.text = "What high school did you attend?"
                }
                R.id.question_6 -> {
                    binding.menuPopup.text = "In what city were you born?"
                }
            }
            false
        }
        binding.menuPopup.setOnClickListener {
            popupMenu.show()
        }
    }

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSecurityQuestionBinding {
        binding = FragmentSecurityQuestionBinding.inflate(inflater, container, false)

        setupViews()
        return binding
    }

    private fun setupViews() {
        binding.buttonNext.setOnClickListener {
            if (binding.editTextAnswer.text.isEmpty()){
                binding.textViewWarning.visibility = View.VISIBLE
            }else {
                addQuestion()
                val newFragment = SuccessfullyQuestionFragment()
                val fragmentManager = requireActivity().supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container_security_question, newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        binding.buttonSkip.setOnClickListener {
            val newFragment = NotesFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_security_question, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun addQuestion() {
        val question = Question(0, binding.menuPopup.text as String,binding.editTextAnswer.text.toString())
        viewModel.addQuestion(question)
    }

}