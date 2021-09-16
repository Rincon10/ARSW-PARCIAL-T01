package eci.arsw.covidanalyzer.model;

public class Result {
    private String persona;
    private ResultType resultType;

    public Result( String persona ){
        this.persona = persona;
    }

    public Result( String persona, ResultType resultType){
        this.persona = persona;
        this.resultType=resultType;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }
}
