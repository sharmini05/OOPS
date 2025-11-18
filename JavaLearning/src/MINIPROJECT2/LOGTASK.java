package MINIPROJECT2;

import java.util.*;
import java.util.concurrent.Callable;

public class LOGTASK implements Callable<Map<String, Integer>> {
    private List<String> logLines;  // fake log file content

    public LOGTASK(List<String> logLines) {
        this.logLines = logLines;
    }

    @Override
    public Map<String, Integer> call() {
        Map<String, Integer> keywordCount = new HashMap<>();
        for (String line : logLines) {
            line = line.toLowerCase();
            if (line.contains("error"))
                keywordCount.merge("error", 1, Integer::sum);
            if (line.contains("warning"))
                keywordCount.merge("warning", 1, Integer::sum);
            if (line.contains("failed"))
                keywordCount.merge("failed", 1, Integer::sum);
        }
        System.out.println(Thread.currentThread().getName() + " analyzed file");
        return keywordCount;
    }
}

