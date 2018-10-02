package sample;

import java.util.ArrayList;

public class NameSayerFileElementController {

    NameSayerFile _fileToDisplay;
    ArrayList<DatabaseFile> _databaseFiles = new ArrayList<>();

    //This method takes objects from the parent class and sets it in the controller as fields
    public void setFields(NameSayerFile fileToDisplay ,ArrayList<DatabaseFile> databaseFiles){
        _fileToDisplay = fileToDisplay;
        _databaseFiles = databaseFiles;
    }












}
