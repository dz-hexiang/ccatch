package com.aimissu.ccatch.Intercept

import com.aimissu.ccatch.log.MLog

import java.util.concurrent.TimeoutException

/**
 * @Date Created: 2019-09-25
 * @Author: hexiang
 * @Description: 自定义 特定异常拦截 finalize crash
 * 异常例子：java.util.concurrent.TimeoutException: android.content.res.AssetManager.finalize() timed
 * out
 * after 120 seconds
 */
class FinalizerInterceptor : Interceptor {

    @Throws(Exception::class)
    override fun intercept(interceptor: Interceptor): Boolean {
        MLog.info(Interceptor.TAG, "FinalizerInterceptor intercept")
        if (interceptor is RealInterceptor) {
            interceptor.thread?.takeIf { interceptor.throwable is TimeoutException }
                ?.takeIf { it.name.contains("FinalizerWatchdogDaemon") }
                ?.takeIf { it.name.contains("FinalizerDaemon") }
                .apply { return true }
            return interceptor.intercept(interceptor)


        }
        return false
    }
}
