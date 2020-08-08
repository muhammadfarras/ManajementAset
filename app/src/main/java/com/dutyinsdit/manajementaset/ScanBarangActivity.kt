package com.dutyinsdit.manajementaset

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.json.JSONObject
import java.io.FileNotFoundException
import java.net.URL

class ScanBarangActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    lateinit var starterIntent:Intent
    lateinit var scannerView:ZXingScannerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_barang)

        starterIntent = intent
        scannerView = ZXingScannerView (this@ScanBarangActivity)
        setContentView(scannerView)
    }
    override fun handleResult(rawResult: Result?) {
        val result = "${Izin.LINK_ISTHEREARE}?id=${rawResult.toString()}"
        Izin.LINK_DETAIL = "${Izin.LINK}detail.php?id=${rawResult.toString()}"
        val getStatusAsync = GetStatus (this@ScanBarangActivity,starterIntent,result,this@ScanBarangActivity)
        getStatusAsync.execute()
    }
    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler (this)
        scannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView.startCamera()
    }

    override fun onStop() {
        super.onStop()
        scannerView.stopCamera()
    }
}

class GetStatus (var context: Context, var starterIntent: Intent, var urlParam:String, var acvtivity: Activity): AsyncTask<String, String, String>(){
    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: String?): String {


        try {
            val url = URL (urlParam).readText()
            return url
        }
        catch (e: FileNotFoundException){
            val url = "{\"status\":\"gagal\"}"
            return url
        }

    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        // parse json
        val parsJson = JSONObject (result)
        var result = parsJson.get("status")

        if (result == Izin.SUKSES){
            Toast.makeText(context,"Berhasil intent ke activity lain", Toast.LENGTH_LONG).show()
            val intent = Intent(context,ShowDetailActivity::class.java)
            context.startActivity(intent)
            acvtivity.finish()

        }
        else {
            Toast.makeText(context,"Gagal Scan Ulang", Toast.LENGTH_LONG).show()
            acvtivity.finish()
            context.startActivity(Intent(starterIntent))

        }
    }
}