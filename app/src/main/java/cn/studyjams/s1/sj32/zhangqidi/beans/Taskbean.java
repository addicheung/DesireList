package cn.studyjams.s1.sj32.zhangqidi.beans;

import java.util.Date;

/**
 * the rask bean
 *
 * Created by AddiCheung on 2016/4/26 0026.
 */
public class Taskbean  {

    private int id;             //id
    private String date;        //date like 2016-5-01
    private String type;        //like: task or desire
    private String detail;      //the detail of the thing
    private int points;         //the earn or cost points
    private int isdone;         //shows the is being donr or is doing

    public Taskbean(){}

    public Taskbean(int id,String type,String date, String detail, int points, int isdone) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.detail = detail;
        this.points = points;
        this.isdone = isdone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getIsdone() {
        return isdone;
    }

    public void setIsdone(int isdone) {
        this.isdone = isdone;
    }
}
