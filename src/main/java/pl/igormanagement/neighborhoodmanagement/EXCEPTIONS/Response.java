package pl.igormanagement.neighborhoodmanagement.EXCEPTIONS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response {
    private int status;
    private String message;
}
