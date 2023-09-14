package com.mobiai.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobiai.R
import com.mobiai.base.basecode.ui.fragment.BaseFragment
import com.mobiai.databinding.FragmentAddBinding
import com.mobiai.databinding.FragmentWarningBinding


class WarningFragment : BaseFragment<FragmentWarningBinding>() {
    companion object {
        fun instance(): WarningFragment {
            return newInstance(WarningFragment::class.java)
        }
    }
    override fun initView() {
        binding.buttonConfirm.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }
    }

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWarningBinding {
        binding = FragmentWarningBinding.inflate(inflater, container, false)
        return binding
    }

}