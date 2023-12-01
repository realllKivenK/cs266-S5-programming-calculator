package easy.tuto.easycalculator;

import static easy.tuto.easycalculator.Constants.TIME;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {

    DecimalFormat formatter = new DecimalFormat("#,###.##");
    EventsData events;
    ListView listView;  // ย้ายการประกาศ listView มาที่นี่
    final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.listView);  // ย้าย findViewById มาที่นี่
        final Button clearButton = findViewById(R.id.clearButton);

        final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<>();

        events = new EventsData(this);

        try {
            // ดึงข้อมูลจาก SQLite
            Cursor cursor = getEvents();

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("myFormat", String.valueOf(cursor.getString(cursor.getColumnIndex(EventsData.TIME))));
                        map.put("weight", cursor.getString(cursor.getColumnIndex(EventsData.SOLUTION)));
                        map.put("criteria", cursor.getString(cursor.getColumnIndex(EventsData.RESULT)));
                        MyArrList.add(map);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            Collections.reverse(MyArrList);

            SimpleAdapter simpleAdapter = new SimpleAdapter(ListActivity.this, MyArrList, R.layout.activity_column,
                    new String[]{"myFormat", "weight", "criteria"},
                    new int[]{R.id.day_id, R.id.col_solution, R.id.col_result});
            listView.setAdapter(simpleAdapter);

            clearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearAllData();
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long id) {
                    String myFormat = MyArrList.get(position).get("myFormat");
                    String solution = MyArrList.get(position).get("solution");
                    String result = MyArrList.get(position).get("result");

                    String message = "วันที่" + myFormat + "\n"
                            + "น้ำหนัก : " + solution + "\n" + "\n"
                            + "หมายเหตุ : " + result;
                    Toast.makeText(ListActivity.this, message, Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

            Log.e("ListActivity", "Error accessing database: " + e.getMessage());

            Toast.makeText(this, "Error accessing database: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Cursor getEvents() {
        SQLiteDatabase db = events.getReadableDatabase();
        String[] columns = {EventsData.TIME, EventsData.SOLUTION, EventsData.RESULT};
        String orderBy = TIME;
        return db.query(EventsData.TABLE_NAME, columns, null, null, null, null, orderBy);
    }

    private void clearAllData() {
        try {
            SQLiteDatabase db = events.getWritableDatabase();
            db.delete(EventsData.TABLE_NAME, null, null);

            MyArrList.clear();
            SimpleAdapter simpleAdapter = new SimpleAdapter(ListActivity.this, MyArrList, R.layout.activity_column,
                    new String[]{"myFormat", "weight", "criteria"},
                    new int[]{R.id.day_id, R.id.col_solution, R.id.col_result});
            listView.setAdapter(simpleAdapter);

            Toast.makeText(this, "ลบข้อมูลทั้งหมดแล้ว", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("ListActivity", "Error clearing database: " + e.getMessage());
            Toast.makeText(this, "Error clearing database: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // สร้าง Intent เพื่อไปยัง MainActivity
                Intent intent = new Intent(this, MainActivity.class);
                // เรียก startActivity และปิด Activity ปัจจุบัน
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


//บันทึกข้อมูลปกติ
//public class ListActivity extends AppCompatActivity {
//    DecimalFormat formatter = new DecimalFormat("#,###.##");
//    EventsData events;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list);
//        final ListView listView = findViewById(R.id.listView);
//        final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
//        events = new EventsData(this);
//
//        try {
//            // ดึงข้อมูลจาก SQLite
//            Cursor cursor = getEvents();
//
//            if (cursor != null) {
//                if (cursor.moveToFirst()) {
//                    do {
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put("myFormat", String.valueOf(cursor.getString(cursor.getColumnIndex(EventsData.TIME)))); // เปลี่ยนนี้เป็น getString
//                        map.put("weight", cursor.getString(cursor.getColumnIndex(EventsData.SOLUTION)));
//                        map.put("criteria", cursor.getString(cursor.getColumnIndex(EventsData. RESULT)));
//                        MyArrList.add(map);
//                    } while (cursor.moveToNext());
//                }
//                cursor.close();
//            }
//            Collections.reverse(MyArrList);
//
//            SimpleAdapter simpleAdapter = new SimpleAdapter(ListActivity.this, MyArrList, R.layout.activity_column,
//                    new String[]{"myFormat", "weight", "criteria"},
//                    new int[]{R.id.day_id, R.id.col_solution, R.id.col_result});
//            listView.setAdapter(simpleAdapter);
//
//
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long id) {
//                    String myFormat = MyArrList.get(position).get("myFormat");
//                    String solution = MyArrList.get(position).get("solution");
//                    String result = MyArrList.get(position).get("result");
//
//                    // แสดงข้อมูลในรูปแบบ Toast
//                    String message = "วันที่" + myFormat + "\n"
//                            + "น้ำหนัก : " + solution + "\n" + "\n"
//                            + "หมายเหตุ : " + result;
//                    Toast.makeText(ListActivity.this, message, Toast.LENGTH_LONG).show();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        Log.e("ListActivity", "Error accessing database: " + e.getMessage());
//
//        // แสดง Toast เพื่อแจ้งเตือนถึงข้อผิดพลาด
//        Toast.makeText(this, "Error accessing database: " + e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
//
//
//    private Cursor getEvents() {
//        SQLiteDatabase db = events.getReadableDatabase();
//        String[] columns = {EventsData.TIME, EventsData.SOLUTION, EventsData.RESULT};
//        String orderBy = TIME;
//        return db.query(EventsData.TABLE_NAME, columns, null, null, null, null, orderBy);
//    }
//}



