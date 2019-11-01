package com.aimissu.ccatch.demo.intercept

import com.aimissu.ccatch.Intercept.Interceptor
import com.aimissu.ccatch.Intercept.RealInterceptor

/**
 * @Date Created: 2019-09-25
 * @Author: hexiang
 * @Description: 测试异常拦截
 */
class TestExceptionIntercept : Interceptor {
    override fun intercept(interceptor: Interceptor): Boolean {
        if (interceptor is RealInterceptor) {
            interceptor.throwable?.takeIf { throwable -> throwable is TestException }
                ?.apply { return true }
            return interceptor.intercept(interceptor)
        }
        return false
    }

}
