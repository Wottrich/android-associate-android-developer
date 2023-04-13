package wottrich.github.io.pomodorouniverse.designsystem

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import wottrich.github.io.pomodorouniverse.R
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.state.SectionTimeConfigType
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.state.TimeConfigurationCardUiModel

class TimerConfigurationCardView : ConstraintLayout {
    constructor(context: Context) : super(context) {
        setAttrs(null, null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setAttrs(attrs, null)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setAttrs(attrs, defStyleAttr)
    }

    private val view: View

    init {
        view = inflate(context, R.layout.view_timer_configuration_card, this)
    }

    private val timerComponentContainer = view.findViewById<ConstraintLayout>(R.id.timerComponentContainer)
    private val componentTitleTextView = view.findViewById<TextView>(R.id.componentTitle)
    private val componentContentResumeTextView = view.findViewById<TextView>(R.id.componentContentResume)
    private val leftOptionButtonCardView = view.findViewById<MaterialCardView>(R.id.leftOptionButton)
    private val leftButtonLabelTextView = view.findViewById<TextView>(R.id.leftButtonLabel)
    private val rightOptionButtonCardView = view.findViewById<MaterialCardView>(R.id.rightOptionButton)
    private val rightButtonLabelTextView = view.findViewById<TextView>(R.id.rightButtonLabel)
    private val disclaimerLabelTextView = view.findViewById<TextView>(R.id.disclaimerLabel)
    private val contentContainerConstraintLayout = view.findViewById<ConstraintLayout>(R.id.contentContainer)

    private var title: String = ""
    private var leftButtonLabel: String = ""
    private var rightButtonLabel: String = ""
    private var disclaimerLabel: String = ""
    private var isOpened: Boolean = false

    private fun setAttrs(attrs: AttributeSet?, defStyleAttr: Int?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TimerConfigurationCardView,
            defStyleAttr ?: 0,
            0
        ).apply {
            try {
                setupDefaultValues()
            } finally {
                recycle()
            }
        }
    }

    private fun TypedArray.setupDefaultValues() {
        title = getStringResFromResourceId(R.styleable.TimerConfigurationCardView_timerConfigurationTitle)
        leftButtonLabel = getStringResFromResourceId(R.styleable.TimerConfigurationCardView_timerConfigurationLeftOptionLabel)
        rightButtonLabel = getStringResFromResourceId(R.styleable.TimerConfigurationCardView_timerConfigurationRightOptionLabel)
        disclaimerLabel = getStringResFromResourceId(R.styleable.TimerConfigurationCardView_timerConfigurationDisclaimerLabel)
        isOpened = getBoolean(R.styleable.TimerConfigurationCardView_timerConfigurationOpened, false)
    }

    override fun onFinishInflate() {
        componentTitleTextView.text = title
        leftButtonLabelTextView.text = leftButtonLabel
        rightButtonLabelTextView.text = rightButtonLabel
        disclaimerLabelTextView.text = disclaimerLabel
        contentContainerConstraintLayout.isVisible = isOpened
        super.onFinishInflate()
    }

    private fun TypedArray.getStringResFromResourceId(attrsIndex: Int): String {
        val stringRes = getResourceId(attrsIndex, 0)
        return if (stringRes == 0) ""
        else context.resources.getString(stringRes)
    }

    fun setupCard(
        timeConfigurationCardUiModel: TimeConfigurationCardUiModel,
        onItemClick: (SectionTimeConfigType) -> Unit,
        onLeftButtonClick: () -> Unit,
        onRightButtonClick: () -> Unit
    ) {
        componentContentResumeTextView.text = context.resources.getString(
            timeConfigurationCardUiModel.timeFormat,
            timeConfigurationCardUiModel.timeResumed.toString()
        )
        contentContainerConstraintLayout.isVisible = timeConfigurationCardUiModel.isOpened
        timerComponentContainer.setOnClickListener {
            onItemClick(timeConfigurationCardUiModel.section)
        }
        leftOptionButtonCardView.setOnClickListener {
            onLeftButtonClick()
        }
        rightOptionButtonCardView.setOnClickListener {
            onRightButtonClick()
        }
    }
}
