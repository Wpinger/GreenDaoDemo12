package com.fh.greendaodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fh.greeddao.db.core.DBUtils;
import com.fh.greeddao.db.entity.Song;
import com.fh.greeddao.gen.DaoSession;
import com.fh.greeddao.gen.SongDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SongDao songDao = null;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        Button btn_add = (Button) findViewById(R.id.btn_add);
        Button btn_del = (Button) findViewById(R.id.btn_del);
        Button btn_update = (Button) findViewById(R.id.btn_update);
        Button btn_query = (Button) findViewById(R.id.btn_query);
        tv = (TextView) findViewById(R.id.tv);

        // 获取Song这张表的操作类SongDao
        DaoSession daoSession = DBUtils.getInstace().getDaoSession(getApplicationContext());
        songDao = daoSession.getSongDao();

        // 增
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //增加
                Song song = new Song();
                String str = "green-dao-test"+(int)(1+Math.random()*1000);
                song.setSongName(str);
                songDao.insert(song);
                tv.setText("已增加:"+str);
            }
        });


        // 删
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songDao.deleteAll();
                tv.setText("数据已全部删除");
            }
        });


        // 改
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Song> songList = songDao.queryBuilder().list();
                if (null != songList && songList.size() > 0) {
                    Song song = songList.get(0);
                    song.setSongName("aaa");
                    songDao.update(song);
                    tv.setText("已修改首条数据为aaa");
                } else {
                    tv.setText("无数据可修改");
                }
            }
        });


        // 查
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Song> songList = songDao.queryBuilder().list();
                if (null != songList && songList.size() > 0) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("查询结果:  \n");
                    for (int i = 0;i<songList.size();i++) {
                        stringBuffer.append("第"+i+"条数据 : "+songList.get(i).toString()+"\n");
                    }
                    tv.setText(""+stringBuffer);
                } else {
                    tv.setText("查询结果为null");
                }
            }
        });

    }
}
