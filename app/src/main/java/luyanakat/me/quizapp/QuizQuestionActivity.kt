package luyanakat.me.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import luyanakat.me.quizapp.databinding.ActivityQuizQuestionBinding

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityQuizQuestionBinding

    private var tvQuestion: TextView? = null
    private var imageQuestion: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null

    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null
    private var btnSubmit: Button? = null

    private var questionList: ArrayList<Question>? = null
    private var currentPosition = 0
    private var selectedOptionPosition = 0
    private var userName: String? = null
    private var countCorrectAnswers: Int = 0
    private var countTotalQuestion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra(Constants.USER_NAME)

        tvQuestion = binding.tvQuestion
        imageQuestion = binding.imageQuestion
        progressBar = binding.progressBar
        tvProgress = binding.tvProgressQuestion

        tvOptionOne = binding.tvOptionOne
        tvOptionTwo = binding.tvOptionTwo
        tvOptionThree = binding.tvOptionThree
        tvOptionFour = binding.tvOptionFour

        btnSubmit = binding.btnSubmit

        questionList = Constants.getQuestion()
        setQuestion()

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)

    }

    private fun setQuestion() {
        defaultOptionView()
        var question = questionList!![currentPosition]
        tvQuestion?.text = question.question
        imageQuestion?.setImageResource(question.image)
        progressBar?.progress = currentPosition + 1
        tvProgress?.text = "${currentPosition+1}/10"

        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour

        if (currentPosition == questionList!!.size) {
            btnSubmit?.text = "Finish"
        } else {
            btnSubmit?.text = "Submit"
        }
    }



    private fun defaultOptionView() {
        val options = ArrayList<TextView>()
        tvOptionOne?.let {
            options.add(0, it)
        }
        tvOptionTwo?.let {
            options.add(1, it)
        }
        tvOptionThree?.let {
            options.add(2, it)
        }
        tvOptionFour?.let {
            options.add(3, it)
        }
        for (i in options) {
            i.setTextColor(Color.parseColor("#7A8089"))
            i.typeface = Typeface.DEFAULT
            i.background = ContextCompat.getDrawable(this,
                R.drawable.default_option_border_bg)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNumber: Int) {
        defaultOptionView()
        selectedOptionPosition = selectedOptionNumber
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,
            R.drawable.selected_option_border_bg)
    }

    override fun onClick(view: View) {
        when (view) {
            binding.tvOptionOne -> {
                tvOptionOne?.let {
                    selectedOptionView(it, 1)
                }
            }
            binding.tvOptionTwo -> {
                tvOptionTwo?.let {
                    selectedOptionView(it, 2)
                }
            }
            binding.tvOptionThree -> {
                tvOptionThree?.let {
                    selectedOptionView(it, 3)
                }
            }
            binding.tvOptionFour -> {
                tvOptionFour?.let {
                    selectedOptionView(it, 4)
                }
            }
            binding.btnSubmit -> {
                if (selectedOptionPosition == 0) {
                    currentPosition++
                when {
                    currentPosition + 1 <= questionList!!.size -> {
                        setQuestion()
                    }
                    else -> {
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra(Constants.USER_NAME, userName)
                        intent.putExtra(Constants.TOTAL_QUESTIONS, questionList!!.size)
                        intent.putExtra(Constants.CORRECT_ANSWERS, countCorrectAnswers)
                        startActivity(intent)
                        finish()
                    }
                }
                }else {
                    val question = questionList?.get(currentPosition)
                    if (question!!.correctAnswer != selectedOptionPosition) {
                        answerView(selectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        countCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)
                    if (currentPosition + 1 == questionList!!.size) {
                        btnSubmit?.text = "Finish"
                    } else {
                        btnSubmit?.text = "Next Question"
                    }
                    selectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                tvOptionOne?.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 -> {
                tvOptionTwo?.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                tvOptionThree?.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> {
                tvOptionFour?.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

}