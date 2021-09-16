package eci.arsw.covidanalyzer.threads;

import eci.arsw.covidanalyzer.CovidAnalyzerTool;
import eci.arsw.covidanalyzer.Result;
import eci.arsw.covidanalyzer.TestReader;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 9/16/2021
 */
public class TestThread extends Thread {
    private int a;
    private int b;
    private List<File> csvFiles;
    private boolean stop;

    public TestThread(int a, int b, List<File> csvFiles){
        this.a = a;
        this.b = b;
        this.csvFiles = csvFiles;
        stop = false;
        start();
    }
    public void stopThread() {
        stop = true;
    }

    public void resumeThread() {
        stop = false;
        synchronized ( this ){
            notifyAll();
        }
    }

    @Override
    public void run() {
        csvFiles.forEach( file ->{
            while ( stop ){
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(TestThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            List<Result> results = CovidAnalyzerTool.testReader.readResultsFromFile(file);


            for (Result result : results) {
//                System.out.println(this.getName()+" "+result);
                CovidAnalyzerTool.resultAnalyzer.addResult(result);
            }
            CovidAnalyzerTool.amountOfFilesProcessed.incrementAndGet();
        } );


        System.out.println("termine");
    }
}
