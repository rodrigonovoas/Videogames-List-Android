package app.rodrigonovoa.myvideogameslist.view.ui.addRecord

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import app.rodrigonovoa.myvideogameslist.R
import app.rodrigonovoa.myvideogameslist.databinding.ActivityAddRecordBinding
import app.rodrigonovoa.myvideogameslist.model.domain.GameDetailResponse
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class AddRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecordBinding
    private var gameResponse: GameDetailResponse? = null

    private val model: AddRecordViewModel by viewModel()

    private var fromCalendar: Calendar? = null
    private var toCalendar: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameResponse = intent.getSerializableExtra("EXTRA_GAME") as? GameDetailResponse

        if(gameResponse != null){
            binding.tvGameTitle.text = gameResponse!!.name
        }

        // OBSERVERS
        this.model.recordInserted.observe(this) { inserted ->
            if(inserted == true){
                finish()
            }
        }

        // CALENDAR LISTENERS
        val fromDateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {

                if(fromCalendar==null) fromCalendar = Calendar.getInstance()

                fromCalendar?.set(Calendar.YEAR, year)
                fromCalendar?.set(Calendar.MONTH, monthOfYear)
                fromCalendar?.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                fromCalendar?.let { setDateInEditext(it, binding.edtInitDate) }
            }
        }

        val toDateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                if(toCalendar==null) toCalendar = Calendar.getInstance()

                toCalendar?.set(Calendar.YEAR, year)
                toCalendar?.set(Calendar.MONTH, monthOfYear)
                toCalendar?.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                toCalendar?.let { setDateInEditext(it, binding.edtFinishDate) }
            }
        }

        // VIEW LISTENERS
        binding.btnAddRecord.setOnClickListener {
            if(checkIfRecordCanBeAdded()){
                val score =  if(binding.edtScore.text.toString() == "") "0" else binding.edtScore.text.toString()
                val notes = binding.edtNotes.text?.toString() ?: ""

                model.insertRecord(gameResponse!!, fromCalendar!!, toCalendar!!, score.toInt(), notes)
            }
        }

        binding.edtInitDate.setOnClickListener {
            openDatePicker(fromDateSetListener,fromCalendar)
        }

        binding.edtFinishDate.setOnClickListener {
            openDatePicker(toDateSetListener, toCalendar)
        }

        binding.edtScore.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Timber.i("after: " + s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Timber.i("before: " + s.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.isNullOrEmpty()){
                    val num = s.toString().toInt()

                    if(num > 10){
                        binding.edtScore.setText("")
                    }
                }
            }
        })
    }

    private fun openDatePicker(listener: DatePickerDialog.OnDateSetListener, cal: Calendar?) {
        val calendar = cal ?: Calendar.getInstance()

        DatePickerDialog(this@AddRecordActivity,
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun setDateInEditext(cal: Calendar, edt: EditText) {
        val dateFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(dateFormat, Locale.FRANCE)
        edt.setText(sdf.format(cal.getTime()))
    }

    private fun checkIfRecordCanBeAdded(): Boolean{
        if(fromCalendar == null){
            createToast(getString(R.string.add_record_from_date_error))
            return false
        }

        if(toCalendar == null){
            createToast(getString(R.string.add_record_to_date_error))
            return false
        }

        return true
    }

    private fun createToast(text: String){
        Toast.makeText(this@AddRecordActivity, text, Toast.LENGTH_SHORT).show()
    }
}