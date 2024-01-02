/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exam.model;

/**
 *
 * @author admin
 */
public class exam {
    private int id;
    private String nameExam;
    private int soCauHoi;
    private int thoigian;

    public exam() {
    }

    public exam(int id, String nameExam, int soCauHoi, int thoigian) {
        this.id = id;
        this.nameExam = nameExam;
        this.soCauHoi = soCauHoi;
        this.thoigian = thoigian;
    }

    public exam(String nameExam, int soCauHoi, int thoigian) {
        this.nameExam = nameExam;
        this.soCauHoi = soCauHoi;
        this.thoigian = thoigian;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameExam() {
        return nameExam;
    }

    public void setNameExam(String nameExam) {
        this.nameExam = nameExam;
    }

    public int getSoCauHoi() {
        return soCauHoi;
    }

    public void setSoCauHoi(int soCauHoi) {
        this.soCauHoi = soCauHoi;
    }

    public int getThoigian() {
        return thoigian;
    }

    public void setThoigian(int thoigian) {
        this.thoigian = thoigian;
    }

    @Override
    public String toString() {
        return "exam{" + "id=" + id + ", nameExam=" + nameExam + ", soCauHoi=" + soCauHoi + ", thoigian=" + thoigian + '}';
    }
    
    
}
