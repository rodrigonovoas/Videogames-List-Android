package app.rodrigonovoa.myvideogameslist.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import app.rodrigonovoa.myvideogameslist.R
import app.rodrigonovoa.myvideogameslist.model.domain.GameResponse
import app.rodrigonovoa.myvideogameslist.ui.gameDetail.GameDetailActivity
import com.bumptech.glide.Glide

class CommonListAdapter(private val list: List<GameResponse>, private val listFromRepo: Boolean = true) :
    RecyclerView.Adapter<CommonListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardViewGame: CardView
        val tvGameTitle: TextView
        val tvGameRelaseDate: TextView
        val tvGameMetacritic: TextView
        val imvGameImage: ImageView

        init {
            tvGameTitle = view.findViewById(R.id.tv_game_title)
            tvGameRelaseDate = view.findViewById(R.id.tv_game_release_date)
            tvGameMetacritic = view.findViewById(R.id.tv_game_metacritic)
            imvGameImage = view.findViewById(R.id.imv_game)
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
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tvGameTitle.text = list[position].name
        viewHolder.tvGameRelaseDate.text = list[position].released
        viewHolder.tvGameMetacritic.text = "Metacritic: " + list[position].metacritic.toString()

        val imageSrc = list[position].background_image
        if(imageSrc != null && !imageSrc.isEmpty()){
            Glide.with(context)
                .load(imageSrc)
                .into(viewHolder.imvGameImage);
        }else{
            viewHolder.imvGameImage.setImageResource(R.drawable.app_icon)
        }

        viewHolder.cardViewGame.setOnClickListener {
            val intent = Intent(context,GameDetailActivity::class.java)
            intent.putExtra("id",list[position].id)
            intent.putExtra("fromRepo",listFromRepo)
            context.startActivity(intent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = list.size

}