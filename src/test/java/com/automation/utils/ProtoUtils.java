package com.automation.utils;

import com.google.protobuf.util.JsonFormat;
import com.tp.greeting.Greeeting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProtoUtils {
    public static Greeeting.ClientInput readNameRequestFromJsonFile(String filename) {
        try {
            String json = Files.readString(Paths.get("src/test/resources/data/" + filename));
            Greeeting.ClientInput.Builder builder = Greeeting.ClientInput.newBuilder();
            JsonFormat.parser().merge(json, builder);
            return builder.build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read or parse JSON file: " + filename, e);
        }
    }
}
