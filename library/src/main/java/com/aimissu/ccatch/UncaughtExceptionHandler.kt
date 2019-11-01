package com.aimissu.ccatch

import android.os.Looper
import com.aimissu.ccatch.Intercept.Interceptor

import com.aimissu.ccatch.Intercept.RealInterceptor
import com.aimissu.ccatch.log.MLog

/**
 * @Date Created: 2019-10-14
 * @Author: hexiang
 * @Description: 未捕获异常处理
 */
class UncaughtExceptionHandler(private val mExceptionCatchBuilder: ExceptionCatchBuilder?) :
    Thread.UncaughtExceptionHandler {
    val originalHandler: Thread.UncaughtExceptionHandler?
    private var mICrashHandler: ICrashHandler? = null

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

    init {
        originalHandler = Thread.getDefaultUncaughtExceptionHandler()
        if (this.mExceptionCatchBuilder != null) {
            this.mICrashHandler = this.mExceptionCatchBuilder.mCrashHandler
        }

        MLog.info(Interceptor.TAG, "original uncaught exception handle class:" + originalHandler!!.javaClass)
        setUncaughtHandleSelf()
    }

    /**
     * 重置最初的捕获
     */
    private fun reSetOriginalUncaughtHandler() {
        Thread.setDefaultUncaughtExceptionHandler(originalHandler)
        MLog.info(
            Interceptor.TAG, "reSetOriginalUncaughtHandler Thread.getDefaultUncaughtExceptionHandler() " +
                    "class:" + originalHandler!!.javaClass
        )
    }

    /**
     * 自行拦截处理未捕获异常
     */
    private fun setUncaughtHandleSelf() {
        if (Thread.getDefaultUncaughtExceptionHandler() is UncaughtExceptionHandler) {
            MLog.info(Interceptor.TAG, "already setUncaughtHandleSelf")
        } else {
            Thread.setDefaultUncaughtExceptionHandler(this)
            MLog.info(Interceptor.TAG, "setUncaughtHandleSelf")
        }
    }

    /**
     * 系统的未捕获异常回调
     *
     * @param thread
     * @param ex
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!isRealIntercept) {
            originalHandler?.uncaughtException(thread, ex)
            return
        }
        preUncaughtException(thread, ex)
        if (intercept(thread, ex)) {
            MLog.info(Interceptor.TAG, "uncaughtException start intercept")
            reportInterceptExceptionLog(thread, ex)
            try {
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    Looper.loop()
                }
            } catch (e: Throwable) {
                MLog.info(Interceptor.TAG, "uncaughtException loop err:%s", e)
                this.uncaughtException(Thread.currentThread(), e)
            }

        } else {
            reportUnInterceptExceptionLog(thread, ex)
            MLog.info(Interceptor.TAG, "uncaughtException call default uncaughtException")
            originalHandler?.uncaughtException(thread, ex)
        }
    }

    /**
     * 上报拦截处理的异常
     *
     * @param thread
     * @param ex
     */
    private fun reportInterceptExceptionLog(thread: Thread, ex: Throwable) {
        MLog.info(Interceptor.TAG, "reportInterceptExceptionLog")
        if (mICrashHandler != null) {
            mICrashHandler!!.reportInterceptExceptionLog(thread, ex, this)
        }
        setUncaughtHandleSelf()
    }

    /**
     * 上报未拦截处理的异常
     *
     * @param thread
     * @param ex
     */
    private fun reportUnInterceptExceptionLog(thread: Thread, ex: Throwable) {
        MLog.info(Interceptor.TAG, "reportUnInterceptExceptionLog")
        if (mICrashHandler != null) {
            mICrashHandler!!.reportUnInterceptExceptionLog(thread, ex, this)
        }
        setUncaughtHandleSelf()
    }

    /**
     * 捕获异常处理之前
     *
     * @param thread
     * @param ex
     */
    private fun preUncaughtException(thread: Thread, ex: Throwable) {
        MLog.info(Interceptor.TAG, "preUncaughtException")
        if (mICrashHandler != null) {
            mICrashHandler!!.preUncaughtException(thread, ex, this)
        }
        setUncaughtHandleSelf()
    }

    /**
     * 根据配置判断是否拦截
     *
     * @param thread
     * @param ex
     * @return true 拦截 false 不拦截
     */
    private fun intercept(thread: Thread, ex: Throwable): Boolean {
        if (mExceptionCatchBuilder == null) {
            return false
        }
        try {
            val originInterceptor = RealInterceptor(
                0, null,
                mExceptionCatchBuilder.mInterceptors, thread, ex
            )
            //拦截部分java crash 不上报
            if (originInterceptor.intercept(originInterceptor)) {
                MLog.info(Interceptor.TAG, "intercept true")
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            MLog.info(Interceptor.TAG, "intercept err:%s", e)
        }

        return false
    }

    fun startCatch() {
        setUncaughtHandleSelf()
        mICrashHandler?.onStartCatch(this)
    }

    fun stopCatch() {
        if (isRealIntercept) {
            this.reSetOriginalUncaughtHandler()
        }
        mICrashHandler?.onStopCatch(this)
    }


}
