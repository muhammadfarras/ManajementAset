package com.dutyinsdit.manajementaset

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.IdToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var parentView: View
    private var TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        parentView = findViewById<View>(R.id.context_view)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient =  GoogleSignIn.getClient(this,gso)

        // fill data with detail in google
        val preference = PreferenceManager.getDefaultSharedPreferences(this)
        val name = preference.getString(Session.KEY_USER_NAME,"")
        val email = preference.getString(Session.KEY_EMAIL,"")
        val phoneNumber = preference.getString(Session.KEY_PHONE_NUMBER,getString(R.string.number_fil))
        val urlPhoto = preference.getString(Session.KEY_URL_PHOTO,"")
        text_name.text = name
        text_email.text = email
        text_phone.text = phoneNumber
        Picasso.get().load(urlPhoto).into(image_view)

        // Try to scan dengan  click floating action button
        fab_scan.setOnClickListener {

            // Check permisi apakah memiliki akses kekamera
            val hasCameraPermission = ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
            Log.d(TAG,"onCreateCheckPermission: $hasCameraPermission")

            if (hasCameraPermission == PackageManager.PERMISSION_GRANTED){
                // Jika ia maka intent
                Log.d(TAG,"onCreateCheckPermission: True")
                val intent = Intent(this,ScanBarangActivity::class.java)
                startActivity(intent)
            }
            else {
                // Jika tidk maka minta akses
                Log.d(TAG,"onCreateCheckPermission: Minta akses")
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),Izin.IZIN_KAMERA)
            }
        }

        bottom_app_bar.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.about -> {

                    true
                }

                R.id.keluar -> {

                    // signout dari aplikasi
                    FirebaseAuth.getInstance().signOut()

                    googleSignInClient.signOut().addOnCompleteListener(this, OnCompleteListener {

                    })
                    preference.edit().clear().commit()
                    val intent = Intent (this,LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                    true
                }

                else -> {
                    false
                }
            }
        }
        bottom_app_bar.setNavigationOnClickListener {

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.d(TAG,"onRequestPermission: mulai")

        when (requestCode){
            Izin.IZIN_KAMERA -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Jika izin diberikan
                    Log.d(TAG,"onRequestPermission: Izin diberikan")
                    true
                }
                else {
                    // check apakah always denied atau tidak
                    // jika Tidak atau masih bisa ada pilihan muncculakns nack bar ini
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.CAMERA)){
                        Snackbar.make(parentView,"Kami butuh akses kamera anda.",Snackbar.LENGTH_LONG)
                            .setAnchorView(fab_scan)
                            .show()
                    }
                    else {
                        Snackbar.make(parentView,"Kami butuh akses kamera anda.",Snackbar.LENGTH_LONG)
                            .setAnchorView(fab_scan)
                            .setAction("Settings", View.OnClickListener {
                                val intent = Intent()
                                intent.action  =Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                val uri = Uri.fromParts("package",this.packageName,null)
                                intent.data = uri
                                this.startActivity(intent)
                            })
                            .show()
                    }



                    // Jika izin tidak diberikan
                    false
                }
            }
        }
    }

}
