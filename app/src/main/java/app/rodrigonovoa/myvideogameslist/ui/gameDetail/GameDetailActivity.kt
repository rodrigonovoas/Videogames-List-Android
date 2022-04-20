package app.rodrigonovoa.myvideogameslist.ui.gameDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import app.rodrigonovoa.myvideogameslist.R
import app.rodrigonovoa.myvideogameslist.databinding.ActivityGameDetailBinding
import app.rodrigonovoa.myvideogameslist.data.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.ui.addRecord.AddRecordActivity
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

class GameDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameDetailBinding
    private val model: GameDetailViewModel by inject()

    private var id: Number = 0
    private var fromRepo: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getIntExtra("id", 0)
        fromRepo = intent.getBooleanExtra("fromRepo", true)

        if(fromRepo){
            binding.btnAddRecord.visibility = View.VISIBLE
            model.getGameFromRepo(id.toInt())
        }else{
            binding.btnAddRecord.visibility = View.GONE
            model.getGameFromLocalDb(id.toInt())
        }

        this.model.retrievedGame.observe(this) { game ->
            if(game != null){
                setUpLayout(game)
                if(fromRepo){
                    setAddRecordClickListener(game)
                }
            }
        }
    }

    private fun setUpLayout(game: GameDetailResponse){
        binding.tvGameTitle.text = game.name
        binding.tvGameDescription.text = Html.fromHtml(game.description)

        val imageSrc = game.background_image_additional
        if(imageSrc != null && !imageSrc.isEmpty()){
            Glide.with(this)
                .load(imageSrc)
                .into(binding.imvGameImage);
        }else{
            binding.imvGameImage.setImageResource(R.drawable.image_placeholder)
        }
    }

    private fun setAddRecordClickListener(game: GameDetailResponse){
        binding.btnAddRecord.setOnClickListener {
            val intent = Intent(this, AddRecordActivity::class.java)
            intent.putExtra("EXTRA_GAME", game)
            startActivity(intent)
        }
    }
}