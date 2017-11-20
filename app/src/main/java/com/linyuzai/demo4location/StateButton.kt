package com.linyuzai.demo4location

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.Button

/**
 * Created by linyuzai on 2017/11/20.
 * @author linyuzai
 */
class StateButton constructor(context: Context, attrs: AttributeSet?) : Button(context, attrs) {
    companion object {
        const val DEFAULT = 0
        const val ACTIVE = 1
    }

    private var mDefaultText = "Default"
    private var mActiveText = "Active"

    private var mDefaultTextColor = Color.BLACK
    private var mActiveTextColor = Color.BLACK

    private var mDefalutBackground = null
    private var mActiveBackground = null

    private var mState = DEFAULT
    private var mBeforeFilter: ((v: View, state: Int) -> Boolean)? = null
    private var mStateClickListener: ((v: View, state: Int) -> Unit)? = null

    init {
        mDefaultText = text.toString()
        mActiveText = text.toString()

        setOnClickListener {
            val isPass = mBeforeFilter?.invoke(this, mState) ?: true
            if (!isPass)
                return@setOnClickListener
            mStateClickListener?.invoke(this, mState)
        }
    }

    fun setOnStateClickListener(listener: (v: View, state: Int) -> Unit): StateButton = apply {
        mStateClickListener = listener
    }

    fun setBeforeFilter(filter: (v: View, state: Int) -> Boolean): StateButton = apply {
        mBeforeFilter = filter
    }

    fun defaultText(text: String): StateButton = apply { mDefaultText = text;this.text = text }

    fun activeText(text: String): StateButton = apply { mActiveText = text }

    fun updateState(){
        when (mState) {
            DEFAULT -> {
                mState = ACTIVE
                text = mActiveText
            }
            ACTIVE -> {
                mState = DEFAULT
                text = mDefaultText
            }
            else -> {
                mState = DEFAULT
                text = mDefaultText
            }
        }
    }
}