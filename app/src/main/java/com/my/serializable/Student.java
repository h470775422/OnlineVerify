package com.my.serializable;

import java.io.Serializable;

public class Student implements Serializable
{
    private static final long serialVersionUID = -1L;
    public int type;
    public String name;//姓名
    public String studentClass;//班级
    public String idNumber;//学号
    public String macAddr;//mac地址
    public String picName;
    public int picLength;
    public byte[] headPic;
}
