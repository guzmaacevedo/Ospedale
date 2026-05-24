/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package response;

/**
 *
 * @author domtr
 */

public class Response {
    private boolean success;
    private int code;
    private String message;
    private Object data;

    public Response(boolean success, int code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Response ok(String message) {
        return new Response(true, 200, message, null);
    }

    public static Response ok(String message, Object data) {
        return new Response(true, 200, message, data);
    }

    public static Response error(int code, String message) {
        return new Response(false, code, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}