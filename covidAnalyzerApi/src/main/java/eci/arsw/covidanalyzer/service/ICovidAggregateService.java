package eci.arsw.covidanalyzer.service;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;

import java.util.Set;
import java.util.UUID;

public interface ICovidAggregateService {

    /**
     * Add a new result into the specified result type storage.
     *
     * @param result
     * @param type
     * @return
     */
    void aggregateResult(Result result, ResultType type) throws Exception;

    /**
     * Get all the results for the specified result type.
     *
     * @param type
     * @return
     */
    Result getResult( String name )  throws Exception;

    Set<Result> getResult(ResultType resultType )  throws Exception;

    /**
     * 
     * @param id
     * @param type
     */
    void upsertPersonWithMultipleTests(UUID id, ResultType type)  throws Exception;


}
