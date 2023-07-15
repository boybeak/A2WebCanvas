package com.github.boybeak.a2webcanvas.app.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import com.github.boybeak.a2webcanvas.app.R

class InfoCard : CardView {

    private val Number.dp get() = (context.resources.displayMetrics.density * this.toFloat()).toInt()

    private val contentLayout = LinearLayoutCompat(context).apply {
        orientation = LinearLayoutCompat.VERTICAL
        showDividers = LinearLayoutCompat.SHOW_DIVIDER_MIDDLE
        dividerDrawable = ResourcesCompat.getDrawable(context.resources, android.R.drawable.divider_horizontal_bright, null)
    }
    private val titleView = AppCompatTextView(context).apply {
        setPadding(16.dp, 16.dp, 16.dp, 8.dp)
        setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Title)
    }
    private val textView = AppCompatTextView(context).apply {
        setPadding(16.dp, 8.dp, 16.dp, 16.dp)
        setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Caption)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttributes(attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttributes(attrs)
    }

    init {
        contentLayout.addView(titleView, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        contentLayout.addView(textView, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        addView(contentLayout, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
    }

    private fun initAttributes(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.InfoCard, 0, 0).apply {
            try {
                titleView.text = getText(R.styleable.InfoCard_title)
                textView.text = getText(R.styleable.InfoCard_text)
                if (textView.text.isNullOrEmpty()) {
                    textView.visibility = View.GONE
                } else {
                    textView.visibility = View.VISIBLE
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }.recycle()
    }

    fun setText(text: CharSequence) {
        textView.text = text
    }

}