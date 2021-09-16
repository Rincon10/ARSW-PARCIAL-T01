package eci.arsw.covidanalyzer.threads;

import java.io.File;
import java.util.List;

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
        for (int i = a; i < b; i++) {

        }
    }
}
