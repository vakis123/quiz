package com.example.quiz

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class HangmanView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 8f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private var wrongGuesses = 0

    fun setWrongGuesses(count: Int) {
        wrongGuesses = count
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        val centerX = width / 2

        // Draw base
        canvas.drawLine(50f, height - 50f, width - 50f, height - 50f, paint)



        when (wrongGuesses) {
            1 -> {
                // Draw vertical line
                canvas.drawLine(centerX, height - 50f, centerX, 50f, paint)

                // Draw top line
                canvas.drawLine(centerX, 50f, centerX + 100f, 50f, paint)

                // Draw rope
                canvas.drawLine(centerX + 100f, 50f, centerX + 100f, 100f, paint)
            }
            2 -> {
                // Draw vertical line
                canvas.drawLine(centerX, height - 50f, centerX, 50f, paint)

                // Draw top line
                canvas.drawLine(centerX, 50f, centerX + 100f, 50f, paint)

                // Draw rope
                canvas.drawLine(centerX + 100f, 50f, centerX + 100f, 100f, paint)
                // Draw head
                canvas.drawCircle(centerX + 100f, 130f, 30f, paint)
            }
            3 -> {
                // Draw vertical line
                canvas.drawLine(centerX, height - 50f, centerX, 50f, paint)

                // Draw top line
                canvas.drawLine(centerX, 50f, centerX + 100f, 50f, paint)

                // Draw rope
                canvas.drawLine(centerX + 100f, 50f, centerX + 100f, 100f, paint)
                // Draw head and body
                canvas.drawCircle(centerX + 100f, 130f, 30f, paint)
                canvas.drawLine(centerX + 100f, 160f, centerX + 100f, 250f, paint)
            }
            4 -> {
                // Draw vertical line
                canvas.drawLine(centerX, height - 50f, centerX, 50f, paint)

                // Draw top line
                canvas.drawLine(centerX, 50f, centerX + 100f, 50f, paint)

                // Draw rope
                canvas.drawLine(centerX + 100f, 50f, centerX + 100f, 100f, paint)
                // Draw head, body, and left arm
                canvas.drawCircle(centerX + 100f, 130f, 30f, paint)
                canvas.drawLine(centerX + 100f, 160f, centerX + 100f, 250f, paint)
                canvas.drawLine(centerX + 100f, 180f, centerX + 50f, 220f, paint)
            }
            5 -> {
                // Draw vertical line
                canvas.drawLine(centerX, height - 50f, centerX, 50f, paint)

                // Draw top line
                canvas.drawLine(centerX, 50f, centerX + 100f, 50f, paint)

                // Draw rope
                canvas.drawLine(centerX + 100f, 50f, centerX + 100f, 100f, paint)
                // Draw head, body, left arm, and right arm
                canvas.drawCircle(centerX + 100f, 130f, 30f, paint)
                canvas.drawLine(centerX + 100f, 160f, centerX + 100f, 250f, paint)
                canvas.drawLine(centerX + 100f, 180f, centerX + 50f, 220f, paint)
                canvas.drawLine(centerX + 100f, 180f, centerX + 150f, 220f, paint)
            }
            6 -> {
                // Draw vertical line
                canvas.drawLine(centerX, height - 50f, centerX, 50f, paint)

                // Draw top line
                canvas.drawLine(centerX, 50f, centerX + 100f, 50f, paint)

                // Draw rope
                canvas.drawLine(centerX + 100f, 50f, centerX + 100f, 100f, paint)
                // Draw head, body, left arm, right arm, and left leg
                canvas.drawCircle(centerX + 100f, 130f, 30f, paint)
                canvas.drawLine(centerX + 100f, 160f, centerX + 100f, 250f, paint)
                canvas.drawLine(centerX + 100f, 180f, centerX + 50f, 220f, paint)
                canvas.drawLine(centerX + 100f, 180f, centerX + 150f, 220f, paint)
                canvas.drawLine(centerX + 100f, 250f, centerX + 50f, 300f, paint)
            }
            7 -> {
                // Draw vertical line
                canvas.drawLine(centerX, height - 50f, centerX, 50f, paint)

                // Draw top line
                canvas.drawLine(centerX, 50f, centerX + 100f, 50f, paint)

                // Draw rope
                canvas.drawLine(centerX + 100f, 50f, centerX + 100f, 100f, paint)
                // Draw head, body, left arm, right arm, left leg, and right leg
                canvas.drawCircle(centerX + 100f, 130f, 30f, paint)
                canvas.drawLine(centerX + 100f, 160f, centerX + 100f, 250f, paint)
                canvas.drawLine(centerX + 100f, 180f, centerX + 50f, 220f, paint)
                canvas.drawLine(centerX + 100f, 180f, centerX + 150f, 220f, paint)
                canvas.drawLine(centerX + 100f, 250f, centerX + 50f, 300f, paint)
                canvas.drawLine(centerX + 100f, 250f, centerX + 150f, 300f, paint)
            }
        }
    }
} 