package com.aimissu.ccatch

import android.content.Context

import com.aimissu.ccatch.Intercept.Interceptor
import com.aimissu.ccatch.log.ILog

import java.util.concurrent.CopyOnWriteArrayList

/**
 * @Date Created: 2019-10-30
 * @Author: hexiang
 * @Description:
 */
class ExceptionCatchBuilder private constructor(builder: Builder) {
    internal val mInterceptors: CopyOnWriteArrayList<Interceptor>?
    internal val mCrashHandler: ICrashHandler?
    internal val mContext: Context?
    internal val mLog: ILog?

    init {
        mInterceptors = builder.mInterceptors
        mCrashHandler = builder.mCrashHandler
        mContext = builder.mContext
        mLog = builder.mLog
    }

    class Builder {
        internal var mInterceptors: CopyOnWriteArrayList<Interceptor>? = null
        internal var mCrashHandler: ICrashHandler? = null
        internal var mContext: Context? = null
        internal var mLog: ILog? = null

        fun setInterceptors(interceptors: CopyOnWriteArrayList<Interceptor>): Builder {
            this.mInterceptors = interceptors
            return this
        }

        fun setCrashHandler(iCrashHandler: ICrashHandler): Builder {
            this.mCrashHandler = iCrashHandler
            return this
        }


        fun addInterceptor(interceptor: Interceptor): Builder {
            if (this.mInterceptors == null) {
                this.mInterceptors = CopyOnWriteArrayList()
            }
            this.mInterceptors!!.add(interceptor)
            return this
        }

        fun setContext(context: Context): Builder {
            this.mContext = context
            return this
        }

        fun setLog(iLog: ILog): Builder {
            this.mLog = iLog
            return this
        }

        fun build(): ExceptionCatchBuilder {
            return ExceptionCatchBuilder(this)
        }
    }
}
