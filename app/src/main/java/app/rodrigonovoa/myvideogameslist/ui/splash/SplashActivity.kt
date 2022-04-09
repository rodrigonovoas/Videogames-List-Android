package app.rodrigonovoa.myvideogameslist.ui.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import app.rodrigonovoa.myvideogameslist.R
import app.rodrigonovoa.myvideogameslist.databinding.ActivitySplashBinding
import app.rodrigonovoa.myvideogameslist.ui.menu.MenuActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input

/**
 * Splash Activity to show information about the project,
 * ask username and retrieve data if necessary
 */

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val TIME_SPLASH_SCREEN = 2500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val context = this@SplashActivity
        // dialog to store username
        startAnimation(context)
        setTimerAndExit(context)
    }

    private fun createUserDialog(context: Context){
        MaterialDialog(context).show {
            title(R.string.username_dialog_title)
            input(allowEmpty = false) { dialog, text ->
                // TODO: store username

                // store and move to the main activity
                openMainMenu(this@SplashActivity)
            }
            positiveButton(R.string.username_dialog_button)

            // prevent dialog cancellation
            cancelable(false)
            cancelOnTouchOutside(false)
        }
    }
    private fun setTimerAndExit(context: Context) {
        val handler = Handler()
        handler.postDelayed({
            // TODO: check if user is stored; then move to the main activity
            createUserDialog(context)
            //openMainMenu(context)
            //this.finish()
        }, TIME_SPLASH_SCREEN)
    }

    private fun openMainMenu(context: Context){
        val intent = Intent(context, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startAnimation(context: Context){
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.root.startAnimation(animation)
    }
}