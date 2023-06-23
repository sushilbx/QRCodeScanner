package com.sushil.qr

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.sushil.qr.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var b: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySplashBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler().postDelayed({
            val sessionManager = SessionManager(this);
            if (sessionManager.isLoggedIn) {
                val intent = Intent(this, QrActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, LogininActivity::class.java)
                startActivity(intent)
            }

            finish()
        }, 2000) // 3000 is the delayed time in milliseconds.
    }
}