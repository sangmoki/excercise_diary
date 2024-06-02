package com.sangmoki.exercise_diary

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.GregorianCalendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val writeBtn = findViewById<ImageView>(R.id.writeBtn)

        // 이미지 버튼 클릭 이벤트
        writeBtn.setOnClickListener {

            // 커스텀 다이얼로그 객체 생성
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("운동 기록 추가")

            // 다이얼로그 띄움
            val mAlertDialog = mBuilder.show()

            // 띄운 다이얼로그에서 날짜 선택 버튼 클릭 이벤트
            mAlertDialog.findViewById<Button>(R.id.dateSelectBtn)?.setOnClickListener {

                // 오늘 날짜의 년 월 일 구하기
                val today = GregorianCalendar()
                val year: Int = today.get(GregorianCalendar.YEAR)
                val month: Int = today.get(GregorianCalendar.MONTH)
                val day: Int = today.get(GregorianCalendar.DAY_OF_MONTH)

                // 날짜 선택 다이얼로그 초기 데이터 설정
                val dlg = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {

                    // 날짜 선택 시 이벤트
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int
                    ) {

                    }

                }, year, month, day)
                
                // 날짜 선택 다이얼로그 띄움
                dlg.show()
            }
        }
    }
}