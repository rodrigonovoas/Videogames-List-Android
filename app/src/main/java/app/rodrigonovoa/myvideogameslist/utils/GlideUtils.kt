package app.rodrigonovoa.myvideogameslist.utils

import android.content.Context
import android.widget.ImageView
import app.rodrigonovoa.myvideogameslist.R
import com.bumptech.glide.Glide

class GlideUtils(private val context: Context) {
    fun loadImage(url: String?, imv: ImageView){
        if(url != null){
            Glide.with(context)
                .load(url)
                .placeholder(R.drawable.image_placeholder)
                .into(imv);
        }
    }
}