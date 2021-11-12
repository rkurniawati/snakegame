package edu.wsu;

import javafx.scene.shape.Circle;

import java.util.List;

public class Util {
    public static final double EPSILON = 1e-10;

    static boolean overlaps(Circle x, Circle y) {
        return Math.abs(x.getCenterX() - y.getCenterX()) < EPSILON && Math.abs(x.getCenterY() - y.getCenterY()) < EPSILON;
    }

    static boolean overlaps(List<Circle> circles, Circle circle) {
        for(Circle c : circles) {
            if (overlaps(c, circle)) return true;
        }
        return false;
    }


}
