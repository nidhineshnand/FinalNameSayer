package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**This class contains all the functionality for User Recording Files*/
public class UserRecordingFile implements NameSayerFile, Comparable<UserRecordingFile> {

    private String _pathToSave;
    private String _displayName;
    private String _filename;
    private File _file;
    private LocalDateTime _creationTime;
    private Process _process;
    private Pane _fileView;
    private String _identity;
    private NameSayerFileElementController _controller;

    public UserRecordingFile(String pathToSave, ArrayList<String> fileNames){
        _pathToSave = pathToSave;
        _identity = String.join(" ", fileNames);
        _creationTime = LocalDateTime.now();
        _displayName = setDisplayName();
        _filename = setFileName();
        _file = new File(_pathToSave + _filename);
        loadView();
    }

    public UserRecordingFile(File file){
        _file = file;
        _filename = file.getName();
        _identity = extrapolateIdentity();
        _creationTime = setDateTime();
        _displayName = setDisplayName();
        loadView();
    }

    /**This method sets display name for UserRecordingFile*/
    private String setDisplayName(){
        DateTimeFormatter dateAndTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
        return  _identity + " " + _creationTime.format(dateAndTimeFormatter);
    }



    /**Setting file name when a new file is created*/
    private String setFileName(){
        DateTimeFormatter dateAndTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        String timeStamp = _creationTime.format(dateAndTimeFormatter);
        return "User_" + timeStamp + "_" + _identity.replace(" ", "-") + ".wav";

    }

    /**Setting date and time by extrapolating data from the file name*/
    private LocalDateTime setDateTime(){
        DateTimeFormatter dateAndTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        return LocalDateTime.parse(_file.getName().substring(5, 24), dateAndTimeFormatter);
    }

    /**This method gets the display name of a file when it is instantiated using a file*/
    private String extrapolateIdentity(){
        String[] splitUserRecordingName = _filename.split("_");
        return splitUserRecordingName[3].substring(0, splitUserRecordingName[3].length() - 4).replace("-", " ");
    }

    /**Method that starts recording name when it is called*/
    public void startRecording(){
        try{
            ProcessBuilder builder = new ProcessBuilder("bash", "-c", "  ffmpeg -f alsa -ac 1 -ar 44100 -i default -t 15 " + _pathToSave + _filename + " -loglevel quiet");
            _process = builder.start();
            //Exception handling for the process builder
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**Method that stops recording name when it is called*/
    public void stopRecording(){
        _process.destroy();
    }

    /**Method deletes the UserRecordingFile from the folder*/
    public void deleteFile(){
        _file.delete();
    }

    /**Loads a FXML pane that will be used to display the file on the scene*/
    private void loadView(){
        try {
            //Loading view
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/NameSayerFileElement.fxml"));
            _fileView =  fxmlLoader.load();
            //Setting up controller
            _controller = fxmlLoader.getController();
            _controller.setup(this, null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pane get_fileView() {
        return _fileView;
    }

    @Override
    public ArrayList<File> filesToPlay() {
        ArrayList<File> output = new ArrayList<>();
        output.add(_file);
        return output;
    }

    @Override
    public String get_displayName() {
        return _displayName;
    }


    public String get_identity() {
        return _identity;
    }

    public NameSayerFileElementController get_controller() {
        return _controller;
    }

    @Override
    public int compareTo(UserRecordingFile other) {
        if (!_identity.equals(other._identity)){
            return _identity.compareTo(other._identity);
        } else {
            return _creationTime.compareTo(other._creationTime);
        }
    }

    @Override
    public String toString() {
        return _displayName;
    }
}
