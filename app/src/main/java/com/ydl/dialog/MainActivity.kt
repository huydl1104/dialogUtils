package com.ydl.dialog

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.dialoglibrary.dialog.CustomSelectDialog
import com.android.dialoglibrary.dialog.CustomSelectDialog.SelectDialogListener
import com.android.dialoglibrary.dialogFragment.BaseDialogFragment.onLoadFinishListener
import com.android.dialoglibrary.dialogFragment.BottomDialogFragment
import com.android.dialoglibrary.dialogMenu.CustomBottomDialog
import com.android.dialoglibrary.dialogMenu.CustomItem
import com.android.dialoglibrary.dialogMenu.OnItemClickListener
import com.android.dialoglibrary.loading.ViewLoading
import com.android.dialoglibrary.popupWindow.CustomPopupWindow
import com.android.dialoglibrary.popupWindow.CustomPopupWindow.PopupWindowBuilder
import com.android.dialoglibrary.snackbar.SnackBarUtils
import com.android.dialoglibrary.toast.ToastUtils
import com.android.dialoglibrary.utils.DialogUtils
import com.android.dialoglibrary.utils.DialogUtils.requestMsgPermission
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback
import com.google.android.material.snackbar.Snackbar
import com.ydl.dialog.adapter.DialogListAdapter
import com.ydl.dialog.bean.DialogInfo
import com.ydl.dialog.fragment.TestCustomDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var popWindow: CustomPopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv.movementMethod = ScrollingMovementMethod.getInstance()
        initListenr()

        requestMsgPermission(this)
    }

    private fun initListenr() {
        button11.setOnClickListener(this)
        button22.setOnClickListener(this)
        button33.setOnClickListener(this)
        button44.setOnClickListener(this)
        button55.setOnClickListener(this)
        button66.setOnClickListener(this)
        button67.setOnClickListener(this)
        button77.setOnClickListener(this)
        button88.setOnClickListener(this)
        button99.setOnClickListener(this)
        button100.setOnClickListener(this)
        button101.setOnClickListener(this)
        button102.setOnClickListener(this)
        button103.setOnClickListener(this)
        button104.setOnClickListener(this)
        button105.setOnClickListener(this)
        button106.setOnClickListener(this)
        button107.setOnClickListener(this)
        button108.setOnClickListener(this)
        button109.setOnClickListener(this)
        button110.setOnClickListener(this)
        button111.setOnClickListener(this)
        button207.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button67->{
                showPopupWindow33(v)
            }

            R.id.button11->{
                showCustomDialog()
            }
            R.id.button22->{
                ToastUtils.showToast("原生吐司")
            }
            R.id.button33->{
                ToastUtils.setToastBackColor(this.resources.getColor(R.color.color_7f000000))
                ToastUtils.showRoundRectToast("自定义吐司")
            }
            R.id.button44->{
                ToastUtils.showRoundRectToast(R.layout.view_layout_toast_delete_dialog)
            }
            R.id.button55->{
                ToastUtils.showRoundRectToast(R.layout.view_layout_toast_load_dialog)
            }
            R.id.button66 ->{
                val builder = ToastUtils.Builder(this.application)
                builder.setDuration(Toast.LENGTH_SHORT)
                    .setFill(false)
                    .setGravity(Gravity.CENTER)
                    .setOffset(0)
                    .setDesc("内容内容")
                    .setTitle("标题")
                    .setTextColor(Color.WHITE)
                    .setBackgroundColor(this.resources.getColor(R.color.blackText))
                    .build()
                    .show()
            }
            R.id.button77 ->{
                val customPop = CustomPop(this)
                customPop.setDelayedMsDismiss(2500)
                customPop.setBgAlpha(0.5f)
                customPop.showAsDropDown(button22)
//                customPop.showAsDropDown(button11, 0, -button11.getMeasuredHeight() - button11.getHeight())
            }
            R.id.button88->{
                showPopupWindow11()
            }
            R.id.button99->{
                showPopupWindow22()
            }
            R.id.button100->{
                showCustomBottomDialog()
            }
            R.id.button101->{
                showDialogFragment()
            }
            R.id.button102->{
                showDialogFragment2()
            }
            R.id.button103->{
                showCustomDialog2()
            }
            R.id.button104->{
                showCustomDialog3()
            }
            R.id.button105->{
                showCustomDialog4()
            }
            R.id.button106->{
                showViewLoading()
            }
            R.id.button107->{
                showSnakeBar11()
            }
            R.id.button108->{
                showSnakeBar22()
            }
            R.id.button109->{
                showSnakeBar33()
            }
            R.id.button110->{
                showSnakeBar44(v)
            }
            R.id.button111->{
                showSnakeBar55(v)
            }
            R.id.button207->{
                val sb = Snackbar.make(v, "提示信息11", Snackbar.LENGTH_LONG)
                    .setAction("删除吗？") {
                        ToastUtils.showRoundRectToast("删除")
                    }
                    .setActionTextColor(Color.RED)
                    .setText("风萧萧易水寒")
                    .addCallback(object : BaseCallback<Snackbar?>() {
                        override fun onDismissed(
                            transientBottomBar: Snackbar?,
                            event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            when (event) {
                                Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE,
                                Snackbar.Callback.DISMISS_EVENT_MANUAL,
                                Snackbar.Callback.DISMISS_EVENT_SWIPE,
                                Snackbar.Callback.DISMISS_EVENT_TIMEOUT -> ToastUtils.showRoundRectToast(
                                    "删除成功"
                                )
                                Snackbar.Callback.DISMISS_EVENT_ACTION -> ToastUtils.showRoundRectToast(
                                    "撤销了删除操作"
                                )
                            }
                        }

                        override fun onShown(transientBottomBar: Snackbar?) {
                            super.onShown(transientBottomBar)
                        }
                    })
                sb.show()
            }
            R.id.button13->{
                showAlertDialog()
            }
        }

    }
    private var alertDialog: AlertDialog? = null
    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setMessage("潇湘剑雨")
        builder.setTitle("这个是标题")
        builder.setView(R.layout.activity_main)
        builder.setPositiveButton("确定") {
                dialog, which ->
            alertDialog?.cancel()
            alertDialog?.dismiss()
        }
        builder.setNegativeButton("取消") {
                dialog, which ->
            alertDialog?.dismiss() }
        alertDialog = builder.create()
        alertDialog?.show()
    }

    private fun showPopupWindow33(view: View) {
        val popupWindow = PopupWindow(this) //创建对象
        val inflate = LayoutInflater.from(this).inflate(R.layout.view_pop_custom_dialog, null)
        popupWindow.contentView = inflate
        popupWindow.width = LinearLayout.LayoutParams.WRAP_CONTENT
        popupWindow.height = LinearLayout.LayoutParams.WRAP_CONTENT
        popupWindow.animationStyle = R.style.BottomDialog
        popupWindow.isTouchable = true//设置焦点，设置为true之后，PopupWindow内容区域，才可以响应点击事件
        popupWindow.setBackgroundDrawable(ColorDrawable(0x00000000))// 设置背景
        popupWindow.isOutsideTouchable = true//点击空白处的时候让PopupWindow消失
        // true时，点击返回键先消失 PopupWindow
        // 但是设置为true时setOutsideTouchable，setTouchable方法就失效了（点击外部不消失，内容区域也不响应事件）
        // false时PopupWindow不处理返回键，默认是false
        popupWindow.isFocusable = false
        popupWindow.setOnDismissListener {

        }
        val showing = popupWindow.isShowing
        if (!showing) {
            popupWindow.showAsDropDown(view)
        }
    }

    private fun showSnakeBar55(v: View) {

        SnackBarUtils.builder()
            .setBackgroundColor(this.resources.getColor(R.color.color_000000))
            .setTextSize(14f)
            .setTextColor(this.resources.getColor(R.color.white))
            .setTextTypefaceStyle(Typeface.BOLD)
            .setText("提示文字信息")
            .setMaxLines(4)
            .centerText()
            .setActionText("收到")
            .setActionTextColor(this.resources.getColor(R.color.color_f25057))
            .setActionTextSize(16f)
            .setActionTextTypefaceStyle(Typeface.BOLD)
            .setActionClickListener(View.OnClickListener { ToastUtils.showRoundRectToast("消失") })
            .setIcon(R.mipmap.ic_launcher_round)
            .setActivity(this@MainActivity)
            .setDuration(Toast.LENGTH_SHORT)
            .build()
            .show()
    }

    private fun showSnakeBar44(view:View) {
        val snackbar: Snackbar = SnackBarUtils.builder()
            .setView(view)
            .setText("提示文字")
            .setDuration(Toast.LENGTH_SHORT)
            .build()
        snackbar.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar?>() {
            fun onDismissed(transientBottomBar: Snackbar, event: Int) {
                super.onDismissed(transientBottomBar, event)
            }

            fun onShown(transientBottomBar: Snackbar) {
                super.onShown(transientBottomBar)
            }
        })
        snackbar.show()
    }

    private fun showSnakeBar33() {
        Log.e("yuyu","showSnakeBar33 111 ")
        SnackBarUtils.showSnackBar(
            this,
            "提示信息 33",
            "ACTION 33",
            R.mipmap.ic_launcher_round,
            View.OnClickListener { ToastUtils.showRoundRectToast("信息消失") })
    }

    private fun showSnakeBar22() {
        SnackBarUtils.showSnackBar(this, "提示信息", "ACTION", View.OnClickListener { ToastUtils.showRoundRectToast("消失") })
    }

    private fun showSnakeBar11() {
        SnackBarUtils.showSnackBar(this, "snackbar 111 ")
    }

    private fun showViewLoading(){
        ViewLoading.show(this)
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                ViewLoading.dismiss(this@MainActivity)
            }
        }.start()
    }

    private fun showCustomDialog4() {
        val dialog = TestCustomDialogFragment()
            dialog.setFragmentManager(supportFragmentManager)
            .setTitle("这个是是标题44")
            .setContent("这个是弹窗的内容这个是弹窗的内容这个是弹窗的内容这个是弹窗的内容")
            //.setCancelContent("取消")
            .setOkContent("确定")
            .setDimAmount(0.2f)
            .setTag("dialog")
            .setCancelOutside(true)
            .setCancelListener(View.OnClickListener {
                dialog.dismissDialogFragment()
                ToastUtils.showRoundRectToast("取消了")
            })
            .setOkListener(View.OnClickListener {
                dialog.dismissDialogFragment()
                ToastUtils.showRoundRectToast("确定了")
            })
            .setOtherListener(View.OnClickListener {
                dialog.dismissDialogFragment()
                ToastUtils.showRoundRectToast("其他内容")
            })
            .show()
    }

    private fun showCustomDialog3() {
       val fragment =  TestCustomDialogFragment()
            fragment.setFragmentManager(supportFragmentManager)
            .setTitle("这个是是标题33")
            .setContent("这个是弹窗的内容33")
            .setOtherContent("其他")
            .setCancelContent("取消")
            .setOkContent("确定")
            .setDimAmount(0.2f)
            .setTag("dialog1")
            .setCancelOutside(true)
            .setCancelListener(View.OnClickListener {
                fragment.dismissDialogFragment()
                ToastUtils.showRoundRectToast("取消了")
            })
            .setOkListener(View.OnClickListener {
                fragment.dismissDialogFragment()
                ToastUtils.showRoundRectToast("确定了")
            })
            .setOtherListener(View.OnClickListener {
                fragment.dismissDialogFragment()
                ToastUtils.showRoundRectToast("其他内容")
            })
            .show()
    }

    private fun showCustomDialog2() {
        val dialog = TestCustomDialogFragment()
        dialog.setFragmentManager(supportFragmentManager)
        dialog.setTitle("这个是是标题22")
        dialog.setContent("这个是弹窗的内容22")
        dialog.setCancelContent("取消")
        dialog.setOkContent("确定")
        dialog.setDimAmount(0.0f)
        dialog.setTag("dialog2")
        dialog.setCancelOutside(true)
        dialog.setCancelListener(View.OnClickListener {
            dialog.dismissDialogFragment()
            ToastUtils.showRoundRectToast("取消了")
        })
        dialog.setOkListener(View.OnClickListener {
            dialog.dismissDialogFragment()
            ToastUtils.showRoundRectToast("确定了")
        })
        //这个高度可以自己设置，十分灵活
        //dialog.setHeight(getScreenHeight() / 2);
        dialog.show()
        dialog.setLoadFinishListener(object : onLoadFinishListener {
            override fun listener() {
                Log.e("结束了", "监听事件")
            }
        })
    }

    private fun showDialogFragment2() {
        val list: MutableList<DialogInfo> = ArrayList<DialogInfo>()
        for (a in 0..19) {
            val dialogBean = DialogInfo("ooo", "yudl", "title")
            list.add(dialogBean)
        }
        BottomDialogFragment.setFragmentManager(supportFragmentManager)
            .setViewListener(object :
                BottomDialogFragment.ViewListener {
                override fun bindView(v: View) {
                    val recyclerView: RecyclerView = v.findViewById<RecyclerView>(R.id.recyclerView)
                    val ivCancel =
                        v.findViewById<ImageView>(R.id.iv_cancel)
                    val ivDownload =
                        v.findViewById<ImageView>(R.id.iv_download)
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    val mAdapter =
                        DialogListAdapter(this@MainActivity, list)
                    recyclerView.setAdapter(mAdapter)
                    mAdapter.setOnItemClickListener(object : DialogListAdapter.OnAdapterItemClickListener {
                        override fun onItemClick(position: Int) {}
                    })
                    val listener = View.OnClickListener { v ->
                            when (v.id) {
                                R.id.iv_cancel -> {
                                }
                                R.id.iv_download -> ToastUtils.showToast("下载")
                                else -> {
                                }
                            }
                        }
                    ivCancel.setOnClickListener(listener)
                    ivDownload.setOnClickListener(listener)
                }
            })
            .setLayoutRes(R.layout.dialog_bottom_layout_list)
            .setDimAmount(0.5f)
            .setTag("BottomDialog")
            .setCancelOutside(true)
            .setHeight(DialogUtils.getScreenHeight(this@MainActivity) / 2)
            .show()
    }


    private fun showDialogFragment() {
        val dialog = BottomDialogFragment.setFragmentManager(supportFragmentManager)
        dialog.setViewListener(object : BottomDialogFragment.ViewListener {
            override fun bindView(v: View) {
                val tv_cancel =
                    v!!.findViewById<View>(R.id.tv_cancel) as TextView
                tv_cancel.setOnClickListener { dialog.dismissDialogFragment() }
            }
        })
        dialog.setLayoutRes(R.layout.dialog_bottom_layout)
        dialog.setDimAmount(0.5f)
        dialog.setTag("BottomDialog")
        dialog.setCancelOutside(true)
        //dialog.setHeight(getScreenHeight() / 2);
        dialog.show()
    }


    private fun showCustomBottomDialog() {
        CustomBottomDialog(this@MainActivity)
            .title("这个是标题")
            .setCancel(true, "取消选择")
            .orientation(CustomBottomDialog.VERTICAL)
            .inflateMenu(
                R.menu.menu_share,
                object : OnItemClickListener {
                    override fun click(item: CustomItem?) {}
                })
            .show()
    }

    private fun showPopupWindow22() {
        val popWindow = PopupWindowBuilder(this)
            .setView(R.layout.view_pop_custom_dialog)
            .create()
        Log.e("yuyu","height -->${button88.height} , getHeight -->${popWindow.getHeight()}")
        popWindow.showAsDropDown(button88, 0, -(button88.height + popWindow.getHeight()))
        //popWindow.showAtLocation(mButton1, Gravity.NO_GRAVITY,0,0);
    }

    private fun handleLogic(contentView: View) {
        val listener =
            View.OnClickListener { v ->
                popWindow?.dismiss()
                var showContent = ""
                when (v.id) {
                    R.id.menu1 -> showContent = "点击 Item1"
                    R.id.menu2 -> showContent = "点击 Item2"

                }
                ToastUtils.showRoundRectToast(showContent)
            }
        contentView.findViewById<View>(R.id.menu1).setOnClickListener(listener)
        contentView.findViewById<View>(R.id.menu2).setOnClickListener(listener)
    }
    private fun showPopupWindow11() {
        val contentView: View = LayoutInflater.from(this).inflate(R.layout.pop_layout_dialog, null,false)
        //处理popWindow 显示内容,自定义布局
        handleLogic(contentView)
        //处理popWindow 显示内容,recycleView
        //handleListView(contentView);
        //创建并显示popWindow
        popWindow = PopupWindowBuilder(this) //.setView(R.layout.pop_layout)
            .setView(contentView)
            .setFocusable(true) //弹出popWindow时，背景是否变暗
            .enableBackgroundDark(true) //控制亮度
            .setBgDarkAlpha(0.7f)
            .setOutsideTouchable(true)
            .setAnimationStyle(R.style.popWindowStyle)
            .setOnDissmissListener(PopupWindow.OnDismissListener {
                //对话框销毁时
            })
            .create()
            .showAsDropDown(button22, 0, 10)
    }

    private fun showCustomDialog() {
        val names: MutableList<String> = ArrayList()
        names.add("拍照")
        names.add("相册")
        names.add("其他")
        showDialog(object : SelectDialogListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@MainActivity, names[position], Toast.LENGTH_SHORT)
                    .show()
            }
        }, names)
    }



    private fun showDialog(
        listener: SelectDialogListener,
        names: List<String>
    ): CustomSelectDialog? {
        val dialog = CustomSelectDialog(this,
            R.style.transparentFrameWindowStyle, listener, names
        )
        dialog.setItemColor(R.color.colorAccent, R.color.colorPrimary)
        //判断activity是否finish
        if (!this.isFinishing) {
            dialog.show()
        }
        return dialog
    }

}
