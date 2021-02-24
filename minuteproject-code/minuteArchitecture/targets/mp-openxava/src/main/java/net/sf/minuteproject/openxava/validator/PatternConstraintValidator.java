package net.sf.minuteproject.openxava.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.regex.Pattern;

public interface PatternConstraintValidator<A extends Annotation, T> extends ConstraintValidator<A, T> {

    String getRegex ();

    @Override
    default void initialize(A a) {

    }

    @Override
    default boolean isValid(T t, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.isNull(t) || (Objects.nonNull(t) && Pattern.matches(getRegex(), t.toString()));
    }

}
