import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Locale;
import java.util.Scanner;
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
    private static final String  POST ="POST";
    private static final String  GET ="GET";
    private static final String  PUT ="PUT";
    private static final String  DELETE ="DELETE";
    private static final String MSG_METHOD= "Method tested ";
    private static final String URL =" URL : ";
    private static final String CONTENT= "Content-Type";
    private static final String VALUE = "text/plain";
    private static final String REGEX = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public static void main(String[] args) throws Exception{

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

    static void connect(String url,String msg,String method) throws Exception{
        log = Logger.getLogger("com.variacode.echo");
        log.setLevel(Level.ALL);
        HttpRequest requestSelected = null;

        switch (method){
            case POST:
                System.out.println(MSG_METHOD+POST+URL+url);
                requestSelected = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header(CONTENT,VALUE)
                    .POST(HttpRequest.BodyPublishers.ofString(msg))
                    .build();
                break;

            case GET:
                System.out.println(MSG_METHOD+GET+URL+url);
                requestSelected = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header(CONTENT, VALUE)
                    .GET()
                    .build();
                break;

            case PUT:
                System.out.println(MSG_METHOD+PUT+URL+url);
                requestSelected = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header(CONTENT, VALUE)
                    .PUT(HttpRequest.BodyPublishers.ofString(msg))
                    .build();
                break;

            case DELETE:
                System.out.println(MSG_METHOD+DELETE+URL+url);
                requestSelected = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header(CONTENT, VALUE)
                    .DELETE()
                    .build();
                break;

            default:
                System.out.println("You can only test, POST, GET, PUT, DELETE methods");
                break;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        var client = HttpClient.newBuilder().executor(executor).build();

        var responseFuture = client.sendAsync(requestSelected, HttpResponse.BodyHandlers.ofString());
        responseFuture.thenApply(res -> {
            //log.info("StatusCode: "+ res.statusCode());
            System.out.println("Status code : "+res.statusCode());
            System.out.print("Meaning : ");
            switch (res.statusCode()){
                case 100:
                    System.out.println("The server is thinking through the request.");
                    break;

                case 200:
                    System.out.println("Success! The request was successfully completed and the server gave the browser the expected response.");
                    break;

                case 404:
                    System.out.println("Page not found. The site or page couldn't be reached.");
                    break;

                case 500:
                    System.out.println("Failure. A valid request was made by the client but the server failed to complete the request.");
                    break;

                default:
                    System.out.println("Something unexpected went wrong. Try again.");
                    break;
            }
            return res;

        })
                //.thenApply(HttpResponse::body)
                //.thenAccept(log::info)
                .get();
        //log.info("test");

        executor.shutdownNow();
    }
}
