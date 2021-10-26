package eu.builderscoffee.api.common.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Configuration {

    /**
     * @return Le nom du fichier généré, si le String est vide,
     */
    String value() default "";
    Class<? extends Enum<?>>[] models() default {SigleModel.class};

    enum SigleModel{
        DEFAULT
    }
}
