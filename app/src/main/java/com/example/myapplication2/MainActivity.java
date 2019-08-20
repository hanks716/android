package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication2.util.AselTran;
import com.example.myapplication2.util.SOAPLoadTask;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SOAPLoadTask.OnResultListener, SOAPLoadTask.OnErrorListener{

    EditText userid, userpwd;
    Button btn_login;
    Toast m_toast;
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userid = findViewById(R.id.userid);
        userpwd = findViewById(R.id.userpwd);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClick2();
            }
        });
      /*
        try {
            btn_login.setOnClickListener(this);
            if(setting.getBoolean("chk_auto", false)){
                userid.setText(setting.getString("User_Id", null));
                userpwd.setText(setting.getString("User_pwd", null));
               // chk_auto.setChecked(true);
                onClick(null);
            }
        }
        catch (Exception err)
        {

        }
        */
    }

    public void onClick2()
    {
        if(userid.length()==0)
        {
            if(m_toast!=null)
                m_toast.cancel();
            m_toast= Toast.makeText(this,"아이디를 입력하세요.",Toast.LENGTH_SHORT);
            m_toast.show();

            userid.requestFocus();
            return;
        }

        if(userpwd.length()==0)
        {
            if(m_toast!=null)
                m_toast.cancel();

            m_toast= Toast.makeText(this,"비밀번호를 입력하세요.",Toast.LENGTH_SHORT);
            m_toast.show();

            userpwd.requestFocus();
            return;
        }
/*
        if(chk_auto.isChecked()){

            Toast.makeText(this, "자동 로그인", Toast.LENGTH_SHORT).show();
            editor.putBoolean("chk_auto", true);


        }
        if(!(chk_auto.isChecked())){
            editor.clear();
            editor.commit();
        }*/


        List<String> _param = new ArrayList<String>();
        _param.add(userid.getText().toString());
        _param.add(userpwd.getText().toString());
        _param.add("");

        //프로시저 == 틀 제목
        new SOAPLoadTask((SOAPLoadTask.OnResultListener) this, this).execute("usp_MOB_GetLoginInfo", SOAPLoadTask.convertParams(_param));
    }

    @Override
    public void onGetResult(String processer, SoapObject _result)
    {
        SoapObject rs = (SoapObject)_result.getProperty(1);

        String Flag = AselTran.GetValue(rs, "RFLAG");				// 결과
        String rMsg = AselTran.GetValue(rs, "RMSG");				// 메세지
        String user_nm = AselTran.GetValue(rs, "user_nm");
        if (!Flag.equals("T")) {
            Toast.makeText(this, rMsg, Toast.LENGTH_SHORT).show();
            return;
        }
     /*   editor.putString("User_Id",userid.getText().toString());
        Log.wtf(userid.getText().toString(),"ss");
        editor.putString("User_pwd",userpwd.getText().toString());
        editor.commit();*/
        Intent intent = new Intent(MainActivity.this, ListActivity.class);      // 지금 화면에서 다음화면으로 전환
        intent.putExtra("userid", userid.getText().toString());         //다음화면으로 값 전달
        intent.putExtra("usernm", user_nm);
        startActivity(intent);
        finish();

    }

    @Override
    public void onError(SoapObject _result) {
        Toast.makeText(this,_result+"통신에 실패하였습니다.",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
