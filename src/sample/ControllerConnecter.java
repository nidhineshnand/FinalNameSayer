package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

public class ControllerConnecter {

    private InitializeFiles initializeFiles = new InitializeFiles("./names");
    private DatabaseFileList databaseFileList = new DatabaseFileList(initializeFiles.get_localDatabasePath(), initializeFiles.get_ratingsFilePath());
    private PractiseFileList practiseFileList = new PractiseFileList(initializeFiles.get_practiseDirPath());
    private UserRecordingFileList userRecordingFileList = new UserRecordingFileList(initializeFiles.get_localUserRecordingDirPath());
    private PointsSystem pointsSystem = new PointsSystem(initializeFiles.get_savedScore());

    public ControllerConnecter(){
        loadPreviousState();
    }

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
        return userRecordingFileList.get_associatedUserRecordingFileListVbox(practiseFile);
    }


    /**This method takes a path to text file as an String input and also a DatabaseList object, it outputs an empty Arraylist if all
     * the names from that object were found, otherwise it returns a ArrayList of names that were not found in the database
     * Names in the names concatenations that were not found are created into PractiseFile objects with the un-found
     * name removed
     */
    public ArrayList<String> loadPractiseFilesFromTextFile(String path){
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
    public ArrayList<UserRecordingFile> getSelectedLocalRecordingFilesFromPractiseScene(){
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

    /**This method deletes the selected UserRecordingFile recordings from the user recordings list displayed in the main
     * scene
     */
    public void deleteSelectedUserRecordingFiles(){
        userRecordingFileList.deleteSelectedRecordings();
    }

    /**This method is called when the user presses the recording button. It takes the practise file associated with
     * the recording as an input and a new UserRecordingFile object is outputted by this method.
     * The method startRecording() should be invoked on the UserRecordingFile object to start recording. The method stopRecording()
     * should be invoked on the object if the user wants to stop recording. If the user saves the file then the method
     * saveUserRecordingFile(UserRecordingFile file) should be invoked from ControllerConnector. If the user chooses to delete the recording then
     * the method deleteFile() should be invoked on the on the UserRecordingFile object.
     */
    public UserRecordingFile createNewUserRecordingFile(PractiseFile file){
        return userRecordingFileList.createUserRecordingFile(file);
    }

    /**This method should be invoked when the user wants to save that particular recording. This method takes in a
     * UserRecordingFile object as an input
     */
    public void addUserRecording(UserRecordingFile file){
        userRecordingFileList.addUserRecordingToList(file);
    }

    /**This method sets all the practise file recording as either selected or not selected. It takes a boolean input.
     * True is select all whilst false is deselect all.
     */
    public void setPractiseFileListCheckBox(Boolean set){
        practiseFileList.setCheckBoxesTo(set);
    }

    /**This method sets all the user recording files recording as either selected or not selected. It takes a boolean input.
     * True is select all whilst false is deselect all.
     */
    public void setUserRecordingFileListCheckBox(Boolean set){
        userRecordingFileList.setCheckBoxesTo(set);
    }

    /**This method changes the points associated with the user*/
    public void changePoints(int val){
        pointsSystem.changePoints(val);
    }

    /**This method gets the points if there needs to be any calculations done on the points
     */
    public int getPoints(){
        return pointsSystem.get_points();
    }

    /**This method returns an observable list of user recording files that will be used in the loop names section. The
     * user recording in the list will be the most recent one
      */
    public ObservableList<UserRecordingFile> getAssociatedUserRecordingFilesForLoop(PractiseFile file){
        return userRecordingFileList.get_associatedUserRecordingFiles(file);
    }

    /**Load previously saved state*/
    private void loadPreviousState(){
        practiseFileList.loadPractiseFilesFromText(initializeFiles.get_practiseDirPath(), databaseFileList);
        pointsSystem.loadPoints();
    }

    /**This method saves the practise file list, points and theme of the program so that the user can resume whenever they want to*/
    public void saveProgramState(String css){
        practiseFileList.savePractiseFileList();
        pointsSystem.savePoints();
    }

    /**This method adds the .wav files that are present in the path to the folder provided that meet the specific pattern
     * of a database file
     */
    public void addFilesToDatabase(String path){
        initializeFiles.copyDatabaseFiles(path);
    }
}
