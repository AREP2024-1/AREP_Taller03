package edu.eci.arep.ASE.app.http;

import java.util.function.BiFunction;

import edu.eci.arep.ASE.app.model.Request;
import edu.eci.arep.ASE.app.model.Response;

public class HTTPMetodo {

    private String metodo;
    private BiFunction<Request, Response, ?> handler;

    public HTTPMetodo(String metodo, BiFunction<Request, Response, ?> handler) {
        this.metodo = metodo;
        this.handler = handler;
    }

    public <R> R ejecutar(Request request, Response response) {
        return (R) handler.apply(request, response);
    }

    public boolean validateMetodoHttp(String metodoHttp) {
        return this.metodo.equals(metodoHttp);
    }
    
}
