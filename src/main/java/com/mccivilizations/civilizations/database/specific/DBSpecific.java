package com.mccivilizations.civilizations.database.specific;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DBSpecific {
    String value();
}
