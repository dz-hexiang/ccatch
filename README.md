# ccatch
catch crash exeception ,repot log with no more crash



##原理
拦截未捕获异常回掉，重启主线程消息循环

###使用
####捕获异常
```
  val builder = ExceptionCatchBuilder.Builder()
                .setCrashHandler(crashHandle) //异常捕获回调
                .setContext(it.context)
                .setLog(MyLog()) //打印日志
                .addInterceptor(FinalizerInterceptor()) //拦截特定异常
                .addInterceptor(TestExceptionIntercept()) //拦截自定义异常
                .addInterceptor(
                    MultiSimpleClassInterceptor.Builder()
                        .addExceptionClass(RemoteException::class.java) //拦截RemoteException异常
                        .addExceptionClass(ArrayIndexOutOfBoundsException::class.java) //拦截越界异常
                        .addExceptionClass(NullPointerException::class.java) //拦截NPE
                        .addExceptionClass(ClassNotFoundException::class.java) 
                        .addExceptionClass(IllegalArgumentException::class.java)
                        .build()
                )
                .build()
            ExceptionCatch.instance.startCatch(builder) //设置捕获
```

####停止捕获

```
  ExceptionCatch.instance.stopCatch() //取消
```

####拦截回调

i

```
nterface ICrashHandler {

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
        //todo 在这里上报crash日志
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
```

