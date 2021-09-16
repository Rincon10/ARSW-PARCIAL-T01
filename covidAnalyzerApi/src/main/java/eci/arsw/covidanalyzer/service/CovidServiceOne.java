package eci.arsw.covidanalyzer.service;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.persitence.CovidPersitence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 9/16/2021
 */
@Service
public class CovidServiceOne implements ICovidAggregateService{
    @Autowired
    @Qualifier("one")
    CovidPersitence persitence;

    @Override
    public void aggregateResult(Result result, ResultType type) throws Exception {
        persitence.addResult(result,type);

    }

    @Override
    public Result getResult( String name ) throws Exception {
        return persitence.getResult(name);
    }

    @Override
    public Set<Result> getResult(ResultType resultType) throws Exception {
        return persitence.getResults(resultType);
    }

    @Override
    public void upsertPersonWithMultipleTests(UUID id, ResultType type) {

    }
}
