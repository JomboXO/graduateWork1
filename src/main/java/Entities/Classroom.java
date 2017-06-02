package Entities;

/**
 * Created by ������� on 14.05.2017.
 */
public class Classroom {
    private int numberClassroom;
    private Hull hull; // 0 - главный корпус, кронверский, 1 - ломоносова, 2 - гривцова, 3 - биржевая
    private TypeClassroom typeClassroom; // 0 - просто аудитория; 2 - компьютерный класс; 24 - компьютерный класс с проектором
  // 4 - аудитория с проектором
    private int capacity; //количество мест в аудитории

    /*устанавливаем сюда номер типа, чтобы потом по типу аудитории определить подходящюю*/
    public Classroom(TypeClassroom typeClassroom, Hull hull) {
        this.typeClassroom = typeClassroom;
        this.hull = hull;
    }

    public Classroom(TypeClassroom typeClassroom,int numberClassroom, Hull hull, int capacity) {
        this.typeClassroom = typeClassroom;
        this.numberClassroom = numberClassroom;
        this.hull = hull;
        this.capacity = capacity;
    }
    public Hull getNumberHull() {
        return hull;
    }

    public void setNumberHull(Hull numberHull) {
        this.hull = numberHull;
    }

    public int getNumberClassroom() {
        return numberClassroom;
    }

    public void setNumberClassroom(int numberClassroom) {
        this.numberClassroom = numberClassroom;
    }

    public TypeClassroom getTypeClassroom() {
        return typeClassroom;
    }

    public void setTypeClassroom(TypeClassroom typeClassroom) {
        this.typeClassroom = typeClassroom;
    }

    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        /* obj ссылается на null */

        if (obj == null)
            return false;
         /* Удостоверимся, что ссылки имеют тот же самый тип */

        if (!(getClass() == obj.getClass()))
            return false;
        else {
            Classroom tmp = (Classroom) obj;
            if (tmp.numberClassroom == this.numberClassroom && tmp.hull == this.hull)
                return true;
            else
                return false;
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
