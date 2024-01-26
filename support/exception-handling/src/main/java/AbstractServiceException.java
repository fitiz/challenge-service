import org.springframework.http.HttpStatus;

import java.util.List;

public abstract class AbstractServiceException extends RuntimeException {

    private String serviceExceptionMessage;

    private HttpStatus status;

    public AbstractServiceException() {
    }

    public AbstractServiceException(String serviceExceptionMessage, HttpStatus status) {
        this.serviceExceptionMessage = serviceExceptionMessage;
        this.status = status;
    }

    public String getServiceExceptionMessage() {
        return serviceExceptionMessage;
    }

    public void setServiceExceptionMessage(String serviceExceptionMessage) {
        this.serviceExceptionMessage = serviceExceptionMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public ServiceErrorResponse buildServiceErrorResponse() {
        ServiceError serviceError = new ServiceError(getServiceExceptionMessage());
        return new ServiceErrorResponse(List.of(serviceError));
    }
}
