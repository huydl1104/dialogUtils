package com.ydl.dialog.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.android.dialoglibrary.dialogFragment.CustomDialogFragment
import com.ydl.dialog.R

class TestCustomDialogFragment : CustomDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.CenterDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getLayoutRes(): Int {
        return R.layout.view_fragment_dialog
    }
    fun showDialog(activity: FragmentActivity) {
        val testCustomDialogFragment = TestCustomDialogFragment()
        testCustomDialogFragment.show(activity.supportFragmentManager, "dialog")
    }
}