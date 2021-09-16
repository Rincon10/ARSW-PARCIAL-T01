package eci.arsw.covidanalyzer.threads;

import eci.arsw.covidanalyzer.CovidAnalyzerTool;
import eci.arsw.covidanalyzer.Result;
import eci.arsw.covidanalyzer.ResultAnalyzer;
import eci.arsw.covidanalyzer.TestReader;

import java.io.File;
import java.util.List;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 9/16/2021
 */
public class ResultTread extends Thread{
    private TestReader testReader ;
    private ResultAnalyzer resultAnalyzer ;
    private CovidAnalyzerTool covidAnalyzerTool ;
    private List<File> resultFiles ;
    private boolean stop = false;

    public ResultTread(TestReader testReader, ResultAnalyzer resultAnalyzer, CovidAnalyzerTool covidAnalyzerTool, List<File> resultFiles) {
        this.testReader= testReader;
        this.resultAnalyzer= resultAnalyzer;
        this.covidAnalyzerTool= covidAnalyzerTool;
        this.resultFiles= resultFiles;
        super.start();
    }

    public boolean isStoped() {
        return stop;
    }

    public void stopThread(){
        stop = true;
    }

    public synchronized void resumeThread(){
        stop =false;
        notifyAll();
    }

    @Override
    public void run() {
        for (File resultFile : resultFiles) {
            List<Result> results = testReader.readResultsFromFile(resultFile);
            for (Result result : results) {
                while (stop){
                    try {
                        synchronized (this ){
                            wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                resultAnalyzer.addResult(result);
            }
            covidAnalyzerTool.incrementFilesProcessed();
        }
    }
}
