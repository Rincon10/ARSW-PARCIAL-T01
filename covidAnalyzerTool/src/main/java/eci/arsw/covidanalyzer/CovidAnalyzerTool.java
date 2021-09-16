package eci.arsw.covidanalyzer;

import eci.arsw.covidanalyzer.threads.TestThread;

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

    public static ResultAnalyzer resultAnalyzer;
    public static TestReader testReader;
    public static int amountOfFilesTotal;
    public static AtomicInteger amountOfFilesProcessed;
    public static List<TestThread> threads;

    public CovidAnalyzerTool() {
        resultAnalyzer = new ResultAnalyzer();
        testReader = new TestReader();
        amountOfFilesProcessed = new AtomicInteger();
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
        List<File> resultFiles = getResultFileList();
        amountOfFilesProcessed.set(0);
        amountOfFilesTotal = resultFiles.size();

        int partition = amountOfFilesTotal/numberOfThreads;

        // Creacion de los Threads
        prepareThreads( numberOfThreads, partition, amountOfFilesTotal, resultFiles);

    }

    private void prepareThreads(int numberOfThreads, int partition, int amountOfFilesTotal, List<File> csvFiles) {
        threads = new ArrayList<>();
        int start = 0;
        int end;
        for (int i = 0; i < numberOfThreads; i++) {

            end = start + partition;
            end+= ( i == 0 )? amountOfFilesTotal%numberOfThreads :0 ;

            threads.add(new TestThread(start , end ,csvFiles));

            //System.out.println(start+" "+end);
            start = end;

        }
    }

    public static void printMessage( CovidAnalyzerTool covidAnalyzerTool) {
        String message = "Processed %d out of %d files.\nFound %d positive people:\n%s";
        Set<Result> positivePeople = covidAnalyzerTool.getPositivePeople();
        String affectedPeople = positivePeople.stream().map(Result::toString).reduce("", (s1, s2) -> s1 + "\n" + s2);
        message = String.format(message, covidAnalyzerTool.amountOfFilesProcessed.get(), covidAnalyzerTool.amountOfFilesTotal, positivePeople.size(), affectedPeople);
        System.out.println(message);
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

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        int numberOfThreads = 22;
        CovidAnalyzerTool covidAnalyzerTool = new CovidAnalyzerTool();
        covidAnalyzerTool.processResultData(numberOfThreads);

        threads.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Presione enter");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine( );
            if (line.contains("exit"))
                break;
            printMessage(covidAnalyzerTool);
        }
    }

}

