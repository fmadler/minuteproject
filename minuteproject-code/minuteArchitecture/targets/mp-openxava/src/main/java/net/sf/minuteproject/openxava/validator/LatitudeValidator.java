package net.sf.minuteproject.openxava.validator;

import net.sf.minuteproject.openxava.annotation.Latitude;

public class LatitudeValidator implements PatternConstraintValidator<Latitude, Number> {

    final String regexp="^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$";

    @Override
    public String getRegex() {
        return regexp;
    }
}
