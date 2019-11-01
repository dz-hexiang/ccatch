package com.aimissu.ccatch.Intercept

/**
 * @Date Created: 2019-09-25
 * @Author: hexiang
 * @Description: 错误异常拦截
 */
interface Interceptor {
    companion object {
        const val TAG = "ExceptionCatch"
    }

    /**
     * 拦截处理
     *
     * @param interceptor
     * @return true 拦截，false 不拦截，realInterceptor.intercept(interceptor) 继续传递下去
     * @throws Exception
     */
    @Throws(Exception::class)
    fun intercept(interceptor: Interceptor): Boolean
}
