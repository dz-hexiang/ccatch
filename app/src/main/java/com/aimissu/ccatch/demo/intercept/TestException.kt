package com.aimissu.ccatch.demo.intercept

/**
 * @Date Created: 2019-09-26
 * @Author: hexiang
 * @Description: 测试异常
 */
class TestException : RuntimeException {

    constructor() : super() {}

    constructor(s: String) : super(s) {}

}
