package csnojam.app.user.enums.converter;

import csnojam.app.user.enums.UniqueFields;
import org.springframework.core.convert.converter.Converter;

public class UniqueFieldConverter implements Converter<String, UniqueFields> {

    @Override
    public UniqueFields convert(String source) {
        return UniqueFields.valueOf(source.toUpperCase());
    }
}
