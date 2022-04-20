package app.rodrigonovoa.myvideogameslist.ui.addRecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.rodrigonovoa.myvideogameslist.R
import app.rodrigonovoa.myvideogameslist.databinding.ActivityAddRecordBinding
import app.rodrigonovoa.myvideogameslist.databinding.ActivityGameDetailBinding
import app.rodrigonovoa.myvideogameslist.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.model.localdb.GameRecord
import app.rodrigonovoa.myvideogameslist.room.GameDAO
import app.rodrigonovoa.myvideogameslist.room.GameRecordDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class AddRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecordBinding
    private var gameResponse: GameDetailResponse? = null

    private val gameDao: GameDAO by inject()
    private val gameRecordDao: GameRecordDAO by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameResponse = intent.getSerializableExtra("EXTRA_GAME") as? GameDetailResponse

        if(gameResponse != null){
            binding.tvGameTitle.text = gameResponse!!.name
        }

        binding.btnAddRecord.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val game = Game(null, gameResponse!!.name, gameResponse!!.description, 1000, gameResponse!!.metacritic, gameResponse!!.website, gameResponse!!.background_image_additional)
                val insertedGameId = gameDao.insert(game)

                val gameRecord = GameRecord(null, insertedGameId.toInt(), 1,1000,2000,binding.edtScore.text.toString().toInt(), binding.edtNotes.text.toString())
                val insertedRecordId = gameRecordDao.insert(gameRecord)

                if(insertedRecordId > 0){
                    runOnUiThread {
                        finish()
                    }
                }
            }
        }
    }
}