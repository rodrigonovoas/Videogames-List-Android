package app.rodrigonovoa.myvideogameslist.ui.gameDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.rodrigonovoa.myvideogameslist.R
import app.rodrigonovoa.myvideogameslist.databinding.ActivityGameDetailBinding
import app.rodrigonovoa.myvideogameslist.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.model.domain.GameResponse
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

class GameDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameDetailBinding
    private val model: GameDetailViewModel by inject()
    private lateinit var gameId: Number

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameId = intent.getIntExtra("gameid", 0)

        model.getGameFromRepo(gameId.toInt())

        this.model.retrievedGame.observe(this) { game ->
            if(game != null){
                setUpLayout(game)
            }
        }
    }

    private fun setUpLayout(game: GameDetailResponse){
        binding.tvGameTitle.text = game.name
        binding.tvGameDescription.text = game.description

        val imageSrc = game.background_image_additional
        if(imageSrc != null && !imageSrc.isEmpty()){
            Glide.with(this)
                .load(imageSrc)
                .into(binding.imvGameImage);
        }else{
            binding.imvGameImage.setImageResource(R.drawable.app_icon)
        }

    }
}