package app.rodrigonovoa.myvideogameslist.ui.addRecord

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.EditText
import app.rodrigonovoa.myvideogameslist.databinding.ActivityAddRecordBinding
import app.rodrigonovoa.myvideogameslist.data.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.data.model.localdb.Game
import app.rodrigonovoa.myvideogameslist.data.model.localdb.GameRecord
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import app.rodrigonovoa.myvideogameslist.utils.DateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class AddRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecordBinding
    private var gameResponse: GameDetailResponse? = null

    private val model: AddRecordViewModel by viewModel()

    private var fromCalendar = Calendar.getInstance()
    private var toCalendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameResponse = intent.getSerializableExtra("EXTRA_GAME") as? GameDetailResponse

        if(gameResponse != null){
            binding.tvGameTitle.text = gameResponse!!.name
        }

        // CALENDAR LISTENERS
        val fromDateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                fromCalendar.set(Calendar.YEAR, year)
                fromCalendar.set(Calendar.MONTH, monthOfYear)
                fromCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
        }

        val toDateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                toCalendar.set(Calendar.YEAR, year)
                toCalendar.set(Calendar.MONTH, monthOfYear)
                toCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
        }

        // OBSERVERS
        this.model.recordInserted.observe(this) { inserted ->
            if(inserted == true){
                finish()
            }
        }

        // VIEW LISTENERS
        binding.btnAddRecord.setOnClickListener {
            model.insertRecord(gameResponse!!, fromCalendar, toCalendar,
                binding.edtScore.text.toString().toInt(), binding.edtNotes.text.toString())
        }

        binding.edtInitDate.setOnClickListener { openDatePicker(fromDateSetListener, fromCalendar, binding.edtInitDate) }
        binding.edtFinishDate.setOnClickListener { openDatePicker(toDateSetListener, toCalendar, binding.edtFinishDate) }
    }

    private fun openDatePicker(listener: DatePickerDialog.OnDateSetListener, cal: Calendar, edt:EditText) {
        DatePickerDialog(this@AddRecordActivity,
            listener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()

        setDateInEditext(cal, edt)
    }

    private fun setDateInEditext(cal: Calendar, edt: EditText) {
        val dateFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(dateFormat, Locale.FRANCE)
        edt.setText(sdf.format(cal.getTime()))
    }
}