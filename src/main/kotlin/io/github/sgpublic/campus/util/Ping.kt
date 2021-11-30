package io.github.sgpublic.campus.util

import java.net.InetAddress

object Ping {
    fun start(domain: String = "180.101.49.12", timeout: Int = 5000): Boolean {
        return InetAddress.getByName(domain)
            .isReachable(timeout)
    }
}