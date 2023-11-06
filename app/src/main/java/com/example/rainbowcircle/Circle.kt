package com.example.rainbowcircle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat
import kotlin.random.Random
import kotlin.random.nextInt

class Circle(
    context: Context,
    attr: AttributeSet
) : View(context, attr), Animation.AnimationListener {

    private val drawTextDegrees = mutableSetOf<Int>()

    private val colors = listOf(
        ContextCompat.getColor(context, R.color.RED),
        ContextCompat.getColor(context, R.color.ORANGE),
        ContextCompat.getColor(context, R.color.YELLOW),
        ContextCompat.getColor(context, R.color.GREEN),
        ContextCompat.getColor(context, R.color.LIGHTBLUE),
        ContextCompat.getColor(context, R.color.BLUE),
        ContextCompat.getColor(context, R.color.PURPLE),
    )

    private val sweepAngle = 360f / colors.size
    private val sectorDegrees = colors.indices.map {
        it * sweepAngle
    }
    private val startAngle = -(90f + sweepAngle / 2)
    private var winnerIndex = 0

    private val paint = Paint()
    private val textPaint = Paint().apply {
        color = Color.BLUE
        textSize = 100F
        style = Paint.Style.FILL
    }

    init {
        setOnClickListener {
            spinCircle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawRainbowCircle(canvas)
        drawText(canvas)
    }

    private fun drawText(canvas: Canvas) {
        val textX = width / 2f
        val textY = height / 2f
        drawTextDegrees.forEach { currentDegreeIndex ->
            val currentDegree =
                startAngle + sectorDegrees[(currentDegreeIndex + 1) % sectorDegrees.size]
            canvas.rotate(currentDegree, textX, textY)
            canvas.drawText("TEXT", textX, textY, textPaint)
            canvas.rotate(-currentDegree, textX, textY)
        }
    }

    private fun drawRainbowCircle(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = width.coerceAtMost(height) / 2f
        for (i in colors.indices) {
            paint.color = colors[i]
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                startAngle + i * sweepAngle,
                sweepAngle,
                true,
                paint
            )
        }
    }

    private fun spinCircle() {
        winnerIndex = Random.nextInt(colors.indices)
        RotateAnimation(
            0f,
            -((360 * colors.size) + sectorDegrees[winnerIndex]),
            RotateAnimation.RELATIVE_TO_SELF,
            0.5f,
            RotateAnimation.RELATIVE_TO_SELF,
            0.5f
        ).apply {
            duration = 100
            fillAfter = true
            startAnimation(this)
            setAnimationListener(this@Circle)
        }
    }

    override fun onAnimationStart(p0: Animation?) = Unit

    override fun onAnimationEnd(p0: Animation?) {
        when (winnerIndex) {
            0, 2, 4, 6 -> {
                drawTextDegrees.add(winnerIndex)
            }
        }
        invalidate()
    }

    override fun onAnimationRepeat(p0: Animation?) = Unit

    fun reset() {
        drawTextDegrees.clear()
        invalidate()
    }

}





