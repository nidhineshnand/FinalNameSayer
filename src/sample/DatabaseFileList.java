package sample;

import java.io.File;
import java.util.ArrayList;

/**This class manages the database files and all its abstractions*/
public class DatabaseFileList extends NameSayerFileList{

    private final String _databasePath;
    private ArrayList<DatabaseFile> _databaseFiles = new ArrayList<>();

    DatabaseFileList(String databasePath){
        _databasePath = databasePath;
    }



    /*This method reads the files from the database and stores it in the  Arraylist databaseFiles. This method needs
    to be run at the start of the program.
     */
    public void readDatabaseFiles() {

        //Reading file
        File database = new File(_databasePath);
        File[] listOfFiles = database.listFiles();

        //Creating DatabaseFile objects
        for (File file : listOfFiles) {
            DatabaseFile databaseFile = new DatabaseFile(file);
            _databaseFiles.add(databaseFile);

        }

    }

}
