package sample;

import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;

public class NameSayerFileElementController {


    public JFXCheckBox _checkBox;
    public Label _badRatingText;
    public CheckComboBox<DatabaseFile> _comboBox;

    private NameSayerFile _fileToDisplay;
    private ArrayList<DatabaseFile> _databaseFiles = new ArrayList<>();
    private Boolean _isSelected = false;

    //This method takes objects from the parent class and sets it in the controller as fields
    public void setup(NameSayerFile fileToDisplay ,ArrayList<DatabaseFile> databaseFiles){
        _fileToDisplay = fileToDisplay;
        _databaseFiles = databaseFiles;
        setLabelName();
        if(databaseFiles != null){
            populateComboBox();
        } else {
            _comboBox.setVisible(false);
        }
        setListnersToComboBox();
    }


    //This method sets the name of the label to the object associated with it
    private void setLabelName(){
        _checkBox.setText(_fileToDisplay.get_displayName());
    }

    //This method gets called whenever the checkbox is ticked
    private void isCheckboxTicked(){
        //If checkbox it ticked then the field _isSelected is set to true
            _isSelected = _checkBox.isSelected();


    }

    //Populates the choice box with a list of databasefiles
    private void populateComboBox(){
        _comboBox.getItems().addAll(_databaseFiles);
    }

    //Sets a listener to checkbox items in order to mark the file as bad
    private void setListnersToComboBox(){
        _comboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<DatabaseFile>() {
            public void onChanged(ListChangeListener.Change<? extends DatabaseFile> c) {
                for (DatabaseFile databaseFile : _comboBox.getCheckModel().getCheckedItems()) {
                    databaseFile.setRatingBad();
                }
            }
        });
    }

    public Boolean get_isSelected() {
        return _isSelected;
    }
}
