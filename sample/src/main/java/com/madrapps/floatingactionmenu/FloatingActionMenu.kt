package com.madrapps.floatingactionmenu

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.animation.DecelerateInterpolator

class FloatingActionMenu @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private var openAnimator = AnimatorInflater.loadAnimator(context, R.animator.open) as AnimatorSet
    private var openAnimator1 = AnimatorInflater.loadAnimator(context, R.animator.open) as AnimatorSet
    private var openAnimator2 = AnimatorInflater.loadAnimator(context, R.animator.open) as AnimatorSet
    private var openAnimator3 = AnimatorInflater.loadAnimator(context, R.animator.open) as AnimatorSet
    private var closeAnimator = AnimatorInflater.loadAnimator(context, R.animator.close) as AnimatorSet
    private var closeAnimator1 = AnimatorInflater.loadAnimator(context, R.animator.close) as AnimatorSet
    private var closeAnimator2 = AnimatorInflater.loadAnimator(context, R.animator.close) as AnimatorSet
    private var closeAnimator3 = AnimatorInflater.loadAnimator(context, R.animator.close) as AnimatorSet

    init {
        openAnimator1.startDelay = 30
        openAnimator2.startDelay = 60
        openAnimator3.startDelay = 90
        openAnimator.interpolator = DecelerateInterpolator()
        openAnimator1.interpolator = DecelerateInterpolator()
        openAnimator2.interpolator = DecelerateInterpolator()
        openAnimator3.interpolator = DecelerateInterpolator()

        closeAnimator.startDelay = 90
        closeAnimator1.startDelay = 60
        closeAnimator2.startDelay = 30
        closeAnimator.interpolator = DecelerateInterpolator()
        closeAnimator1.interpolator = DecelerateInterpolator()
        closeAnimator2.interpolator = DecelerateInterpolator()
        closeAnimator3.interpolator = DecelerateInterpolator()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val left = paddingLeft
        val right = r - l - paddingRight
        var top = paddingTop
//        val bottom = b - t - paddingBottom

        (0 until childCount)
                .map { getChildAt(it) }
                .filter { it.visibility != GONE }
                .reversed()
                .forEach { child ->

                    val lp = child.layoutParams as MarginLayoutParams

                    val childLeft = left + lp.leftMargin
                    val childRight = right - lp.rightMargin
                    val childTop = top + lp.topMargin
                    val childBottom = childTop + child.measuredHeight

                    child.layout(childLeft, childTop, childRight, childBottom)

                    top = childBottom + lp.bottomMargin
                }

        openAnimator.setTarget(getChildAt(0))
        openAnimator1.setTarget(getChildAt(1))
        openAnimator2.setTarget(getChildAt(2))
        openAnimator3.setTarget(getChildAt(3))
        closeAnimator.setTarget(getChildAt(0))
        closeAnimator1.setTarget(getChildAt(1))
        closeAnimator2.setTarget(getChildAt(2))
        closeAnimator3.setTarget(getChildAt(3))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var maxWidth = 0
        var maxHeight = 0
        (0 until childCount)
                .map { getChildAt(it) }
                .filter { it.visibility != GONE }
                .forEach { child ->
                    measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)

                    val lp: MarginLayoutParams = child.layoutParams as MarginLayoutParams

                    maxWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
                    maxHeight += child.measuredHeight + lp.topMargin + lp.bottomMargin
                }

        // TODO We need to obtain this ANCHOR ID from the anchor tag
        val anchorView = (parent as ViewGroup).findViewById<View>(R.id.floatingActionButton)
        setMeasuredDimension(maxWidth, maxHeight + (anchorView?.measuredHeight ?: 0))
    }

    override fun generateLayoutParams(attrs: AttributeSet?) = MarginLayoutParams(context, attrs)
    override fun generateDefaultLayoutParams() = MarginLayoutParams(MATCH_PARENT, MATCH_PARENT)
    override fun checkLayoutParams(p: LayoutParams?) = p is MarginLayoutParams
    override fun generateLayoutParams(p: LayoutParams?) = MarginLayoutParams(p)
    override fun shouldDelayChildPressedState() = false

    fun open() {
        openAnimator.start()
        openAnimator1.start()
        openAnimator2.start()
        openAnimator3.start()
    }

    fun close() {
        closeAnimator.start()
        closeAnimator1.start()
        closeAnimator2.start()
        closeAnimator3.start()

    }
}