package com.mobiai.app.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mobiai.R
import com.mobiai.base.basecode.ui.fragment.BaseFragment
import com.mobiai.databinding.FragmentWebBinding

class WebFragment : BaseFragment<FragmentWebBinding>() {
    companion object {
        fun instance(): WebFragment {
            return newInstance(WebFragment::class.java)
        }
    }
    override fun initView() {
        val webView = binding.webView
        val webSettings = webView.settings
        CookieManager.getInstance().setAcceptCookie(false)
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.clearHistory()
        webView.clearCache(true)
        webView.clearFormData()
        @Suppress("DEPRECATION")
        webSettings.savePassword = false
        @Suppress("DEPRECATION")
        webSettings.saveFormData = false
        binding.imageButtonOut.setOnClickListener {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            }else {
                val newFragment = CalculatorFragment()
                val fragmentManager = requireActivity().supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container_web, newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentWebBinding {
        binding = FragmentWebBinding.inflate(inflater, container, false)
        binding.webView.settings.javaScriptEnabled = true

        binding.webView.settings.setSupportZoom(true)
        binding.webView.settings.builtInZoomControls = true
        binding.webView.settings.displayZoomControls = false

        binding.webView.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }
        binding.editTextSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val searchKeyword = binding.editTextSearch.text.toString()

                if (searchKeyword.isNotEmpty()) {
                    performGoogleSearch(searchKeyword)
                    hideKeyboard()
                    return@setOnKeyListener true
                }
            }
            false
        }

        return binding

    }

    override fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextSearch.windowToken, 0)
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun performGoogleSearch(keyword: String) {
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100) {
                    binding.webView.visibility = View.VISIBLE
                }
            }
        }
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.webView.visibility = View.VISIBLE
            }
        }
        if (URLUtil.isNetworkUrl(keyword)) {
            binding.webView.loadUrl(keyword)
        } else {
            binding.webView.loadUrl("https://www.google.com/search?q=$keyword")
        }
    }
}