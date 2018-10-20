package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PointsSystem {

    String _pathToSave;

    /**This class manages the points of the whole system*/
    PointsSystem(String pathToSave){
        _pathToSave = pathToSave;
    }

    private int _points;

    public void changePoints(int val){
        _points = _points + val;
    }

    public int get_points() {
        return _points;
    }

    //Saves the current point to a new class
    public void savePoints(){
        File file = new File(_pathToSave);

        //Writing to file
        try {
            FileWriter f2 = new FileWriter(file, false);
            f2.write(_points);
            f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
