package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PractiseFileList extends NameSayerFileList{

    String _pathToWrite;
    ObservableList<PractiseFile> _practiseFileList = FXCollections.observableArrayList();
    VBox _practiseFileListVbox = new VBox(0);

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
            while ((line = reader.readLine()) != null) {
                PractiseFile file = searchGivenName(line, databaseFileList, namesNotFound);
                if(file!=null){
                    addPractiseFileToList(file);
                }
            }
                //Handling exceptions
            } catch(FileNotFoundException e){
                e.printStackTrace();
            } catch(IOException ex){
                ex.printStackTrace();
            }

        return namesNotFound;
    }


    /**Method takes input a string and DatabaseFileList and returns PractiseFile object if it can associate it to one.
     * otherwise a null is returned
     */
    public PractiseFile searchGivenName(String line, DatabaseFileList databaseFileList, ArrayList<String> namesNotFound) {

        //Arraylist to save file names
        ArrayList<DatabaseFile> associatedDatabaseFiles = new ArrayList<>();
        ArrayList<String> fileNames = new ArrayList<>();
        //Splitting filenames by spaces
        String[] namesHyphened = line.split("\\s+");

        for (String names : namesHyphened) {

            //Splitting filenames by hyphens
            String[] singleNames = names.split("-");

            for (String name : singleNames) {

                //Getting associated database files
                DatabaseFile associatedFile = databaseFileList.getDatabaseFileWithName(name);

                //Checking if database file is null
                if (associatedFile == null) {
                    namesNotFound.add(name);
                } else {
                    //Adding names that are found to the arraylist
                    associatedDatabaseFiles.add(associatedFile);
                    fileNames.add(name);
                }
            }
        }
        if (!associatedDatabaseFiles.isEmpty()) {
            //Creating practisefile object and adding it to the list of practisefile objects
            PractiseFile practiseFile = new PractiseFile(associatedDatabaseFiles, _pathToWrite, fileNames);
            addPractiseFileToList(practiseFile);
            return practiseFile;
        }

        return null;
    }


    /**This method searches takes an input a string and outputs an arraylist of strings that correspond to that string
     * relating to a concatenation
     */
    public ArrayList<String> search(String spaceSeperatedName, DatabaseFileList databaseFileList){
        ArrayList<String> outputStrings = new ArrayList<>();

        //Separating names by spaces
        String[] hyphenSeperatedName = spaceSeperatedName.split("\\s+");

        StringBuilder outputString = new StringBuilder();
        //Iterating through the names in the string
        for (String string : hyphenSeperatedName){

            boolean addHyphen = false;

            String[] seperatedNames = string.split("-");

            //Making add hyphen true if the string was actually separated
            if(seperatedNames.length > 1){
                addHyphen = true;
            }

            for (String name : seperatedNames){

                DatabaseFile databaseFile = databaseFileList.getDatabaseFileWithName(name);

                // Getting files that are associated with the name typed in
                if(databaseFile != null){
                    outputString.append(databaseFile.get_displayName());
                    //If full name is not typed than part name is searched
                } else {
                    //Getting a list of files that match a particular string
                    ArrayList<DatabaseFile> databaseFilePartNames = databaseFileList.getDatabaseFileWithPartName(name);

                    if(databaseFilePartNames != null){
                        for (DatabaseFile currentFile : databaseFilePartNames) {
                            //Returning names with the last name added
                            outputStrings.add(outputString + currentFile.get_displayName());
                        }
                    }
                }
                outputStrings.add(outputString.toString());
                if(addHyphen){
                    outputString.append("-");
                } else {
                    outputString.append(" ");
                }
            }
        }
        return outputStrings;
    }

    /**This method adds a practise file to the list of practise file if no object exist with the same name*/
    public void addPractiseFileToList(PractiseFile practiseFile){
        boolean doesFileAlreadyExist = false;
        //Checking if a practise file with the same already exists
        for (PractiseFile file : _practiseFileList){
            if (file.get_displayName().equals(practiseFile.get_displayName())){
                doesFileAlreadyExist = true;
            }

        }
        if(!doesFileAlreadyExist){
            _practiseFileList.add(practiseFile);
        }
        getFilesForMainScene();
    }

    /**This method deletes a given practise file from the list*/
    public void deletePractiseFile(ArrayList<PractiseFile> files){
        _practiseFileList.removeAll(files);
        getFilesForMainScene();
    }

    //This method get files to paint for the scene
    private void getFilesForMainScene(){
        _practiseFileListVbox = getFilesForScene(_practiseFileList);
    }

    /**This method returns the list of practiseFiles that are currently selected in the main scene. If no recordings
     * are selected then an empty list is returned*/
    public ArrayList<PractiseFile> getSelectedPractiseFiles(){
        ArrayList<PractiseFile> list = new ArrayList<>();

        //Loops through all user recordings to find the ones that have been selected
        for(PractiseFile file : _practiseFileList){
            if (file.get_controller().get_isSelected()){
                list.add(file);
            }
        }
            return list;
    }

    //This method gets all the checks all the files in the view
    /**This method returns the list of practiseFiles that are currently selected in the main scene. If no recordings
     * are selected then an empty list is returned*/
    public void setCheckBoxesTo(boolean set){

        //Loops through all user recordings to set teh checkbox either true or false
        for(PractiseFile file : _practiseFileList){
            file.get_controller().setCheckBox(set);
        }
    }


    public ScrollPane get_practiseFileListVbox() {
        getFilesForMainScene();
        _practiseFileListVbox.setPrefHeight(500);
        ScrollPane scrollPane = new ScrollPane(_practiseFileListVbox);
        scrollPane.setFitToWidth(true);
        //scrollPane.setFitToWidth(true);
        return scrollPane;
    }
}
