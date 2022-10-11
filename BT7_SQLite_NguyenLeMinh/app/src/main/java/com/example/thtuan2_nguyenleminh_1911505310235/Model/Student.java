package com.example.thtuan2_nguyenleminh_1911505310235.Model;

public class Student {
    private String id, name;
    private int diem;

    public Student(){}

    public Student(String id, String name, int diem) {
        this.id = id;
        this.name = name;
        this.diem = diem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }
}
