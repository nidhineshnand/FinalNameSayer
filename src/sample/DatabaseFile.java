package sample;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**This class is responsible for handling database files*/
public class DatabaseFile implements NameSayerFile {


    private File _databaseFile;
    private String _savedName;
    private String _displayName;
    private LocalDateTime _dateAndTime;
    private boolean _badRating;
    private String _ratingFilePath;


    DatabaseFile(File databaseFile, String ratingFilePath) {
        _databaseFile = databaseFile;
        setDateTimeAndName(_databaseFile);
        _ratingFilePath = ratingFilePath;
        getRating();
    }


    //Method that extracts the time value from the file
    private void setDateTimeAndName(File databaseFile) {

        _databaseFile = databaseFile;

        _savedName = _databaseFile.getName();

        //Splitting string
        String[] splitDatabaseName = _savedName.split("_");

        //Getting fields from split name
        _displayName = splitDatabaseName[3].substring(0, splitDatabaseName[3].length() - 4);
        _dateAndTime = getDateAndTime(splitDatabaseName[1], splitDatabaseName[2]);
    }


    //Method creates a date object based on the time the file was created
    private LocalDateTime getDateAndTime(String date, String time) {

        //Using dateTimeformat to set the format of the date
        DateTimeFormatter dateAndTimeFormatter = DateTimeFormatter.ofPattern("d-M-yyyy/H-m-s");


        return LocalDateTime.parse(date + "/" + time, dateAndTimeFormatter);
    }


    //Checks if the file has been given a bad recording
    private void getRating() {

        //Loading file
        File ratingFile = new File(_ratingFilePath);
        boolean doesFileHaveRating = false;
        boolean firstLine = true;

        //Reading file
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(ratingFile));
            //Filename and rating
            String line;
            while ((line = reader.readLine()) != null) {
                //Splitting filename and rating
                String[] filenameAndRating = line.split(",");

                //Checking if line refers to this database file
                if (_savedName.equals(filenameAndRating[0])) {
                    _badRating = true;
                }
            }
            //Handling exceptions
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    //This method sets the rating for this file as good as a general rating
    public void setRatingBad(){

        try {
            Files.write(Paths.get(_ratingFilePath), (_savedName + "\n").getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**This method checks if the given database file has a bad rating. It takes in the rating file location as the input.
     * it outputs a boolean which says if recording is bad
     */
    public boolean isRecordingBad(){
        return _badRating;
    }

    @Override
    //Method returns the database file that will be used for playing
    public ArrayList<File> filesToPlay() {
        ArrayList<File> databaseFile = new ArrayList<>();
        databaseFile.add(_databaseFile);
        return databaseFile;
    }

    @Override
    public String get_displayName(){
        return _displayName;
    }

    public boolean is_badRating() {
        return _badRating;
    }
}
