package sample;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class PractiseFile implements NameSayerFile {

    private ArrayList<DatabaseFile> _filesToPlay;
    private String _pathToWrite;
    private String _displayName;

    public PractiseFile(ArrayList<DatabaseFile> filesToPlay, String pathToWrite, ArrayList<String> fileNames){
        _filesToPlay = filesToPlay;
        _pathToWrite = pathToWrite;
        _displayName = String.join(" ", fileNames);
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

    //Writes to file so that it can be accessed when the app is closed
    private void writeToFile(){
        ArrayList<String> fileNames = new ArrayList<>();
        //Getting the string to add to file
        for(DatabaseFile file: _filesToPlay){
            fileNames.add(file.get_recordingName());
        }

        //Saving to file
        try {
            Files.write(Paths.get(_pathToWrite), (String.join(" ",fileNames) + "\n").getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String get_displayName() {
        return _displayName;
    }
}
