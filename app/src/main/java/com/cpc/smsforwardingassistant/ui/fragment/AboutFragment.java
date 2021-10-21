package com.cpc.smsforwardingassistant.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cpc.smsforwardingassistant.R;
import com.cpc.smsforwardingassistant.dao.database.MySQLiteOpenHelp;
import com.cpc.smsforwardingassistant.dao.db.DBEngine;
import com.cpc.smsforwardingassistant.dao.room.ShortMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.util.ObjectUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment {

    private static final String TAG = "数据库";
    private DBEngine dbEngine;
    private Context context;
    private View rootView;
    private Button btnDB;
    private Button btnDBQuery;
    private Button btnDBInsert;
    private Button btnDBUpdate;
    private Button btnDBDelete;
    private Button btnRoomQuery;
    private Button btnRoomInsert;
    private Button btnRoomUpdate;
    private Button btnRoomDelete;
    private Button btnRoomDeleteAll;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (ObjectUtil.isNull(rootView)){
            rootView = inflater.inflate(R.layout.fragment_about, container, false);
        }
        context = rootView.getContext();
        //初始化数据库引擎
        if (dbEngine == null){
            dbEngine = DBEngine.getInstance(context);
        }

        btnDB = rootView.findViewById(R.id.btn_db);
        btnDBQuery = rootView.findViewById(R.id.btn_db_query);
        btnDBInsert = rootView.findViewById(R.id.btn_db_insert);
        btnDBUpdate = rootView.findViewById(R.id.btn_db_update);
        btnDBDelete = rootView.findViewById(R.id.btn_db_delete);

        btnRoomQuery = rootView.findViewById(R.id.btn_room_query);
        btnRoomInsert = rootView.findViewById(R.id.btn_room_insert);
        btnRoomUpdate = rootView.findViewById(R.id.btn_room_update);
        btnRoomDelete = rootView.findViewById(R.id.btn_room_delete);
        btnRoomDeleteAll = rootView.findViewById(R.id.btn_room_delete_all);
        btnDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySQLiteOpenHelp help = MySQLiteOpenHelp.getInstance(context);
                //help.getReadableDatabase()或help.getWritableDatabase()之后才会创建数据库
                help.getReadableDatabase();
            }
        });
        btnDBQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySQLiteOpenHelp help = MySQLiteOpenHelp.getInstance(context);
                SQLiteDatabase db = help.getReadableDatabase();
                if (db.isOpen()){
                    String sql = "select * from short_messages";
                    //迭代游标
                    Cursor cursor = db.rawQuery(sql, null);
                    while (cursor.moveToNext()){
                        int _id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                        String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));
                        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                        String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                        Long date = cursor.getLong(cursor.getColumnIndexOrThrow("date"));
                        Log.d(TAG, "查询到数据:_id:"+_id+",phoneNumber:"+phoneNumber+",title:"+title+",content:"+content+",date:"+date);
                    }
                    cursor.close();
                    db.close();
                }
            }
        });
        btnDBInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySQLiteOpenHelp help = MySQLiteOpenHelp.getInstance(context);
                SQLiteDatabase db = help.getWritableDatabase();
                if (db.isOpen()){
                    long time = new Date().getTime();
                    String phoneNumber = String.valueOf(time);
                    String data = String.valueOf(time);;
                    String sql = "insert into short_messages(phone_number,title,content,date) values('"+phoneNumber+"','标题','内容','"+data+"')";
                    db.execSQL(sql);
                    db.close();
                }
            }
        });
        btnDBUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySQLiteOpenHelp help = MySQLiteOpenHelp.getInstance(context);
                SQLiteDatabase db = help.getWritableDatabase();
                if (db.isOpen()){
                    String sql = "update short_messages set title = ?,content = ? where _id = ?";
                    db.execSQL(sql,new Object[]{"标题5","内容5",5});
                    db.close();
                }
            }
        });
        btnDBDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySQLiteOpenHelp help = MySQLiteOpenHelp.getInstance(context);
                SQLiteDatabase db = help.getWritableDatabase();
                if (db.isOpen()){
                    String sql = "delete from short_messages where _id = ?";
                    db.execSQL(sql,new Object[]{5});
                    db.close();
                }
            }
        });
        //使用Room组件之后
        btnRoomQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbEngine.queryAllShortMessages();
                Log.d(TAG, "数据查询完毕");
            }
        });
        btnRoomInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ShortMessage> shortMessages = new ArrayList<>();
                for (int i = 1; i <= 1000; i++) {
                    shortMessages.add(new ShortMessage("138388"+i,"标题"+i,"内容"+i,new Date().getTime(),1,null));
                }
                dbEngine.insertShortMessages(shortMessages.toArray(new ShortMessage[shortMessages.size()]));
                Log.d(TAG, "数据插入成功");
            }
        });
        btnRoomUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShortMessage newShortMessage = new ShortMessage(999,"13838838438", "全新标题", "全新内容", new Date().getTime(), 1, null);
                dbEngine.updateShortMessages(newShortMessage);
                Log.d(TAG, "数据修改成功");
            }
        });
        btnRoomDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbEngine.deleteShortMessages(new ShortMessage(999));
                Log.d(TAG, "数据删除成功");
            }
        });
        btnRoomDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbEngine.deleteAllShortMessages();
                Log.d(TAG, "数据清空成功");
            }
        });





        return rootView;
    }

}