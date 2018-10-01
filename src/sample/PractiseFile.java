package sample;

import java.io.File;
import java.util.ArrayList;

public class PractiseFile implements NameSayerFile {

    private ArrayList<DatabaseFile> _filesToPlay;

    public PractiseFile(ArrayList<DatabaseFile> filesToPlay){
        _filesToPlay = filesToPlay;
    }


    //Gets the files that will be used to play audio
    @Override
    public ArrayList<File> filesToPlay() {
        ArrayList<File> output = new ArrayList<>();
        //Going through all the database files
        for (DatabaseFile databaseFile : _filesToPlay){
            output.add(databaseFile.filesToPlay().get(0));
        }
        return output;
    }
}
