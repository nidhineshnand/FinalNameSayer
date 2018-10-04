package sample;

public class PointsSystem {
    int _points;

    public void changePoints(int val){
        _points = _points + val;
    }

    public int get_points() {
        return _points;
    }
}
