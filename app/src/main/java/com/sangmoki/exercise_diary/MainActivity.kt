package com.sangmoki.exercise_diary

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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

            // 오늘 날짜의 년 월 일 구하기
            val today = GregorianCalendar()
            val year: Int = today.get(GregorianCalendar.YEAR)
            val month: Int = today.get(GregorianCalendar.MONTH)
            val day: Int = today.get(GregorianCalendar.DAY_OF_MONTH)

            // 커스텀 다이얼로그 객체 생성
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("운동 기록 추가")

            // 다이얼로그 띄움
            val mAlertDialog = mBuilder.show()

            // 오늘 날짜로 날짜 선택 버튼 default 값 설정
            mAlertDialog.findViewById<Button>(R.id.dateSelectBtn)!!.setText("${year}년 ${month + 1}월 ${day}일")

            var dataText = "${year}년 ${month + 1}월 ${day}일"

            // 띄운 다이얼로그에서 날짜 선택 버튼 클릭 이벤트
            mAlertDialog.findViewById<Button>(R.id.dateSelectBtn)?.setOnClickListener {

                // 날짜 선택 다이얼로그 초기 데이터 설정
                val dlg = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {

                    // 날짜 선택 시 이벤트
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int
                    ) {
                        // 날짜 선택 시 버튼 텍스트 변경
                        mAlertDialog.findViewById<Button>(R.id.dateSelectBtn)!!.setText("${year}년 ${month + 1}월 ${dayOfMonth}일")

                        dataText = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                    }

                }, year, month, day)
                
                // 날짜 선택 다이얼로그 띄움
                dlg.show()
            }

            // 저장하기 버튼 객체 담기
            val setDataBtn = mAlertDialog.findViewById<Button>(R.id.setDataBtn)

            // 저장하기 버튼 클릭 이벤트
            setDataBtn?.setOnClickListener {

                // 운동 시간, 메모 데이터 객체에 담기
                val exerciseMemo = mAlertDialog.findViewById<EditText>(R.id.exerciseMemo)?.text.toString()

                // firebase database 객체 생성
                val database = Firebase.database
                // firebase database 객체 참조 생성
                val myRef = database.getReference("나의 운동 기록")

                Log.d("테스트ㅅ", "dataText : $dataText, exerciseMemo : $exerciseMemo")

                // 데이터 모델 객체 생성
                val model = DataModel(dataText, exerciseMemo)

                // 데이터 모델 객체 데이터베이스에 저장
                myRef.push().setValue(model)
            }
        }
    }
}