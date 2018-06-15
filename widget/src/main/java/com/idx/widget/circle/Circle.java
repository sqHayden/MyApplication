package com.idx.widget.circle;

import java.util.Random;
import static com.idx.widget.circle.CustomView.PI;

/**
 * Created by danny on 4/9/18.
 */

public class Circle {
    float x;
    float y;
    double angle=(new Random().nextFloat())*2*PI;
    int speed=3;
    int radius;
    int height,width;

    public Circle(int wid,int hei) {
        width = wid;
        height = hei;
    }

    public Circle(float x, float y, int radius,int wid,int hei) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        width = wid;
        height = hei;
    }

    public void updateLocate(){
        x=x+(float)(speed*Math.cos(angle));
        y=y+(float)(speed*Math.sin(angle));
        if (x+radius>=width){
            if (angle>=0&&angle<=(PI/2)){
                angle=PI-angle;
            }
            if (angle>1.5*PI&&angle<=2*PI){
                angle=3*PI-angle;
            }
        }
        if (x-radius<=0){
            if (angle>=PI&&angle<=1.5*PI){
                angle=3*PI-angle;
            }
            if (angle>=PI/2&&angle<PI){
                angle=PI-angle;
            }
        }
        if (y-radius<=0||y+radius>=height){
            angle=2*PI-angle;
        }
    }

    public void changeDerection(Circle other){
        double temp=this.angle;
        this.angle=other.angle;
        other.angle=temp;
    }
}
