package sample;

import javax.xml.crypto.Data;
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
    private void readDatabaseFiles() {

        //Reading file
        File database = new File(_databasePath);
        File[] listOfFiles = database.listFiles();

        //Creating DatabaseFile objects
        for (File file : listOfFiles) {
            DatabaseFile databaseFile = new DatabaseFile(file);
            _databaseFiles.add(databaseFile);

        }
    }

    //This method takes input a database file name as a string and outputs the best file associated with that string name
    //If no file is found with that name then a null value is returned
    public DatabaseFile getDatabaseFileWithName(String fileName){
        DatabaseFile cachedFile = null;

        //Looping through all files
        for(DatabaseFile file : _databaseFiles){

            //Checking if files has the same name as the given
            if (file.get_recordingName().equalsIgnoreCase(fileName)){

                //Caching file so that if there are no files without a bad rating, the last file is given
                cachedFile = file;

                //Checking if file has bad rating
                if(!file.is_badRating()){
                    return file;
                }
            }
        }

        return cachedFile;
    }



}
