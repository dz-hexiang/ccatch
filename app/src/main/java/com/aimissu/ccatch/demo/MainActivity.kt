package com.aimissu.ccatch.demo

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aimissu.ccatch.ExceptionCatch
import com.aimissu.ccatch.ExceptionCatchBuilder
import com.aimissu.ccatch.ICrashHandler
import com.aimissu.ccatch.Intercept.FinalizerInterceptor
import com.aimissu.ccatch.Intercept.MultiSimpleClassInterceptor
import com.aimissu.ccatch.UncaughtExceptionHandler
import com.aimissu.ccatch.demo.intercept.TestException
import com.aimissu.ccatch.demo.intercept.TestExceptionIntercept
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSwitchText()

        btn_switch.setOnClickListener {
            if (!ExceptionCatch.instance.isRealIntercept) {
                val builder = ExceptionCatchBuilder.Builder()
                    .setCrashHandler(crashHandle)
                    .setContext(it.context)
                    .setLog(MyLog())
                    .addInterceptor(FinalizerInterceptor())
                    .addInterceptor(TestExceptionIntercept())
                    .addInterceptor(
                        MultiSimpleClassInterceptor.Builder()
                            .addExceptionClass(RemoteException::class.java)
                            .addExceptionClass(ArrayIndexOutOfBoundsException::class.java)
                            .addExceptionClass(NullPointerException::class.java)
                            .addExceptionClass(ClassNotFoundException::class.java)
                            .addExceptionClass(IllegalArgumentException::class.java)
                            .build()
                    )
                    .build()
                ExceptionCatch.instance.startCatch(builder)
            } else {
                ExceptionCatch.instance.stopCatch()
            }

            setSwitchText()
        }

        btn_throw_npe.setOnClickListener {
            throw NullPointerException()
        }

        btn_throw_custom_exe.setOnClickListener {
            throw TestException(getString(R.string.throw_custom_exception))
        }
    }

    private fun setSwitchText() {
        if (ExceptionCatch.instance.isRealIntercept) {
            btn_switch.setText(R.string.btn_stop_catch)
        } else {
            btn_switch.setText(R.string.btn_start_catch)
        }


    }

    private val crashHandle: ICrashHandler = object : ICrashHandler {

        override fun reportInterceptExceptionLog(thread: Thread, ex: Throwable, handler: UncaughtExceptionHandler) {
            ex?.let {
                dialogMsg(
                    String.format(
                        getString(R.string.intercepted_exception),
                        Log.getStackTraceString(ex)
                    )
                )
            }
        }

        override fun reportUnInterceptExceptionLog(thread: Thread, ex: Throwable, handler: UncaughtExceptionHandler) {
            ex?.let {
                dialogMsg(
                    String.format(
                        getString(R.string.unIntercepted_exception),
                        Log.getStackTraceString(ex)
                    )
                )
            }
        }

        override fun preUncaughtException(thread: Thread, ex: Throwable, handler: UncaughtExceptionHandler) {

        }

        override fun onStopCatch(handler: UncaughtExceptionHandler) {
            toastMsg(getString(R.string.btn_stop_catch))
        }

        override fun onStartCatch(handler: UncaughtExceptionHandler) {
            toastMsg(getString(R.string.btn_start_catch))

        }
    }

    private fun toastMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun dialogMsg(msg: String) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.dialog_title))
        dialog.setMessage(msg)
        dialog.setCancelable(true)
        dialog.show().window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, 900)
    }
}
