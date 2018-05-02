package com.fh.greeddao.db.core;

import android.content.Context;

import com.fh.greeddao.gen.DaoMaster;
import com.fh.greeddao.gen.DaoSession;

/**
 * Created by ChuanWang on 2018/4/28.
 */

public class DBUtils {
    private final String DB_NAME = "android.db";
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private DBUtils() {
    }

    public static DBUtils mInstance = new DBUtils();

    public static DBUtils getInstace() {

        return mInstance;
    }

    public DaoMaster getDaoMaster(Context mContext) {

        DaoMaster.DevOpenHelper mHelper = new DaoMaster
                .DevOpenHelper(mContext, DB_NAME, null);
        daoMaster = new DaoMaster(mHelper.getWritableDatabase());
        return daoMaster;
    }

    public DaoSession getDaoSession(Context mContext) {

        if (daoSession == null) {

            if (daoMaster == null) {
                getDaoMaster(mContext);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

}
