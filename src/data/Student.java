package data;

public class Student implements ITableData {

    private int id;
    private String fio;
    private char sex;
    private int id_group;

    public Student(int id, String fio, char sex, int id_group) {
        this.id = id;
        this.fio = fio;
        this.sex = sex;
        this.id_group = id_group;
    }

    public int getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public char getSex() {
        return sex;
    }

    public int getIdGroup() {
        return id_group;
    }

    public String toString(){
        return String.format("'%s', '%s', '%s', '%s'", getId(), getFio(), getSex(), getIdGroup());
    }
}
