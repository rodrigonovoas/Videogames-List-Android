package app.rodrigonovoa.myvideogameslist.view.ui.splash

import android.animation.Animator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.rodrigonovoa.myvideogameslist.R
import app.rodrigonovoa.myvideogameslist.databinding.ActivitySplashBinding
import app.rodrigonovoa.myvideogameslist.persistance.sharedPreferences.Prefs
import app.rodrigonovoa.myvideogameslist.view.ui.menu.MenuActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Splash Activity to show information about the project,
 * ask username and retrieve data if necessary
 */

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val model: SplashViewModel by viewModel()
    private val prefs: Prefs by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val context = this@SplashActivity
        startAnimation(context)

        model.getUser()
    }

    private fun createUserDialog(context: Context){
        MaterialDialog(context).show {
            title(R.string.username_dialog_title)
            input(allowEmpty = false) { dialog, text ->
                model.insertUser(text.toString())
                prefs.username = text.toString()
                openMainMenu(this@SplashActivity)
            }
            positiveButton(R.string.username_dialog_button)

            // prevent dialog cancellation
            cancelable(false)
            cancelOnTouchOutside(false)
        }
    }

    private fun openMainMenu(context: Context){
        val intent = Intent(context, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startAnimation(context:Context){
        binding.lottieSplashAnim.setAnimation(R.raw.splash_anim)
        binding.lottieSplashAnim.playAnimation()

        binding.lottieSplashAnim.addAnimatorListener(object : Animator.AnimatorListener {
            var cont = 0
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                if(cont <2){
                    binding.lottieSplashAnim.playAnimation()
                    cont++
                }else if (cont == 2){
                    if(model.appUser.value == null){
                        createUserDialog(context)
                    }else{
                        openMainMenu(this@SplashActivity)
                    }
                }
            }
        })
    }
}