package eci.arsw.covidanalyzer.persistence.impl;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.persistence.IResultPersitence;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 9/16/2021
 */
@Component
public class ResultPersitence implements IResultPersitence {
    private ConcurrentHashMap<ResultType, ConcurrentHashMap<String, List<Result>>> results;


    public ResultPersitence(){
        results = new ConcurrentHashMap<>();
    }


    private List<Result> addResult( Result result){
        ArrayList<Result> list =  new ArrayList<>();
        list.add(result);
        return list;

    }

    @Override
    public void aggregateResult(Result result, ResultType type) {
        String key = result.getName();
        if(results.containsKey(type)) {
            if(results.get(type).containsKey(key)) results.get(type).get(key).add(result);
            else{
                results.get(type).put(key, (ArrayList<Result>) addResult(result));
            }

        }
        else{
            ConcurrentHashMap<String, List<Result>> temp = new ConcurrentHashMap<>();
            temp.put(key, addResult(result));
            results.put(type, temp);
        }

    }

    @Override
    public List<Result> getResult(ResultType type) throws Exception {
        List<Result> answer = new ArrayList<>();
        results.get(type).forEach( (k,v) ->{
            v.forEach( result -> answer.add(result));
        });

        return answer;
    }
}
