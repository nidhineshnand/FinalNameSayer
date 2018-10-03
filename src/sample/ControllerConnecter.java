package sample;

import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

public class ControllerConnecter {

    InitializeFiles initializeFiles = new InitializeFiles("./resources/database");
    DatabaseFileList databaseFileList = new DatabaseFileList(initializeFiles.get_localDatabasePath(), initializeFiles.get_ratingsFilePath());
    PractiseFileList practiseFileList = new PractiseFileList(initializeFiles.get_practiseDirPath());
    UserRecordingFileList userRecordingFileList = new UserRecordingFileList(initializeFiles.get_localUserRecordingDirPath());


    /**This method outputs a VBox that contains the PractiseFile objects that are loaded by the user*/
    public VBox populatePractiseFileForMainScene(){
        return practiseFileList.get_practiseFileListVbox();
    }

    /**This method outputs a VBox that contains the LocalFile objects that are previously recorded by the user. This is
     * for the main screen*/
    public VBox populateUserRecordingFilesForMainScene(){
        return userRecordingFileList.get_userRecordingFileListVBox();
    }

    /**This method takes as input a PractiseFile that the user is currently practising and outputs a VBox that contains
     *  the LocalFile objects that are previously recorded by the user that are associated with the given practise file.
     *  This is for the practise screen*/
    public VBox populateUserRecordingsForPractiseScene(PractiseFile practiseFile){
        return userRecordingFileList.getFilesForPractiseScene(practiseFile);
    }


    /**This method takes a path to text file as an String input and also a DatabaseList object, it outputs an empty Arraylist if all
     * the names from that object were found, otherwise it returns a ArrayList of names that were not found in the database
     * Names in the names concatenations that were not found are created into PractiseFile objects with the un-found
     * name removed
     */
    public ArrayList<String> loadPractiseFilesFromList(String path){
        return practiseFileList.loadPractiseFilesFromText(path, databaseFileList);
    }

    /**Method takes as input a name as a string and String Arraylist and returns PractiseFile object if it can associate it to one.
     * Otherwise a null is returned. The arraylist will be populated with the list of names not found. This method should
     * be called when the user presses the search button.
     */
    public PractiseFile searchButtonPressed(String string, ArrayList<String> namesNotFound){
        return practiseFileList.searchGivenName(string, databaseFileList, namesNotFound);
    }

    /**This method searches takes an input a string of names and outputs an Arraylist of strings that correspond to string input.
     This method should be called when the user is typing so that they can see teh available list of names*/
    public ArrayList<String> continousSearchResults(String stringName){
        return practiseFileList.search(stringName, databaseFileList);
    }

    /**This method gets a list of practise names that are selected in the form of an ArrayList*/
    public ArrayList<PractiseFile> getSelectedPractiseFiles(){
        return practiseFileList.getSelectedPractiseFiles();
    }

    /**This method gets a list of local recording names that are selected (in the main scene) in the form of an ArrayList*/
    public ArrayList<UserRecordingFile> getSelectedLocalRecordingFiles(){
        return userRecordingFileList.getSelectedUserRecording();
    }


    /**This method gets a list of local recording names that are selected (in the practise scene) in the form of an ArrayList*/
    public ArrayList<UserRecordingFile> getSelectedLocalRecordignFilesFromPractiseScene(){
        return userRecordingFileList.getSelectedUserRecordingInPractiseScene();
    }

    /**This method takes in a NameSayerFile object and it outputs a file that can be used to play*/
    public File getPlayableFileFor(NameSayerFile file){
        return databaseFileList.getFileToPlay(file);
    }

    /**This method deletes the selected PractiseFiles from the list*/
    public void deleteSelectedPractiseFiles(){
        practiseFileList.deletePractiseFile(practiseFileList.getSelectedPractiseFiles());
    }

    /**This method deletes the selected UserRecordingFile recordings from the list
}
