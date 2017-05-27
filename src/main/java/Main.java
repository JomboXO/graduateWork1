import Entities.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.*;
import java.util.*;
import java.util.List;

public class Main extends JFrame{


    private static List<Load> groups= new ArrayList<>();;
    private static List<Flow> flows = new ArrayList<>();;
    private static List<Requirements> requirementses = new ArrayList<Requirements>();;
    private static List<Classroom> classroom = new ArrayList<Classroom>();;
    static List<ListClasses> listClasses = new ArrayList<ListClasses>();;
    static Map<Timeslot, Element> elem = new HashMap<Timeslot, Element>();
    static List<TempElem> tempElems = new ArrayList<TempElem>();;
    final static int p = 8, l = 12;
    private JButton getFile;
    static File file;


    public Main(){
        super("My First Window"); //Заголовок окна
        setBounds(100, 100, 200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        final JLabel label = new JLabel("Выбранный файл");
        label.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(label);

        panel.add(Box.createRigidArea(new Dimension(10, 10)));

        JButton button = new JButton("Показать JFileChooser");
        button.setAlignmentX(CENTER_ALIGNMENT);
        JButton button1 = new JButton("Get schedule");
        button1.setAlignmentX(CENTER_ALIGNMENT);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    file = fileopen.getSelectedFile();
                    label.setText(file.getName());
                }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                XMLParser.goLoads(groups, flows, file);
                requirementses.add(new Requirements("Введение в специальность", 0, 13, 0));
                requirementses.add(new Requirements("Вычисление с использованием пакета MathCad", 0, 4, 0));
                requirementses.add(new Requirements("Дискретная математика", 0, 13, 0));
                requirementses.add(new Requirements("Введение в специальность", 1, 13, 0));
                requirementses.add(new Requirements("Вычисление с использованием пакета MathCad", 2, 2, 0));
                requirementses.add(new Requirements("Дискретная математика", 1, 13, 0));

                requirementses.add(new Requirements("Информатика", 0, 4, 0));
                requirementses.add(new Requirements("Программирование", 0, 4, 0));
                requirementses.add(new Requirements("Языки и парадигмы программирования", 0, 4, 0));
                requirementses.add(new Requirements("Информатика", 1, 2, 0));
                requirementses.add(new Requirements("Программирование", 2, 2, 0));
                requirementses.add(new Requirements("Языки и парадигмы программирования", 2, 2, 0));

                requirementses.add(new Requirements("Мировые информационные ресурсы", 0, 13, 1));
                requirementses.add(new Requirements("Мировые информационные ресурсы", 2, 2, 1));

                classroom.add(new Classroom(4, 302, 0, 100));
                classroom.add(new Classroom(4, 359, 0, 50));
                classroom.add(new Classroom(4, 285, 0, 80));
                classroom.add(new Classroom(4, 403, 0, 70));
                classroom.add(new Classroom(4, 466, 0, 80));

                classroom.add(new Classroom(24, 303, 0, 30));
                classroom.add(new Classroom(2, 304, 0, 30));
                classroom.add(new Classroom(2, 305, 0, 30));
                classroom.add(new Classroom(2, 306, 0, 30));

                classroom.add(new Classroom(4, 102, 1, 90));
                classroom.add(new Classroom(24, 103, 1, 30));
                classroom.add(new Classroom(24, 104, 1, 30));
                classroom.add(new Classroom(24, 105, 1, 30));
                classroom.add(new Classroom(24, 106, 1, 30));

                SetListClasses.getListClasses(requirementses, groups, classroom, listClasses, flows);
                sortByS(listClasses);
                doSchedule();
                try {
                    printElements();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                label.setText("Расписание готово");
            }
        });
        panel.add(button);
        panel.add(button1);
        panel.add(Box.createVerticalGlue());


        getContentPane().add(panel);

        setPreferredSize(new Dimension(260, 220));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    /* Изначально, на вход подаем только нагрузку для учеников
     * Данные по аудиториям и преподавателей уже в системе
     */
    public static void main(String[] args) throws IOException {


        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
                new Main();

            }
        });


//        requirementses.add(new Requirements("Введение в специальность", 0, 13));
//        requirementses.add(new Requirements("Вычисление с использованием пакета MathCad", 0, 4));
//        requirementses.add(new Requirements("Дискретная математика", 0, 13));
//        requirementses.add(new Requirements("Введение в специальность", 1, 13));
//        requirementses.add(new Requirements("Вычисление с использованием пакета MathCad", 2, 2));
//        requirementses.add(new Requirements("Дискретная математика", 1, 13));
//
//        requirementses.add(new Requirements("Информатика", 0, 4));
//        requirementses.add(new Requirements("Программирование", 0, 4));
//        requirementses.add(new Requirements("Языки и парадигмы программирования", 0, 4));
//        requirementses.add(new Requirements("Информатика", 1, 2));
//        requirementses.add(new Requirements("Программирование", 2, 2));
//        requirementses.add(new Requirements("Языки и парадигмы программирования", 2, 2));
//
//        classroom.add(new Classroom(40, 302));
//        classroom.add(new Classroom(40, 359));
//        classroom.add(new Classroom(40, 285));
//        classroom.add(new Classroom(40, 403));
//        classroom.add(new Classroom(40, 466));
//
//        classroom.add(new Classroom(24, 303));
//        classroom.add(new Classroom(2, 304));
//        classroom.add(new Classroom(2, 305));
//        classroom.add(new Classroom(2, 306));
//
//        SetListClasses.getListClasses(requirementses, groups, classroom, listClasses, flows);
//        sortByS(listClasses);
//        doSchedule();
//        printElements();
    }

    private static void doSchedule() {
        double k = 0, t = 0, R = 0;
        int checkLoad, countInWeek, countInDay;
        for (ListClasses list : listClasses) {
            boolean equals = false;
            countInDay = 0;
            countInWeek = 0;
            checkLoad = 0;
            tempElems.clear();
            List<Element> element = new ArrayList<>();
            Timeslot key;
            for (int i = 0; i < p; i++) {
                for (int n = 0; n < (int) l / 2; n++) {
                    element.clear();
                    key = new Timeslot(n, i);
                    element.addAll(getElem(key));
                    /*если тип предмета практика или лаба, то
                    если нагрузка 2 раза в неделю, то ставим подряд, добавляем во вторую неделю на то же время
                    если нагрузка 1 раз в неделю, но практика или лаба и каждую неделю, тогда ставим подряд и записываем только в одну неделю
                    если нагрузка 1 раз в две недеи (не знаю как это показать), тогда один раз будет в одной неделе
                     а нагрузка 2 раза в неделю - ставим подряд
                    * */
                    if (thisIsHalfLoad(list.getElement(), listClasses)) {
                        checkLoad = 50;
                    } else {
                        checkLoad = countLoad(list.getElement(), listClasses);
                    }
                    if (element.isEmpty()) {
                        switch (checkLoad) {
                            case 3:
                                countInDay = 1;
                                countInWeek = 2;
                                break;
                            case 2:
                                if (list.getElement().getTypeSubject() == 0) {
                                    countInDay = 1;
                                    countInWeek = 2;
                                } else {
                                    countInDay = 2;
                                    countInWeek = 2;
                                }
                                break;
                            case 1:
                                if (list.getElement().getTypeSubject() == 0) {
                                    countInDay = 1;
                                    countInWeek = 2;
                                } else {
                                    countInDay = 2;
                                    countInWeek = 1;
                                }
                                break;
                            case 50:
                                countInDay = 1;
                                countInWeek = 1;
                                break;
                        }
                    } else {
                        if (element.contains(list.getElement())) {
                            switch (checkLoad) {
                                case 3:
                                    countInDay = 1;
                                    countInWeek = 2;
                                    break;
                                case 2:
                                    equals = true;
                                    break;
                                case 1:
                                    equals = true;
                                    break;
                                case 50:
                                    equals = true;
                                    break;
                            }
                        }
                    }

                    if (equals) {
                        break;
                    }
                    for (int h = 0; h < classroom.size(); h++) {
                        if (!element.isEmpty()) {
                            for (Element element1 : element) {
                                k = checkOverlap(element1, list.getElement(), classroom.get(h));
                                if (k == 0 || k == 1) {
                                    break;
                                }
                            }
                        } else k = 10;
                        if (k == 0) break;
                        if (k == 1) continue;

                        if (classroom.get(h).getNumberHull() != list.getElement().getClassroom().getNumberHull()) {
                            continue;
                        }
                        t = checkAud(classroom.get(h), list.getElement());
                        if (t == 0) continue;

                        k = k + t;
                        double t1 = checkWindowForStudens(n, i, list.getElement());
//                        k = k + t;
                        double t2 = checkWindowForTeachers(n, i, list.getElement());
//                        if (t1 != t2)
                        k = k + t1 + t2;
                        t = countClassesForFlow(n, list.getElement().getFlow());
                        k = k + t;
                        tempElems.add(new TempElem(k, new Element(list.getElement().getTeacher(), classroom.get(h), list.getElement().getSubject(), list.getElement().getGroup(), list.getElement().getTypeSubject()), i, n));
                    }
                }
                if (equals) break;
            }
            if (equals) {
                continue;
//                elem.put(new Timeslot(tempElems.get(0).getN(), tempElems.get(0).getI()), tempElems.get(0).getElement());
//                System.out.println(tempElems.get(0).getN() + " " + tempElems.get(0).getI() + " " + tempElems.get(0).getElement().toString());
            } else {
                //здесь находим самый большой R и записываем в element занятие
                sortByR(tempElems);
                elem.put(new Timeslot(tempElems.get(0).getN(), tempElems.get(0).getI()), tempElems.get(0).getElement());
                if (countInWeek == 2) {
                    elem.put(new Timeslot(tempElems.get(0).getN() + 6, tempElems.get(0).getI()), tempElems.get(0).getElement());
                    if (countInDay == 2) {
                        elem.put(new Timeslot(tempElems.get(0).getN(), tempElems.get(0).getI() + 1), tempElems.get(0).getElement());
                        elem.put(new Timeslot(tempElems.get(0).getN() + 6, tempElems.get(0).getI() + 1), tempElems.get(0).getElement());
                    }
                } else {
                    if (countInDay == 2) {
                        elem.put(new Timeslot(tempElems.get(0).getN(), tempElems.get(0).getI() + 1), tempElems.get(0).getElement());
                    }
                }
                // System.out.println(tempElems.get(0).getN() + " " + tempElems.get(0).getI() + " " + tempElems.get(0).getElement().toString());

            }
        }
    }
    private static boolean thisIsHalfLoad(Element element1, List<ListClasses> listClasses) {

        for (Load loads : groups) {
            for (ListClasses listClasses1 : listClasses) {
                if (loads.getFlow() == listClasses1.getElement().getFlow() && loads.getTeacher() == listClasses1.getElement().getTeacher()
                        && loads.getSubject() == listClasses1.getElement().getSubject() && loads.getTypeSubject() == listClasses1.getElement().getTypeSubject()) {
                    if (loads.getLoad() == 0.5) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private static int countLoad(Element element1, List<ListClasses> listClasses) {
        int i = 0;
        for (ListClasses listClasses1 : listClasses) {
            if (listClasses1.getElement().equals(element1)) i++;
        }
        return i;
    }

    /*Проверка "перекрытия": если в это же время и этот же день уже заняты
            преподаватель или группа, тогда возвращаем ноль и прекращаем рассматривать эту аудиторию*/
// добавить возможность не рассматривать остальные аудитории, если вернет 0
    private static double checkOverlap(Element element, Element elementList, Classroom classroom) {
        int w = 10;
        if (element != null) {
            if (element.getTeacher().equals(elementList.getTeacher())) {
                return 0;
            } else if (checkGroup(element.getFlow(), elementList.getFlow())) { //element.getGroup().equals(elementList.getGroup())) {
                return 0;
            } else if (element.getClassroom().getNumberClassroom() == classroom.getNumberClassroom()) {
                return 1;
            } else return 1 * w;
        }
        return 1 * w;
    }
    private static double checkAud(Classroom classroom, Element listElement) {
        int w = 10;
        double ret = 0;
        if (listElement.getClassroom().getTypeClassroom() == 0) {
            if (classroom.getTypeClassroom() == 4 || classroom.getTypeClassroom() == 0) {
                ret = checkCapacity(classroom, listElement.getFlow()) * w;
            }
        } else if (listElement.getClassroom().getTypeClassroom() == 2) {
            if (classroom.getTypeClassroom() == 2 || classroom.getTypeClassroom() == 24) {
                ret = checkCapacity(classroom, listElement.getFlow()) * w;
            }
        } else if (listElement.getClassroom().getTypeClassroom() == 4) {
            if (classroom.getTypeClassroom() == 4) {
                ret = checkCapacity(classroom, listElement.getFlow()) * w;
            }
        } else if (listElement.getClassroom().getTypeClassroom() == 24) {
            if (classroom.getTypeClassroom() == 24) {
                ret = checkCapacity(classroom, listElement.getFlow()) * w;
            }
        }
        return ret;
    }

    private static double checkCapacity(Classroom classroom, int flow) {
        int c = getFlowCount(flow);
        if (classroom.getCapacity() >= c){
            if (classroom.getCapacity() - c > 20){
                return 0.6;
            }else {
                return 1;
            }
        }else {
            return 0;
        }
    }
    private static int getFlowCount(int flow) {
        int ret = 0;
        for (Flow fl : flows){
            if (fl.getNumberFlow() == flow){
                ret = fl.getCountPeople();
                break;
            }
        }
        return ret;
    }


    private static void printElements() throws IOException {
        writeElementsToFile();
//        for (int i = 0; i < p; i++){
//            for (int j = 0; j < l; j++){
//                System.out.print(elements[i][j] + " | ");
//            }
//            System.out.println();
//        }
        for (Map.Entry entry : elem.entrySet()) {
            Timeslot t = (Timeslot) entry.getKey();
            System.out.println(t.getDayOfWeek() + "  " + t.getNumberClass() + "  " + " Value: " + entry.getValue());
        }
    }

    private static void writeElementsToFile() throws IOException {
        List<Element> element = new ArrayList<>();
        Timeslot key;

        File file = new File("C://schedule.xls");
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Schedule");
        Row row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("");
        CellRangeAddress region;
        int k = 0, t = 0;
        for (int i = 0; i < 12; i++){
            t = k+1;
            k = t+3;
            region = new CellRangeAddress(0, 0, t, k);
            sheet.addMergedRegion(region);
        }
        row1.createCell(1).setCellValue("пн");
        row1.createCell(5).setCellValue("вт");
        row1.createCell(9).setCellValue("ср");
        row1.createCell(13).setCellValue("чт");
        row1.createCell(17).setCellValue("пт");
        row1.createCell(21).setCellValue("сб");
        row1.createCell(25).setCellValue("пн");
        row1.createCell(29).setCellValue("вт");
        row1.createCell(33).setCellValue("ср");
        row1.createCell(37).setCellValue("чт");
        row1.createCell(41).setCellValue("пт");
        row1.createCell(45).setCellValue("сб");
        int g = 1;
        for (int i = 1; i <= p; i++) {
            int q = 1;
            for (int j = 1; j <= l; j++) {
                element.clear();
                key = new Timeslot(j - 1, i - 1);
                element.addAll(getElem(key));
                if (!element.isEmpty()) {
//                    Row row = sheet.createRow(g);
//                    for (int h = 0; h < element.size(); h++) {
//                        int q = 1;
//                        boolean b = true;
//                        while (b) {
//
////                            row = sheet.createRow(q);
////                            Cell cell = row.getCell(0);
//                            if (row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK) {
//                                if (row.getCell(0).getNumericCellValue() == i - 1) {
//                                    if (row.getCell(j).getCellType() == Cell.CELL_TYPE_BLANK) {
//                                        row.getCell(j).setCellValue(element.get(h).toString());
//                                        b = false;
//                                    } else {
//                                        q++;
//                                    }
//                                } else {
//                                    q++;
//                                }
//                            } else {
//                                row.createCell(0).setCellValue(i-1);
//                                row.createCell(j).setCellValue(element.get(h).toString());
//                                b = false;
//                            }
//                        }
//                    }
                    for (int h = 0; h < element.size(); h++) {
                        Row row = sheet.createRow(g);
                        String str = countgroup(element.get(h).getFlow());

                        Cell name = row.createCell(0);
                        name.setCellValue(i - 1);
                        name = row.createCell(q);
                        name.setCellValue(element.get(h).getSubject());
                        name = row.createCell(q+1);
                        name.setCellValue(str);
                        name = row.createCell(q+2);
                        name.setCellValue(element.get(h).getClassroom().getNumberClassroom());
                        name = row.createCell(q+3);
                        name.setCellValue(element.get(h).getTeacher());
                        g++;
                    }
                    q = q + 4;
                }
            }
        }
        book.write(new FileOutputStream(file));
        book.close();
    }

    private static String countgroup(int flow) {
        String ret = "";
        for (Flow fl : flows) {
            if (fl.getNumberFlow() == flow) {
                for (int h = 0; h < fl.getGroupList().size(); h++) {
                    ret += fl.getGroupList().get(h).getGroup().toString() + " ";
                }
                break;
            }
        }
        return ret;
    }

    private static List<Element> getElem(Timeslot key) {
        List<Element> e = new ArrayList<>();
        for (Map.Entry entry : elem.entrySet()) {
            if (entry.getKey().equals(key)) e.add((Element) entry.getValue());
        }
        return e;
    }

    private static double countClassesForFlow(int n, int fl) {
        int w = 4, j = 0;
        Element element = null;
        List<Group> elemFlow = new ArrayList<>();
        List<Group> elementFlow = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        Timeslot key;
        for (int i = 0; i < p; i++) {
            key = new Timeslot(n, i);
            if (elem.containsKey(key)) element = elem.get(key);
            if (element != null) {
                for (Flow flow : flows) {
                    if (flow.getNumberFlow() == fl) {
                        elemFlow.addAll(flow.getGroupList());
                        break;
                    }
                }
                if (element.getFlow() == fl) {
                    for (Flow flow : flows) {
                        if (flow.getNumberFlow() == fl) {
                            elemFlow.addAll(flow.getGroupList());
                        }
                        if (flow.getNumberFlow() == element.getFlow()) {
                            elementFlow.addAll(flow.getGroupList());
                        }
                    }
                    for (int f = 0; f < elementFlow.size(); f++) {
                        count.add(0);
                    }
                    for (int l = 0; l < elemFlow.size(); l++) {
                        for (int f = 0; f < elementFlow.size(); f++) {
                            if (elementFlow.get(f).getGroup().equals(elemFlow.get(l).getGroup())) {
                                count.set(f, count.get(f) + 1);
                            }
                        }
                    }
                }
            }
        }
        if (returnMax(count) > 3) return 0;
        else return 1 * w;
    }
    private static int returnMax(List<Integer> count) {
        int max = 0;
        if (!count.isEmpty()) {
            max = count.get(0);
            for (Integer i : count) {
                if (i > max) max = i;
            }
        }
        return max;
    }
    private static boolean checkGroup(int elementFlow, int elementListFlow) {
        List<Group> elementGroup = new ArrayList<>();
        List<Group> elementListGroup = new ArrayList<>();
        for (Flow flow : flows) {
            if (flow.getNumberFlow() == elementFlow) elementGroup.addAll(flow.getGroupList());
            if (flow.getNumberFlow() == elementListFlow) elementListGroup.addAll(flow.getGroupList());
        }
        for (Group group : elementGroup) {
            if (elementListGroup.contains(group)) return true;
        }
        return false;
    }

    private static double checkWindowForTeachers(int nn, int ii, Element element) {
        int w = 2;
        double k = 0;
        boolean hull = false;
        Element el = null;
        Timeslot key;
        for (int i = 0; i < p; i++) {
            key = new Timeslot(nn, i);
            if (elem.containsKey(key)) el = elem.get(key);
            if (el != null) {
                if (!el.equals(element)) {
                    if (el.getClassroom().getNumberHull() == element.getClassroom().getNumberHull()) {
                        hull = false;
                    } else hull = true;
                    if (el.getTeacher().equals(element.getTeacher())) {
                        if (ii > i && ii - i > 1 || ii < i && i - ii > 1) {
                            k = 2; // окно в 2 и более пары
                        } else if (ii > i && ii - i == 1 || ii < i && i - ii == 1) {
                            k = 1; //окно в одну пару
                        } else k = 0;
                    }
                }
            }
            if (!hull) {
                if (k > 1) {
                    k = 0;
                    break;
                }
            } else {
                if (k == 0) break;
            }
        }
        if (!hull) {
            if (k > 1) k = 0;
            else if (k == 1) k = 0.5;
            else k = 1;
        } else {
            if (k == 2) k = 0.2;
        }
        return k * w;
    }

    private static double checkWindowForStudens(int nn, int ii, Element element) {
        int w = 2;
        double k = 0;
        boolean hull = false;
        Element el = null;
        List<Group> elFlow = new ArrayList<>();
        List<Group> elementFlow = new ArrayList<>();
        Timeslot key;
        for (int i = 0; i < p; i++) {
            key = new Timeslot(nn, i);
            if (elem.containsKey(key)) el = elem.get(key);
            if (el != null) {
                if (!el.equals(element)) {
                    for (Flow flow : flows) {
                        if (flow.getNumberFlow() == element.getFlow()) {
                            elFlow.addAll(flow.getGroupList());
                        }
                        if (flow.getNumberFlow() == element.getFlow()) {
                            elementFlow.addAll(flow.getGroupList());
                        }
                    }
                    //проверяем поток, если у какой-нибудь из него группы более
                    //двух пар окно - тогда возвращаем значение, как 0
                    for (int l = 0; l < elFlow.size(); l++) {
                        for (int f = 0; f < elementFlow.size(); f++) {
                            if (el.getClassroom().getNumberHull() == element.getClassroom().getNumberHull()) {
                                hull = false;
                            } else hull = true;
                            if (elementFlow.get(f).getGroup().equals(elFlow.get(l).getGroup())) {
                                if (ii > i && ii - i > 1 || ii < i && i - ii > 1) {
                                    k = 2; // окно в 2 и более пары
                                } else if (ii > i && ii - i == 1 || ii < i && i - ii == 1) {
                                    k = 1; //окно в одну пару
                                } else k = 0;
                            }
                        }
                    }
                }
            }
            if (!hull) {
                if (k > 1) {
                    k = 0;
                    break;
                }
            } else {
                if (k == 0) break;
            }
        }
        if (!hull) {
            if (k > 1) k = 0;
            else if (k == 1) k = 0.5;
            else k = 1;
        } else {
            if (k == 2) k = 0.2;

        }
        return k * w;
    }
    private static void sortByS(List<ListClasses> listClasses) {
        for (int i = listClasses.size() - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (listClasses.get(j).getS() > listClasses.get(j + 1).getS()) {
                    ListClasses t = listClasses.get(j);
                    listClasses.set(j, listClasses.get(j + 1));
                    listClasses.set(j + 1, t);
                }
            }
        }
    }

    private static void sortByR(List<TempElem> tempElems) {
        for (int i = tempElems.size() - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (tempElems.get(j).getR() < tempElems.get(j + 1).getR()) {
                    TempElem t = tempElems.get(j);
                    tempElems.set(j, tempElems.get(j + 1));
                    tempElems.set(j + 1, t);
                }
            }
        }
    }
}
