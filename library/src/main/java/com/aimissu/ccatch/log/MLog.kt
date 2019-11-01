package com.aimissu.ccatch.log

object MLog {

    private var mLog: ILog? = null

    internal fun setLog(logger: ILog?) {
        if (logger != null) {
            mLog = logger
        }
    }


    fun verbose(obj: Any, format: String, vararg args: Any) {
        mLog?.v(tag(obj), format, *args)
    }

    fun debug(obj: Any, format: String, vararg args: Any) {
        mLog?.d(tag(obj), format, *args)
    }

    fun info(obj: Any, format: String, vararg args: Any) {
        mLog?.i(tag(obj), format, *args)
    }

    fun warn(obj: Any, format: String, vararg args: Any) {
        mLog?.w(tag(obj), format, *args)
    }

    fun error(obj: Any, format: String, vararg args: Any) {
        mLog?.e(tag(obj), format, Throwable(""), *args)
    }

    fun error(obj: Any, format: String, t: Throwable, vararg args: Any) {
        mLog?.e(tag(obj), format, t, *args)
    }

    fun error(obj: Any, t: Throwable) {
        mLog?.e(tag(obj), "", t)
    }

    private fun tag(tag: Any): String {
        return tag as? String ?: tag.javaClass.simpleName
    }


}
