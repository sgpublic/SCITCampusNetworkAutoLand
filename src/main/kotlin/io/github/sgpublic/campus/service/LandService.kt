package io.github.sgpublic.campus.service

import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine
import com.gargoylesoftware.htmlunit.util.Cookie
import io.github.sgpublic.campus.logback.util.Log
import io.github.sgpublic.campus.util.Ping
import org.ini4j.Ini
import java.io.File
import java.lang.Exception
import java.net.URLEncoder

object LandService {
    private val serviceType: Array<String> = arrayOf(
        "中国移动", "中国电信", "办公网", "校园网"
    )
    private val client: WebClient = WebClient(BrowserVersion.CHROME).apply {
        options.isThrowExceptionOnScriptError = false
        cookieManager.apply {
            addCookie(Cookie(
                "10.100.1.10", "EPORTAL_COOKIE_SAVEPASSWORD", "true"
            ))
            addCookie(Cookie(
                "10.100.1.10", "EPORTAL_AUTO_LAND", "true"
            ))
        }
    }

    fun land(callback: LandCallback) {
        Log.i("疑似校园网认证失效，正在尝试认证")
        val ini = Ini(File("./conf.ini"))
        val sb = StringBuilder("认证信息：\n")
        val username = ini.get("AotuLand", "username")
        sb.append("用户名：").append(username).append("\n")
        val password = ini.get("AotuLand", "password")
        sb.append("密码：").append(password).append("\n")
        val type: String = try {
            serviceType[Integer.parseInt(
                ini.get("AotuLand", "type")
            )]
        } catch (e: IndexOutOfBoundsException) {
            Log.f("请设置正确的服务类型。")
            "未知"
        } catch (e: NumberFormatException) {
            Log.f("无法解析服务类型。")
            "未知"
        }
        sb.append("服务类型：").append(type)
        Log.d(sb)

        try {
            client.cookieManager.let {
                it.addCookie(Cookie(
                    "10.100.1.10", "EPORTAL_COOKIE_USERNAME", username
                ))
                it.addCookie(Cookie(
                    "10.100.1.10", "EPORTAL_COOKIE_PASSWORD", password
                ))
                it.addCookie(Cookie("10.100.1.10", "EPORTAL_COOKIE_SERVER",
                    URLEncoder.encode(type, Charsets.UTF_8)))
            }
            client.getPage<HtmlPage>("http://10.100.1.10/eportal/success.jsp")
            Thread.sleep(10000)
            if (Ping.start()) {
                callback.onSuccess()
            } else {
                callback.onFailed()
            }
        } catch (e: Exception) {
            callback.onFailed(e)
        }
    }

    abstract class LandCallback {
        val ts: Long = System.currentTimeMillis()
        open fun onSuccess() { }
        abstract fun onFailed(e: Exception? = null)
    }
}