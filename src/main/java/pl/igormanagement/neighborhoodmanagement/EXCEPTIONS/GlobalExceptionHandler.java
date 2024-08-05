package pl.igormanagement.neighborhoodmanagement.EXCEPTIONS;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        Response response = new Response(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handleAlreadyExistsException(AlreadyExistsException ex) {
        Response response = new Response(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OversizeException.class)
    public ResponseEntity<?> handleOversizeException(OversizeException ex) {
        Response response = new Response(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProblematicPersonException.class)
    public ResponseEntity<?> handleProblematicPersonException(ProblematicPersonException ex) {
        Response response = new Response(HttpStatus.BAD_REQUEST.value(), "Person has more or exactly 3 files/dues");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AllSpaceTakenException.class)
    public ResponseEntity<?> handleAllSpaceTakenException(AllSpaceTakenException ex) {
        Response response = new Response(HttpStatus.BAD_REQUEST.value(), "All parking spaces are already taken");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemTooBigException.class)
    public ResponseEntity<?> handleItemTooBigException(ItemTooBigException ex) {
        Response response = new Response(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
