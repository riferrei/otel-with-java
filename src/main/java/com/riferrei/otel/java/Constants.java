package com.riferrei.otel.java;

public interface Constants {

    public static final String METRIC_PREFIX = "custom.metric.";

    public static final String NUMBER_OF_EXEC_NAME = METRIC_PREFIX + "number.of.exec";
    public static final String NUMBER_OF_EXEC_DESCRIPTION = "Count the number of executions.";

    public static final String JVM_MEMORY_NAME = METRIC_PREFIX + "jvm.memory";
    public static final String JVM_MEMORY_DESCRIPTION = "Reports JVM memory utilization.";
    
}
