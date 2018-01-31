package com.agura.com.spotify.Controller

import android.annotation.SuppressLint

import android.content.pm.PackageManager
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import com.agura.com.spotify.Adapters.SongAdapter
import com.agura.com.spotify.Interface.SongList
import com.agura.com.spotify.R
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.widget.BottomNavigationView
import android.database.Cursor
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

class MainActivity : AppCompatActivity(){
    private var mRecyclerView: RecyclerView? = null
    private var mNestedScrollView: NestedScrollView? = null
    private var currentsong = -1;
    private var songList = ArrayList<SongList>()
    companion object {
        val PERMISSION_REQUEST_CODE  = 111
    }


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCollapsingToolbar()
        findView()

        if(ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        }
        else
        {
            loadSongs()
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        BottomNavigationHelper.disableShiftMode(bottomNavigationView)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    public fun setSelectedSong(pos: Int){
        for(item in songList){
            item.stat = 0
        }
        songList.get(pos).stat = 1

        val adapter = SongAdapter(songList,applicationContext,this)

        mRecyclerView!!.adapter = adapter


    }

    private fun initCollapsingToolbar() {

        val collapsingToolbar = findViewById<View>(R.id.collapsing) as CollapsingToolbarLayout
        collapsingToolbar.title = " "
        val appBarLayout = findViewById<View>(R.id.appbarlayout) as AppBarLayout
        appBarLayout.setExpanded(true)

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = false
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = "Top Hits Philippines"
                    collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    collapsingToolbar.setCollapsedTitleTextColor(Color.TRANSPARENT)
                    isShow = false
                }
            }
        })
    }

    fun findView() {
        mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        mNestedScrollView = findViewById<NestedScrollView>(R.id.nestedScrollView)
    }

    fun loadSongs()
    {
        var songCursor:Cursor? = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,null,null,null)
        while (songCursor != null && songCursor.moveToNext())
        {

            var songName = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            var songArtist = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            var songAlbum = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
            var songPath = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            songList.add(SongList(
                    songName,
                    songArtist,
                    songAlbum,
                    songPath
            ))
        }

        mRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


        val adapter = SongAdapter(songList,applicationContext,this)

        mRecyclerView!!.adapter = adapter

        mRecyclerView!!.addItemDecoration(DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL))



    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                loadSongs()
            }
        }
    }



}

