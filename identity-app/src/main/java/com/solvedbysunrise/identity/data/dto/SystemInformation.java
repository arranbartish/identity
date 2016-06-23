package com.solvedbysunrise.identity.data.dto;

public class SystemInformation {

    private final String applicationVersion;
    private final String applicationHost;
    private final String applicationRevision;
    private final String applicationName;

    public SystemInformation(final String applicationVersion,
                             final String applicationHost,
                             final String applicationRevision,
                             final String applicationName) {
        this.applicationVersion = applicationVersion;
        this.applicationHost = applicationHost;
        this.applicationRevision = applicationRevision;
        this.applicationName = applicationName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getApplicationRevision() {
        return applicationRevision;
    }

    public String getApplicationHost() {
        return applicationHost;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }
}
