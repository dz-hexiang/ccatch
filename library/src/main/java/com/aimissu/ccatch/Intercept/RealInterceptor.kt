package com.aimissu.ccatch.Intercept

import java.util.concurrent.CopyOnWriteArrayList

/**
 * @Date Created: 2019-09-25
 * @Author: hexiang
 * @Description: 拦截实现
 */
class RealInterceptor : Interceptor {
    private var index = 0
    var interceptor: Interceptor? = null
    var interceptors: CopyOnWriteArrayList<Interceptor>? = null
    var thread: Thread? = null
    var throwable: Throwable? = null

    constructor(
        index: Int, interceptor: Interceptor?,
        interceptors: CopyOnWriteArrayList<Interceptor>?, thread: Thread?,
        throwable: Throwable?
    ) {
        this.index = index
        this.interceptor = interceptor

        this.interceptors = interceptors
        this.thread = thread
        this.throwable = throwable
    }

    @Throws(Exception::class)
    override fun intercept(interceptor: Interceptor): Boolean {
        interceptors?.takeIf { index < interceptors!!.size && index >= 0 }?.apply {
            val next = RealInterceptor(index + 1, interceptor, this, thread, throwable)
            val interceptorCur = interceptors!![index]
            return interceptorCur.intercept(next)
        }
        return false

    }
}
