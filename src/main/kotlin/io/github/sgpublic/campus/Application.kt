package io.github.sgpublic.campus

import io.github.sgpublic.campus.logback.util.Log
import io.github.sgpublic.campus.service.PingService
import org.apache.commons.logging.LogFactory

class Application {
    companion object {
        private var debug = false

        val DEBUG: Boolean get() = debug

        @JvmStatic
        fun main(args: Array<String>) {
            debug = args.contains("--debug")
            Log.i("川工科校园网自动登陆工具运行中。。。")
            if (!debug) {
                LogFactory.getFactory().setAttribute(
                    "org.apache.commons.logging.Log",
                    "org.apache.commons.logging.impl.NoOpLog"
                )
            }
            PingService.start()
        }
    }
}