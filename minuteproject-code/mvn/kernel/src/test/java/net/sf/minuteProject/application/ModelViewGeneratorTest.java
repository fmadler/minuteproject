package net.sf.minuteProject.application;

import net.sf.minuteProject.exception.MinuteProjectException;
import org.junit.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;

public class ModelViewGeneratorTest {

    @Test
    public void testEnvironmentModelOnMysql() throws MinuteProjectException {
/*
        replace in fonctional testing suite
        ModelViewGenerator mvg = new ModelViewGenerator("environment.xml");
        mvg.generate();*/
    }

    @Test
    public void testEnvironmentWinyOnMysql() throws Exception {
/*

        withEnvironmentVariable("jdbcUrl", "jdbc:mysql://localhost:3306/xxx")
                .and("username", "xxx")
                .and("password", "xxx")
                .and("version", "xxx")
                .execute(() -> {
                            try {
                                ModelViewGenerator mvg = new ModelViewGenerator("sample.xml");
                                mvg.generate();
                            } catch (Exception e) {
                            }
                        }
                );
*/

    }
}
