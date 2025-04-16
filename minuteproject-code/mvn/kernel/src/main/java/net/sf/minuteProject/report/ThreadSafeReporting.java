package net.sf.minuteProject.report;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadSafeReporting {

    private static final ThreadLocal<Reporting> REPORTING_THREAD_LOCAL =
           new ThreadLocal<Reporting>(){
            protected Reporting initialValue() {
                return new GenerationReport();
            }
    };

    public static final Reporting report() {
        return REPORTING_THREAD_LOCAL.get();
    }

}
