package com.example;

import java.awt.*;

public class Box {

    private boolean top = true;
    private boolean right = true;
    private boolean bottom = true;
    private boolean left = true;
    private boolean toTop = false;
    private boolean toRight = false;
    private boolean toBottom = false;
    private boolean toLeft = false;
    private boolean isCloseTop = false;
    private boolean isCloseRight = false;
    private boolean isCloseBottom = false;
    private boolean isCloseLeft = false;
    private boolean isEdited = false;

    public void draw(Graphics g, Point pos, Point player, int boxSize, int dis) {
        double distance = Math.sqrt(Math.pow((player.x * boxSize) - pos.x, 2) + Math.pow((player.y * boxSize) - pos.y, 2));
        if (distance < dis * boxSize) return;

        Color openRoad = new Color(21, 210, 0);
        Color closeRoad = new Color(227, 11, 11);
        int halfBoxSize = boxSize / 2;
        if (toTop) {
            if (!isCloseTop) g.setColor(openRoad);
            else g.setColor(closeRoad);
            g.drawLine(pos.x + halfBoxSize, pos.y + halfBoxSize, pos.x + halfBoxSize, pos.y);
        }
        if (toRight) {
            if (!isCloseRight) g.setColor(openRoad);
            else g.setColor(closeRoad);
            g.drawLine(pos.x + halfBoxSize, pos.y + halfBoxSize, pos.x + boxSize, pos.y + halfBoxSize);
        }
        if (toBottom) {
            if (!isCloseBottom) g.setColor(openRoad);
            else g.setColor(closeRoad);
            g.drawLine(pos.x + halfBoxSize, pos.y + halfBoxSize, pos.x + halfBoxSize, pos.y + boxSize);
        }
        if (toLeft) {
            if (!isCloseLeft) g.setColor(openRoad);
            else g.setColor(closeRoad);
            g.drawLine(pos.x + halfBoxSize, pos.y + halfBoxSize, pos.x, pos.y + halfBoxSize);
        }

        if (!isEdited) return;

        g.setColor(new Color(134, 134, 134));
        if (top) g.drawLine(pos.x, pos.y, pos.x + boxSize, pos.y);
        if (left) g.drawLine(pos.x, pos.y, pos.x, pos.y + boxSize);
        if (bottom) g.drawLine(pos.x, pos.y + boxSize, pos.x + boxSize, pos.y + boxSize);
        if (right) g.drawLine(pos.x + boxSize, pos.y, pos.x + boxSize, pos.y + boxSize);
    }

    public void restart() {
        toTop = false;
        toRight = false;
        toBottom = false;
        toLeft = false;

        isCloseTop = false;
        isCloseRight = false;
        isCloseBottom = false;
        isCloseLeft = false;
    }

    public boolean isEdited() {
        return !isEdited;
    }

    public boolean isTop() {
        return top;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isBottom() {
        return bottom;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isToTop() {
        return toTop;
    }

    public boolean isToRight() {
        return toRight;
    }

    public boolean isToBottom() {
        return toBottom;
    }

    public boolean isToLeft() {
        return toLeft;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setToTop(boolean toTop) {
        this.toTop = toTop;
    }

    public void setToRight(boolean toRight) {
        this.toRight = toRight;
    }

    public void setToBottom(boolean toBottom) {
        this.toBottom = toBottom;
    }

    public void setToLeft(boolean toLeft) {
        this.toLeft = toLeft;
    }

    public void setCloseTop(boolean closeTop) {
        isCloseTop = closeTop;
    }

    public void setCloseRight(boolean closeRight) {
        isCloseRight = closeRight;
    }

    public void setCloseBottom(boolean closeBottom) {
        isCloseBottom = closeBottom;
    }

    public void setCloseLeft(boolean closeLeft) {
        isCloseLeft = closeLeft;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }
}
