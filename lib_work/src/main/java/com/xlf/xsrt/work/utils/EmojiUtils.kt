package com.xlf.xsrt.work.utils

import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import java.util.regex.Pattern

/**
 * Created by YeeLL on 17/12/8.
 */

object EmojiUtils {

    /**
     * 去除字符串中的Emoji表情
     *
     * @param source
     * @return
     */
    private fun removeEmoji(source: CharSequence): String {
        var result = ""
        for (i in 0 until source.length) {
            val c = source[i]
            if (isEmojiCharacter(c)) {
                continue
            }
            result += c
        }
        return result
    }

    /**
     * 判断一个字符串中是否包含有Emoji表情
     *
     * @param input
     * @return true 有Emoji
     */
    fun isEmojiCharacter(input: CharSequence): Boolean {
        for (i in 0 until input.length) {
            if (isEmojiCharacter(input[i])) {
                return true
            }
        }
        return false
    }

    /**
     * 是否是Emoji 表情,抄的那哥们的代码
     *
     * @param codePoint
     * @return true 是Emoji表情
     */
    fun isEmojiCharacter(codePoint: Char): Boolean {
        // Emoji 范围
        Log.d("chufei", "${codePoint.toInt()}")
//        10084
//        65039
//        9202  9774 9203 9000 9996
        // 0 9 10 13 >=32&&<=55295&&!=9786   >=57344&&<=65533 >=65536&&<=1114111

        val isScopeOf = (codePoint.toInt() == 0x0 || codePoint.toInt() == 0x9 || codePoint.toInt() == 0xA || codePoint.toInt() == 0xD
                || codePoint.toInt() >= 0x20 && codePoint.toInt() <= 0xD7FF && codePoint.toInt() != 0x263a
                || codePoint.toInt() >= 0xE000 && codePoint.toInt() <= 0xFFFD
                || codePoint.toInt() >= 0x10000 && codePoint.toInt() <= 0x10FFFF)

        return !isScopeOf
    }

    /**
     * Emoji表情校验
     *
     * @param string
     * @return
     */
    fun isEmoji(string: String): Boolean {
//        过滤Emoji表情
//        val p = Pattern.compile("[^\\u0000-\\uFFFF]")
        //过滤Emoji表情和颜文字
        val p = Pattern.compile("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]|[\\ud83e\\udd00-\\ud83e\\uddff]|[\\u2300-\\u23ff]|[\\u2500-\\u25ff]|[\\u2100-\\u21ff]|[\\u0000-\\u00ff]|[\\u2b00-\\u2bff]|[\\u2d06]|[\\u3030]");
        val m = p.matcher(string)
        return m.find()
    }

    fun setEmojiEditText(s: CharSequence, start: Int, count: Int, editText: EditText, context: Context) {
        val input = s.subSequence(start, start + count)
        // 退格
        if (count == 0) return
        //如果 输入的类容包含有Emoji
        if (isEmojiCharacter(input)) {
            //那么就去掉
            editText.setText(removeEmoji(s))
            Toast.makeText(context, "不能输入表情哟", Toast.LENGTH_SHORT).show()
        }
    }
}
