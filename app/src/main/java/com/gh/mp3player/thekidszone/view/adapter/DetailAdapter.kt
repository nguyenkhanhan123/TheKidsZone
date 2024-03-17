package com.gh.mp3player.thekidszone.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.gh.mp3player.thekidszone.R
import com.gh.mp3player.thekidszone.model.GameModel

class DetailAdapter(val context: Context, val listGame: List<GameModel>) : PagerAdapter() {
    override fun getCount(): Int {
        return listGame.size
    }
    @SuppressLint("MissingInflatedId")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_detail, container, false)
        val data: GameModel = listGame[position]
        val ivPicture: ImageView = view.findViewById(R.id.iv_picture)
        val tvDescribe: TextView = view.findViewById(R.id.tv_describe)
        val btPlay: Button = view.findViewById(R.id.bt_play)
        val btLeft: ImageView = view.findViewById(R.id.bt_left)
        val btRight: ImageView = view.findViewById(R.id.bt_right)
        ivPicture.setImageResource(data.idPicture ?: R.drawable.bg_color)
        tvDescribe.text = ContextCompat.getString(
            context,
            data.describe ?: R.string.ai_la_trieu_phu_text
        )
        btPlay.setOnClickListener {
            val intent = Intent(context, Class.forName(data.tag))
            context.startActivity(intent)
        }
        btLeft.setOnClickListener {
            val newPosition = if (position - 1 < 0) (listGame.size - 1) else (position - 1)
            (container as ViewPager).currentItem = newPosition
        }
        btRight.setOnClickListener {
            val newPosition = if (position + 1 >= listGame.size) 0 else (position + 1)
            (container as ViewPager).currentItem = newPosition
        }
        container.addView(view)
        return view
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}