package com.excel.beans;


/**
 * @author Pradeep CH
 * @version 1.0
 */

public class ReconsiledItem {


    private String name;
    private Object val1;
    private Object val2;

    public ReconsiledItem(){ super();}

    public ReconsiledItem(String name, Object val1, Object val2) {
        this();
        this.name = name;
        this.val1 = val1;
        this.val2 = val2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getVal1() {
        return val1;
    }

    public void setVal1(Object val1) {
        this.val1 = val1;
    }

    public Object getVal2() {
        return val2;
    }

    public void setVal2(Object val2) {
        this.val2 = val2;
    }

    @Override
    public String toString() {
        return "ReconsiledItem{" +
                "name='" + name + '\'' +
                ", val1=" + val1 +
                ", val2=" + val2 +
                '}';
    }
}
