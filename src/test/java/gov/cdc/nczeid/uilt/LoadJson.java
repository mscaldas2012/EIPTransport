package gov.cdc.nczeid.uilt;

import com.google.gson.Gson;
import gov.cdc.nczeid.eip.transport.model.EIPMessage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class LoadJson {

    public static EIPMessage readJson(String filename) {
        String content = readFile(filename);
        //get JSON:
        Gson gson = new Gson();
        return gson.fromJson(content, EIPMessage.class);
    }

    public static String readFile(String filename) {
        InputStream is = LoadJson.class.getClassLoader().getResourceAsStream(filename);
        return new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining());
    }
}
