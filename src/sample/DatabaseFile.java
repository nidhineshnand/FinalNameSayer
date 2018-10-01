package sample;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**This class is responsible for handling database files*/
public class DatabaseFile implements NameSayerFile {


    private File _databaseFile;
    private String _savedName;
    private String _recordingName;
    private LocalDateTime _dateAndTime;


    DatabaseFile(File databaseFile){
        _databaseFile = databaseFile;
        setDateTimeAndName(_databaseFile);
    }


    //Method that extracts the time value from the file
    private void setDateTimeAndName(File databaseFile) {

        _databaseFile = databaseFile;

        _savedName = _databaseFile.getName();

        //Splitting string
        String[] splitDatabaseName = _savedName.split("_");

        //Getting fields from split name
        _recordingName = splitDatabaseName[3].substring(0, splitDatabaseName[3].length() - 4);
        _dateAndTime = getDateAndTime(splitDatabaseName[1], splitDatabaseName[2]);
    }



    //Method creates a date object based on the time the file was created
    private LocalDateTime getDateAndTime(String date, String time) {

        //Using dateTimeformat to set the format of the date
        DateTimeFormatter dateAndTimeFormatter = DateTimeFormatter.ofPattern("d-M-yyyy/H-m-s");


        return LocalDateTime.parse(date + "/" + time, dateAndTimeFormatter);
    }












    @Override
    public ArrayList<File> getFilesToPlay() {
        return null;
    }
}
