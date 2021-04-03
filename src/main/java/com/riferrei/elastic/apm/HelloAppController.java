package com.riferrei.elastic.apm;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloAppController {

    @RequestMapping(method= RequestMethod.GET, value="/hello")
    public Response Car() {
        return new Response("Hello World");
    }

    private class Response {

        private String message;

        public Response(String message) {
            setMessage(message);
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

}
