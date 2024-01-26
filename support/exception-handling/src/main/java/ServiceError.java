public class ServiceError {

    private final String DEFAULT_ERROR_MESSAGE = "Oops, something went wrong...";

    private final String errorMessage;

    public ServiceError() {
        this.errorMessage = DEFAULT_ERROR_MESSAGE;
    }

    public ServiceError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return errorMessage;
    }
}
