package com.example.sqlitedatabase_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CongViecAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<CongViec> congViecList;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
    }

    @Override
    public int getCount() {
        return congViecList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView tvTenCV;
        ImageView imgSuaCV, imgXoaCV;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTenCV = convertView.findViewById(R.id.dong_cong_viec_ten);
            viewHolder.imgSuaCV = convertView.findViewById(R.id.IMG_Sua_dong_cv);
            viewHolder.imgXoaCV = convertView.findViewById(R.id.IMG_Xoa_dong_cv);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CongViec congViec = congViecList.get(position);
        viewHolder.tvTenCV.setText(congViec.getTenCV());

        //bắt sự kiện xóa và sửa
        viewHolder.imgSuaCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.SuaCongViec(congViec.getTenCV(), congViec.getIdCV());
            }
        });
        viewHolder.imgXoaCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoaCongViec(congViec.getTenCV(), congViec.getIdCV());

            }
        });


        return convertView;
    }
}
