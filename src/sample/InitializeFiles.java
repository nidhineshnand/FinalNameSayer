package sample;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**This class initializes the files that will be used by this program in the future*/
public class InitializeFiles {

    String _databaseFilePath;

    public InitializeFiles(String databaseFilePath){
        _databaseFilePath = databaseFilePath;
        initialize();
    }


    //This method is run at the beginning of the program to create files that will be used in the future
    public void initialize(){
        //Creating directory if one does not already exist
        try {
            new File("./resources").mkdir();
            new File("./resources/database").mkdir();
            new File ("./resources/concatenations").mkdir();
            new File("./resources/practise.txt").createNewFile();
            new File ("./resources/ratings.txt").createNewFile();
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

}
