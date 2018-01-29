package com.agura.com.spotify.Service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.agura.com.spotify.Adapters.SongAdapter

/**
 * Created by SmartStart on 1/28/18.
 */
class PlaySongService: Service() {
    var currentPosition:Int = 0
    var songDataList:ArrayList<String> = ArrayList()
    var mp: MediaPlayer?=null
    var position = 0
    override fun onBind(p0: Intent?): IBinder ? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        currentPosition = intent!!.getIntExtra(SongAdapter.SONGPOS,0)
        songDataList = intent.getStringArrayListExtra(SongAdapter.SONGLIST)

        if(mp!=null)
        {
            mp!!.stop()
            mp!!.release()
            mp = null
        }
        mp = MediaPlayer()
        mp!!.setDataSource(songDataList[currentPosition])
        mp!!.prepare()
        mp!!.start()


        return super.onStartCommand(intent, flags, startId)
    }
    fun Play(){

    }
    fun Pause(){
        mp = MediaPlayer()
        if (mp!!.isPlaying()) {
            position = mp!!.getCurrentPosition()
            mp!!.pause()
        }
//        if (mp!!.isPlaying() == false) {
//            mp!!.seekTo (position)
//            mp!!.start()
//        }
    }

}