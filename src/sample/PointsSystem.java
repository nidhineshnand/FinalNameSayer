package sample;

import java.io.*;

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
            f2.write(Integer.toString(_points));
            f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This method loads the points if they were previously saved
    public void loadPoints(){
        //Reading file
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(_pathToSave));
            //Filename and rating
            String line;
            while ((line = reader.readLine()) != null) {
                //Checking if line has points
                _points = Integer.parseInt(line);
            }
            //Handling exceptions
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
