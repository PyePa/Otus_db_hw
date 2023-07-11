package data;

public class Group implements ITableData {

    private int id;
    private String name_group;
    private int id_curator;

    public Group(int id, String name_group, int id_curator) {
        this.id = id;
        this.name_group = name_group;
        this.id_curator = id_curator;
    }

    public int getId() {
        return id;
    }

    public String getName_group() {
        return name_group;
    }

    public int getIdCurator() {
        return id_curator;
    }

    public String toString(){
        return String.format("'%s', '%s', '%s'", getId(), getName_group(), getIdCurator());
    }
}
