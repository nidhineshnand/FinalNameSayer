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
    private String _saves = "./resources/saves";
    private String _practiseDirPath = "./resources/saves/practise.txt";
    private String _savedScore = "./resources/saves/points.txt";
    private String _savedCSS = "./resources/saves/CSS.txt";
    private String _ratingsFilePath = "./resources/saves/ratings.txt";
    private String _localUserRecordingDirPath = "./resources/user-recording/";

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
            new File (_localUserRecordingDirPath).mkdir();
            new File(_saves).mkdir();
            new File (_ratingsFilePath).createNewFile();
            new File(_practiseDirPath).createNewFile();
            new File (_savedScore).createNewFile();
            new File (_savedCSS).createNewFile();
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
            //If file exists or is not a file then it is not copied
            if (!new File("./resources/database/" + file.getName()).exists() && file.getName().matches("se206_[\\S]+_[\\S]+_[\\S]+\\.wav")) {
                equalizeBitrate(file.getAbsolutePath(), "./resources/database/" + file.getName());

            }
        }
    }

    //Equalizes bitrate
    private void equalizeBitrate(String input, String output) {
        try {
            ProcessBuilder builder = new ProcessBuilder("bash", "-c", "ffmpeg -i " + input + " -ar 44100 " + output);
            Process process = builder.start();
            process.waitFor();
            //Exception handling for the process builder
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    public String get_localUserRecordingDirPath() {
        return _localUserRecordingDirPath;
    }

    public String get_savedScore() {
        return _savedScore;
    }

    public String get_savedCSS() {
        return _savedCSS;
    }
}
