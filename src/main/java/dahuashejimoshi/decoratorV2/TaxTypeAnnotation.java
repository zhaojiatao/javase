package dahuashejimoshi.decoratorV2;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TaxTypeAnnotation {
    TaxType taxType();
}
