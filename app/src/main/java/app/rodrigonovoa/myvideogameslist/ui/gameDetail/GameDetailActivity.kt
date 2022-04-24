package app.rodrigonovoa.myvideogameslist.ui.gameDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
            model.checkIfGameRecordExists(id.toInt())
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

        this.model.disableAddButton.observe(this) { disable ->
            if(disable == true){
                binding.btnAddRecord.isEnabled = false
                binding.btnAddRecord.text = getString(R.string.common_added)
            }
        }
    }

    private fun setUpLayout(game: GameDetailResponse){
        binding.tvGameTitle.text = game.name
        binding.tvGameDescription.text = Html.fromHtml(game.description)

        val imageSrc = game.background_image_additional
        setImageOnLayout(binding.imvGameImage, imageSrc)
        setInfoOnLayout(game, binding.tvGameInfoContent)
        setExtraOnLayout(game, binding.tvGameExtraContent)
    }

    private fun setInfoOnLayout(game: GameDetailResponse, tv_info: TextView){
        var content: String = ""

        content = getString(R.string.game_detail_info_release) + game.released

        if(game.publishers != null){
            content += "\n" + getString(R.string.game_detail_info_publishers)
            game.publishers.forEach {
                content += it.name + ","
            }

            content = content.dropLast(1)
        }

        if (game.platforms != null) {
            content += "\n" + getString(R.string.game_detail_info_platforms)
            game.platforms.forEach {
                if (it.platform != null) {
                    content += it.platform.name + ","
                }
            }
            content = content.dropLast(1)
        }

        if(game.esrb_rating != null) content += "\n" + getString(R.string.game_detail_info_rate) + game.esrb_rating.name

        tv_info.setText(content)
    }

    private fun setExtraOnLayout(game: GameDetailResponse, tv_extra: TextView){
        var content = ""

        content += getString(R.string.common_list_metacritic) + " " + game.metacritic
        content += "\n" + game.website

        tv_extra.setText(content)
    }

    private fun setImageOnLayout(imv: ImageView, src: String){
        if(src != null && !src.isEmpty()){
            Glide.with(this)
                .load(src)
                .into(imv);
        }else{
            imv.setImageResource(R.drawable.image_placeholder)
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