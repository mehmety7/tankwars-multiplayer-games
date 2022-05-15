package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int x, y;
    public int speed = 1;

    public BufferedImage up, down, left, right;
    public String direction;

    public int fireTime = 1;

}
