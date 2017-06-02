package Entities;

/**
 * Created by ������� on 13.05.2017.
 */
public class Group {
    private String group;
    private int countPeople;

    public Group(String group, int countPeople) {
        this.countPeople = countPeople;
        this.group = group;
    }

    public String getGroup() {

        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        /* obj ��������� �� null */

        if(obj == null)
            return false;
         /* �������������, ��� ������ ����� ��� �� ����� ��� */

        if(!(getClass() == obj.getClass()))
            return false;
        else
        {
            Group tmp = (Group)obj;
            if(tmp.group.equals(this.group))
                return true;
            else
                return false;
        }
    }

    @Override
    public String toString() {
        return group;
    }

    public int getCountPeople() {
        return countPeople;
    }

    public void setCountPeople(int countPeople) {
        this.countPeople = countPeople;
    }
}
