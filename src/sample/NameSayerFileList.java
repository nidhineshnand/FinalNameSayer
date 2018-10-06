package sample;

import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import javax.naming.Name;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

//This abstract class is to share code for handeling the playing of a file
public abstract class NameSayerFileList {



    /**
     * This method takes in a list of files. Then is concatenates, equalizes and removes the silence in those files.
     * After that the final file to play is returned
     */
    public File getFileToPlay(NameSayerFile file) {

        //Making new file to concatenate
        File catFile = new File("./myList.txt");
        try {
            //Always creating a new file
            if (catFile.exists()) {
                catFile.delete();
                catFile.createNewFile();
            } else {

                catFile.createNewFile();

            }
        } catch (IOException e) {
        }

        //Writing the names of files to a txt file to concatanate
        for (File fileToConcatanate : file.filesToPlay()) {
            try {
                Files.write(catFile.toPath(), ("file './resources/database/" + fileToConcatanate.getName() + "'\n").getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Always creating a new output file
        File catAudio = new File("./resources/concatenations/output.wav");

        if (catAudio.exists()) {
            catAudio.delete();
        }

        //Concatenating recording
        concatenateRecording();

        //Always creating a new output file
        File catAudioclean = new File("./resources/concatenations/output1.wav");

        if (catAudioclean.exists()) {
            catAudioclean.delete();
        }

        //Cleaning recording
        cleanRecording();

        //Always creating a new output file
        File catAudioFinal = new File("./resources/concatenations/output2.wav");

        if (catAudioFinal.exists()) {
            catAudioFinal.delete();
        }
        normalizeAudio();

        return new File("./resources/concatenations/output2.wav");

    }

    //Concatenates the set of files provided
    private void concatenateRecording() {
        try {
            ProcessBuilder builder = new ProcessBuilder("bash", "-c", "ffmpeg -f concat -safe 0 -i myList.txt -c copy ./resources/concatenations/output.wav");
            Process process = builder.start();
            process.waitFor();
            //Exception handling for the process builder
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Tries to remove the silence required
    private void cleanRecording() {
        try {
            ProcessBuilder builder = new ProcessBuilder("bash", "-c", "ffmpeg -hide_banner -i  ./resources/concatenations/output.wav -af silenceremove=1:0:-50dB:1:5:-50dB:0 ./resources/concatenations/output1.wav");
            Process process = builder.start();
            process.waitFor();
            //Exception handling for the process builder
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Normalized audio
    private void normalizeAudio(){
        try {
            ProcessBuilder builder = new ProcessBuilder("bash", "-c", "ffmpeg -i ./resources/concatenations/output1.wav -filter:a \"dynaudnorm=f=75:g=15\" ./resources/concatenations/output2.wav");
            Process process = builder.start();
            process.waitFor();
            //Exception handling for the process builder
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**This method takes in a list of NamesSayerFile objects and creates a vbox that holds the objects which will be used
     * to paint the files onscreen*/
    public VBox getFilesForScene(ObservableList<? extends NameSayerFile> files){
        VBox container = new VBox();

        //Looping through files to add them to vbox
        for (NameSayerFile file : files){
            container.getChildren().add(file.get_fileView());
        }
        return container;
    }





}
