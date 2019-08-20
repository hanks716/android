package com.example.myapplication2.util;

import android.util.Log;


public class CLog
{

	public CLog()
	{
	}

	public static int d(String _msg)
	{
		StackTraceElement element = Thread.currentThread().getStackTrace()[3];
		String tag = makeTag(element);
		return Log.d(tag, _msg);
	}

	public static int v(String _msg)
	{
		
		StackTraceElement element = Thread.currentThread().getStackTrace()[3];
		String tag = makeTag(element);
		return Log.v(tag, _msg);
	}

	public static int e(String _msg)
	{
		
		StackTraceElement element = Thread.currentThread().getStackTrace()[3];
		String tag = makeTag(element);
		return Log.e(tag, _msg);
	}

	public static int i(String _msg)
	{
		StackTraceElement element = Thread.currentThread().getStackTrace()[3];
		String tag = makeTag(element);
		return Log.i(tag, _msg == null ? "msg==null" : _msg);
	}
	
	public static int w(String _msg)
	{

		StackTraceElement element = Thread.currentThread().getStackTrace()[3];
		String tag = makeTag(element);
		return Log.w(tag, _msg);
	}

	private static String makeTag(StackTraceElement element)
	{
		String str=element.getFileName();

		return element.getMethodName() + " (" + str + ":" + element.getLineNumber() + ")";
	}
}
