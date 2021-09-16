package eci.arsw.covidanalyzer;

import eci.arsw.covidanalyzer.threads.ResultTread;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A Camel Application
 */
public class CovidAnalyzerTool {

    private ResultAnalyzer resultAnalyzer;
    private TestReader testReader;
    private int amountOfFilesTotal;
    private AtomicInteger amountOfFilesProcessed;
    private static List<ResultTread> threads;

    public CovidAnalyzerTool() {
        resultAnalyzer = new ResultAnalyzer();
        testReader = new TestReader();
        amountOfFilesProcessed = new AtomicInteger();
        threads = new ArrayList<>();
    }

    public void processResultData() {
        amountOfFilesProcessed.set(0);
        List<File> resultFiles = getResultFileList();
        amountOfFilesTotal = resultFiles.size();
        for (File resultFile : resultFiles) {
            List<Result> results = testReader.readResultsFromFile(resultFile);
            for (Result result : results) {
                resultAnalyzer.addResult(result);
            }
            amountOfFilesProcessed.incrementAndGet();
        }
    }
    public void processResultData( int numberOfThreads ) {
        amountOfFilesProcessed.set(0);
        List<File> resultFiles = getResultFileList();
        amountOfFilesTotal = resultFiles.size();

        int partition = amountOfFilesTotal/numberOfThreads;
        prepareThreads( numberOfThreads, partition, resultFiles, amountOfFilesTotal );
    }

    public void incrementFilesProcessed(){
        amountOfFilesProcessed.incrementAndGet();
    }

    private void prepareThreads(int numberOfThreads, int partition, List<File> resultFiles, int amountOfFilesTotal) {
        int start = 0;
        int end;
        for (int i = 0; i < numberOfThreads; i++) {
            end = start + partition;
            end+= ( i == 0 )?  amountOfFilesTotal%numberOfThreads:0;
            List<File> temp = new ArrayList<>();
            for (int j = start; j < end; j++) {
                temp.add( resultFiles.get(j));
            }
            threads.add( new ResultTread(testReader, resultAnalyzer, this, temp) );
            start = end;

        }
    }

    private List<File> getResultFileList() {
        List<File> csvFiles = new ArrayList<>();
        try (Stream<Path> csvFilePaths = Files.walk(Paths.get("src/main/resources/")).filter(path -> path.getFileName().toString().endsWith(".csv"))) {
            csvFiles = csvFilePaths.map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFiles;
    }


    public Set<Result> getPositivePeople() {
        return resultAnalyzer.listOfPositivePeople();
    }

    public static void printMessage(CovidAnalyzerTool covidAnalyzerTool){
        String message = "Processed %d out of %d files.\nFound %d positive people:\n%s";
        Set<Result> positivePeople = covidAnalyzerTool.getPositivePeople();
        String affectedPeople = positivePeople.stream().map(Result::toString).reduce("", (s1, s2) -> s1 + "\n" + s2);
        message = String.format(message, covidAnalyzerTool.amountOfFilesProcessed.get(), covidAnalyzerTool.amountOfFilesTotal, positivePeople.size(), affectedPeople);
        System.out.println(message);
    }

    private static void stopAllThreads() {
        System.out.println("=============Deteniendo Threads=====================");
        threads.forEach( t-> t.stopThread());
    }

    private static void resumeAllThreads() {
        System.out.println("=============Resumiendo Threads=====================");
        threads.forEach( t-> t.resumeThread());
    }

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        int numberOfThreads=10;
        CovidAnalyzerTool covidAnalyzerTool = new CovidAnalyzerTool();
        Thread processingThread = new Thread(() -> covidAnalyzerTool.processResultData(numberOfThreads));
        processingThread.start();

        boolean stop = true;
        System.out.println("=============Presione Enter=====================");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();

            stop=!stop;
            if (line.contains("exit"))
                break;

            if( stop ){
                stopAllThreads();
                printMessage(covidAnalyzerTool);
            }
            else{
                resumeAllThreads();
            }

        }
    }



}

