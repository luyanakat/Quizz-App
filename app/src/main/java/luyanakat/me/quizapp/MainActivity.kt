package luyanakat.me.quizapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import luyanakat.me.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textInput = binding.editTextInput
        val btnStart = binding.btnStart

        btnStart.setOnClickListener {
            if (textInput.text.toString() == "") {
                Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, QuizQuestionActivity::class.java)
                intent.putExtra(Constants.USER_NAME, textInput.text.toString())
                startActivity(intent)
                finish()
            }
        }
        textInput.setOnKeyListener {
                view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }

    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}