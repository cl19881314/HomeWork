package com.xlf.xsrt.work.student

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import com.xlf.xsrt.work.R
import com.xlf.xsrt.work.base.BaseActivity
import com.xlf.xsrt.work.constant.UserInfoConstant
import com.xlf.xsrt.work.detail.QuestionDetailActivity
import com.xlf.xsrt.work.fragment.StudentAnswerDetailFragment
import com.xlf.xsrt.work.student.bean.HomeworkStuVo
import com.xlf.xsrt.work.student.model.StudentAnswerModel
import com.xlf.xsrt.work.teacher.answer.adapter.StudentAnswerDetailAdapter
import com.xlf.xsrt.work.widget.TitleBar
import kotlinx.android.synthetic.main.xsrt_activity_student_choose_answer.*
import kotlinx.android.synthetic.main.xsrt_item_answer_option.view.*
import org.json.JSONObject

/**
 * @author Chenhong
 * @date 2018/12/17.
 * @des 学生答题界面
 */
class StudentToAnswerActivity : BaseActivity() {
    private var mAdapter: StudentAnswerDetailAdapter? = null
    private var mAnswerList = ArrayList<HashMap<String, String>>()
    private var mDataList = ArrayList<HomeworkStuVo.HomeworkBaseVo>()
    private var mAnswerIndex = 0
    private var mAnswerSize = 5
    private var mGroupWorkId = 0
    private var mTitle = ""
    private val mDataViewModel by lazy {
        ViewModelProviders.of(this).get(StudentAnswerModel::class.java)
    }

    override fun getContentViewId(): Int {
        return R.layout.xsrt_activity_student_choose_answer
    }

    override fun init() {
        initTitle()
        mAdapter = StudentAnswerDetailAdapter(supportFragmentManager)
        answerVp.adapter = mAdapter
        initDetailData()
        initViewModel()
    }

    private fun initTitle() {
        titleBar.setRightTextClor(resources.getColor(R.color.xsrt_btn_bg_color))
        titleBar.setRightTextVisibility(View.GONE)
        mTitle = intent.getStringExtra("title")
        titleBar.setTitleTxt(mTitle)
    }

    private fun initViewModel() {
        mDataViewModel.mStudentModel.observe(this, Observer {
            if (it?.flag == 1) {
                toast("提交成功")
                //跳转到解析界面
                mDataViewModel.getAnalysis(UserInfoConstant.getUserId(), mGroupWorkId)
            } else {
                toast("提交失败")
            }
        })

        mDataViewModel.mAnalysisModel.observe(this, Observer {
            if (it?.flag == 1) {
                var intent = Intent(this@StudentToAnswerActivity, QuestionDetailActivity::class.java)
                intent.putExtra("title", mTitle)
                intent.putStringArrayListExtra("urlList", it.analysisUrlList)
                intent.putExtra("showTodo", false)
                startActivity(intent)
                finish()
            }
        })

        mDataViewModel.mErrorData.observe(this, Observer {
            toast(it!!)
        })
    }

    override fun initListener() {
        titleBar.setTitleBarClickListener(object : TitleBar.TitleBarClickListener {
            override fun leftImgClick() {
                onBackPressed()
            }

            override fun rightTextClick() {
                //处理最后一道题答案
                setAnswer(mAnswerSize - 1)
                var answerString = setAnswerToJson()
                mDataViewModel.submitAnswer(UserInfoConstant.getUserId(), mGroupWorkId, answerString)
            }
        })
        answerVp.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                countNumTxt.text = "${position + 1}/$mAnswerSize"
                setAnswer(mAnswerIndex)
                mAnswerIndex = position
                initOption(mDataList[position].optionCount)
                if (position == mAnswerSize - 1) {
                    titleBar.setRightTextVisibility(View.VISIBLE)
                } else {
                    titleBar.setRightTextVisibility(View.GONE)
                }
            }

        })
    }

    private fun setAnswerToJson(): String {
        var json = JSONObject()
        for (answer in mAnswerList) {
            for (entry in answer.entries) {
                val key = entry.key
                val value = entry.value
                json.put(key, value)
            }
        }
        Log.e("chen", "result:" + json.toString())
        return json.toString()
    }

    private fun setAnswer(position: Int) {
        var data = HashMap<String, String>()
        var answer = ""
        for (i in 0 until chooseAnswerLL.childCount) {
            val optionView = chooseAnswerLL.getChildAt(i).findViewById<CheckBox>(R.id.optionTxt)
            if (optionView.isChecked) {
                answer += (i + 65).toChar().toString()
            }
        }
        data["$position"] = answer
        mAnswerList[position] = data
    }

    private fun initDetailData() {
        mGroupWorkId = intent.getIntExtra("groupId", -1)
        mDataList = intent.getParcelableArrayListExtra<HomeworkStuVo.HomeworkBaseVo>("data")
        mAnswerSize = mDataList.size
        countNumTxt.text = "1/$mAnswerSize"
        var data = ArrayList<Fragment>()
        for (i in 0 until mAnswerSize) {
            var fragment = StudentAnswerDetailFragment()
            var bundle = Bundle()
            bundle.putString("url", mDataList[i].homeworkDetailUrl)
            fragment.arguments = bundle
            data.add(fragment)
            var map = HashMap<String, String>()
            map["${mDataList[i].homeworkId}"] = ""
            mAnswerList.add(map)
        }
        mAdapter?.setData(data)
        if (mDataList.size > 0) {
            initOption(mDataList[0].optionCount)
        }
    }

    private fun initOption(size: Int) {
        if (size <= 0) {
            return
        }
        chooseAnswerLL.removeAllViews()
        for (i in 0 until size) {
            var optionView = LayoutInflater.from(this).inflate(R.layout.xsrt_item_answer_option, null, false)
            var txt = (i + 65).toChar().toString()
            optionView.optionTxt.text = txt
            var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.weight = 1f
            optionView.layoutParams = params
            //回滑的时候恢复之前的选中状态
            for (lastAnswer in mAnswerList[mAnswerIndex].entries) {
                if (lastAnswer.value.contains(txt)) {
                    optionView.optionTxt.isChecked = true
                }
            }
            chooseAnswerLL.addView(optionView)
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).setTitle("提示")
                .setMessage("您还没有交卷，是否确认退出")
                .setNegativeButton("确认退出", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    finish()
                })
                .setPositiveButton("继续做作业", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .show()

    }

}