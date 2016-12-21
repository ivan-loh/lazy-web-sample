package com.eriad.deffered;

import com.eriad.deffered.controllers.Maintenance;
import com.eriad.deffered.controllers.Stats;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static spark.Spark.*;


public class App {

    public static void main(String[] args) {

        /**
         * Base Stuff
         */

        int             port      = 8080;
        int             cores     = 4;
        String          name      = "new-deffered";
        ExecutorService executors = Executors
                                        .newFixedThreadPool(4, r -> {
                                            Thread t = new Thread(r);
                                                   t.setName(name + " worker");
                                            return t;
                                        });


        /**
         * Setup
         */
        port(port);
        loadControllers(executors);

    }


    /**
     * Programatically load all the controllers, cause we lazy
     *
     * @param executorService the service to be used by all the controllers
     */
    static void loadControllers(ExecutorService executorService) {

        Class[] cArgs = new Class[]{ExecutorService.class};

        new Reflections("com.eriad.deffered")
                .getSubTypesOf(Controller.class)
                .forEach((controller) -> {

                    try {

                        controller
                                .getDeclaredConstructor(cArgs)
                                .newInstance(executorService)
                                .load();

                        System.out.println(controller.getSimpleName() + " Loaded");

                    } catch (NoSuchMethodException
                            | IllegalAccessException
                            | InstantiationException
                            | InvocationTargetException e) {

                        System.out.println(controller.getSimpleName() + " Failed to load, " + e.getMessage());
                        e.printStackTrace();
                    }

                });

    }
}
