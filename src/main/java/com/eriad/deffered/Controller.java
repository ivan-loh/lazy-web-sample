package com.eriad.deffered;

import java.util.concurrent.ExecutorService;

public abstract class Controller {

    protected final ExecutorService executorService;

    public Controller(ExecutorService executorService) {
        this.executorService = executorService;
    }

    protected final String path() {
        return "/" + this.getClass().getSimpleName().toLowerCase() + "/";
    }

    public abstract void load();

}
