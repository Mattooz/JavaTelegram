package me.nickframe.telegramapi.api;

/**
 * Created by Utente on 23/10/2015.
 */
public class RequestErrorException extends Exception {

	private static final long serialVersionUID = 8566879561112381717L;
	
	private RequestError error = null;

    public RequestErrorException(String ok, String error_code, String description) {
        error = new RequestError(ok, error_code, description);
    }

    public RequestErrorException(String message, String ok, String error_code, String description) {
        super(message);
        error = new RequestError(ok, error_code, description);
    }

    public RequestError getError() {
        return this.error;
    }

}
