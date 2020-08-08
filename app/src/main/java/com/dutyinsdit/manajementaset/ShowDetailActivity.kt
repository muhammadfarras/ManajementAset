package com.dutyinsdit.manajementaset

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.InputStream
import java.lang.Exception
import java.net.URL

class ShowDetailActivity : AppCompatActivity() {
    lateinit var detailTextView: TextView
    lateinit var imageDetailView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_detail)
        detailTextView = findViewById(R.id.detailView)
        imageDetailView = findViewById(R.id.imageView2)



        val getDetailAset = getDetail (detailTextView,imageDetailView)
        getDetailAset.execute()
    }
}

class getDetail (var detailView: TextView, var imageDetailView: ImageView) : AsyncTask<String, String, String>(){
    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: String?): String {
        val url = URL (Izin.LINK_DETAIL).readText()
        return url
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        val jsonParse = JSONObject (result)
        val id = jsonParse.get("id")
        val keterangan = jsonParse.get("keterangan")
        val golongan = jsonParse.get("golongan")
        val kategori = jsonParse.get("kategori")
        val kelompok = jsonParse.get("kelompok")
        val subkelompok = jsonParse.get("sub_kelompok")
        val imageUrl = jsonParse.get("img")

        val resultDetail = "id\t: $id\n" +
                "Keterangan\t: $keterangan\n" +
                "Golongan\t: $golongan\n" +
                "Kategori\t: $kategori\n" +
                "Kelompok\t: $kelompok\n" +
                "Sub Kelompok\t: $subkelompok\n"
        detailView.text = resultDetail

        Picasso.get().load(imageUrl.toString()).into(imageDetailView)
    }
}
