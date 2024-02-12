package edu.eci.arep.ASE.app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import edu.eci.arep.ASE.app.http.HTTPMetodo;
import edu.eci.arep.ASE.app.http.exception.HTTPException;
import edu.eci.arep.ASE.app.model.Request;
import edu.eci.arep.ASE.app.model.Response;

public class Registry {

    private Map<String, Collection <HTTPMetodo>> registers;

    public Registry() {
        this.registers = new HashMap<>();
    }

    public <R> void get(String endPoint, BiFunction<Request, Response, R> handler){
        if (!validateEndPoint(endPoint, true)){
            throw new HTTPException();
        }

        if (!registers.containsKey(endPoint)) {
            registers.put(endPoint, new ArrayList<>());
            
        }
        registers.get(endPoint).add(new HTTPMetodo("GET", handler));
    }

    public <R> R doGet(String endPoint, Request request, Response response){
        String endPointFinal = findEndPoint(endPoint);
        if (endPointFinal.equals("")){
            throw new HTTPException();
        }
        HTTPMetodo funcionMetodo = registers.get(endPointFinal).stream()
            .filter((httpMetodo) -> httpMetodo.validateMetodoHttp("GET"))
            .findFirst()
            .orElseThrow(HTTPException::new);
        return funcionMetodo.ejecutar(request, response);
    } 

    public <R> void post(String endPoint, BiFunction<Request, Response, R> handler){
        if (!validateEndPoint(endPoint, true)){
            throw new HTTPException();
        }

        if (!registers.containsKey(endPoint)) {
            registers.put(endPoint, new ArrayList<>());
            
        }
        registers.get(endPoint).add(new HTTPMetodo("POST", handler));
    }

    public <R> R doPost(String endPoint, Request request, Response response){
        String endPointFinal = findEndPoint(endPoint);
        if (endPointFinal.equals("")){
            throw new HTTPException();
        }
        HTTPMetodo funcionMetodo = registers.get(endPointFinal).stream()
            .filter((httpMetodo) -> httpMetodo.validateMetodoHttp("POST"))
            .findFirst()
            .orElseThrow(HTTPException::new);
        return funcionMetodo.ejecutar(request, response);
    } 

    private String findEndPoint (String endPoint){
        if (!validateEndPoint(endPoint, false)) {
            throw new HTTPException();
        }

        String [] result = new String[0];
        String [] compareEndPoint = endPoint.substring(1).split("/");        
        String finalResult = "";
        for (String key : registers.keySet()) {
            String [] compareKey = key.substring(1).split("/");
            if(compareEndPoint.length != compareKey.length){
                continue;
            }

            boolean isEqual = true;
            for (int i = 0; i < compareEndPoint.length; i++) {

                if(!compareKey[i].startsWith(":") && !compareEndPoint[i].equals(compareKey[i])) {
                    isEqual = false;
                    break;
                }
            }
            if (isEqual && compareKey.length > result.length) {
                result = compareKey;
                finalResult = key;                
            }

        }
        return finalResult;
    }

    public boolean validateEndPoint(String endPoint, boolean isRegistry){
        if (!endPoint.startsWith("/")) {
            return false;            
        } else if (endPoint.equals("/")) {
            return true;
        }

        return Arrays.asList(endPoint.substring(1).split("/")).parallelStream()
            .allMatch((seccion)->
                seccion != null && 
                !(seccion.equals("")) && 
                !(seccion.contains("*+¨^$# ")) && 
                (isRegistry || !seccion.startsWith(":"))
    
            );
    }

    public boolean hasEndPoint(String endPoint){
        String resultFind = this.findEndPoint(endPoint); 
        return resultFind != null && !resultFind.equals("");
    }
    
}
