import Entities.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tatiana on 14.05.2017.
 */
public class SetListElements {
    public static void getListElements(List<Requirements> requirementses, List<Load> groups,
                                      List<Classroom> classroom, List<Element> Elements, List<Flow> flows) {
        int h = 0;
        for (int i = 0; i < groups.size(); i++) {
            String subject = groups.get(i).getSubject();
            String teacher = groups.get(i).getTeacher();
            TypeSubject typeSubject = groups.get(i).getTypeSubject();
            TypeClassroom requirements = TypeClassroom.ORDINARY_CLASS;
            Hull reqHull = Hull.KRONWERK;
            TypeClassroom reqTypeClass = TypeClassroom.ORDINARY_CLASS;
            int flow = groups.get(i).getFlow();

            for (int j = 0; j < requirementses.size(); j++) {
                if (requirementses.get(j).getSubject().equals(subject)) { //если в ограничениях есть такой предмет
                    if (requirementses.get(j).getTypeSubject() == typeSubject) { //если тип его совпадает
                        requirements = requirementses.get(j).getRequiredClass(); //тогда узнаем какие ограничения есть на аудиторию
                        /*это не обязательно теперь*/ //reqType = requirementses.get(j).getTypeSubject(); //и какого типа она должна быть
                        reqHull = requirementses.get(j).getNumberHull(); //и в каком корпусе должна быть
                        break;
                    }
                }
            }
//            if (requirements == TypeClassroom.ORDINARY_CLASS) { //если 13, то никаких ограничений
//                reqTypeClass = TypeClassroom.ORDINARY_CLASS;
//            } else if (requirements == TypeClassroom.CLASS_WITH_PROJECTOR) { // 4 - нужен проектор
//                reqTypeClass = TypeClassroom.CLASS_WITH_PROJECTOR;
//            } else if (requirements == TypeClassroom.COMPUTER_CLASS) { // 2 - нужен компьютерный класс
//                reqTypeClass = TypeClassroom.COMPUTER_CLASS; //компьютерный класс
//            } else if (requirements == TypeClassroom.COMPUTER_CLASS_WITH_PROJECTOR) {
//                //нужен комп. класс с проектором
//                reqTypeClass = TypeClassroom.COMPUTER_CLASS_WITH_PROJECTOR;
//            }


            reqTypeClass = requirements;

            Element sch;
            double load = groups.get(i).getLoad();
            if (load == 0.5) load = 1;
            for (int k = 0; k < load; k++) {
                double a = getClassrooms(requirements, reqHull, classroom);
                double p = getTeachersLoad(teacher, groups);
                double g = getStudientsLoad(flow, groups, flows);
                double S = getS(a, p, g);
                Elements.add(new Element(teacher, (new Classroom(reqTypeClass, reqHull)), subject, flow, typeSubject, S));
            }

        }

    }

    private static double getS(double a, double p, double g) {
        return a / (p * g);
    }

    private static double getStudientsLoad(int flow, List<Load> groups, List<Flow> flows) {
        double e = 0;
        List<Double> d = new ArrayList<>();
        for (Flow flow1 : flows) {
            if (flow1.getNumberFlow() == flow) {
                for (int i = 0; i < flow1.getGroupList().size(); i++) {
                    Group group = flow1.getGroupList().get(i);
                    d.add(checkGroup(group, groups, flows));
                }
            }
        }
        e = getMax(d);
        return e;
    }

    private static double getMax(List<Double> d) {
        double max = d.get(0);
        for (double dd : d) {
            if (dd > max) max = dd;
        }
        return max;
    }

    private static double checkGroup(Group group, List<Load> groups, List<Flow> flows) {
        double e = 0;
        for (int h = 0; h < groups.size(); h++) {
            for (int j = 0; j < flows.size(); j++) {
                if (flows.get(j).getNumberFlow() == groups.get(h).getFlow()) {
                    if (flows.get(j).getGroupList().contains(group)) {
                        e = e + groups.get(h).getLoad();
                    }
                }
            }
        }
        return e;
    }

    private static double getTeachersLoad(String teacher, List<Load> groups) {
        double t = 0;
        for (int i = 0; i < groups.size(); i++) {
            if (teacher.equals(groups.get(i).getTeacher())) {
                t = t + groups.get(i).getLoad();
            }
        }
        return t;
    }

    private static float getClassrooms(TypeClassroom requirements, Hull reqHull, List<Classroom> classroom) {
        int k = 0;
        for (int i = 0; i < classroom.size(); i++) {
            if (classroom.get(i).getNumberHull() == reqHull) {
                if (requirements == TypeClassroom.ORDINARY_CLASS) { //лекция без ограничений
                    if (classroom.get(i).getTypeClassroom() == TypeClassroom.CLASS_WITH_PROJECTOR || classroom.get(i).getTypeClassroom() == TypeClassroom.ORDINARY_CLASS) { //берем обычные или с проектором
                        k++;
                    }
                }
//                else if ((requirements == 13 && (reqType == 1 || reqType == 2))) {
//                    if (classroom.get(i).getTypeClassroom() == 2 || classroom.get(i).getTypeClassroom() == 24) {
//                        k++; //просто комп.класс или комп.класс с проектором
//                    }
//                }
                if (requirements == TypeClassroom.CLASS_WITH_PROJECTOR){
                    if (classroom.get(i).getTypeClassroom() == TypeClassroom.CLASS_WITH_PROJECTOR) { //лекционная с проектором
                        k++;
                    }
                }
                if (requirements == TypeClassroom.COMPUTER_CLASS_WITH_PROJECTOR){
                    if (classroom.get(i).getTypeClassroom() == TypeClassroom.COMPUTER_CLASS_WITH_PROJECTOR) { //computer class с проектором
                        k++;
                    }
                }
                if (requirements == TypeClassroom.COMPUTER_CLASS){
                    if (classroom.get(i).getTypeClassroom() == TypeClassroom.COMPUTER_CLASS || classroom.get(i).getTypeClassroom() == TypeClassroom.COMPUTER_CLASS_WITH_PROJECTOR) { //computer class
                        k++;
                    }
                }
                /*else {
                    if (requirements == 4) {
                        if (classroom.get(i).getTypeClassroom() == 4) { //лекционная с проектором
                            k++;
                        }
                    } else if (requirements == 4 && (reqType == 1 || reqType == 2)) { //то ищем аудитории с проектором, в которых может проводиться практика или лаба


                    } else if (requirements == 2 && reqType == 0) { //если лекция должна быть в комп. классе
                        if (classroom.get(i).getTypeClassroom() == 2 || classroom.get(i).getTypeClassroom() == 24) {
                            k++; //просто комп.класс или комп.класс с проектором
                        }
                    } else if (requirements == 2 && (reqType == 1 || reqType == 2)) {
                        if (classroom.get(i).getTypeClassroom() == 2 || classroom.get(i).getTypeClassroom() == 24) {
                            k++; //просто комп.класс или комп.класс с проектором
                        }
                    }
                }*/
            }
        }
        return k;
    }
}
