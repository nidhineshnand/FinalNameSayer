package sample;

import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;

public interface NameSayerFile {

    /**When this method is called, it much output a list of files that the object is associated with. These files will be
     * used to play the objects recording
     */
    public ArrayList<File> filesToPlay();

    /**When this method is called, the object should return its display name. This name will be used to display the
     * on screen
     */
    public String get_displayName();

    /**This method gets the view of the object being painted*/
    public Pane get_fileView();
}
