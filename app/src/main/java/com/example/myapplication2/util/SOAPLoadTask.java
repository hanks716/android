package com.example.myapplication2.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 이영곤 on 2015-01-28.
 */
public class SOAPLoadTask extends AsyncTask<String, Integer, SoapObject>
{
    Context mContext;

    private OnErrorListener m_errorListener=null;
	private String m_tag=null;
	private OnResultListener m_resultListener=null;
    private String m_proceser;

        public SOAPLoadTask(OnResultListener resultListener){
            this(resultListener, null);
        }

        public SOAPLoadTask(OnResultListener resultListener, OnErrorListener errorListener){
            m_resultListener=resultListener;
            m_errorListener=errorListener;
        }

        public SOAPLoadTask(OnResultListener resultListener, OnErrorListener errorListener,String tag){
            m_resultListener=resultListener;
            m_errorListener=errorListener;
            m_tag = tag;
        }

        public SOAPLoadTask(OnResultListener resultListener, OnErrorListener errorListener, Context context){
        m_resultListener = resultListener;
        m_errorListener = errorListener;

        //asyncDialog = new ProgressDialog(context);
        mContext = context;
    }

    @Override
    protected SoapObject doInBackground(String... params) {
            SoapObject request = new SoapObject(COM.NAMESPACE,  COM.SEHARCH_METHOD);
            m_proceser=params[0];

            request.addProperty("proc_nm", params[0]);
            request.addProperty("_params", params[1]);

	        CLog.i("URL=" + params[0]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setAddAdornments(false);
            envelope.setOutputSoapObject(request);

            HttpTransportSE adt = new HttpTransportSE(COM.URLSRV);

            try
            {
                Log.d("tag", envelope.toString());
                Log.d("tag2", COM.SOAP_ACTION + COM.SEHARCH_METHOD);
                adt.call(COM.SOAP_ACTION + COM.SEHARCH_METHOD, envelope);
                SoapObject rec = (SoapObject)envelope.getResponse();

                return rec;
            }
            catch (Exception err)
            {
                err.printStackTrace();
                Log.d("tagerr",  err.getMessage());
            }
            return null;
     }

    @Override
    protected void onPreExecute() {
        if (mContext != null) {
        }

        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(SoapObject rec)
    {
        if(rec==null)
        {
            Log.e("onPostExecute","SoapObject is null");
            if(m_errorListener!=null)
                m_errorListener.onError(null);

            return;
        }

        SoapObject resultDataSet  = (SoapObject)rec.getProperty(1);		// 반환값들...(0)에는 xml헤더정보가 들어있음.
        resultDataSet = (SoapObject)resultDataSet.getProperty(0);

        // 결과확인
        SoapObject eRet = (SoapObject)resultDataSet.getProperty(0);	// 에러정보
        String ret = eRet.getProperty("RET").toString();
        if (!ret.equals("0"))
        {
            Log.e("onPostExecute",eRet.getProperty("MSG").toString());
            if(m_errorListener!=null)
            {
                m_errorListener.onError(eRet);
                return;
            }
        }

        CLog.i(resultDataSet.toString());
	    if(m_tag!=null)
	        resultDataSet.addProperty("tag", m_tag);

        if(m_resultListener!=null)
            m_resultListener.onGetResult(m_proceser,resultDataSet);
     }

    public static String convertParams(String... params)
    {
        List<String> _param = new ArrayList<String>();
        for(String param:params)
        _param.add(param);

        return convertParams(_param);
    }

    public static String convertParams(List<String> _params)
    {
        String params = "";
        int cnt = 0;
        for (String o : _params)
        {
            if (cnt > 0)
                params += "^";
            params += COM.GetBase64Enc(o);
            CLog.i(o);
            cnt++;
        }
        return params;
    }

    public interface OnErrorListener
    {
        public void onError(SoapObject _result);
    }

    public interface OnResultListener
    {
        /**
         * http 통신 리스너<br>
         * async로 통신후, UI Thread에서 호출됨
         * @param _result 결과값
         */
        public void onGetResult(String processer, SoapObject _result);
    }
}
