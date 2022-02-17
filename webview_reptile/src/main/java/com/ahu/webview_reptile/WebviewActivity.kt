package com.ahu.webview_reptile

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


class WebviewActivity : AppCompatActivity() {

    lateinit var webview: WebView
    val client = OkHttpClient()
    val handler = Handler(Looper.getMainLooper())


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        webview = findViewById(R.id.web_view)
        val sinkWebViewReptile = SinkWebViewReptile(webview)
        sinkWebViewReptile.loginTeach()
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            sinkWebViewReptile.loginTeach()
        }
    }

}