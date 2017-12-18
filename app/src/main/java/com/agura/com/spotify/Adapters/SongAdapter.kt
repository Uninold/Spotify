package com.agura.com.spotify.Adapters


import android.support.v7.widget.RecyclerView
import com.agura.com.spotify.Model.SongList
import com.agura.com.spotify.R
import android.widget.TextView
import org.w3c.dom.Text
import android.R.menu
import android.annotation.SuppressLint
import android.graphics.Color
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.agura.com.spotify.Fragment.SongFragment


/**
 * Created by SmartStart on 12/17/17.
 */
class SongAdapter(val songList: ArrayList<SongList>): RecyclerView.Adapter<SongAdapter.ViewHolder>(){

    companion object {
        val KEY_RECIPE = "111"
    }
    var isClicked = true

    override fun getItemCount(): Int {
        return songList.size
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mLinear = itemView.findViewById<LinearLayout>(R.id.linearLayout) as LinearLayout
        val txtSongTitle = itemView.findViewById<TextView>(R.id.txtsong_title) as TextView
        val txtSongArtist = itemView.findViewById<TextView>(R.id.txtsong_artist) as TextView
        val txtSongAlbum = itemView.findViewById<TextView>(R.id.txtsong_album) as TextView


    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val song: SongList = songList[position]
        holder?.txtSongTitle?.text = song.song_title
        holder?.txtSongArtist?.text = song.song_artist
        holder?.txtSongAlbum?.text = song.song_album
        holder?.mLinear?.setOnClickListener(object: View.OnClickListener{
            @SuppressLint("ResourceAsColor")
            override fun onClick(v: View) {
                if (!song.stat) {
                    holder?.txtSongTitle.setTextColor(Color.parseColor("#1DB954"));
                    song.stat!=true
                }
                if(song.stat){
                    holder?.txtSongTitle.setTextColor(Color.parseColor("#fffff"));
                    song.stat!=false
                }
            }
            })


    }



}