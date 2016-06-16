package main.inputservice;

import main.facade.ApplicationError;
import main.facade.OperatorFacade;
import main.logic.Rate;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by oglandx on 6/16/16.
 */
public class FileService {
    public static void load(String filename) {
        String content = null;
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filename));
            content = new String(encoded);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (content != null) {
            JSONObject json;
            try {
                json = new JSONObject(content);
                Rate rate = new Rate(
                        BigDecimal.valueOf(json.getDouble("costPerKm")),
                        BigDecimal.valueOf(json.getDouble("costPerMin")),
                        json.getInt("freeMinutes")
                );
                OperatorFacade facade = OperatorFacade.getInstance();
                facade.addRate(rate);
            }
            catch (JSONException | ApplicationError e){
                e.printStackTrace();
            }
        }
    }
}