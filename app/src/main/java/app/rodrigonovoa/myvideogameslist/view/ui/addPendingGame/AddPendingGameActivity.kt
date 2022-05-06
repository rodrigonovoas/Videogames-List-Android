package app.rodrigonovoa.myvideogameslist.view.ui.addPendingGame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import app.rodrigonovoa.myvideogameslist.R
import app.rodrigonovoa.myvideogameslist.databinding.ActivityAddPendingGameBinding
import app.rodrigonovoa.myvideogameslist.databinding.ActivityGameDetailBinding
import app.rodrigonovoa.myvideogameslist.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.model.localdb.PendingGame
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPendingGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPendingGameBinding
    private var gameResponse: GameDetailResponse? = null
    private val model: AddPendingGameViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPendingGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameResponse = intent.getSerializableExtra("EXTRA_GAME") as? GameDetailResponse

        if(gameResponse != null){
            binding.tvGameTitle.text = gameResponse!!.name
        }

        model.recordInserted.observe(this) { it ->
            if(it == true){
                finish()
            }
        }

        binding.btnSave.setOnClickListener {
            if(gameResponse != null){
                insertPendingGame()
            }
        }

        fillSpinner()
    }

    private fun insertPendingGame(){
        val notes = binding.edtNotes.text?.toString() ?: ""
        val state = binding.spState.selectedItem.toString()
        model.insertGameInDb(gameResponse!!, notes, state)
    }

    private fun fillSpinner(){
        val options = listOf<String>(getString(R.string.add_pending_spinner_state_1), getString(R.string.add_pending_spinner_state_2))
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, options
        )

        binding.spState.adapter = adapter
    }
}