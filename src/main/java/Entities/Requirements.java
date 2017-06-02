package Entities;

/**
 * Created by ������� on 13.05.2017.
 */
public class Requirements {
    private String subject;
    private TypeSubject typeSubject; // 0 - lecture; 1 - practice 2- lab
    private TypeClassroom requiredClass;  // 1 - lecture class; 2 computer class; 3 practice class; 4 - проектор; 13 - no requiredClass
    private Hull hull; //0 - главный корпус, кронверский, 1 - ломоносова, 2 - гривцова, 3 - биржевая

    public Requirements(String subject, /*String teacher,*/ TypeSubject typeSubject, TypeClassroom requiredClass, Hull hull) {
        this.subject = subject;
        this.typeSubject = typeSubject;
        this.requiredClass = requiredClass;
        this.hull = hull;
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

    public TypeSubject getTypeSubject() {
        return typeSubject;
    }

    public void setTypeSubject(TypeSubject typeSubject) {
        this.typeSubject = typeSubject;
    }

    public TypeClassroom getRequiredClass() {
        return requiredClass;
    }

    public void setRequiredClass(TypeClassroom requiredClass) {
        this.requiredClass = requiredClass;
    }

    public Hull getNumberHull() {
        return hull;
    }

    public void setNumberHull(Hull hull) {
        this.hull = hull;
    }
}
