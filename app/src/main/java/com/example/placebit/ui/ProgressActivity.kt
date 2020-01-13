package com.example.placebit.ui

import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

abstract class ProgressActivity : AppCompatActivity() {

    abstract fun getProgressBar(): ProgressBar

    private var runnableAction: Runnable? = null
    private var cancelAction:Runnable? = null

//    fun checkPermission(permitAction: Runnable, cancelAction:Runnable?, vararg permissions: String) {
//        this.runnableAction = permitAction
//        this.cancelAction = cancelAction
//        if (CheckPermissionsUtils.checkPermission(this, permissions))
//            permitAction.run()
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        CheckPermissionsUtils.handlePermissions(requestCode, grantResults, runnableAction!!, cancelAction)
//    }

}