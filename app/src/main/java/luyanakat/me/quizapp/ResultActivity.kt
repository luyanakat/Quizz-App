package luyanakat.me.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import luyanakat.me.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var userName: String? = null
    private var countCorrectAnswer: Int = 0
    private var countTotalQuestion: Int = 0

    private var tvUserName: TextView? = null
    private var tvShowScore: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra(Constants.USER_NAME)
        countCorrectAnswer = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        countTotalQuestion = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)

        tvUserName = binding.tvShowUsername
        tvShowScore = binding.tvShowScore

        tvUserName?.text = userName
        tvShowScore?.text = "Total score is: ${countCorrectAnswer}/${countTotalQuestion}"
    }
}