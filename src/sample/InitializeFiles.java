package sample;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**This class initializes the files that will be used by this program in the future*/
public class InitializeFiles {

    private String _databaseFilePath;
    private String _localResourcesPath = "./resources";
    private String _localDatabasePath = "./resources/database";
    private String _concatenationsPath = "./resources/concatenations";
    private String _practiseDirPath = "./resources/practise.txt";
    private String _ratingsFilePath = "./resources/ratings.txt";

    public InitializeFiles(String databaseFilePath){
        _databaseFilePath = databaseFilePath;
        initialize();
    }


    //This method is run at the beginning of the program to create files that will be used in the future
    public void initialize(){
        //Creating directory if one does not already exist
        try {
            new File(_localResourcesPath).mkdir();
            new File(_localDatabasePath).mkdir();
            new File (_concatenationsPath).mkdir();
            new File(_practiseDirPath).createNewFile();
            new File (_ratingsFilePath).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        copyDatabaseFiles();
    }

    //This method copies the files from where the user specifies to our internal folder
    private void copyDatabaseFiles() {
        //Reading database files
        File databaseFolder = new File(_databaseFilePath);
        File[] listOfFiles = databaseFolder.listFiles();

        for (File file : listOfFiles) {
            //If file exists then it is not copied
            if (!new File("./resources/database/" + file.getName()).exists()) {

                //Copying database recording file
                try {
                    Files.copy(file.toPath(), (new File("./resources/database/" + file.getName()).toPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String get_localResourcesPath() {
        return _localResourcesPath;
    }

    public String get_localDatabasePath() {
        return _localDatabasePath;
    }

    public String get_concatenationsPath() {
        return _concatenationsPath;
    }

    public String get_practiseDirPath() {
        return _practiseDirPath;
    }

    public String get_ratingsFilePath() {
        return _ratingsFilePath;
    }
}