package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;

public class UserRecordingFileList extends NameSayerFileList {

    ObservableList<UserRecordingFile> _userRecordingFilesList = FXCollections.observableArrayList();
    ObservableList<UserRecordingFile> _associatedUserRecordingFiles = FXCollections.observableArrayList();
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


    //This method get files to paint for the scene
    public void getFilesForMainScene(){
        getFilesForScene(_userRecordingFilesList);
    }

    //This method get files to paint for the practise scene
    public void getFilesForPractiseScene(PractiseFile file){
        setUserRecordingsAssociatedWith(file);
        getFilesForScene(_associatedUserRecordingFiles);
    }


    //Method finds all the user recording files that are associated with a particular practise file
    public void setUserRecordingsAssociatedWith(PractiseFile file){
        //Looping through all local files to find file that is associated with this practise file
        for(UserRecordingFile userFile : _userRecordingFilesList){
           if (file.get_displayName().equalsIgnoreCase(userFile.get_identity())){
               _associatedUserRecordingFiles.add(userFile);
           }
        }
    }


    /**This method returns the list of userRecordingFiles that are currently selected in the main scene. If no recordings
     * are selected then an empty list is returned*/
    public ArrayList<UserRecordingFile> getSelectedUserRecording(){
        ArrayList<UserRecordingFile> list = new ArrayList<>();

        //Loops through all user recordings to find the ones that have been selected
        for(UserRecordingFile file : _userRecordingFilesList){
            if (file.get_controller().get_isSelected()){
                list.add(file);
            }
        }
        return list;
    }




}
