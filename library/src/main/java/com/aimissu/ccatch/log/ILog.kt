package com.aimissu.ccatch.log

interface ILog {

    fun v(tag: String, message: () -> Any?)

    fun v(tag: String, format: String, vararg args: Any?)

    fun i(tag: String, message: () -> Any?)

    fun i(tag: String, format: String, vararg args: Any?)

    fun d(tag: String, message: () -> Any?)

    fun d(tag: String, format: String, vararg args: Any?)

    fun w(tag: String, message: () -> Any?)

    fun w(tag: String, format: String, vararg args: Any?)

    fun e(tag: String, message: () -> Any?, error: Throwable? = null)

    fun e(tag: String, format: String, error: Throwable? = null, vararg args: Any?)
}
