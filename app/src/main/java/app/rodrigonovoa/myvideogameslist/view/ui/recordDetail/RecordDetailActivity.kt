package app.rodrigonovoa.myvideogameslist.view.ui.recordDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.rodrigonovoa.myvideogameslist.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.model.localdb.GameRecord
import app.rodrigonovoa.myvideogameslist.databinding.ActivityRecordDetailBinding
import app.rodrigonovoa.myvideogameslist.utils.GlideUtils
import org.koin.android.ext.android.inject

class RecordDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordDetailBinding

    private val model: RecordDetailViewModel by inject()
    private val glidUtils: GlideUtils by inject()

    private val dateFormatterUtil: app.rodrigonovoa.myvideogameslist.utils.DateFormatterUtil by inject()
    private var id: Number = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getIntExtra("id", 0)

        this.model.retrievedRecord.observe(this) { record ->
            if(record != null){
                setUpLayout(record)
            }
        }

        this.model.retrievedGame.observe(this) { game ->
            if(game != null){
                loadGameData(game)
            }
        }

        model.getRecordFromLocalDb(id as Int)
    }

    private fun setUpLayout(record: GameRecord){
        binding.tvFromContent.setText(dateFormatterUtil.fromTimeStampToDateString(record.initdate))
        binding.tvToContent.setText(dateFormatterUtil.fromTimeStampToDateString(record.enddate))
        binding.tvScoreContent.setText(record.score.toString())
        binding.tvNotesContent.setText(record.notes)
    }

    private fun loadGameData(game: Game){
        glidUtils.loadImage(game.image, binding.imvGameImage)
        binding.tvGameTitle.setText(game.name)
    }
}