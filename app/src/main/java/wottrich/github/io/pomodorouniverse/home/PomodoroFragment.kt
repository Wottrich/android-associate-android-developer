package wottrich.github.io.pomodorouniverse.home

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import wottrich.github.io.pomodorouniverse.databinding.FragmentPomodoroBinding
import java.util.*
import java.util.concurrent.TimeUnit

class PomodoroFragment : Fragment() {

    private var binding: FragmentPomodoroBinding? = null

    private var pomodoroTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPomodoroBinding.inflate(
            inflater,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding?.playPomodoroButton?.setOnClickListener {
            setupPomodoroTimer()
            binding?.playPomodoroButton?.text = "Pausar"
        }
    }

    private fun setupPomodoroTimer() {
        //CountDownTimer
        val currentTime = 3.6e+6.toLong()
        pomodoroTimer?.cancel()
        pomodoroTimer = object : CountDownTimer(currentTime, 10L) {
            override fun onTick(time: Long) {
                binding?.circularProgressIndicator?.progress = ((time * 100) / currentTime).toInt()
                binding?.pomodoroStatusTextView?.text = formatElapsedTime(time).toString()
            }

            override fun onFinish() {
                binding?.circularProgressIndicator?.progress = 100
                binding?.playPomodoroButton?.text = "Reomeçar"
                binding?.pomodoroStatusTextView?.text = "Finalizado"
            }
        }
        pomodoroTimer?.start()
    }

    fun formatElapsedTime(time: Long): CharSequence {
        return String.format(
            Locale.getDefault(), "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(time) % TimeUnit.DAYS.toHours(1),
            TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}