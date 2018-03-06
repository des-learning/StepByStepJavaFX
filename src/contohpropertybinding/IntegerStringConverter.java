package contohpropertybinding;

import javafx.util.StringConverter;

// Convert Integer menjadi String
public class IntegerStringConverter extends StringConverter<Number> {
    @Override
    public String toString(Number integer) {
        return integer.toString();
    }

    @Override
    public Number fromString(String s) {
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
