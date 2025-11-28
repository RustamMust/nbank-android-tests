package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для указания регулярного выражения для генерации тестовых данных
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GeneratingRule {
    /**
     * Регулярное выражение для генерации значения поля
     * @return строка с регулярным выражением
     */
    String regex();
}
