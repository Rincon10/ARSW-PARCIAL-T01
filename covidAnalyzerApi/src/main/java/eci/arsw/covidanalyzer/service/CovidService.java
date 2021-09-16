package eci.arsw.covidanalyzer.service;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.persistence.IResultPersitence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 9/16/2021
 */
@Service
public class CovidService implements ICovidAggregateService{
    @Autowired
    IResultPersitence persitence;

    @Override
    public void aggregateResult(Result result, ResultType type) throws Exception {
        persitence.aggregateResult(result, type);

    }

    @Override
    public List<Result> getResult(ResultType type) throws Exception {
        return persitence.getResult(type);
    }

    @Override
    public void upsertPersonWithMultipleTests(UUID id, ResultType type) {

    }
}
