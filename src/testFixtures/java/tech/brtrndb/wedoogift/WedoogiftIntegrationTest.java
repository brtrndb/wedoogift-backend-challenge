package tech.brtrndb.wedoogift;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.context.SpringBootTest;

import tech.brtrndb.wedoogift.persistence.WithDatabase;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = {WedoogiftDepositApplication.class})
@WithDatabase
public @interface WedoogiftIntegrationTest {
}
