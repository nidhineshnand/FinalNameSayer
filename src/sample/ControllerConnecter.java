package sample;

import javafx.scene.layout.VBox;

public class ControllerConnecter {

    InitializeFiles initializeFiles = new InitializeFiles("./resources/database");
    DatabaseFileList databaseFileList = new DatabaseFileList(initializeFiles.get_localDatabasePath(), initializeFiles.get_ratingsFilePath());
    PractiseFileList practiseFileList = new PractiseFileList(initializeFiles.get_practiseDirPath());
    UserRecordingFileList userRecordingFileList = new UserRecordingFileList(initializeFiles.get_localUserRecordingDirPath());


    public VBox populatePractiseFile(){return null;}







}
