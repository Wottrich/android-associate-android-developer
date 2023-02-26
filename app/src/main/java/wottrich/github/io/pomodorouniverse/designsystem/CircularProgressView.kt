package wottrich.github.io.pomodorouniverse.designsystem

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class CircleProgressBar : View {
    private var rectF: RectF = RectF()
    private var backgroundPaint: Paint
    private var foregroundPaint: Paint

    private var _progress = 0f
    private var _backgroundProgressColor = Color.GRAY
    private var _foregroundProgressColor = Color.GREEN
    private var _maxValue = 100
    private var _strokeWidth = 10f

    var strokeWidth
        get() = _strokeWidth
        set(value) {
            _strokeWidth = value
            invalidate()
        }

    var progress
        get() = _progress
        set(value) {
            _progress = value
            invalidate()
        }

    var maxValue
        get() = _maxValue
        set(value) {
            _maxValue = value
            invalidate()
        }

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = _backgroundProgressColor
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = _strokeWidth * resources.displayMetrics.density
        }

        foregroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = _foregroundProgressColor
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = _strokeWidth * resources.displayMetrics.density
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val startAngle = -90
        val angle = 360 * progress / maxValue
        canvas.drawOval(rectF, backgroundPaint)
        canvas.drawArc(rectF, startAngle.toFloat(), angle, false, foregroundPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val min = min(width, height)
        setMeasuredDimension(min, min)
        rectF.set(
            0 + strokeWidth * 2,
            0 + strokeWidth * 2,
            min - strokeWidth * 2,
            min - strokeWidth * 2
        )
    }
}