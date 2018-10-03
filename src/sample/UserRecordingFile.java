package sample;

import application.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserRecordingFile implements NameSayerFile {

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
        _filename = setFileName();
        _creationTime = LocalDateTime.now();
        _displayName = setDisplayName();
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

    //This method sets display name for UserRecordingFile
    private String setDisplayName(){
        DateTimeFormatter dateAndTimeFormatter = DateTimeFormatter.ofPattern(" dd MMM yyyy_HH:mm:ss");
        return  _identity + _creationTime.format(dateAndTimeFormatter);
    }



    //Setting file name when a new file is created
    private String setFileName(){
        DateTimeFormatter dateAndTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        String timeStamp = _creationTime.format(dateAndTimeFormatter);
        return "User_" + timeStamp + "_" + _displayName.replace(" ", "-") + ".wav";

    }

    //Setting date and time by extrapolating data from the file name
    private LocalDateTime setDateTime(){
        DateTimeFormatter dateAndTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        return LocalDateTime.parse(_file.getName().substring(5, 25), dateAndTimeFormatter);
    }

    //This method gets the display name of a file when it is instantiated using a file
    private String extrapolateIdentity(){
        String[] splitUserRecordingName = _filename.split("_");
        return splitUserRecordingName[3].substring(0, splitUserRecordingName[3].length() - 4).replace("-", " ");
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

    //Method deletes the UserRecordingFile from the folder
    public void deleteFile(){
        _file.delete();
    }

    //Loads a FXML pane that will be used to display the file on the scene
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
}
