package app.rodrigonovoa.myvideogameslist.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app.rodrigonovoa.myvideogameslist.R
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input

/**
 * Splash Activity to show information about the project,
 * ask username and retrieve data if necessary
 */

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // dialog to ask for username
        MaterialDialog(this).show {
            title(R.string.username_dialog_title)
            input(allowEmpty = false) { dialog, text ->
               Toast.makeText(context,"Prueba",Toast.LENGTH_SHORT).show()
            }
            positiveButton(R.string.username_dialog_button)

            // prevent cancellation
            cancelable(false)
            cancelOnTouchOutside(false)
        }
    }
}