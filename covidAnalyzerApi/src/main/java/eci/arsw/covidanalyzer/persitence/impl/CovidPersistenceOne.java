package eci.arsw.covidanalyzer.persitence.impl;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.persitence.CovidPersitence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 9/16/2021
 */

@Component
@Qualifier("one")
public class CovidPersistenceOne implements CovidPersitence {
    //Primer llave es el resultado
    private ConcurrentHashMap<String, List<Result>> persons;

    public CovidPersistenceOne(){
        persons = new ConcurrentHashMap<>();
        int times = 4;
        for (int i = 0; i < times; i++) {
            List<Result> results = new ArrayList<>();
            results.add(new Result("test"+i));
            persons.put(i+"",results);
        }
    }


    @Override
    public void addResult(Result result, ResultType resultType)  {
        String key = result.getPersona();
        result.setResultType(resultType);
        if( persons.containsKey(key)) persons.get(key).add(result);
        else{
            ArrayList<Result> arrayList = new ArrayList<>();
            arrayList.add(result);
            persons.put(key, arrayList);
        }
    }

    @Override
    public Set<Result> getResults(ResultType resultType) throws Exception {
        Set<Result> r = new HashSet<>();
        persons.forEach((k,v) ->{
            v.forEach( re -> {
                if(re.getResultType().equals(resultType)) r.add(re);
            });
        });
        return r;
    }

    @Override
    public Result getResult( String name ) throws Exception {
        int index = persons.get(name).size();
        return persons.get(name).get(index-1);
    }



    @Override
    public void upsertPersonWithMultipleTests(UUID id, ResultType type) {

    }
}
