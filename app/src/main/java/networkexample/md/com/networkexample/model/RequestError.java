package networkexample.md.com.networkexample.model;


import java.util.HashMap;

public class RequestError {

    private String status;
    private String message;
    private HashMap<String, String> errors;


    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }
}
