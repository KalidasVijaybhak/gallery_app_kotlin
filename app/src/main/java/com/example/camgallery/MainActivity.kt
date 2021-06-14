package com.example.camgallery

import android.content.pm.PackageManager
import android.media.Image


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.GridLayout
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

private var allPictures:ArrayList<Image1>?=null
private var imageRecycler:RecyclerView? = null;
private var progressBar: ProgressBar? = null
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageRecycler  = findViewById(R.id.image_recycler)
        progressBar= findViewById(R.id.recycler_progress)
        imageRecycler?.layoutManager = GridLayoutManager(this,3)
        image_recycler?.setHasFixedSize(true)
            if(ContextCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),101)
            }
        allPictures = ArrayList( )
            if(allPictures!!.isEmpty()){
                          progressBar?.visibility = View.VISIBLE
                allPictures = getAllImages()
               image_recycler?.adapter = ImageAdapter(this, allPictures!!)
                progressBar?.visibility = View.GONE
            }

        

    }

    private fun     getAllImages(): ArrayList<Image1> {
        val images = ArrayList<Image1>()
        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)
        val cursor  = this@MainActivity.contentResolver.query(allImageUri,projection,null,null,null)
        try{
            cursor!!.moveToFirst()

            do{
                val image1 = Image1(
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)) )
                images.add(image1)
            } while(cursor.moveToNext())
            cursor.close()
            
        }  catch(e:Exception){
                     e.printStackTrace()
        }
        return images
    }
}

