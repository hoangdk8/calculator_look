package com.mobiai.app.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.mobiai.R
import com.mobiai.app.db.password.Password
import com.mobiai.app.viewmodel.PasswordViewModel
import com.mobiai.base.basecode.ui.fragment.BaseFragment
import com.mobiai.databinding.FragmentCalculatorBinding
import org.mariuszgromada.math.mxparser.Expression


class CalculatorFragment : BaseFragment<FragmentCalculatorBinding>() {
    private val viewModel: PasswordViewModel by viewModels()

    companion object {
        fun instance(): CalculatorFragment {
            return newInstance(CalculatorFragment::class.java)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        binding.textViewReturnPass.setOnClickListener {
            binding.layoutConstraintPassword.visibility = View.VISIBLE
            binding.layoutConstraintLayoutConfirmPassword.visibility = View.INVISIBLE
            binding.editTextPass1.text = null
            binding.editTextPass2.text = null
            binding.editTextPass3.text = null
            binding.editTextPass4.text = null
            binding.editTextConfirmPass1.text = null
            binding.editTextConfirmPass2.text = null
            binding.editTextConfirmPass3.text = null
            binding.editTextConfirmPass4.text = null
            binding.textViewError.visibility = View.INVISIBLE
            binding.editTextConfirmPass1.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_edit_text_password)
            binding.editTextConfirmPass2.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_edit_text_password)
            binding.editTextConfirmPass3.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_edit_text_password)
            binding.editTextConfirmPass4.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_edit_text_password)
            createPassword()
        }

    }

    private fun colorText() {
        val textView = binding.textViewInput
        val spannableString = SpannableString(binding.textViewInput.text)

        val mathOperators = arrayOf("+", "-", "*", "/") // Dấu phép tính cần đổi màu

        for (operator in mathOperators) {
            val start = binding.textViewInput.text.indexOf(operator)
            if (start != -1) {
                val end = start + operator.length
                val color = Color.parseColor("#109DFF")
                spannableString.setSpan(
                    ForegroundColorSpan(color), // Màu xanh cho dấu phép tính
                    start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        textView.text = spannableString
    }

    @SuppressLint("SetTextI18n")
    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCalculatorBinding {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        createPassword()

        return binding
    }

    private fun createPassword() {
        viewModel.getPassword()
        viewModel.password.observe(this) { pass ->
            if (pass.isNullOrEmpty()) {
                Log.d("hoang", "getBinding: trống ")
                val editTextList = listOf(
                    binding.editTextPass1,
                    binding.editTextPass2,
                    binding.editTextPass3,
                    binding.editTextPass4
                )
                val editTextConfirmList = listOf(
                    binding.editTextConfirmPass1,
                    binding.editTextConfirmPass2,
                    binding.editTextConfirmPass3,
                    binding.editTextConfirmPass4
                )

                binding.layoutConstraintPassword.visibility = View.VISIBLE
                binding.textViewInput.visibility = View.INVISIBLE
                binding.textViewResult.visibility = View.INVISIBLE
                binding.button0.setOnClickListener {
                    val emptyEditText = editTextList.find { it.text.isBlank() }
                    emptyEditText?.setText("0")
                }
                binding.button1.setOnClickListener {
                    val emptyEditText = editTextList.find { it.text.isBlank() }
                    emptyEditText?.setText("1")
                }
                binding.button2.setOnClickListener {
                    val emptyEditText = editTextList.find { it.text.isBlank() }
                    emptyEditText?.setText("2")
                }
                binding.button3.setOnClickListener {
                    val emptyEditText = editTextList.find { it.text.isBlank() }
                    emptyEditText?.setText("3")
                }
                binding.button4.setOnClickListener {
                    val emptyEditText = editTextList.find { it.text.isBlank() }
                    emptyEditText?.setText("4")
                }
                binding.button5.setOnClickListener {
                    val emptyEditText = editTextList.find { it.text.isBlank() }
                    emptyEditText?.setText("5")
                }
                binding.button6.setOnClickListener {
                    val emptyEditText = editTextList.find { it.text.isBlank() }
                    emptyEditText?.setText("6")
                }
                binding.button7.setOnClickListener {
                    val emptyEditText = editTextList.find { it.text.isBlank() }
                    emptyEditText?.setText("7")
                }
                binding.button8.setOnClickListener {
                    val emptyEditText = editTextList.find { it.text.isBlank() }
                    emptyEditText?.setText("8")
                }
                binding.button9.setOnClickListener {
                    val emptyEditText = editTextList.find { it.text.isBlank() }
                    emptyEditText?.setText("9")
                }
                binding.buttonDelete.setOnClickListener {
                    if (binding.editTextPass4.text.isNotEmpty()||binding.editTextConfirmPass4.text.isNotEmpty()) {
                        binding.editTextPass4.text.clear()
                        binding.editTextConfirmPass4.text.clear()
                    } else if (binding.editTextPass3.text.isNotEmpty()||binding.editTextConfirmPass3.text.isNotEmpty()) {
                        binding.editTextPass3.text.clear()
                        binding.editTextConfirmPass3.text.clear()
                    } else if (binding.editTextPass2.text.isNotEmpty()||binding.editTextConfirmPass2.text.isNotEmpty()) {
                        binding.editTextPass2.text.clear()
                        binding.editTextConfirmPass2.text.clear()
                    } else if (binding.editTextPass1.text.isNotEmpty()||binding.editTextConfirmPass1.text.isNotEmpty()) {
                        binding.editTextPass1.text.clear()
                        binding.editTextConfirmPass1.text.clear()
                    }
                }

                binding.buttonBang.setOnClickListener {

                    val stringBuilderCreate = StringBuilder()
                    for (editText in editTextList) {
                        stringBuilderCreate.append(editText.text.toString())
                    }
                    val passCreateString = stringBuilderCreate.toString()
                    val passCreate = Password(passCreateString)
                    binding.layoutConstraintPassword.visibility = View.INVISIBLE
                    binding.layoutConstraintLayoutConfirmPassword.visibility = View.VISIBLE
                    binding.button0.setOnClickListener {
                        val emptyEditText = editTextConfirmList.find { it.text.isBlank() }
                        emptyEditText?.setText("0")
                    }
                    binding.button1.setOnClickListener {
                        val emptyEditText = editTextConfirmList.find { it.text.isBlank() }
                        emptyEditText?.setText("1")
                    }
                    binding.button2.setOnClickListener {
                        val emptyEditText = editTextConfirmList.find { it.text.isBlank() }
                        emptyEditText?.setText("2")
                    }
                    binding.button3.setOnClickListener {
                        val emptyEditText = editTextConfirmList.find { it.text.isBlank() }
                        emptyEditText?.setText("3")
                    }
                    binding.button4.setOnClickListener {
                        val emptyEditText = editTextConfirmList.find { it.text.isBlank() }
                        emptyEditText?.setText("4")
                    }
                    binding.button5.setOnClickListener {
                        val emptyEditText = editTextConfirmList.find { it.text.isBlank() }
                        emptyEditText?.setText("5")
                    }
                    binding.button6.setOnClickListener {
                        val emptyEditText = editTextConfirmList.find { it.text.isBlank() }
                        emptyEditText?.setText("6")
                    }
                    binding.button7.setOnClickListener {
                        val emptyEditText = editTextConfirmList.find { it.text.isBlank() }
                        emptyEditText?.setText("7")
                    }
                    binding.button8.setOnClickListener {
                        val emptyEditText = editTextConfirmList.find { it.text.isBlank() }
                        emptyEditText?.setText("8")
                    }
                    binding.button9.setOnClickListener {
                        val emptyEditText = editTextConfirmList.find { it.text.isBlank() }
                        emptyEditText?.setText("9")
                    }
                    val stringBuilder = StringBuilder()
                    for (editText in editTextConfirmList) {
                        stringBuilder.append(editText.text.toString())
                    }
                    val passConfirmString = stringBuilder.toString()
                    val passConfirm = Password(passConfirmString)
                    Log.d("hoang", "getBinding: $passConfirmString")
                    if (binding.editTextConfirmPass4.text.isNotEmpty()) {
                        if (passConfirm == passCreate) {
                            viewModel.createPassword(passConfirm)
                            val newFragment = SecurityQuestionFragment()
                            val fragmentManager = requireActivity().supportFragmentManager
                            val transaction = fragmentManager.beginTransaction()
                            transaction.replace(R.id.fragment_container_calculator, newFragment)
                            transaction.addToBackStack(null)
                            transaction.commit()
                        }else {
                            binding.textViewError.visibility = View.VISIBLE
                            binding.editTextConfirmPass1.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_edit_text_error_password)
                            binding.editTextConfirmPass2.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_edit_text_error_password)
                            binding.editTextConfirmPass3.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_edit_text_error_password)
                            binding.editTextConfirmPass4.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_edit_text_error_password)
                        }
                    }
                }

            } else {
                Log.d("hoang", "getBinding: ko trống ")
                binding.layoutConstraintPassword.visibility = View.INVISIBLE
                binding.buttonAC.setOnClickListener {
                    binding.textViewResult.text = ""
                    binding.textViewInput.text = ""
                }
                binding.buttonDelete.setOnClickListener {
                    val removedLast = binding.textViewResult.text.toString().dropLast(1)
                    binding.textViewResult.text = removedLast
                }
                binding.button0.setOnClickListener {
                    binding.textViewResult.text = binding.textViewResult.text.toString() + "0"
                }
                binding.button1.setOnClickListener {
                    binding.textViewResult.text = binding.textViewResult.text.toString() + "1"
                }
                binding.button2.setOnClickListener {
                    binding.textViewResult.text = binding.textViewResult.text.toString() + "2"
                }
                binding.button3.setOnClickListener {
                    binding.textViewResult.text = binding.textViewResult.text.toString() + "3"
                }
                binding.button4.setOnClickListener {
                    binding.textViewResult.text = binding.textViewResult.text.toString() + "4"
                }
                binding.button5.setOnClickListener {
                    binding.textViewResult.text = binding.textViewResult.text.toString() + "5"
                }
                binding.button6.setOnClickListener {
                    binding.textViewResult.text = binding.textViewResult.text.toString() + "6"
                }
                binding.button7.setOnClickListener {
                    binding.textViewResult.text = binding.textViewResult.text.toString() + "7"
                }
                binding.button8.setOnClickListener {
                    binding.textViewResult.text = binding.textViewResult.text.toString() + "8"
                }
                binding.button9.setOnClickListener {
                    binding.textViewResult.text = binding.textViewResult.text.toString() + "9"
                }
                binding.buttonCham.setOnClickListener {
                    binding.textViewResult.text = binding.textViewResult.text.toString() + "."
                }
                binding.buttonChia.setOnClickListener {
                    binding.textViewInput.text =
                        binding.textViewResult.text.toString() + "/"
                    colorText()
                    binding.textViewResult.text = ""

                }
                binding.buttonNhan.setOnClickListener {
                    binding.textViewInput.text =
                        binding.textViewResult.text.toString() + "*"
                    colorText()
                    binding.textViewResult.text = ""

                }
                binding.buttonTru.setOnClickListener {
                    binding.textViewInput.text =
                        binding.textViewResult.text.toString() + "-"
                    colorText()
                    binding.textViewResult.text = ""

                }
                binding.buttonCong.setOnClickListener {
                    val text = binding.textViewResult.text.toString() + "+"
                    binding.textViewInput.text = text
                    colorText()
                    binding.textViewResult.text = ""

                }
                binding.buttonBang.setOnClickListener {

                    if (binding.textViewResult.text == pass[0].password) {
                        val newFragment = HomeFragment()
                        val fragmentManager = requireActivity().supportFragmentManager
                        val transaction = fragmentManager.beginTransaction()
                        transaction.replace(R.id.fragment_container_calculator, newFragment)
                        transaction.addToBackStack(null)
                        transaction.commit()
                    }
                    binding.textViewInput.text =
                        binding.textViewInput.text.toString() +
                                binding.textViewResult.text.toString()
                    colorText()
                    val expressionString = binding.textViewInput.text.toString()
                    try {
                        val expression = Expression(expressionString)
                        val result = expression.calculate()
                        val formattedResult = if (result % 1 == 0.0) {
                            result.toInt().toString()
                        } else {
                            result.toString()
                        }
                        binding.textViewResult.text = "$formattedResult"
                    } catch (e: Exception) {
                        binding.textViewResult.text = "Error"
                    }
                }
            }

        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.getPassword()
    }
}