package app.rodrigonovoa.myvideogameslist.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import app.rodrigonovoa.myvideogameslist.Constants
import app.rodrigonovoa.myvideogameslist.R
import app.rodrigonovoa.myvideogameslist.model.domain.GameDetailResponse
import app.rodrigonovoa.myvideogameslist.model.domain.GameListItemResponse
import app.rodrigonovoa.myvideogameslist.model.domain.GenreDetailResponse
import app.rodrigonovoa.myvideogameslist.repository.GamesListRepository
import app.rodrigonovoa.myvideogameslist.view.ui.gameDetail.GameDetailActivity
import app.rodrigonovoa.myvideogameslist.view.ui.recordDetail.RecordDetailActivity
import app.rodrigonovoa.myvideogameslist.utils.GlideUtils
import app.rodrigonovoa.myvideogameslist.view.ui.commonFragments.CommonListViewModel
import com.afollestad.materialdialogs.MaterialDialog

class CommonListAdapter(
    private val list: List<GameListItemResponse>, private val listType: String,
    private val completedDates: List<String> = listOf(),
    private val glideUtils: GlideUtils
) :
    RecyclerView.Adapter<CommonListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardViewGame: CardView
        val tvGameTitle: TextView
        val tvGameExtraText: TextView
        val tvGameRelaseDate: TextView
        val tvGameMetacritic: TextView
        val tvGameGenres: TextView
        val tvGamePlatforms: TextView
        val imvGameImage: ImageView
        val llGameMetacritic: LinearLayout

        init {
            tvGameTitle = view.findViewById(R.id.tv_game_title)
            tvGameExtraText = view.findViewById(R.id.tv_aux_text)
            tvGameRelaseDate = view.findViewById(R.id.tv_game_release_date)
            tvGameMetacritic = view.findViewById(R.id.tv_game_metacritic)
            tvGameGenres = view.findViewById(R.id.tv_game_genres)
            tvGamePlatforms = view.findViewById(R.id.tv_game_platforms)
            imvGameImage = view.findViewById(R.id.imv_game)
            llGameMetacritic = view.findViewById(R.id.ll_game_metacritic)
            cardViewGame = view.findViewById(R.id.card_view_game)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_common_list, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.imvGameImage.context
        val game = list[position]
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tvGameTitle.text = game.name

        if (listType.equals(Constants.GAMES_TYPE)) {
            setGamesTypeView(viewHolder, game)
        } else if (listType.equals(Constants.RECORDS_TYPE)) {
            setRecordsTypeView(viewHolder, position)
        } else if (listType.equals(Constants.PENDING_TYPE)) {
            setPendingTypeView(viewHolder, position)
        }

        val imageSrc = game.background_image ?: ""
        glideUtils.loadImage(imageSrc, viewHolder.imvGameImage)

        viewHolder.cardViewGame.setOnClickListener {
            if(listType.equals(Constants.GAMES_TYPE)){
                openGameDetailActivity(context, position)
            }else if(listType.equals(Constants.RECORDS_TYPE)){
                openRecordAndGameDetailDialog(context, position)
            }
        }
    }

    private fun setPendingTypeView(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvGameExtraText.text = list[position].released
        viewHolder.tvGameExtraText.visibility = View.VISIBLE
        viewHolder.tvGameRelaseDate.visibility = View.GONE
        viewHolder.llGameMetacritic.visibility = View.GONE
        viewHolder.tvGamePlatforms.visibility = View.GONE
        viewHolder.tvGameGenres.visibility = View.GONE
    }

    private fun setRecordsTypeView(viewHolder: ViewHolder, position: Int) {
        if(!completedDates.isEmpty()){
            viewHolder.tvGameRelaseDate.text =
                viewHolder.tvGameRelaseDate.context.getString(R.string.common_list_complete_date) + " " + completedDates[position]
        }else{
            viewHolder.tvGameRelaseDate.visibility = View.GONE
        }
        viewHolder.llGameMetacritic.visibility = View.GONE
        viewHolder.tvGamePlatforms.visibility = View.GONE
        viewHolder.tvGameGenres.visibility = View.GONE
    }

    private fun setGamesTypeView(viewHolder: ViewHolder, game: GameListItemResponse) {
        viewHolder.tvGameRelaseDate.text = game.released
        viewHolder.tvGameMetacritic.text = game.metacritic.toString()

        val platforms = game.platforms ?: listOf()

        if (platforms.size == 0) {
            viewHolder.tvGamePlatforms.visibility = View.GONE
        }else{
            setPlatforms(game, viewHolder.tvGamePlatforms)
        }

        val genres = game.genres
        setGenres(genres, viewHolder.tvGameGenres)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = list.size

    private fun openGameDetailActivity(context: Context, position: Int){
        val listFromRepo = listType.equals(Constants.GAMES_TYPE)
        val intent = Intent(context,GameDetailActivity::class.java)
        intent.putExtra("id",list[position].id)
        intent.putExtra("fromRepo",listFromRepo)
        context.startActivity(intent)
    }

    private fun openRecordActivity(context: Context, position: Int){
        val intent = Intent(context,RecordDetailActivity::class.java)
        intent.putExtra("id",list[position].id)
        context.startActivity(intent)
    }

    private fun setPlatforms(game: GameListItemResponse, tvPlatforms: TextView){
        val platforms = game.platforms ?: listOf()

        platforms.forEach {
            if(it.platform != null){
                if(tvPlatforms.text.isEmpty()){
                    tvPlatforms.text = it.platform.name
                }else{
                    tvPlatforms.text = tvPlatforms.text.toString() + " | " + it.platform.name
                }
            }
        }
    }

    private fun setGenres(genres: List<GenreDetailResponse>?, tvGenres: TextView){
        if(genres != null){
            var cont = 0
            genres.forEach {
                if(cont == 0){
                    tvGenres.text = it.name
                }else{
                    tvGenres.text = tvGenres.text.toString() + ", " + it.name
                }

                cont++
            }
        }
    }

    private fun openRecordAndGameDetailDialog(context: Context, position: Int){
        MaterialDialog(context).show {
            title(R.string.common_list_dialog_title)
            positiveButton(R.string.common_list_dialog_option_2){
                openRecordActivity(context, position)
            }
            negativeButton(R.string.common_list_dialog_option_1){
                openGameDetailActivity(context, position)
            }
        }
    }
}