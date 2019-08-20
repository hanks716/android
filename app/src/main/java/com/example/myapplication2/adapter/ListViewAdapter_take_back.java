package com.example.myapplication2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication2.R;
import com.example.myapplication2.data.ItemData;
import com.example.myapplication2.data.ItemData;

import java.util.ArrayList;


public class ListViewAdapter_take_back extends BaseAdapter {
    LayoutInflater inflater = null;
    public ArrayList<ItemData> m_oData = null;
    private int nListCnt = 0;
    public int pos=-1;

    public ListViewAdapter_take_back(ArrayList<ItemData> _oData) {

        m_oData = _oData;
        nListCnt = m_oData.size();

    }

    @Override
    public int getCount() {
        return nListCnt;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final Context context = parent.getContext();
            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.row_takeback_item, parent, false);
        }

        final LinearLayout backc = (LinearLayout) convertView.findViewById(R.id.backc);
        final TextView retReq_no = (TextView) convertView.findViewById(R.id.retReq_no);
        final TextView cust_nm = (TextView) convertView.findViewById(R.id.cust_nm);
        final TextView item_nm = (TextView) convertView.findViewById(R.id.item_nm);
        final TextView item_qty = (TextView) convertView.findViewById(R.id.item_qty);
        final TextView arrival_nm = (TextView) convertView.findViewById(R.id.arrival_nm);

        final View testView = convertView;

        if(m_oData.get(position).backpos == true)
            backc.setBackgroundColor(Color.parseColor("#ffd400"));
        else if (m_oData.get(position).backpos == false && position % 2 == 0)
            backc.setBackgroundColor(Color.parseColor("#f1f1f1"));
        else
            backc.setBackgroundColor(Color.parseColor("#ffffff"));


        retReq_no.setText(m_oData.get(position).retReq_no);
        cust_nm.setText(m_oData.get(position).cust_nm);
        item_nm.setText(m_oData.get(position).item_nm);
        arrival_nm.setText(m_oData.get(position).arrival_nm);
        item_qty.setText("수량 : "+m_oData.get(position).item_qty);

        return convertView;
    }


}



