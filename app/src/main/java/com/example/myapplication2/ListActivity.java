package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.adapter.ListViewAdapter_take_back;
import com.example.myapplication2.data.ItemData;
import com.example.myapplication2.util.AselTran;
import com.example.myapplication2.util.SOAPLoadTask;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    String userid, usernm;

    ArrayList<ItemData> oData = new ArrayList<>(); //데이터를 담을 리스트 선언
    ListAdapter oAdapter;
    ListView m_oListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list); // 화면 연결

        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        usernm = intent.getStringExtra("usernm");

        m_oListView = (ListView) findViewById(R.id.listview);

        getdata();
    }

    // 로그인 함수
    public void getdata(){
        //프로시저 결과를 받아오는 부분
        SOAPLoadTask.OnResultListener onresult = new SOAPLoadTask.OnResultListener() {
            @Override
            public void onGetResult(String processer, SoapObject _result) {
                try {
                    int list_amount;
                    oData.clear();
                    for (int i = 1; i < _result.getPropertyCount(); i++) {      // row 수: _result.getPropertyCount()
                        SoapObject rs = (SoapObject) _result.getProperty(i);

                        String retReq_no = AselTran.GetValue(rs, "retReq_no");
                        String retReq_seq = AselTran.GetValue(rs, "retReq_seq");                // 결과
                        String cust_nm = AselTran.GetValue(rs, "cust_nm");
                        String arrival_cd = AselTran.GetValue(rs, "arrival_cd");
                        String arrival_nm = AselTran.GetValue(rs, "arrival_nm");
                        String item_cd = AselTran.GetValue(rs, "item_cd");
                        String item_nm = AselTran.GetValue(rs, "item_nm");
                        String item_qty = AselTran.GetValue(rs, "item_qty");

                        if (retReq_seq.equals("") || retReq_seq.equals("0"))
                            retReq_seq = "0";


                        ItemData oItem = new ItemData();
                        oItem.retReq_no = retReq_no;
                        oItem.retReq_seq = retReq_seq;
                        oItem.cust_nm  = cust_nm;
                        oItem.arrival_nm = arrival_nm;
                        oItem.item_cd = item_cd;
                        oItem.item_nm = item_nm;
                        oItem.item_qty  = item_qty;

                        oData.add(oItem);
                    }

                    list_amount = _result.getPropertyCount();
                    if(list_amount==1){
                        Toast.makeText(ListActivity.this, "반품 리스트가 없습니다.", Toast.LENGTH_LONG).show();
                    }

                    oAdapter = new ListViewAdapter_take_back(oData);
                    m_oListView.setAdapter(oAdapter);


                } catch (Exception e) {
                    Log.wtf("why dead?",e.getMessage());
                    Toast.makeText(ListActivity.this, e.getMessage() + "ERROR", Toast.LENGTH_SHORT).show();
                }

            }
        };

        //통신 오류 처리
        SOAPLoadTask.OnErrorListener onError = new SOAPLoadTask.OnErrorListener() {
            @Override
            public void onError(SoapObject _result) {
                Toast.makeText(ListActivity.this, "통신 오류가 발생했습니다", Toast.LENGTH_SHORT).show();
            }
        };

        //프로시저에 인자 전달
        //프로시저 == 틀 제목
        new SOAPLoadTask(onresult, onError).execute("usp_MOB_ReturnTarget_list", SOAPLoadTask.convertParams(userid));
    }
}
