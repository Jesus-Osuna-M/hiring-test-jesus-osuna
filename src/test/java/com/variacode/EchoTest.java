package com.variacode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EchoTest {
    @Test
    void executeValidEndpoint(){
        /*Testing successful petition*/
        int testPostCode200= Echo.connect("https://postman-echo.com/post","testing post","POST");
        assertEquals(200,testPostCode200);

        int testPutCode200= Echo.connect("https://postman-echo.com/put","testing put","PUT");
        assertEquals(200,testPutCode200);

        int testGetCode200= Echo.connect("https://postman-echo.com/get","testing get","GET");
        assertEquals(200,testGetCode200);

        int testDeleteCode200= Echo.connect("https://postman-echo.com/delete","testing get","DELETE");
        assertEquals(200,testDeleteCode200);
    }

    @Test
    void executeInvalidEndpoint(){
        /*Testing petition to a wrong url*/
        int testPostCode404= Echo.connect("https://postman-echo.com/INVALID_URL","testing post","POST");
        assertEquals(404,testPostCode404);

        int testPutCode404= Echo.connect("https://postman-echo.com/INVALID_URL","testing put","PUT");
        assertEquals(404,testPutCode404);

        int testGetCode404= Echo.connect("https://postman-echo.com/INVALID_URL","testing get","GET");
        assertEquals(404,testGetCode404);

        int testDeleteCode404= Echo.connect("https://postman-echo.com/INVALID_URL","testing get","DELETE");
        assertEquals(404,testDeleteCode404);
    }
}