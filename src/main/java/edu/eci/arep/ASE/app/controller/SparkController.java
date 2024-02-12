package edu.eci.arep.ASE.app.controller;

import java.util.function.BiFunction;
import edu.eci.arep.ASE.app.model.Request;
import edu.eci.arep.ASE.app.model.Response;
import edu.eci.arep.ASE.app.model.Registry;


public class SparkController {
    private Registry registry = new Registry();

    public void get(String endPoint, BiFunction<Request, Response, ?> handler){
        registry.get(endPoint, handler);
    }

    public <R> R doGet(String endPoint, Request request, Response response){
        return registry.doGet(endPoint, request, response);
    }

    public void post(String endPoint, BiFunction<Request, Response, ?> handler){
        registry.post(endPoint, handler);
    }

    public <R> R doPost(String endPoint, Request request, Response response){
        return registry.doPost(endPoint, request, response);
    }

    public boolean hasEndPoint(String endPoint){
        return registry.hasEndPoint(endPoint);
    }
    
}
