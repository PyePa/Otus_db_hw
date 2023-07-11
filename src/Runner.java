import data.Curator;
import data.Group;
import data.Student;
import db.IDBExecutor;
import db.MySqlExecutor;
import tables.AbsTable;
import tables.CuratorTable;
import tables.GroupTable;
import tables.StudentTable;

import java.util.ArrayList;
import java.util.List;

public class Runner {

    public static void main(String[] args) throws Exception {

        IDBExecutor dbExecutor = new MySqlExecutor();
        try {
            AbsTable studentTable = new StudentTable(dbExecutor);
            AbsTable curatorTable = new CuratorTable(dbExecutor);
            AbsTable groupTable = new GroupTable(dbExecutor);

            List<String> columnsStudentTable = new ArrayList<>();
            columnsStudentTable.add("id INT PRIMARY KEY");
            columnsStudentTable.add("fio VARCHAR(50)");
            columnsStudentTable.add("sex VARCHAR(1)");
            columnsStudentTable.add("id_group INT");

            studentTable.create(columnsStudentTable);

            List<String> columnsGroupTable = new ArrayList<>();
            columnsGroupTable.add("id INT PRIMARY KEY");
            columnsGroupTable.add("name_group VARCHAR(50)");
            columnsGroupTable.add("id_curator INT");

            groupTable.create(columnsGroupTable);

            List<String> columnsCuratorTable = new ArrayList<>();
            columnsCuratorTable.add("id INT PRIMARY KEY");
            columnsCuratorTable.add("fio VARCHAR(50)");

            curatorTable.create(columnsCuratorTable);

            List<String> columnsStudent = new ArrayList<>();
            columnsStudent.add("id");
            columnsStudent.add("fio");
            columnsStudent.add("sex");
            columnsStudent.add("id_group");

            studentTable.insert(columnsStudent, new Student(1, "Шамиль Басаев", 'm', 1));
            studentTable.insert(columnsStudent, new Student(2, "Руслан Гилаев", 'm', 1));
            studentTable.insert(columnsStudent, new Student(3, "Ушат Помоев", 'm', 1));
            studentTable.insert(columnsStudent, new Student(4, "Подрыв Устоев", 'm', 1));
            studentTable.insert(columnsStudent, new Student(5, "Поджог Сараев", 'f', 1));
            studentTable.insert(columnsStudent, new Student(6, "Погром Евреев", 'm', 2));
            studentTable.insert(columnsStudent, new Student(7, "Обвал Забоев", 'f', 2));
            studentTable.insert(columnsStudent, new Student(8, "Угон Харлеев", 'f', 2));
            studentTable.insert(columnsStudent, new Student(9, "Исход Изгоев", 'm', 2));
            studentTable.insert(columnsStudent, new Student(10, "Захват Покоев", 'm', 3));
            studentTable.insert(columnsStudent, new Student(11, "Камаз Отходов", 'f', 3));
            studentTable.insert(columnsStudent, new Student(12, "Развод Супругов", 'm', 3));
            studentTable.insert(columnsStudent, new Student(13, "Улов Кальмаров", 'm', 3));
            studentTable.insert(columnsStudent, new Student(14, "Рулон Обоев", 'm', 3));
            studentTable.insert(columnsStudent, new Student(15, "Удар Морозов", 'f', 3));

            List<String> columnsGroup = new ArrayList<>();
            columnsGroup.add("id");
            columnsGroup.add("name_group");
            columnsGroup.add("id_curator");

            groupTable.insert(columnsGroup, new Group(1, "QA", 1));
            groupTable.insert(columnsGroup, new Group(2, "QC", 2));
            groupTable.insert(columnsGroup, new Group(3, "Team Leads", 3));

            List<String> columnsCurator = new ArrayList<>();
            columnsCurator.add("id");
            columnsCurator.add("fio");

            curatorTable.insert(columnsCurator, new Curator(1, "Чума Ч. А."));
            curatorTable.insert(columnsCurator, new Curator(2, "Война В. А."));
            curatorTable.insert(columnsCurator, new Curator(3, "Голод Г. Д."));
            curatorTable.insert(columnsCurator, new Curator(4, "Смердь С. Д."));

            System.out.println("*** Информация о студентах ***");
            studentTable.resultDisplay(studentTable.selectSimple("*"));
            System.out.println("*** Кол-во студентов ***");
            studentTable.resultDisplay(studentTable.count());
            System.out.println("*** Студентки ***");
            studentTable.resultDisplay(studentTable.selectWithWhere("fio", "sex", "f"));
            System.out.println("*** Обновить информацию о группе сменив куратора ***");
            System.out.println("ДО:");
            groupTable.resultDisplay(groupTable.selectWithWhere("*", "id", "2"));
            groupTable.update("id_curator", "4", "id", "2");
            System.out.println("ПОСЛЕ:");
            groupTable.resultDisplay(groupTable.selectWithWhere("*", "id", "2"));
            System.out.println("*** Группы их кураторы ***");
            groupTable.resultDisplay(groupTable.selectWithJoin("StudentGroup.name_group, Curator.fio", "id_curator", "curator", "id"));
            System.out.println("*** Студенты из определенной группы (по имени) ***");
            studentTable.resultDisplay(studentTable.selectWithSubRequest("fio", "id_group", "id", "StudentGroup", "name_group", "QA"));
        } finally {
            dbExecutor.close();
        }
    }
}
