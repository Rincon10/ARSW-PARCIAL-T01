package eci.arsw.covidanalyzer.persitence;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;

import java.util.Set;
import java.util.UUID;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 9/16/2021
 */
public interface CovidPersitence {

    public void addResult(Result result, ResultType resultType)  throws Exception;

    public Set<Result> getResults(ResultType resultType) throws Exception;

    Result getResult(String name) throws Exception;

    public void upsertPersonWithMultipleTests(UUID id, ResultType type) throws Exception;




}
