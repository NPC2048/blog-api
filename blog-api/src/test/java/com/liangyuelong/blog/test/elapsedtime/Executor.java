package com.liangyuelong.blog.test.elapsedtime;

public interface Executor {

    void execute();

    default String name() {
        return this.toString();
    }

}
