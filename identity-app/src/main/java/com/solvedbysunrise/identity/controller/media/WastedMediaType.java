package com.solvedbysunrise.identity.controller.media;

import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.Map;

public class WastedMediaType extends MediaType {


    public final static MediaType APPLICATION_CSV;

    public final static String APPLICATION_CSV_VALUE = "application/csv";

    static {
        APPLICATION_CSV = valueOf(APPLICATION_CSV_VALUE);
    }

    public WastedMediaType(String type) {
        super(type);
    }

    public WastedMediaType(String type, String subtype) {
        super(type, subtype);
    }

    public WastedMediaType(String type, String subtype, Charset charset) {
        super(type, subtype, charset);
    }

    public WastedMediaType(String type, String subtype, double qualityValue) {
        super(type, subtype, qualityValue);
    }

    public WastedMediaType(MediaType other, Map<String, String> parameters) {
        super(other, parameters);
    }

    public WastedMediaType(String type, String subtype, Map<String, String> parameters) {
        super(type, subtype, parameters);
    }
}
