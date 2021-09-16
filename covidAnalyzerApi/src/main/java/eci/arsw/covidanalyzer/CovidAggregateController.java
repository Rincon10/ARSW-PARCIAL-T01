package eci.arsw.covidanalyzer;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.service.ICovidAggregateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/covid/result/")
public class CovidAggregateController {
    @Autowired
    ICovidAggregateService covidAggregateService;

    //TODO: Implemente todos los metodos POST que hacen falta.

    @RequestMapping(value = "/true-positive", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addTruePositiveResult(@RequestBody Result result) {
        //TODO
        try {
            //Registrar dato
            covidAggregateService.aggregateResult(result, ResultType.TRUE_POSITIVE);

            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase() , HttpStatus.CREATED);
        }  catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(CovidAggregateController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase(),HttpStatus.FORBIDDEN);
        }
    }

    //TODO: Implemente todos los metodos GET que hacen falta.

    @RequestMapping(value = "/true-positive", method = RequestMethod.GET)
    public ResponseEntity getTruePositiveResult() {
        //TODO

        try {
            List<Result> data = covidAggregateService.getResult(ResultType.TRUE_POSITIVE);

            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        }  catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(CovidAggregateController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error inesperado",HttpStatus.NOT_FOUND);
        }
    }


    //TODO: Implemente el método.

    @RequestMapping(value = "/persona/{id}", method = RequestMethod.PUT)
    public ResponseEntity savePersonaWithMultipleTests(@PathVariable("id") String id) {
        //TODO
        try {
            covidAggregateService.getResult(ResultType.TRUE_POSITIVE);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
    
}