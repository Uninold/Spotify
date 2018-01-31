package com.agura.com.spotify.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.agura.com.spotify.Adapters.SongAdapter
import com.agura.com.spotify.Interface.Key
import com.agura.com.spotify.R
import com.agura.com.spotify.Service.PlaySongService

/**
 * Created by SmartStart on 12/18/17.
 */
private val PAUSE = "pausesong"
private val RESUME = "resumesong"

class SongFragment : Fragment(){
    private var mTitle: TextView? = null
    private var mArtist: TextView? = null
    private var mPause: ImageView? = null
    private var mResume: ImageView? = null

    val TAG = "Fragment"
    companion object {

        fun newInstance(song: String, artist: String): SongFragment {

            val args = Bundle()
            args.putString(Key.SONG, song)
            args.putString(Key.ARTIST, artist)
            val fragment = SongFragment()
            fragment.arguments = args

            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater?.inflate(R.layout.fragment_part, container, false)

        mTitle = rootView!!.findViewById(R.id.songTitle) as TextView
        mArtist = rootView!!.findViewById(R.id.songSinger) as TextView
        mPause = rootView!!.findViewById(R.id.pause_icon) as ImageView
        mResume = rootView!!.findViewById(R.id.play_icon) as ImageView
        val song = arguments.getString(Key.SONG)
        val artist = arguments.getString(Key.ARTIST)

        if(song != null && artist != null){
            mTitle!!.text = song
            mArtist!!.text = artist
        }
        mPause!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                mPause!!.visibility = View.INVISIBLE
                mResume!!.visibility = View.VISIBLE
                var songIntent = Intent(context, PlaySongService::class.java).apply {
                    action = PAUSE
                }
                context.startService(songIntent)
            }
        })
        mResume!!.setOnClickListener(object :  View.OnClickListener{
            override fun onClick(p0: View?){
                mPause!!.visibility = View.VISIBLE
                mResume!!.visibility = View.INVISIBLE
                var songIntent = Intent(context, PlaySongService::class.java).apply {
                    action = RESUME
                }
                context.startService(songIntent)
            }
        })
        return rootView

    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}