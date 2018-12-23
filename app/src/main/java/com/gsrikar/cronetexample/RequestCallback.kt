package com.gsrikar.cronetexample

import android.util.Log
import org.chromium.net.CronetException
import org.chromium.net.UrlRequest
import org.chromium.net.UrlResponseInfo
import java.nio.ByteBuffer
import java.nio.charset.Charset


/**
 * Different methods are invoked for difference response status
 */

class RequestCallback : UrlRequest.Callback() {

    companion object {
        // Log cat tag
        private val TAG = RequestCallback::class.java.simpleName
    }

    override fun onResponseStarted(request: UrlRequest?, info: UrlResponseInfo?) {
        Log.i(TAG, "Response Started")
        val statusCode = info?.httpStatusCode
        Log.i(TAG, "Status Code $statusCode")
        if (statusCode == 200) {
            // Read the buffer
            request?.read(ByteBuffer.allocateDirect(32 * 1024))
        }
    }

    override fun onReadCompleted(request: UrlRequest?, info: UrlResponseInfo?, byteBuffer: ByteBuffer?) {
        Log.i(TAG, "Response Completed")

        // Flip the buffer
        byteBuffer?.flip()

        // Convert the byte buffer to a string
        byteBuffer?.let {
            val byteArray = ByteArray(it.remaining())
            it.get(byteArray)
            String(byteArray, Charset.forName("UTF-8"))
        }.apply {
            Log.d(TAG, "Response: $this")
        }

        // Clear the buffer
        byteBuffer?.clear()

        // Read the buffer
        request?.read(byteBuffer)
    }

    override fun onFailed(request: UrlRequest?, info: UrlResponseInfo?, error: CronetException?) {
        Log.e(TAG, "Response Failed: ${error?.message}")
    }

    override fun onSucceeded(request: UrlRequest?, info: UrlResponseInfo?) {
        Log.i(TAG, "Response Succeeded")
    }

    override fun onRedirectReceived(request: UrlRequest?, info: UrlResponseInfo?, newLocationUrl: String?) {
        Log.i(TAG, "Response Redirect to $newLocationUrl")
        request?.followRedirect()
    }

    override fun onCanceled(request: UrlRequest?, info: UrlResponseInfo?) {
        super.onCanceled(request, info)
        Log.i(TAG, "Response cancelled")
    }
}
