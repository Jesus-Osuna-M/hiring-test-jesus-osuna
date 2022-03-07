package com.variacode;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Echo {

    private static Logger log;
    private static final String POST ="POST";
    private static final String GET ="GET";
    private static final String PUT ="PUT";
    private static final String DELETE ="DELETE";
    private static final String MSG_METHOD= "Method tested ";
    private static final String URL =" URL : ";
    private static final String CONTENT= "Content-Type";
    private static final String VALUE = "text/plain";
    private static final String REGEX = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        System.out.print("Enter URL ->");
        String url = in.nextLine();
        if (urlIsValid(url)) {
            System.out.print("Enter message to be echoed ->");
            String msg = in.nextLine();
            System.out.print("Enter method ->");
            String method = in.nextLine();
            in.close();
            if (methodIsValid(method)) {
                connect(url, msg, method);
            } else {
                System.out.println("You can only test, POST, GET, PUT, DELETE methods");
            }
        } else {
            System.out.println("The url must be valid example **https://postman-echo.com/METHOD** or **http://postman-echo.com/METHOD**");
        }
    }

    private static boolean methodIsValid(final String url){
        if(url.equalsIgnoreCase(POST)||url.equalsIgnoreCase(GET)||url.equalsIgnoreCase(PUT)||url.equalsIgnoreCase(DELETE)){
            return true;
        }
        else{
            return false;
        }
    }

    private static boolean urlIsValid(final String url) {
        try {
            Pattern patt = Pattern.compile(REGEX);
            Matcher matcher = patt.matcher(url);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static HttpRequest createRequest(String url, String msg, String method) {
        HttpRequest requestSelected = null;

        switch (method.toUpperCase()){
            case POST:
                log.log(Level.INFO,MSG_METHOD+POST+URL+ url);
                requestSelected = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header(CONTENT,VALUE)
                        .POST(HttpRequest.BodyPublishers.ofString(msg))
                        .build();
                break;

            case GET:
                log.log(Level.INFO,MSG_METHOD+GET+URL+ url);
                requestSelected = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header(CONTENT, VALUE)
                        .GET()
                        .build();
                break;

            case PUT:
                log.log(Level.INFO,MSG_METHOD+PUT+URL+ url);
                requestSelected = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header(CONTENT, VALUE)
                        .PUT(HttpRequest.BodyPublishers.ofString(msg))
                        .build();
                break;

            case DELETE:
                log.log(Level.INFO,MSG_METHOD+DELETE+URL+ url);
                requestSelected = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header(CONTENT, VALUE)
                        .DELETE()
                        .build();
                break;

            default:
                log.log(Level.INFO,"You can only test, POST, GET, PUT, DELETE methods");
                break;
        }
        return requestSelected;
    }

    public static int connect(String url,String msg,String method){
        log = Logger.getLogger("com.variacode.echo");
        HttpRequest requestSelected = createRequest(url, msg, method);
        return executeService(requestSelected);
    }

    private static int executeService(HttpRequest requestSelected)  {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        var client = HttpClient.newBuilder().executor(executor).build();
        var responseFuture = client.sendAsync(requestSelected, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> stringHttpResponse = null;
        try {
            stringHttpResponse = responseFuture.thenApply(res -> {
                log.log(Level.INFO,"Status code : " + res.statusCode());
                        switch (res.statusCode()) {
                            case 100:
                                log.log(Level.INFO,"The server is thinking through the request.");
                                break;

                            case 200:
                                log.log(Level.INFO,"Success! The request was successfully completed and the server gave the browser the expected response.");
                                break;

                            case 404:
                                log.log(Level.SEVERE,"Page not found. The site or page couldn't be reached.");
                                break;

                            case 500:
                                log.log(Level.SEVERE,"Failure. A valid request was made by the client but the server failed to complete the request.");
                                break;

                            default:
                                log.log(Level.SEVERE,"Something unexpected went wrong. Try again.");
                                break;
                        }
                        return res;
                }).get();

        executor.shutdownNow();
        return stringHttpResponse.statusCode();
        } catch (InterruptedException e) {
            log.log(Level.SEVERE,"Error executing service : ",e);
            return -1;
        } catch (ExecutionException e) {
            log.log(Level.SEVERE,"Error executing service : ",e);
            return -1;
        }

    }
}
