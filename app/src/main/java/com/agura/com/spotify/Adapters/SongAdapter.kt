package com.agura.com.spotify.Adapters


import android.support.v7.widget.RecyclerView
import com.agura.com.spotify.Interface.SongList
import com.agura.com.spotify.R
import android.app.Fragment
import android.content.Context
import android.app.FragmentManager
import android.graphics.Color
import android.view.*
import android.widget.*
import com.agura.com.spotify.Controller.MainActivity
import com.agura.com.spotify.Fragment.SongFragment
import com.agura.com.spotify.Service.PlaySongService
import android.content.Intent

/**
 * Created by SmartStart on 12/17/17.
 */
class SongAdapter(val songList: ArrayList<SongList>,val context: Context, val ma:MainActivity): RecyclerView.Adapter<SongAdapter.ViewHolder>(){

    var mRecyclerView: RecyclerView? = null
    var currentSong:Int = 0
    var view:View?  = null
    var mContext = context
    val allSongs:ArrayList<String>  =  ArrayList()
    companion object {
        val SONGLIST = "songlist"
        val SONGPOS = "songpos"
        val SONGPLAY = "songplay"
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

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val song: SongList = songList[position]
        holder?.txtSongTitle?.text = song.song_title
        holder?.txtSongArtist?.text = song.song_artist
        holder?.txtSongAlbum?.text = song.song_album
        if(song.stat==1)
        {
        }

        holder?.mLinear?.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
//             ma.setSelectedSong(position)

                currentSong = position

                holder?.txtSongTitle?.setTextColor(Color.parseColor("#1DB954"));
                for (i in 0 until songList.size) {
                    allSongs.add(songList[i].song_path)
                }

                try {
                    val fragment = SongFragment.newInstance(song.song_title, song.song_artist)
                    ma.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_play, fragment)
                            .commit()
                } catch (e: Exception) {
                    Toast.makeText(ma, "Error!", Toast.LENGTH_SHORT)
                }

                var songIntent = Intent(mContext, PlaySongService::class.java)
                songIntent.putStringArrayListExtra(SONGLIST, allSongs)
                songIntent.setAction(SONGPLAY)
                songIntent.putExtra(SONGPOS, position)
                mContext.startService(songIntent)
            }

        })

    }
    fun addFragmentToActivity(manager: FragmentManager, fragment: Fragment, frameId: Int) {

        val transaction = manager.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.commit()

    }

}