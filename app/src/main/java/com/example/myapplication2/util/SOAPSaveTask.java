package com.example.myapplication2.util;

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
public class SOAPSaveTask extends AsyncTask<String, Integer,SoapObject>
{
    private SOAPLoadTask.OnErrorListener m_errorListener=null;
    private SOAPLoadTask.OnResultListener m_resultListener=null;
    private String m_proceser;

	public SOAPSaveTask(){
            this(null);
    }

    public SOAPSaveTask(SOAPLoadTask.OnResultListener resultListener){
            this(resultListener,null);
    }

    public SOAPSaveTask(SOAPLoadTask.OnResultListener resultListener, SOAPLoadTask.OnErrorListener errorListener){
            m_resultListener=resultListener;
            m_errorListener=errorListener;
        }

    @Override
    protected SoapObject doInBackground(String... params) {
        SoapObject request = new SoapObject(COM.NAMESPACE,  COM.SAVE_METHOD);
        m_proceser=params[0];

	    String tableName = "SAVE";
	    request.addProperty("tbName", tableName);

        request.addProperty("proc_nm", params[0]);
        request.addProperty("_params", params[1]);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        //envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);

        HttpTransportSE adt = new HttpTransportSE(COM.URLSRV);

        try
        {
            Log.d("tag", envelope.toString());
            adt.call(COM.SOAP_ACTION + COM.SAVE_METHOD, envelope);
            SoapObject rec = (SoapObject)envelope.getResponse();

            return rec;
        }
        catch (Exception err)
        {
            err.printStackTrace();
        }
        return null;
     }

    @Override
    protected void onPostExecute(SoapObject rec) {
            SoapObject resultDataSet  = (SoapObject)rec.getProperty(1);		// 반환값들...(0)에는 xml헤더정보가 들어있음.
            resultDataSet = (SoapObject)resultDataSet.getProperty(0);

            // 결과확인
            SoapObject eRet = (SoapObject)resultDataSet.getProperty(0);	// 에러정보
            String ret = eRet.getProperty("RET").toString();
            if (!ret.equals("0"))
            {
                Log.e("onPostExecute",eRet.getProperty("MSG").toString());
                if(m_errorListener!=null)
                m_errorListener.onError(eRet);
            }

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
	                if(o==null)
		                continue;

                    if (cnt > 0)
                        params += "^";

                    params += COM.GetBase64Enc(o);

                    cnt++;
                }
                return params;
            }

}
