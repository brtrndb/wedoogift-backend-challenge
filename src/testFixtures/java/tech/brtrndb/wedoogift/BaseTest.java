package tech.brtrndb.wedoogift;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.ActiveProfiles;

import tech.brtrndb.wedoogift.logging.WithLog;

@Slf4j
@WithLog
@ActiveProfiles({"test"})
public abstract class BaseTest {

    protected static void notImplementedYet() {
        throw new RuntimeException("Not implemented yet");
    }

}
