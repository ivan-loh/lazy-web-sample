package com.eriad.deffered.controllers;

import com.eriad.deffered.Controller;
import com.eriad.deffered.misc.Counter;

import java.util.concurrent.ExecutorService;

import static spark.Spark.*;

public class Maintenance extends Controller {

    public Maintenance(ExecutorService executorService) {
        super(executorService);
    }

    @Override
    public void load() {

        final String path = this.path();

        before(path + "*", (req, res) -> {
            Counter.INSTANCE.incr();
            System.out.println(req.pathInfo() + " invoked");
        });

        get(path + "ping", (req, res) -> "pong");

        get(path + "counters", (req, res) -> {

            this
                    .executorService
                    .submit(() -> {
                        int counter = 0;
                        while (counter < 10) {
                            counter = counter + 1;
                            System.out.println(Thread.currentThread().getName() + " working on \t" + counter);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });

            return "launched";
        });

    }

}
