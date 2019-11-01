package com.aimissu.ccatch.demo

import com.aimissu.ccatch.ICrashHandler
import com.aimissu.ccatch.UncaughtExceptionHandler

/**
 * @Date Created: 2019-10-31
 * @Author: hexiang
 * @Description:
 */
class MyCrashHandle : ICrashHandler {
    override fun reportInterceptExceptionLog(
        thread: Thread,
        ex: Throwable,
        handler: UncaughtExceptionHandler
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reportUnInterceptExceptionLog(
        thread: Thread,
        ex: Throwable,
        handler: UncaughtExceptionHandler
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun preUncaughtException(
        thread: Thread,
        ex: Throwable,
        handler: UncaughtExceptionHandler
    ) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStopCatch(handler: UncaughtExceptionHandler) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCatch(handler: UncaughtExceptionHandler) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
