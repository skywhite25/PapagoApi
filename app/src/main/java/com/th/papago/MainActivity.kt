package com.th.papago

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.th.papago.dto.PapagoDto
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //post
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()

        var url = "https://openapi.naver.com/v1/papago/n2mt"
        val client = OkHttpClient()

        //body로 넘길 json에 필요한 것들 넣기 (네이버 API 참고)
        val json = JSONObject()
        json.put("source", "ko")
        json.put("target", "en")
        json.put("text", "안녕하세요, 오늘 날씨가 참 좋아요.")

        val body = RequestBody.create(JSON, json.toString())
        val request = Request.Builder()
            .header("X-Naver-Client-Id", "5cswsbP4JSRIBvGslgTa")
            .addHeader("X-Naver-Client-Secret", "T7LmrfLoRf")
            .url(url)
            .post(body)
            .build()

        val response = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }
            // main thread말고 별도의 thread에서 실행해야 함.
            override fun onResponse(call: Call, response: Response) {
                /*var translateText = ""*/
                Thread{
                    var str = response.body?.string()
                    println(str)
                    var PapagoDTO = Gson().fromJson<PapagoDto>(str, PapagoDto::class.java)
                    println(PapagoDTO.message?.result?.translateText)
                }.start()

                /*textEN.text = translateText*/
            }
        })

        fun translate() {

        }
    }
}