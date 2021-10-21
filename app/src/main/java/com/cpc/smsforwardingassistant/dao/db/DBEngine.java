package com.cpc.smsforwardingassistant.dao.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cpc.smsforwardingassistant.dao.room.ShortMessage;
import com.cpc.smsforwardingassistant.dao.room.ShortMessageDao;
import com.cpc.smsforwardingassistant.dao.room.ShortMessageDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DBEngine {

    private static DBEngine instance;

    private ShortMessageDao shortMessageDao;

    private DBEngine(Context context) {
        ShortMessageDatabase database = ShortMessageDatabase.getInstance(context);
        shortMessageDao = database.getShortMessageDao();
    }

    /**
     * 单例模式
     *
     * @param context
     * @return
     */
    public static DBEngine getInstance(Context context) {
        if (instance == null) {
            synchronized (DBEngine.class) {
                if (instance == null) {
                    instance = new DBEngine(context);
                }
            }
        }
        return instance;
    }

    /**
     * 新增
     *
     * @param shortMessages
     */
    public void insertShortMessages(ShortMessage... shortMessages) {
        new InsertAsyncTask(shortMessageDao).execute(shortMessages);
    }

    /**
     * 更新
     *
     * @param shortMessages
     */
    public void updateShortMessages(ShortMessage... shortMessages) {
        new UpdateAsyncTask(shortMessageDao).execute(shortMessages);
    }

    /**
     * 条件删除
     *
     * @param shortMessages
     */
    public void deleteShortMessages(ShortMessage... shortMessages) {
        new DeleteAsyncTask(shortMessageDao).execute(shortMessages);
    }

    /**
     * 全部删除
     */
    public void deleteAllShortMessages() {
        new DeleteAllAsyncTask(shortMessageDao).execute();
    }

    /**
     * 查询全部
     */
    public List<ShortMessage> queryAllShortMessages() {
        QueryAllAsyncTask task = new QueryAllAsyncTask(shortMessageDao);
        task.execute();
        try {
            List<ShortMessage> shortMessages = task.get();
            return shortMessages;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 异步操作新增
     */
    static class InsertAsyncTask extends AsyncTask<ShortMessage, Void, Void> {

        private ShortMessageDao dao;

        public InsertAsyncTask(ShortMessageDao shortMessageDao) {
            dao = shortMessageDao;
        }

        @Override
        protected Void doInBackground(ShortMessage... shortMessages) {
            dao.insertShortMessage(shortMessages);
            return null;
        }

    }

    /**
     * 异步操作更新
     */
    static class UpdateAsyncTask extends AsyncTask<ShortMessage, Void, Void> {

        private ShortMessageDao dao;

        public UpdateAsyncTask(ShortMessageDao shortMessageDao) {
            dao = shortMessageDao;
        }

        @Override
        protected Void doInBackground(ShortMessage... shortMessages) {
            dao.updateShortMessage(shortMessages);
            return null;
        }

    }

    /**
     * 异步操作条件删除
     */
    static class DeleteAsyncTask extends AsyncTask<ShortMessage, Void, Void> {

        private ShortMessageDao dao;

        public DeleteAsyncTask(ShortMessageDao shortMessageDao) {
            dao = shortMessageDao;
        }

        @Override
        protected Void doInBackground(ShortMessage... shortMessages) {
            dao.deleteShortMessage(shortMessages);
            return null;
        }

    }

    /**
     * 异步操作全部删除
     */
    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private ShortMessageDao dao;

        public DeleteAllAsyncTask(ShortMessageDao shortMessageDao) {
            dao = shortMessageDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllShortMessage();
            return null;
        }

    }

    /**
     * 异步操作全部删除
     */
    static class QueryAllAsyncTask extends AsyncTask<Void, Void, List<ShortMessage>> {

        private ShortMessageDao dao;

        public QueryAllAsyncTask(ShortMessageDao shortMessageDao) {
            dao = shortMessageDao;
        }

        @Override
        protected List<ShortMessage> doInBackground(Void... voids) {
            List<ShortMessage> allShortMessage = dao.findAllShortMessage();
            Log.d("数据库", "查到数据:" + allShortMessage.size()+"条");

//            for (ShortMessage sm : allShortMessage) {
////                Log.d("数据库", "查到数据:" + sm.toString());
//            }
            return allShortMessage;
        }

    }


}
