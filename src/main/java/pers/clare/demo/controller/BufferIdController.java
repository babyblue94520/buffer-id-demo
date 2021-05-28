package pers.clare.demo.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class BufferIdController {
    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");

    protected String run(
            Integer thread
            , Integer count
            , Callable<Long> callable
    ) throws Exception {
        ExecutorService executors = Executors.newFixedThreadPool(thread);
        long start = System.currentTimeMillis();
        List<Callable<Long>> tasks = new ArrayList<>();
        for (int t = 0; t < thread; t++) {
            tasks.add(() -> {
                long total = 0;
                for (int i = 0; i < count; i++) {
                    total += callable.call();
                }
                return total;
            });
        }
        long sum = 0;
        List<Future<Long>> futures = executors.invokeAll(tasks);
        for (Future<Long> f : futures) {
            sum += f.get();
        }
        long ms = System.currentTimeMillis() - start;
        executors.shutdown();
        long totalCount = (long) thread * count;
        long max = callable.call();
        long valid = ((max - totalCount) + (max - 1)) * totalCount / 2;

        return new StringBuffer()
                .append("ID格式：" + max).append('\n')
                .append("ID總數量：" + formatter.format(totalCount)).append('\n')
                .append("花費總時間：" + ms + "ms").append('\n')
                .append("平均：" + formatter.format(totalCount * 1000L / ms) + "/s").append('\n')
                .append("驗證").append('\n')
                .append("總和：" + sum).append('\n')
                .append("檢驗：" + valid).append('\n')
                .toString();
    }

    protected String run2(
            Integer thread
            , Integer count
            , Callable<String> callable
    ) throws Exception {
        ExecutorService executors = Executors.newFixedThreadPool(thread);
        long start = System.currentTimeMillis();
        List<Callable<Integer>> tasks = new ArrayList<>();
        for (int t = 0; t < thread; t++) {
            tasks.add(() -> {
                for (int i = 0; i < count; i++) {
                    callable.call();
                }
                return count;
            });
        }
        long total = 0;
        List<Future<Integer>> futures = executors.invokeAll(tasks);
        for (Future<Integer> f : futures) {
            total += f.get();
        }
        long ms = System.currentTimeMillis() - start;
        executors.shutdown();
        return "\nID格式:" + callable.call() + "\nID總數量；" + formatter.format(total) + "\n花費總時間：" + ms + " ms\n平均：" + formatter.format(total * 1000L / ms) + "/s";
    }
}
