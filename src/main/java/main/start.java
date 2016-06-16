package main;

import main.facade.ApplicationError;
import main.gui.GUIMain;
import main.inputservice.FileService;
import main.restapi.Server;

import java.io.IOException;

/**
 * Created by oglandx on 6/5/16.
 */
public class start {
    public static void main(final String[] args){
        try {
            new Server(8000);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        FileService.load("/home/oglandx/newRate.json");
        new GUIMain();
    }
}
