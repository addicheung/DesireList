package cn.studyjams.s1.sj32.zhangqidi.util;

/**
 * DataBaseHelper
 * Created by AddiCheung on 2015/12/15.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASENAME = "desirelist.db.";
    private static final int DATABASEVERSION = 1;
    private static final String TABLENAME = "dolist";
    private static DataBaseHelper dbHelper;

    public DataBaseHelper(Context context)
    {
        super(context,DATABASENAME,null,DATABASEVERSION);
    }

    public static DataBaseHelper getInstance(Context context){
        if(dbHelper==null){
            dbHelper = new DataBaseHelper(context);
        }
        return dbHelper;
    }

    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE "+TABLENAME+" ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                "date TEXT NOT NULL ,"+
                "type VARCHAR(20) NOT NULL ,"+
                "detail TEXT NOT NULL ,"+
                "points INT NOT NULL ,"+
                "isdone TINYINT NOT NULL"+
                ")";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        String sql = "DROP TABLE IF EXISTS " + TABLENAME;
        db.execSQL(sql);
        this.onCreate(db);
    }
}
