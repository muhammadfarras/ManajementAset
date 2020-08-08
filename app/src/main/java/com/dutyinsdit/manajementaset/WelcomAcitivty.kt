package com.dutyinsdit.manajementaset

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_welcom_acitivty.*

class WelcomAcitivty : AppCompatActivity() {

    lateinit var mySession: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcom_acitivty)

        mySession = Session(this)

        // Hide action bar
        supportActionBar?.hide()
        lottie_animation.setAnimation("welcom_sdit.json")
        lottie_animation.playAnimation()
//        lottie_animation.pauseAnimation()

        // eksekusi setelah 3 detik
        Handler().postDelayed(Runnable {

            // jika ada session pernah masuk
            if (mySession.isLoged()){
                val intent = Intent (this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                val intent = Intent (this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }



        },3000)
    }
}
