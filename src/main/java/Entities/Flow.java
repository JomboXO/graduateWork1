package Entities;

import java.util.List;

/**
 * Created by Татьяна on 15.05.2017.
 */
public class Flow {
    private int numberFlow;
    private List<Group> groupList;

    public Flow(int numberFlow, List<Group> groupList) {
        this.numberFlow = numberFlow;
        this.groupList = groupList;
    }

    public int getNumberFlow() {
        return numberFlow;
    }

    public void setNumberFlow(int numberFlow) {
        this.numberFlow = numberFlow;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null)
            return false;

        if (!(getClass() == obj.getClass()))
            return false;
        else {
            Flow tmp = (Flow) obj;
            if (tmp.groupList.containsAll(this.groupList) && tmp.groupList.size() == this.groupList.size())
                return true;
            else
                return false;
        }
    }

}