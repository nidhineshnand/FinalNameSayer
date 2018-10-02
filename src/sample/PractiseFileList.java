package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PractiseFileList extends NameSayerFileList{

    String _pathToWrite;
    ObservableList<PractiseFile> _practiseFileList = FXCollections.observableArrayList();

    public PractiseFileList(String pathToWrite){
        _pathToWrite = pathToWrite;
    }

    /**This method takes a path to text file as an String input and also a DatabaseList object, it outputs an empty arraylist if all
     * the names from that object were found, otherwise it returns a ArrayList of names that were not found in the database
     * Names in the names concatenations that were not found are created into PractiseFile objects with the un-found
     * name removed*/
    public ArrayList<String> loadPractiseFilesFromText(String pathToList,DatabaseFileList databaseFileList){
        ArrayList<String> namesNotFound = new ArrayList<>();

        //Reading file
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(pathToList));
            //Filenames
            String line;
            while ((line = reader.readLine()) != null || !line.isEmpty()) {
                //Arraylist to save file names
                ArrayList<DatabaseFile> associatedDatabaseFiles = new ArrayList<>();

                //Splitting filenames by spaces
                String[] namesHyphened = line.split("\\s+");

                for (String names : namesHyphened){

                    //Splitting filenames by hyphens
                    String[] singleNames = line.split("-");

                    for(String name : singleNames) {

                        //Getting associated database files
                        DatabaseFile associatedFile = databaseFileList.getDatabaseFileWithName(name);

                        //Checking if database file is null
                        if (associatedFile.equals(null)){
                            namesNotFound.add(name);
                        } else {
                            //Adding names that are found to the arraylist
                            associatedDatabaseFiles.add(associatedFile);
                        }

                    }

                }
                //Creating practisefile object and adding it to the list of practisefile objects
                addPractiseFileToList(new PractiseFile(associatedDatabaseFiles, _pathToWrite, line));
            }
            //Handling exceptions
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return namesNotFound;
    }

    /**This method adds a practise file to the list of practise file if no object exist with the same name*/
    public void addPractiseFileToList(PractiseFile practiseFile){
        boolean doesFileAlreadyExist = false;
        //Checking if a practise file with the same already exists
        for (PractiseFile file : _practiseFileList){
            if (file.get_name().equals(practiseFile.get_name())){
                doesFileAlreadyExist = true;
            }

        }
        if(!doesFileAlreadyExist){
            _practiseFileList.add(practiseFile);
        }
    }



}
