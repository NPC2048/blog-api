package com.liangyuelong.blog.test.elapsedtime;

public interface LoopExecutor {

    /**
     * 能在函数内部获取到循环的次数
     *
     * @param loop 循环次数
     */
    void execute(int loop);

}
