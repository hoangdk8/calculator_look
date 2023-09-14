package com.mobiai.app.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobiai.R
import com.mobiai.app.adapter.NoteAdapter
import com.mobiai.app.viewmodel.NoteViewModel
import com.mobiai.base.basecode.ui.fragment.BaseFragment
import com.mobiai.databinding.FragmentNotesBinding


class NotesFragment : BaseFragment<FragmentNotesBinding>() {
    private val viewModel: NoteViewModel by viewModels()
    private lateinit var adapter: NoteAdapter

    companion object {
        fun instance(): NotesFragment {
            return newInstance(NotesFragment::class.java)
        }
    }

    override fun initView() {
        binding.buttonAddNoData.setOnClickListener {
            val newFragment = AddFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_notes, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.arrowLeft.setOnClickListener {
            val newFragment = HomeFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_notes, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.buttonAddData.setOnClickListener {
            val newFragment = AddFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_notes, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }


    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentNotesBinding {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        subscribeUI()
        return binding
    }

    private fun setupRecyclerView() {
        adapter = NoteAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        adapter.setOnItemClickListener { note ->
            showNoteDetail(note.id,note.title, note.content)
            Log.d("hoangtv", "click: ")
        }
    }

    private fun showNoteDetail(id: Long, title: String, content: String) {
        val bundle = Bundle()
        bundle.putLong("id", id)
        bundle.putString("title", title)
        bundle.putString("content", content)
        val fragmentAdd = AddFragment()
        fragmentAdd.arguments = bundle
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_notes, fragmentAdd)
        transaction.addToBackStack(null)
        transaction.commit()
        Log.d("hoang", "showNoteDetail: $bundle")
    }

    private fun subscribeUI() {

        viewModel.listNote.observe(this) {
            adapter.setNotes(it) // Cập nhật dữ liệu cho adapter
            Log.d("hoang", "subscribeUI: note$it")
            if (it.isNotEmpty()) {
                binding.recyclerView.smoothScrollToPosition(it.size - 1)
            }
            binding.recyclerView.adapter = adapter
            if (adapter.itemCount > 0) {
                binding.textViewCheck.visibility = View.GONE
                binding.buttonAddNoData.visibility = View.GONE
                binding.buttonAddData.visibility = View.VISIBLE

            } else {
                binding.recyclerView.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListNote()
    }
}