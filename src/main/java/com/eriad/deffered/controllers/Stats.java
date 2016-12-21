package com.eriad.deffered.controllers;

import com.eriad.deffered.Controller;
import com.eriad.deffered.misc.Counter;

import java.util.concurrent.ExecutorService;

import static spark.Spark.*;

public class Stats extends Controller{

    public Stats(ExecutorService executorService) {
        super(executorService);
    }

    @Override
    public void load() {
        System.out.println(this.path());
        get(this.path() + "transactions",
                (req, res) -> Counter.INSTANCE.get());
    }
}
