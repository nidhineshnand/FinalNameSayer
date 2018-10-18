package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static java.util.Arrays.asList;

public class UserRecordingFileList extends NameSayerFileList {

    ObservableList<UserRecordingFile> _userRecordingFilesList = FXCollections.observableArrayList();
    ObservableList<UserRecordingFile> _associatedUserRecordingFiles = FXCollections.observableArrayList();
    String _pathToFiles;
    VBox _userRecordingFileListVBox = new VBox();
    VBox _associatedUserRecordingFileListVbox = new VBox();

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
    private void getFilesForMainScene(){
        FXCollections.reverse(_userRecordingFilesList);
        _userRecordingFileListVBox = getFilesForScene(_userRecordingFilesList);
    }

    //This method get files to paint for the practise scene
    public void getFilesForPractiseScene(PractiseFile file){
        setUserRecordingsAssociatedWith(file);
        FXCollections.reverse(_associatedUserRecordingFiles);
        _associatedUserRecordingFileListVbox = getFilesForScene(_associatedUserRecordingFiles);
    }

    //This method deletes the selected recordings on the main page
    public void deleteSelectedRecordings(){
        //Deleting files from system
        for(UserRecordingFile recordingFile : getSelectedUserRecording()){
            recordingFile.deleteFile();
        }
        _userRecordingFilesList.removeAll(getSelectedUserRecording());
    }

    //This method creates a UserRecording file and passes it to the front end for recording
    public UserRecordingFile createUserRecordingFile(PractiseFile practiseFile){
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList(practiseFile.get_displayName().split(" ")));
        return new UserRecordingFile(_pathToFiles, nameList);
    }

    //Adds a user recording object to the list if a recording is created
    public void addUserRecordingToList(UserRecordingFile file){
        _userRecordingFilesList.add(file);
    }

    //Method finds all the user recording files that are associated with a particular practise file
    public void setUserRecordingsAssociatedWith(PractiseFile file){
        _associatedUserRecordingFiles.clear();
        //Looping through all local files to find file that is associated with this practise file
        for(UserRecordingFile userFile : _userRecordingFilesList){
           if (file.get_displayName().equalsIgnoreCase(userFile.get_identity())){
               _associatedUserRecordingFiles.add(userFile);
           }
        }
    }

    //Method sets all the checkboxes to wither ticked or not
    public void setCheckBoxesTo(boolean set){

        //Loops through all user recordings to set the checkbox either true or false
        for(UserRecordingFile file : _userRecordingFilesList){
            file.get_controller().setCheckBox(set);
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



    /**This method returns the list of userRecordingFiles that are currently selected in the practise scene. If no recordings
     * are selected then an empty list is returned*/
    public ArrayList<UserRecordingFile> getSelectedUserRecordingInPractiseScene(){
        ArrayList<UserRecordingFile> list = new ArrayList<>();

        //Loops through all user recordings to find the ones that have been selected
        for(UserRecordingFile file : _associatedUserRecordingFiles){
            if (file.get_controller().get_isSelected()){
                list.add(file);
            }
        }
        return list;
    }

    public VBox get_userRecordingFileListVBox() {
        getFilesForMainScene();
        //_userRecordingFileListVBox.setPrefHeight(500);
        ScrollPane scrollPane = new ScrollPane(_userRecordingFileListVBox);
        scrollPane.setFitToWidth(true);
        return _userRecordingFileListVBox;
    }

    public VBox get_associatedUserRecordingFileListVbox(PractiseFile file) {
        getFilesForPractiseScene(file);
        //_associatedUserRecordingFileListVbox.setPrefHeight(500);
        ScrollPane scrollPane = new ScrollPane(_associatedUserRecordingFileListVbox);
        scrollPane.setFitToWidth(true);
        return _associatedUserRecordingFileListVbox;
    }

    //Returns that user recordings that are associated with the current practise file
    public ObservableList<UserRecordingFile> get_associatedUserRecordingFiles() {
        return _associatedUserRecordingFiles;
    }
}
