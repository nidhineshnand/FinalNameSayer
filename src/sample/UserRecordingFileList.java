package sample;

import java.io.File;
import java.util.ArrayList;

public class UserRecordingFileList extends NameSayerFileList {

    ArrayList<UserRecordingFile> _userRecordingFilesList = new ArrayList<>();
    String _pathToFiles;

    public UserRecordingFileList(String pathToFiles){
        _pathToFiles = pathToFiles;
        reloadOldRecordings();
    }


    //This method reloads files that has been saved by the user prior to this practise session.
    private void reloadOldRecordings(){

        //Reading past recordings
        File practiseFiles = new File(_pathToFiles);
        File[] listOfFiles = practiseFiles.listFiles();

        //Checking if files are not null
        if (listOfFiles != null) {
            //Creating PractiseFile objects that will contain the recording of names that have been previously recorded
            for (File file : listOfFiles) {
                //Creating a practise file object associated with previously saved practise file and adding it to
                UserRecordingFile userRecordingFile = new UserRecordingFile(file);
                _userRecordingFilesList.add(userRecordingFile);
            }
        }
    }









}
