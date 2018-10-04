package sample;

public class PointsSystem {

    private int _points;

    public void changePoints(int val){
        _points = _points + val;
    }

    public int get_points() {
        return _points;
    }
}
