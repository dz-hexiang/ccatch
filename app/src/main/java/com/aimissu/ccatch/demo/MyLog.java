package com.aimissu.ccatch.demo;

import android.util.Log;

import com.aimissu.ccatch.log.ILog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.jvm.functions.Function0;

/**
 * @Date Created: 2019-10-31
 * @Author: hexiang
 * @Description:
 */
public class MyLog implements ILog {
    @Override
    public void v(@NotNull String tag, @NotNull Function0<?> message) {

    }

    @Override
    public void v(@NotNull String tag, @NotNull String format, @Nullable Object... args) {
        Log.v(tag, String.format(format, args));
    }

    @Override
    public void i(@NotNull String tag, @NotNull Function0<?> message) {
        Log.i(tag, message.toString());
    }

    @Override
    public void i(@NotNull String tag, @NotNull String format, @Nullable Object... args) {
        Log.i(tag, String.format(format, args));
    }

    @Override
    public void d(@NotNull String tag, @NotNull Function0<?> message) {
        Log.d(tag, message.toString());
    }

    @Override
    public void d(@NotNull String tag, @NotNull String format, @Nullable Object... args) {
        Log.d(tag, String.format(format, args));
    }

    @Override
    public void w(@NotNull String tag, @NotNull Function0<?> message) {
        Log.w(tag, message.toString());
    }

    @Override
    public void w(@NotNull String tag, @NotNull String format, @Nullable Object... args) {
        Log.w(tag, String.format(format, args));
    }

    @Override
    public void e(@NotNull String tag, @NotNull Function0<?> message, @Nullable Throwable error) {
        Log.e(tag, message.toString());
    }

    @Override
    public void e(@NotNull String tag, @NotNull String format, @Nullable Throwable error, @Nullable Object... args) {
        Log.e(tag, String.format(format, args));
    }
}
