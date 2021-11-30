package io.github.sgpublic.campus.service

import io.github.sgpublic.campus.logback.util.Log
import io.github.sgpublic.campus.util.Ping
import java.lang.Exception

class PingService {
    companion object {
        private val thread: Thread = Thread {
            while (true) {
                Thread.sleep(10000)
                if (Ping.start()) {
                    Log.d("网络连接正常")
                    continue
                }
                LandService.land(object : LandService.LandCallback() {
                    override fun onFailed(e: Exception?) {
                        Log.e("校园网自动认证失败", e)
                    }

                    override fun onSuccess() {
                        Log.i("校园网自动认证成功")
                    }
                })
            }
        }

        fun start() {
            thread.start()
        }
    }
}