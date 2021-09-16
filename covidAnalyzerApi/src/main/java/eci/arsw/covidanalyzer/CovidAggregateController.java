package eci.arsw.covidanalyzer;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.service.ICovidAggregateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping( value = "/covid/")
public class CovidAggregateController {
    @Autowired
    ICovidAggregateService covidAggregateService;

    //TODO: Implemente todos los metodos POST que hacen falta.

    @RequestMapping(value = "/result/true-positive", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addTruePositiveResult(@RequestBody Result result) {
        //TODO

        try {
            covidAggregateService.aggregateResult(result, ResultType.TRUE_POSITIVE);
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase() , HttpStatus.CREATED);
        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.getLogger(CovidAggregateController.class.getName()).log(Level.SEVERE, null, exception);
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.FORBIDDEN);
        }
    }
    @RequestMapping(value = "/result/true-negative", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addTrueNegativeResult(@RequestBody Result result) {
        //TODO
        try {
            covidAggregateService.aggregateResult(result, ResultType.TRUE_NEGATIVE);
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase() , HttpStatus.CREATED);
        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.getLogger(CovidAggregateController.class.getName()).log(Level.SEVERE, null, exception);
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.FORBIDDEN);
        }
    }
    @RequestMapping(value = "/result/false-negative", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addFalseNegativeResult(@RequestBody Result result) {
        //TODO
        try {
            covidAggregateService.aggregateResult(result, ResultType.FALSE_NEGATIVE);
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase() , HttpStatus.CREATED);
        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.getLogger(CovidAggregateController.class.getName()).log(Level.SEVERE, null, exception);
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.FORBIDDEN);
        }

    }
    @RequestMapping(value = "/result/false-positive", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addFalsePositiveResult(@RequestBody Result result) {
        //TODO
        try {
            covidAggregateService.aggregateResult(result, ResultType.FALSE_POSITIVE);
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase() , HttpStatus.CREATED);
        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.getLogger(CovidAggregateController.class.getName()).log(Level.SEVERE, null, exception);
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.FORBIDDEN);
        }

    }

    //TODO: Implemente todos los metodos GET que hacen falta.

    @RequestMapping(value = "/result/true-positive", method = RequestMethod.GET)
    public ResponseEntity getTruePositiveResult() {
        //TODO
        //covidAggregateService.getResult(ResultType.TRUE_POSITIVE);
        return ResponseEntity.ok("Hello World");
    }


    //TODO: Implemente el método.

    @RequestMapping(value = "/result/persona/{id}", method = RequestMethod.PUT)
    public ResponseEntity savePersonaWithMultipleTests(@PathVariable("id") String id) {
        //TODO
        try {
            Result result = new Result(id);
            covidAggregateService.aggregateResult(result, ResultType.TRUE_NEGATIVE);

            return new ResponseEntity<>( covidAggregateService.getResult( id), HttpStatus.CREATED);
        } catch (Exception exception) {
            exception.printStackTrace();
            Logger.getLogger(CovidAggregateController.class.getName()).log(Level.SEVERE, null, exception);
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase(),HttpStatus.FORBIDDEN);
        }

    }
    
}