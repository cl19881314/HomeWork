package com.xlf.xsrt.work.utils

import android.content.Context
import android.widget.EditText
import android.widget.Toast

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
    private fun isEmojiCharacter(input: CharSequence): Boolean {
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
        val isScopeOf = (codePoint.toInt() == 0x0 || codePoint.toInt() == 0x9 || codePoint.toInt() == 0xA || codePoint.toInt() == 0xD
                || codePoint.toInt() >= 0x20 && codePoint.toInt() <= 0xD7FF && codePoint.toInt() != 0x263a
                || codePoint.toInt() >= 0xE000 && codePoint.toInt() <= 0xFFFD
                || codePoint.toInt() >= 0x10000 && codePoint.toInt() <= 0x10FFFF)

        return !isScopeOf
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
