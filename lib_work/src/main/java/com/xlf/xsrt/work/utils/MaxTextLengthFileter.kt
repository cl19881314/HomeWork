package com.xlf.xsrt.work.utils

import android.content.Context
import android.text.InputFilter
import android.text.Spanned
import android.widget.Toast

class MaxTextLengthFileter(ctx: Context, maxLength: Int) : InputFilter {
    private var mMaxLength = -1
    private var mContext: Context? = null

    init {
        mMaxLength = maxLength
        mContext = ctx
    }

    override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
        if (EmojiUtils.isEmojiCharacter(source!!)) {
            Toast.makeText(mContext, "不能输入表情", Toast.LENGTH_SHORT).show()
            return ""
        }
        val keep = mMaxLength - (dest?.length!!.minus(dend - dstart))
        if (keep < (end - start)) {
            Toast.makeText(mContext, "最多只能输入${mMaxLength}个字", Toast.LENGTH_SHORT).show()
        }
        return when {
            keep <= 0 -> ""
            keep >= end - start -> null
            else -> source!!.subSequence(start, start + keep)
        }
    }


}