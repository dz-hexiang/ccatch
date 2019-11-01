package com.aimissu.ccatch

import com.aimissu.ccatch.Intercept.Interceptor
import com.aimissu.ccatch.log.MLog

/**
 * @Date Created: 2019-09-25
 * @Author: hexiang
 * @Description: 奔溃异常捕获拦截
 */
class ExceptionCatch {
    private var mExceptionCatchBuilder: ExceptionCatchBuilder? = null //配置
    private var mUncaughtExceptionHandler: UncaughtExceptionHandler? = null //捕获处理

    /**
     * 是否正在拦截
     *
     * @return
     */
    val isRealIntercept: Boolean
        get() {
            MLog.info(
                Interceptor.TAG,
                "isRealIntercept handle class:%s",
                Thread.getDefaultUncaughtExceptionHandler()!!
            )
            return Thread.getDefaultUncaughtExceptionHandler() is UncaughtExceptionHandler
        }

    /**
     * 开始捕获
     *
     * @param exceptionCatchBuilder
     */
    fun startCatch(exceptionCatchBuilder: ExceptionCatchBuilder) {
        stopCatch()
        this.mExceptionCatchBuilder = exceptionCatchBuilder
        this.mExceptionCatchBuilder?.let {
            mUncaughtExceptionHandler = UncaughtExceptionHandler(it)
            it.mLog?.let { MLog.setLog(it) }
            mUncaughtExceptionHandler?.startCatch()
        }
    }

    /**
     * 停止捕获处理
     */
    fun stopCatch() {
        this.mUncaughtExceptionHandler?.stopCatch()
    }

    private object SingleHolder {
        var mSingleInstance = ExceptionCatch()
    }

    companion object {
        val instance: ExceptionCatch
            get() = SingleHolder.mSingleInstance
    }
}