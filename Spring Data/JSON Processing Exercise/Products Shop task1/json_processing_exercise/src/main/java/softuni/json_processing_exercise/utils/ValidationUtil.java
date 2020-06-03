package softuni.json_processing_exercise.utils;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidationUtil {

   <E> boolean isValid(E entity);

   <E> Set<ConstraintViolation<E>> violations(E entity);
}
