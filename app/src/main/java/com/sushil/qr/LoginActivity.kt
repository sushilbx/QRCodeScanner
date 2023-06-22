package com.sushil.qr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.sushil.qr.APIs.RetrofitClient
import com.sushil.qr.databinding.ActivityLoginBinding
import com.sushil.qr.models.LoginBody
import com.sushil.qr.models.SessionModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

class LoginActivity : AppCompatActivity() {
    private lateinit var b: ActivityLoginBinding
    var sessionManager: SessionManager? = null
    var sessionModel: SessionModel? = null


    var username = ""
    var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        sessionManager = SessionManager(this@LoginActivity)
        sessionModel = sessionManager!!.getLoginSession()

        b.btnLogin.setOnClickListener(View.OnClickListener {
            if (checkForm()) {
                login()
            }
        })

        b.tvForgot.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        })
















    }

    private fun login() {
        Log.e("sushil", username)
        Log.e("sushil", password)
        val sessionManager = SessionManager(this@LoginActivity)
        val call: Call<SessionModel> = RetrofitClient.getInstance().getApi().login(LoginBody(username,password,"grant_type"))
        call.enqueue(object : Callback<SessionModel> {
            override fun onResponse(
                call: Call<SessionModel>, response: Response<SessionModel>
            ) {

                Log.e("sushil", Gson().toJson(response.body()))

                if (response.isSuccessful()) {
                    val statusCode = response.code()
                    if (statusCode == 200) {
                        sessionManager.createLoginSession(response.body())
                        Toast.makeText(this@LoginActivity, "Login Successfully", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this@LoginActivity, QrActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "something went wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Either email or password is wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<SessionModel?>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkForm(): Boolean {
        username = b.etPhone.getText().toString().trim()
        password = b.etPassword.getText().toString().trim()
        if (username.isEmpty()) {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
            b.etPhone!!.isFocusableInTouchMode = true
            b.etPhone!!.requestFocus()
            return false
        } else if (!Utils.myEmailValid(username)) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()
            b.etPhone!!.isFocusableInTouchMode = true
            b.etPhone!!.requestFocus()
            return false
        }
        if (password.isEmpty()) {
            b.etPassword!!.error = "Password is empty"
            b.etPassword!!.requestFocus()
            return false
        } else if (password.length < 6) {
            b.etPassword!!.error = "Password should be minimum 6 characters"
            b.etPassword!!.requestFocus()
            return false
        }
        return true
    } /*  private void showruledialog() {

        Dialog contacts_dialog = new Dialog(LoginActivity.this, R.style.theme_sms_receive_dialog);
        contacts_dialog.setContentView(R.layout.otp);
        contacts_dialog.setCancelable(true);
        contacts_dialog.setCanceledOnTouchOutside(true);


        contacts_dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        contacts_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final Button continuebutton = contacts_dialog.findViewById(R.id.continuebutton);

        continuebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contacts_dialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        contacts_dialog.show();


    }*/
}