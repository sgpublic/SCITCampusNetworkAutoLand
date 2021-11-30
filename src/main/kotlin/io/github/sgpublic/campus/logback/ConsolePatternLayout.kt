package io.github.sgpublic.campus.logback

import ch.qos.logback.classic.PatternLayout
import io.github.sgpublic.campus.logback.converter.TraceConverter

/**
 * 添加自定义参数
 */
class ConsolePatternLayout: PatternLayout() {
    companion object {
        init {
            DEFAULT_CONVERTER_MAP["trace"] = TraceConverter::class.java.name
        }
    }
}