package sample;

import java.io.File;
import java.util.ArrayList;

/**This class manages the database files and all its abstractions*/
public class DatabaseFileList extends NameSayerFileList{

    private final String _databasePath;
    private final String _ratingFilePath;
    private ArrayList<DatabaseFile> _databaseFiles = new ArrayList<>();

    public DatabaseFileList(String databasePath, String ratingFilePath){
        _databasePath = databasePath;
        _ratingFilePath = ratingFilePath;
        readDatabaseFiles();
    }



    /*This method reads the files from the database and stores it in the  Arraylist databaseFiles. This method is run at
    at initialization of the DatabaseFileList object;
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
