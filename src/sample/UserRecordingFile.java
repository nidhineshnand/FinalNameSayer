package sample;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserRecordingFile implements NameSayerFile {

    private String _pathToSave;
    private String _name;
    private String _filename;
    private File _file;
    private LocalDateTime _creationTime;
    private Process _process;

    public UserRecordingFile(String pathToSave, String name){
        _pathToSave = pathToSave;
        _name = name;
        _filename = setFileName();
        _creationTime = LocalDateTime.now();
    }

    public UserRecordingFile(File file){
        _file = file;
        _filename = file.getName();
        _creationTime = setDateTime();
    }

    //Setting file name when a new file is created
    private String setFileName(){
        DateTimeFormatter dateAndTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        String timeStamp = _creationTime.format(dateAndTimeFormatter);
        return "User_" + timeStamp + "_" + _name + ".wav";

    }

    //Setting date and time by extrapolating data from the file name
    private LocalDateTime setDateTime(){
        DateTimeFormatter dateAndTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        return LocalDateTime.parse(_file.getName().substring(5, 25), dateAndTimeFormatter);
    }


    /**Method that starts recording name when it is called*/
    public void startRecord(){
        try{
            ProcessBuilder builder = new ProcessBuilder("bash", "-c", "  ffmpeg -f alsa -ac 2 -i default -t 15 " + _pathToSave + _filename + " -loglevel quiet");
            _process = builder.start();
            _process.waitFor();
            //Exception handling for the process builder
        } catch (IOException ioe){
            ioe.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**Method that stops recording name when it is called*/
    public void stopRecording(){
        _process.destroy();
    }


    @Override
    public ArrayList<File> filesToPlay() {
        ArrayList<File> output = new ArrayList<>();
        output.add(_file);
        return output;
    }
}
