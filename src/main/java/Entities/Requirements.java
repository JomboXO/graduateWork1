package Entities;

/**
 * Created by ������� on 05.04.2017.
 */
public class Requirements {
    private String subject;
    private int typeSubject; // 0 - lecture; 1 - practice 2- lab
    private int requirment;  // 1 - lecture class; 2 computer class; 3 practice class; 4 - проектор; 13 - no requirment
    private int numberHull; //0 - главный корпус, кронверский, 1 - ломоносова, 2 - гривцова, 3 - биржевая

    public Requirements(String subject, /*String teacher,*/ int typeSubject, int requirment, int numberHull) {
        this.subject = subject;
        this.typeSubject = typeSubject;
        this.requirment = requirment;
        this.numberHull = numberHull;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

//    public String getTeacher() {
//        return teacher;
//    }
//
//    public void setTeacher(String teacher) {
//        this.teacher = teacher;
//    }

    public int getTypeSubject() {
        return typeSubject;
    }

    public void setTypeSubject(int typeSubject) {
        this.typeSubject = typeSubject;
    }

    public int getRequirment() {
        return requirment;
    }

    public void setRequirment(int requirment) {
        this.requirment = requirment;
    }

    public int getNumberHull() {
        return numberHull;
    }

    public void setNumberHull(int numberHull) {
        this.numberHull = numberHull;
    }
}
