package com.aimissu.ccatch.Intercept

import com.aimissu.ccatch.log.MLog

import java.util.ArrayList

/**
 * @Date Created: 2019-09-29
 * @Author: hexiang
 * @Description: 多个简单过滤的异常，简单的根据类过滤
 */
class MultiSimpleClassInterceptor private constructor(builder: Builder) : Interceptor {

    override fun intercept(interceptor: Interceptor): Boolean {
        MLog.info(TAG, "MultiSimpleClassInterceptor intercept")
        if (interceptor is RealInterceptor) {
            interceptor.throwable?.let {
                mExceptionClass?.takeIf { b -> b.contains(it.javaClass) }
                    ?.apply {
                        MLog.info(
                            Interceptor.TAG,
                            "MultiSimpleClassInterceptor intercept class:" + it.javaClass
                        )
                        return true
                    }
            }
            return interceptor.intercept(interceptor)
        }
        return false
    }

    private val mExceptionClass: List<Class<*>>?

    init {
        mExceptionClass = builder.mExceptionClass
    }


    class Builder {
        internal val mExceptionClass: MutableList<Class<*>>?

        init {
            mExceptionClass = ArrayList()
        }

        fun addExceptionClass(cls: Class<*>?): Builder {
            if (mExceptionClass != null && cls != null) {
                mExceptionClass.add(cls)
            }
            return this
        }

        fun build(): MultiSimpleClassInterceptor {
            return MultiSimpleClassInterceptor(this)
        }
    }

    companion object {

        const val TAG = "ExceptionCatch"
    }
}
