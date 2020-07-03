package com.liangyuelong.blog.test.elapsedtime;

/**
 * 统计某些代码的运行时间
 *
 * @author yuelong.liang
 */
public class ElapsedTime {

    private ElapsedTime() {
    }

    public static long elapsedTime(Executor executor) {
        long t = System.currentTimeMillis();
        executor.execute();
        return System.currentTimeMillis() - t;
    }

    public static long elapsedTime(Executor executor, int count) {
        long t = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            executor.execute();
        }
        return System.currentTimeMillis() - t;
    }

    public static void elapsedTimePrint(Executor executor) {
        long time = elapsedTime(executor);
        System.out.println(executor + " elapsed time: " + time);
    }

    public static void elapsedTimePrint(Executor executor, int count) {
        long time = elapsedTime(executor, count);
        System.out.println(executor + " loop elapsed time: " + time);
    }

    public static long elapsedTime(LoopExecutor executor, int count) {
        long t = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            executor.execute(i);
        }
        return System.currentTimeMillis() - t;
    }

    public static void elapsedTimePrint(LoopExecutor executor, int count) {
        long time = elapsedTime(executor, count);
        System.out.println(executor + " loop elapsed time: " + time);
    }

}
