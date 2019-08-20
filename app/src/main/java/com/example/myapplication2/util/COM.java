package com.example.myapplication2.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.TypedValue;

import java.util.ArrayList;

/**
 * Created by yg102 on 2016-03-22.
 */
public class COM
{

    public static String SERVER_ADDR = "http://mall.ppang.biz/";

    public static String SOAP_ACTION = "http://tempuri.org/";
    public static String SEHARCH_METHOD = "AndroidSearch";
    public static String SAVE_METHOD = "AndroidSave";
    public static String NAMESPACE = "http://tempuri.org/";
    public static String IMAGE_SAVE = "ImageSave";

	public static String URLSRV = SERVER_ADDR+"WebSrv/AndroidService.asmx";
	public static String IMAGE_URL= SERVER_ADDR+"WebSrv/COM/getMobileImage.ashx";
	public static String DOCUMENT_URL= SERVER_ADDR+"WebSrv/COM/getDocument.aspx";

	public static ArrayList<Bitmap> advImage = new ArrayList<Bitmap>();   // 배너이미지
	public static ArrayList<String> advLink = new ArrayList<String>();    // 배너링크.

	// base64로 문자열 인코딩한다.
	public static String GetBase64Enc(String val)
	{
		return Base64.encodeToString(val.getBytes(), 0);
	}

	public static float convertDpToPixel(float dp, Context context)
	{
		Resources resources = context.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				resources.getDisplayMetrics());
		return px;
	}
/*
	public static int[] c_intArrSoundRes = new int[]{
			R.raw.type201,
			R.raw.type202,
			R.raw.type203,
			R.raw.type204,
			R.raw.type205,
			R.raw.type206,
			R.raw.type207,
			R.raw.type208
	};

	public static String[] c_strArrSoundName=new String[]{
			"알림음1",
			"알림음2",
			"알림음3",
			"알림음4",
			"알림음5",
			"알림음6",
			"알림음7",
			"알림음8"
	};*/
}
