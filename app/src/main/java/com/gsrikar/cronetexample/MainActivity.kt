package com.gsrikar.cronetexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.chromium.net.CronetEngine
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    companion object {
        // Web page url
        private const val JSON_PLACEHOLDER_API_URL = "https://jsonplaceholder.typicode.com/todos/1"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Build a Cronet engine
        val cronetEngine =
            CronetEngine.Builder(this)
                .enableBrotli(true)
                .build()

        // Build the request
        val request =
            cronetEngine.newUrlRequestBuilder(
                JSON_PLACEHOLDER_API_URL,
                RequestCallback(),
                Executors.newSingleThreadExecutor()
            ).build()

        // Start the request
        request.start()
    }
}
