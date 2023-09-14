package com.mobiai.app.ui.activity

import android.content.Context
import android.content.Intent
import com.apero.inappupdate.AppUpdateManager
import com.mobiai.R
import com.mobiai.app.ui.fragment.CalculatorFragment
import com.mobiai.base.basecode.ui.activity.BaseActivity
import com.mobiai.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    companion object{
        fun startMain(context: Context, clearTask : Boolean ){
            val intent = Intent(context, MainActivity::class.java).apply {
                if(clearTask){
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            }
            context.startActivity(intent)
        }

    }
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_main
    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    override fun createView() {
        attachFragment()
    }


    private fun attachFragment(){
        addFragment(CalculatorFragment.instance())
    }
    override fun onResume() {
        super.onResume()
        AppUpdateManager.getInstance(this).checkNewAppVersionState(this)

    }
}