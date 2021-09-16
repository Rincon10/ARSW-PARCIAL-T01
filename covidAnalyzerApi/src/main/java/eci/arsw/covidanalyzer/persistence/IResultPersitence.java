package eci.arsw.covidanalyzer.persistence;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;

import java.util.List;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 9/16/2021
 */
public interface IResultPersitence {

    void aggregateResult(Result result, ResultType type) throws Exception;

    List<Result> getResult(ResultType type)throws Exception;
}
