package cn.studyjams.s1.sj32.zhangqidi.util;

/**
 * 数据库操作类
 * Created by AddiCheung on 2015/12/15.
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import cn.studyjams.s1.sj32.zhangqidi.beans.Taskbean;

public class OperateTable {

    private static final String TAG  = "DBOperation";
    private static final String TABLENAME = "dolist";
    private SQLiteDatabase db = null;

    public OperateTable(SQLiteDatabase db)
    {
        this.db = db;
    }

    /**
     * insert new data into database
     * @param bean
     */
    public void insert(Taskbean bean)
    {
        String sql = "INSERT INTO " + TABLENAME + " (date,type,detail,points,isdone) VALUES (?,?,?,?,?)";
        this.db.execSQL(sql,new String[]{bean.getDate(),bean.getType(),bean.getDetail(),
                Integer.toString(bean.getPoints()),Integer.toString(bean.getIsdone())});
        Log.i(TAG,"insert");
    }

    /**
     * get the total count of Task Or Desire
     * @param type
     * @return
     */
    public int getTotalOfType(String type){
        String sql = "SELECT COUNT(*) FROM "+TABLENAME+" WHERE type = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{type});
        cursor.moveToFirst();
        int times = cursor.getInt(0);
        Log.i(TAG,"getTotalOfType");
        return times;
    }


    /**
     * get total points from db
     * @param type
     * @return
     */
    public int getPoints(String type){
        int points = 0;
        String one = "1";
        Cursor cursor;
        if(type.equals("left")){
            String sql = "SELECT SUM (points) FROM "+TABLENAME+" WHERE isdone = ?";
            cursor = db.rawQuery(sql,new String[]{one});
            Log.i("getPoints",cursor.getColumnCount()+"");
            cursor.moveToFirst();
            points = cursor.getInt(0);
        }else{
            String sql = "SELECT SUM (points) FROM "+TABLENAME+" WHERE isdone = ?"+" and type = ?";
            cursor = db.rawQuery(sql,new String[]{one,type});
            cursor.moveToFirst();
            points = cursor.getInt(0);
        }
        Log.i(TAG,"getPoints");
        return points;
    }


    /**
     *
     * @param done
     * @return
     */
    public List<Taskbean> getBeanByCondition(int done){
        List<Taskbean> list = new ArrayList<>();
        String sql = "SELECT * FROM "+TABLENAME+" WHERE isdone = ? ORDER BY id DESC";
        Cursor cursor = db.rawQuery(sql,new String[]{Integer.toString(done)});
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String detail = cursor.getString(cursor.getColumnIndex("detail"));
            int points = cursor.getInt(cursor.getColumnIndex("points"));
            int isdone = cursor.getInt(cursor.getColumnIndex("isdone"));
            Taskbean bean = new Taskbean(id,type,date,detail,points,isdone);
            list.add(bean);
        }
        Log.i(TAG,"getBeanByCondition"+"list size"+list.size());
        return list;

    }

    /**
     * delete
     * @param id
     */
    public void delete(int id)
    {

        String sql = "DELETE FROM " + TABLENAME + " WHERE id = ?";
        this.db.execSQL(sql,new String[]{Integer.toString(id)});
        Log.i(TAG,"delete");
    }

    /**
     * delete all
     */
    public void delAll()
    {
        String sql = "DELETE FROM "+ TABLENAME;
        this.db.execSQL(sql);
        Log.i(TAG,"delAll");
    }

    public void upDateBean(Taskbean bean){
        bean.setIsdone(1);
        String sql = "UPDATE "+TABLENAME+" SET isdone = ?"+" WHERE id = ?";
        this.db.execSQL(sql,new String[]{Integer.toString(bean.getIsdone()),Integer.toString(bean.getId())});
        Log.i(TAG,"upDateBean");
    }

    public void closeDB(){
        db.close();
    }
    /**
     *
     */
    public boolean isEnough(int target) {
        int earnPoints = this.getPoints("task");
        int costPoints = this.getPoints("desire");
        int leftPoints = earnPoints-costPoints;
        return leftPoints>target?true:false;
    }
}
