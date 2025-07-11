package org.nutrition.app.util;

import java.util.Date;

public interface Constants {

    String SUCCESS = "Success";
    String FAILURE = "Failure";

    interface ReturnMessages {
        static String notFound(Class<?> clazz, final String propertyName, final Object value) {
            return "Could not find " + clazz.getSimpleName() + " with " + propertyName + ": " + value;
        }

        static String notFound(Class<?> clazz) {
            return "Could not find " + clazz.getSimpleName();
        }

        static String failedToSave(final Class<?> clazz) {
            return "Could not save " + clazz.getSimpleName();
        }
    }

    interface Time {
        int SECOND = 1000;
        int MINUTE = 60 * SECOND;
        int HOUR = 60 * MINUTE;
        int DAY = 24 * HOUR;

        static Date now() {
            return new Date();
        }

        static Date nowWithDelay(final int delay) {
            return new Date(System.currentTimeMillis() + delay);
        }

    }
}
