package com.sangmoki.exercise_diary

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {

    // Firebase Auth 객체 생성
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Firebase Auth 객체 초기화
        auth = Firebase.auth

        // 사용자 현재 로그인 상태 확인
        val currentUser = auth.currentUser

        Log.d("LOGINTEST", currentUser!!.uid)

//        Toast.makeText(this, "기존에 비회원 로그인이 되어있는 사람 !", Toast.LENGTH_SHORT,).show()

        // 익명(비회원) 로그인 실행
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "비회원 로그인 성공 !", Toast.LENGTH_SHORT,).show()

                    // 데이터 불러오는 동안 딜레이 시키기
                    Handler().postDelayed({
                        startActivity(
                            Intent(this, MainActivity::class.java))
                        finish()
                    }, 2000)

                } else {
                    Toast.makeText(this,"비회원 로그인 실패 !", Toast.LENGTH_SHORT,).show()
                }
            }



    }
}