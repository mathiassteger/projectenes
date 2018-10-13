package com.mseb.projectenes.logging;

import com.badlogic.gdx.ApplicationLogger;

import java.util.Date;

public class CustomLogger implements ApplicationLogger {
//    @Override
//    public void log(String tag, String message) {
//    }
//    @Override
//    public void log(String tag, String message, Throwable exception) {
//    }
//    @Override
//    public void error(String tag, String message) {
//    }
//    @Override
//    public void error(String tag, String message, Throwable exception) {
//    }
//    @Override
//    public void debug(String tag, String message) {
//    }
//    @Override
//    public void debug(String tag, String message, Throwable exception) {
//    }

    @Override
    public void log(String tag, String message) {
        String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();

        System.out.println("[" + new Date() + "] " + className + "." + methodName + "." + lineNumber + ":\t" + message);
    }

    @Override
    public void log(String tag, String message, Throwable exception) {
        String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();

        System.err.println("[" + new Date() + "] " + className + "." + methodName + "." + lineNumber + ":\t" + message + "\n" + exception.toString());
    }

    @Override
    public void error(String tag, String message) {
        String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();

        System.out.println("[" + new Date() + "] " + className + "." + methodName + "." + lineNumber + ":\t" + message);
    }

    @Override
    public void error(String tag, String message, Throwable exception) {
        String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();

        System.err.println("[" + new Date() + "] " + className + "." + methodName + "." + lineNumber + ":\t" + message + "\n" + exception.toString());
    }

    @Override
    public void debug(String tag, String message) {
        String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();

        System.out.println("[" + new Date() + "] " + className + "." + methodName + "." + lineNumber + ":\t" + message);
    }

    @Override
    public void debug(String tag, String message, Throwable exception) {
        String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();

        System.err.println("[" + new Date() + "] " + className + "." + methodName + "." + lineNumber + ":\t" + message + "\n" + exception.toString());
    }
}