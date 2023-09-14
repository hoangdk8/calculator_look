package com.mobiai.app.ui.fragment

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.mobiai.R
import com.mobiai.app.db.notes.Note
import com.mobiai.app.viewmodel.NoteViewModel
import com.mobiai.base.basecode.ui.fragment.BaseFragment
import com.mobiai.databinding.FragmentAddBinding

class AddFragment : BaseFragment<FragmentAddBinding>() {
    private val viewModel: NoteViewModel by viewModels()

    companion object {
        fun instance(): AddFragment {
            return newInstance(AddFragment::class.java)
        }
    }

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAddBinding {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        val bundle = arguments
        val id = bundle?.getLong("id")
        Log.d("Hoang", "getBinding:id = $id ")
        setupViews()
        return binding
    }


    private fun setupViews() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if (text.isEmpty()) {
                    binding.buttonSave.setTextColor(Color.parseColor("#78828A"))
                } else {
                    binding.buttonSave.setTextColor(Color.parseColor("#6281F2")) // Màu tím
                }
            }
        }
        binding.editTextTitle.addTextChangedListener(textWatcher)
        binding.arrowLeft.setOnClickListener {
            backNoteFragment()

        }
        binding.buttonSave.setOnClickListener {
            if (binding.editTextTitle.text.isEmpty() || binding.editTextContent.text.isEmpty()) {
                val newFragment = WarningFragment()
                val fragmentManager = requireActivity().supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container_add, newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            } else {
                val bundle = arguments
                val id = bundle?.getLong("id")
                viewModel.getIdNote()
                viewModel.listId.observe(this) {
                    Log.d("hoang", "setupViews:$it ")
                    if (it.contains(id)) {
                        updateNote()
                        hideKeyboard()
                        Toast.makeText(requireContext(), "Cập nhật thành công", Toast.LENGTH_SHORT)
                            .show()
                        backNoteFragment()
                    } else {
                        addNote()
                        hideKeyboard()
                        Toast.makeText(requireContext(), "Lưu thành công", Toast.LENGTH_SHORT)
                            .show()
                        backNoteFragment()
                    }
                }
            }
        }
    }
    override fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextTitle.windowToken, 0)
    }
    private fun updateNote() {
        val bundle = arguments
        val currentTimeMillis = System.currentTimeMillis()
        val id = bundle!!.getLong("id")
        val title = binding.editTextTitle.text.toString()
        val content = binding.editTextContent.text.toString()
        val updatedNote = Note(id, title, content, currentTimeMillis)
        viewModel.updateNote(updatedNote)
    }

    override fun initView() {

        val bundle = arguments
        if (bundle != null) {

            val title = bundle.getString("title")
            val content = bundle.getString("content")

            binding.editTextTitle.setText(title)
            binding.editTextContent.setText(content)
        }
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextTitle.windowToken, 0)
        imm.hideSoftInputFromWindow(binding.editTextContent.windowToken, 0)
    }


    private fun backNoteFragment() {
        val newFragment = NotesFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_add, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun addNote() {
        val currentTimeMillis = System.currentTimeMillis()
        val note = Note(
            0,
            binding.editTextTitle.text.toString(),
            binding.editTextContent.text.toString(),
            currentTimeMillis
        )
        viewModel.addNote(note)
        Log.d("Hoang", "initView: $note")
    }


}