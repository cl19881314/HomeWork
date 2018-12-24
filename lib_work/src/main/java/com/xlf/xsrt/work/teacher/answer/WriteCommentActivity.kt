package com.xlf.xsrt.work.teacher.answer

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.text.TextUtils
import com.jakewharton.rxbinding2.widget.RxTextView
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.eventbus.NeedRefreshSuccessBean
import com.xlf.xsrt.work.teacher.answer.viewmodel.StuAnswerDetailViewModel
import com.xlf.xsrt.work.widget.TitleBar
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.xsrt_activity_write_comment_layout.*
import org.greenrobot.eventbus.EventBus

class WriteCommentActivity : BaseActivity(){
    private var mStuAnswerId = -1
    private val mDataViewModel by lazy {
        ViewModelProviders.of(this).get(StuAnswerDetailViewModel::class.java)
    }
    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_write_comment_layout
    }

    override fun init() {
        mStuAnswerId = intent.getIntExtra("stuAnswerId", -1)
        titleBar.setTitleBarClickListener(object : TitleBar.TitleBarClickListener{
            override fun leftImgClick() {
                finish()
            }

            override fun rightTextClick() {
                addComment()
            }

        })

        RxTextView.textChanges(commentEdt).observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.isNotEmpty()){
                        titleBar.setRightTextClor(resources.getColor(R.color.xsrt_btn_bg_color))
                    } else {
                        titleBar.setRightTextClor(resources.getColor(R.color.xsrt_title3_txt_color))
                    }
                    if (it.length >= 200){
                        toast("最多输入200个字")
                    }
                }
    }

    override fun doResponseData() {
        mDataViewModel.mAddCommentViewModel.observe(this, Observer {
            if (it?.flag == 1){
                toast("发布批阅成功")
                setResult(Activity.RESULT_OK)
                EventBus.getDefault().post(NeedRefreshSuccessBean())
                finish()
            } else {
                toast("发布批阅失败")
            }
        })
        mDataViewModel.mErrorData.observe(this, Observer {
            toast(it!!)
        })
    }

    private fun addComment(){
        val content = commentEdt.text.toString()
        if (TextUtils.isEmpty(content)){
            toast("请输入内容")
            return
        }
        if (content.length > 200){

        }
        mDataViewModel.addComment(mStuAnswerId, content)
    }

}