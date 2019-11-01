package com.aimissu.ccatch

/**
 * @Date Created: 2019-10-14
 * @Author: hexiang
 * @Description: crash  拦截处理
 */
interface ICrashHandler {

    /**
     * 上报拦截处理的异常
     *
     * @param thread
     * @param ex
     */
    fun reportInterceptExceptionLog(
        thread: Thread,
        ex: Throwable,
        handler: UncaughtExceptionHandler
    )

    /**
     * 上报没有拦截处理的异常
     *
     * @param thread
     * @param ex
     */
    fun reportUnInterceptExceptionLog(
        thread: Thread,
        ex: Throwable,
        handler: UncaughtExceptionHandler
    )

    /**
     * 捕获异常处理之前
     *
     * @param thread
     * @param ex
     */
    fun preUncaughtException(thread: Thread, ex: Throwable, handler: UncaughtExceptionHandler)

    /**
     * 开始捕获回掉
     * @param handler
     */
    fun onStopCatch(handler: UncaughtExceptionHandler)

    /**
     * 停止捕获回掉
     * @param handler
     */
    fun onStartCatch(handler: UncaughtExceptionHandler)

}
