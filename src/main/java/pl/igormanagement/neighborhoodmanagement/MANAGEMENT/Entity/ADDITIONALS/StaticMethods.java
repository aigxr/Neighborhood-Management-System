package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.ADDITIONALS;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.List;

public class StaticMethods {
    public static List<String> checkForErrors(BindingResult result) {
        return result.getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
    }
}
