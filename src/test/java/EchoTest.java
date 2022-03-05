import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EchoTest {
    @Test
    void connect() throws Exception{


        /* Expected result code 201 succes*/
        Echo.connect("https://postman-echo.com/post","testing post","POST");
        Echo.connect("https://postman-echo.com/get","testing get","GET");
        Echo.connect("https://postman-echo.com/put","testing put","PUT");
        Echo.connect("https://postman-echo.com/delete","testing delete","DELETE");

        /*
        Misspell on url, or not valid url expected result code 404 success
        Echo.connect("https://postman-echo.com/post1","testing post","POST");
        Echo.connect("https://postman-echo.com/get1","testing get","GET");
        Echo.connect("https://postman-echo.com/put1","testing put","PUT");
        Echo.connect("https://postman-echo.com/delete1","testing delete","DELETE");*/




    }
}