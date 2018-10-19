package sample;

import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class CollectionsFile implements NameSayerFile {

    ArrayList<PractiseFile> _practiseFiles = new ArrayList<>();
    ArrayList<UserRecordingFile> _userRecordingFiles = new ArrayList<>();
    ArrayList<File> _fileToPlay = new ArrayList<>();

    public CollectionsFile(ArrayList<PractiseFile> practiseFiles, ArrayList<UserRecordingFile> userRecordingFiles){
        _practiseFiles = practiseFiles;
        _userRecordingFiles = userRecordingFiles;
        mergeFiles();
    }

    public CollectionsFile(PractiseFile practiseFile, UserRecordingFile userRecordingFile){
        _fileToPlay.addAll(practiseFile.filesToPlay());
        _fileToPlay.addAll(userRecordingFile.filesToPlay());
    }


    //This methods gets all the file objects received and puts them together in one variable so that it can be concatenated together
    public void mergeFiles(){
        //Checking that file is not null
        if(_practiseFiles != null) {
            //Getting files associated with the practise files
            for (PractiseFile practiseFile : _practiseFiles) {
                filesToPlay().addAll(practiseFile.filesToPlay());
            }
        }

        if (_userRecordingFiles != null) {
            //Getting files associated with a recording file
            for (UserRecordingFile userRecordingFile : _userRecordingFiles) {
                filesToPlay().addAll(userRecordingFile.filesToPlay());
            }
        }
    }



    @Override
    public ArrayList<File> filesToPlay() {
        return _fileToPlay;
    }

    //Does not need this implementation as this will not be displayed
    @Override
    public String get_displayName() {
        return null;
    }

    //Does not need this implementation as this will not be displayed
    @Override
    public Pane get_fileView() {
        return null;
    }
}
