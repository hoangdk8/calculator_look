package com.mobiai.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobiai.R
import com.mobiai.base.basecode.ui.fragment.BaseFragment
import com.mobiai.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    companion object {
        fun instance(): HomeFragment {
            return newInstance(HomeFragment::class.java)
        }
    }
    override fun initView() {
        binding.layoutItemNote.setOnClickListener {
            val newFragment = NotesFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_home, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.layoutItemWeb.setOnClickListener {
            val newFragment = WebFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_home, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        return binding
    }

}