import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@Component
public class GenericExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServiceErrorResponse> handle(Exception exception) {
        try {
            if (exception instanceof AbstractServiceException serviceException) {
                LOGGER.error(serviceException.getServiceExceptionMessage(), serviceException);
                return ResponseEntity.status(serviceException.getStatus())
                        .body(serviceException.buildServiceErrorResponse());
            }
        } catch (Exception errorResponseBuildException) {
            LOGGER.error("Exception occurred while building ResponseEntity from the exception.", errorResponseBuildException);
            return buildDefaultErrorResponse();
        }
        LOGGER.error("Unknown exception occurred", exception);
        return buildDefaultErrorResponse();
    }

    private ResponseEntity<ServiceErrorResponse> buildDefaultErrorResponse() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildServiceErrorResponse("Internal Server Error!"));
    }

    private ServiceErrorResponse buildServiceErrorResponse(String message) {
        ServiceError serviceError = new ServiceError(message);
        return new ServiceErrorResponse(List.of(serviceError));
    }
}
