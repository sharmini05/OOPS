package MINIPROJECT2;

import java.util.*;
import java.util.concurrent.*;

public class LOGANALYZER {
    private ExecutorService executor;
    private ConcurrentHashMap<String, Integer> globalCount;

    public LOGANALYZER(int threadCount) {
        executor = Executors.newFixedThreadPool(threadCount);
        globalCount = new ConcurrentHashMap<>();
    }

    public void analyzeLogs(List<List<String>> allLogs) throws Exception {
        long start = System.currentTimeMillis();

        List<Future<Map<String, Integer>>> results = new ArrayList<>();

        // submit each fake log file for analysis
        for (List<String> log : allLogs) {
            results.add(executor.submit(new LOGTASK(log)));
        }

        // collect results from all threads
        for (Future<Map<String, Integer>> f : results) {
            Map<String, Integer> result = f.get(); // wait for thread to finish
            result.forEach((k, v) -> globalCount.merge(k, v, Integer::sum));
        }

        long end = System.currentTimeMillis();
        System.out.println("\nTotal Execution Time: " + (end - start) + " ms");
        System.out.println("Aggregated Keyword Counts: " + globalCount);

        executor.shutdown();
    }
}
