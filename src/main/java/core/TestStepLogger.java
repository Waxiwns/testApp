// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package core;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

import static lombok.AccessLevel.PRIVATE;

@Log4j
@NoArgsConstructor(access = PRIVATE)
public class TestStepLogger {
    public static void logStep(int stepNumber, String message) {
        log.info("STEP " + stepNumber + ": " + message);
    }

    public static void logPreconditionStep(int stepNumber, String message) {
        log.info("Precondition STEP " + stepNumber + ": " + message);
    }

    public static void logPostconditionStep(int stepNumber, String message) {
        log.info("Postcondition STEP " + stepNumber + ": " + message);
    }

    public static void log(String message) {
        log.info("Additional test information: " + message);
    }
}