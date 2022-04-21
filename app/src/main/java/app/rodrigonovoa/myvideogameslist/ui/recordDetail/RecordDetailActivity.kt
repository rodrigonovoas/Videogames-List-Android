package app.rodrigonovoa.myvideogameslist.ui.recordDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateUtils
import app.rodrigonovoa.myvideogameslist.R
import app.rodrigonovoa.myvideogameslist.data.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.data.model.localdb.GameRecord
import app.rodrigonovoa.myvideogameslist.databinding.ActivityGameDetailBinding
import app.rodrigonovoa.myvideogameslist.databinding.ActivityRecordDetailBinding
import org.koin.android.ext.android.inject

class RecordDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordDetailBinding

    private val model: RecordDetailViewModel by inject()
    private val dateUtils: app.rodrigonovoa.myvideogameslist.utils.DateUtils by inject()
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

        model.getGameFromLocalDb(id as Int)
    }

    private fun setUpLayout(record: GameRecord){
        binding.tvFromContent.setText(dateUtils.fromTimeStampToDateString(record.initdate))
        binding.tvToContent.setText(dateUtils.fromTimeStampToDateString(record.enddate))
        binding.tvScoreContent.setText(record.score.toString())
        binding.tvNotesContent.setText(record.notes)
    }
}