package com.example.sqlitedatabase_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView lvdanhsachCV;
    ArrayList<CongViec> congViecArrayList;
    CongViecAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvdanhsachCV = (ListView) findViewById(R.id.lvDanhSachCongViec);
        congViecArrayList = new ArrayList<>();
        adapter = new CongViecAdapter(this, R.layout.dong_cong_viec, congViecArrayList);
        lvdanhsachCV.setAdapter(adapter);

        database = new Database(this, "chang", null,1);
        //tạo bảng
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(100))");

        //insert data
        //database.QueryData("INSERT INTO CongViec VALUES(null, 'Dev Android')");
        GetDataCongViec();
        
    }
    private void GetDataCongViec(){
        Cursor dataCongViec = database.GetData("SELECT * FROM CongViec");
        congViecArrayList.clear();
        while (dataCongViec.moveToNext()){
            int id   = dataCongViec.getInt(0);
            String ten = dataCongViec.getString(1);
            
            congViecArrayList.add(new CongViec(id, ten));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.them){
            DialogThem();
        }



        return super.onOptionsItemSelected(item);
    }

    private void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_cong_viec);
        dialog.show();
        EditText editThem  = dialog.findViewById(R.id.DIALOG_EDT_Them);
        Button btnThem = dialog.findViewById(R.id.DIALOG_BTNTHEM);
        Button btnHuy = dialog.findViewById(R.id.DIALOG_HUY);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenmoi = editThem.getText().toString().trim();
                if (tenmoi.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên công việc cần thêm", Toast.LENGTH_SHORT).show();
                }else {
                    database.QueryData("INSERT INTO CongViec VALUES(null, '"+tenmoi+"')");
                    Toast.makeText(MainActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataCongViec();



                }

            }
        });

    }
    public void SuaCongViec(String ten, int id  ){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua_cong_viec);

        Button btnHuy =(Button) dialog.findViewById(R.id.DIALOG_HUYSUA);
        Button btnSua = (Button) dialog.findViewById(R.id.DIALOG_BTNSUA);
        EditText edtTenSua = (EditText) dialog.findViewById(R.id.DIALOG_EDT_SUA);
        edtTenSua.setText(ten);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenmoii = edtTenSua.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV = '"+tenmoii+"' WHERE Id ='"+id+"'");
                Toast.makeText(MainActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataCongViec();
            }
        });
        dialog.show();
    }
    public void DialogXoaCongViec(String tencv, int id){
        AlertDialog.Builder dialogxoa = new AlertDialog.Builder(this);
        dialogxoa.setMessage("Bạn có muốn xóa "+tencv+" này hay không");
        dialogxoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM CongViec WHERE Id = '"+id+"'");
                Toast.makeText(MainActivity.this, "Đã xóa " + tencv, Toast.LENGTH_SHORT).show();
                GetDataCongViec();
            }
        });
        dialogxoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogxoa.show();
    }

}