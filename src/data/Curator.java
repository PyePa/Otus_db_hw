package data;

public class Curator implements ITableData {

    private int id;
    private String fio;

    public Curator(int id, String fio) {
        this.id = id;
        this.fio = fio;
    }

    public int getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public String toString(){
        return String.format("'%s', '%s'", getId(), getFio());
    }
}
