package com.xlf.xsrt.work.teacher.answer

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.eventbus.RefreshEvent
import com.xlf.xsrt.work.teacher.answer.viewmodel.StuAnswerDetailViewModel
import com.xlf.xsrt.work.utils.EmojiUtils
import com.xlf.xsrt.work.widget.TitleBar
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
        var title = intent.getStringExtra("title")
        if (!TextUtils.isEmpty(title)) {
            titleBar.setTitleTxt(title)
        }
        mStuAnswerId = intent.getIntExtra("stuAnswerId", -1)
        titleBar.setTitleBarClickListener(object : TitleBar.TitleBarClickListener{
            override fun leftImgClick() {
                finish()
            }

            override fun rightTextClick() {
                addComment()
            }

        })

        commentEdt.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()){
                    titleBar.setRightTextClor(resources.getColor(R.color.xsrt_btn_bg_color))
                } else {
                    titleBar.setRightTextClor(resources.getColor(R.color.xsrt_title3_txt_color))
                }
                if (s!!.length >= 200){
                    toast("最多输入200个字")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                EmojiUtils.setEmojiEditText(s!!,start,count,commentEdt,this@WriteCommentActivity)
                commentEdt.setSelection(commentEdt.text.toString().length)
            }

        })
    }

    override fun doResponseData() {
        mDataViewModel.mAddCommentViewModel.observe(this, Observer {
            if (it?.flag == 1){
                toast("发布批阅成功")
                setResult(Activity.RESULT_OK)
                EventBus.getDefault().post(RefreshEvent())
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
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(content.trim()) || TextUtils.isEmpty(content.replace("\n",""))){
            toast("请输入内容")
            return
        }
        if (content.length > 200){

        }
        mDataViewModel.addComment(mStuAnswerId, content)
    }

}