package com.example.imagegenerator.logic

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

import android.app.ProgressDialog
import android.widget.ImageView
import android.widget.TextView
import com.example.imagegenerator.data.ImageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType

class LogicApi() {

    private val imageLoader = ImageLoader()

    val client = OkHttpClient()

    val JSON: MediaType = "application/json; charset=utf-8".toMediaType()

    fun llamarAPI(entrada: String, resultado:TextView, img1: ImageView, imgResized: ImageView,progressDialog:ProgressDialog) {

        val jsonBody= JSONObject()
        val key="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"//llave de Api Dalle
        try{
            jsonBody.put("prompt",entrada);
            jsonBody.put("size","512x512");//"256x256" "512x512"
        }catch (e:Exception){
            e.printStackTrace();
        }
        val requestBody: RequestBody = jsonBody.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("https://api.openai.com/v1/images/generations")
            .header("Authorization", "Bearer $key")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

                GlobalScope.launch (Dispatchers.Main) {
                    resultado.setText("Error al generar imagen"+e.message)
                    progressDialog.dismiss()
                }

            }

            override fun onResponse(call: Call, response: Response) {
                try{
                    val jsonObject= JSONObject(response.body?.string());
                    val imageUrl=jsonObject.getJSONArray("data").getJSONObject(0).getString("url");

                    imageLoader.cargarImagen(imageUrl,resultado,img1,imgResized,progressDialog)

                }catch(e:Exception){
                    e.printStackTrace();
                }
            }
        })
    }

}