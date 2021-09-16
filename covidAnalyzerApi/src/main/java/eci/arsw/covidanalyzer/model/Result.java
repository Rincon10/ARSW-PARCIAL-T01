package eci.arsw.covidanalyzer.model;

public class Result {
    private String id;
    private String name;
    private ResultType type;

    public Result( String id, String name){
        this.id=id;
        this.name=name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResultType getType() {
        return type;
    }

    public void setType(ResultType type) {
        this.type = type;
    }
}
