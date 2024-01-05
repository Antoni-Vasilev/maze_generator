package com.example.frames;


import com.example.Box;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.awt.event.KeyEvent.VK_N;
import static java.awt.event.KeyEvent.VK_SPACE;

public class GameView extends JPanel {

    private static final int boxSize = 20;
    private final JFrame frame;
    private final Box[][] boxes;
    private Point player = new Point(0, 0), finder = new Point(0, 0), find = new Point(0, 0);
    private final List<Point> playerSaves = new ArrayList<>();
    private boolean isFinish = false, isContinue = false, isEdit = false;
    private int distance = 0, wait = 0, setWait = 0;

    public GameView(JFrame frame) {
        this.frame = frame;

        setBackground(new Color(38, 35, 35));
        boxes = new Box[frame.getWidth() / boxSize][frame.getHeight() / boxSize];

        for (int x = 0; x < boxes.length; x++) {
            for (int y = 0; y < boxes[x].length; y++) {
                boxes[x][y] = new Box();
            }
        }

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == VK_SPACE) {
                    if (isFinish) {
                        wait = 10;
                        isContinue = true;
                    }
                } else if (keyCode == VK_N) {
                    new Thread(() -> {
                        isEdit = false;

                        for (Box[] box : boxes) {
                            for (Box value : box) {
                                value.restart();
                            }
                        }

                        Random rand = new Random();
                        find = new Point(rand.nextInt(getWidth() / boxSize), rand.nextInt(getHeight() / boxSize));
                        finder = new Point(0, 0);
                        isEdit = true;
                        finderWay();
                    }).start();
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        drawBoxes(g);

        // Player
        if (!isFinish) {
            try {
                for (int i = 0; i < 100000; i++) {
                    movePlayer(-1);
                }
            } catch (Exception e) {
                isFinish = true;
                System.out.println("Finish");
            }

//            g.setColor(Color.GREEN);
//            g.fillRect(player.x * boxSize, player.y * boxSize, boxSize, boxSize);

            Random rand = new Random();
            find = new Point(rand.nextInt(getWidth() / boxSize), rand.nextInt(getHeight() / boxSize));
        } else if (isContinue) {
            if (distance != 100) {
                distance++;
            } else {
                for (int x = 0; x < boxes.length; x++) {
                    for (int y = 0; y < boxes[x].length; y++) {
                        boxes[x][y] = new Box();
                    }
                }

                isFinish = false;
                isContinue = false;
                distance = 0;
                wait = setWait;
                finder = new Point(0, 0);
            }
        } else {
            g.setColor(Color.YELLOW);
            g.fillRect(find.x * boxSize + boxSize / 2 - boxSize / 4, find.y * boxSize + boxSize / 2 - boxSize / 4, boxSize / 2, boxSize / 2);

            if (!isEdit) {
                isEdit = true;
                new Thread(this::finderWay).start();
            }
        }

        // Repaint
        if (wait != 0) sleep(wait);
        repaint();
    }

    private void drawBoxes(Graphics g) {
        g.setColor(new Color(38, 35, 35));
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int x = 0; x < boxes.length; x++) {
            for (int y = 0; y < boxes[x].length; y++) {
                boxes[x][y].draw(g, new Point(x * boxSize, y * boxSize), new Point((getWidth() / boxSize) / 2, (getHeight() / boxSize) / 2), boxSize, this.distance);
            }
        }

        g.setColor(new Color(134, 134, 134));
        g.drawRect(0, 0, frame.getWidth() - 1, frame.getHeight() - 1);
    }

    private void finderWay() {
        if (finder.y == find.y && finder.x == find.x) isEdit = false;
        if (!isEdit) return;

        int time = 20;
        sleep(time);
        Box box = boxes[finder.x][finder.y];

        for (int i = 0; i < 4; i++) {
            if (i == 0 && !box.isTop() && finder.y - 1 != -1 && !boxes[finder.x][finder.y - 1].isToBottom()) {
                finder.y--;
                box.setToTop(true);
                boxes[finder.x][finder.y].setToBottom(true);
                finderWay();
                if (!isEdit) return;
                box.setCloseTop(true);
                boxes[finder.x][finder.y].setCloseBottom(true);
                finder.y++;
            } else if (i == 1 && !box.isRight() && finder.x + 1 != getWidth() / boxSize && !boxes[finder.x + 1][finder.y].isToLeft()) {
                finder.x++;
                box.setToRight(true);
                boxes[finder.x][finder.y].setToLeft(true);
                finderWay();
                if (!isEdit) return;
                box.setCloseRight(true);
                boxes[finder.x][finder.y].setCloseLeft(true);
                finder.x--;
            } else if (i == 2 && !box.isBottom() && finder.y + 1 != getHeight() / boxSize && !boxes[finder.x][finder.y + 1].isToTop()) {
                finder.y++;
                box.setToBottom(true);
                boxes[finder.x][finder.y].setToTop(true);
                finderWay();
                if (!isEdit) return;
                box.setCloseBottom(true);
                boxes[finder.x][finder.y].setCloseTop(true);
                finder.y--;
            } else if (i == 3 && !box.isLeft() && finder.x - 1 != -1 && !boxes[finder.x - 1][finder.y].isToRight()) {
                finder.x--;
                box.setToLeft(true);
                boxes[finder.x][finder.y].setToRight(true);
                finderWay();
                if (!isEdit) return;
                box.setCloseLeft(true);
                boxes[finder.x][finder.y].setCloseRight(true);
                finder.x++;
            }
        }
        if (!isEdit) return;

        sleep(time);
    }

    private void movePlayer(int move) {
        Random rand = new Random();
        int randMove = move == -1 ? rand.nextInt(4) : move;

        if (randMove == 0 && player.y != 0 && boxes[player.x][player.y - 1].isEdited()) {
            boxes[player.x][player.y].setTop(false);
            boxes[player.x][player.y].setEdited(true);
            player.y--;
            boxes[player.x][player.y].setBottom(false);
            boxes[player.x][player.y].setEdited(true);
            playerSaves.add(new Point(player));
        } else if (randMove == 1 && player.x != (frame.getWidth() / boxSize) - 1 && boxes[player.x + 1][player.y].isEdited()) {
            boxes[player.x][player.y].setRight(false);
            boxes[player.x][player.y].setEdited(true);
            player.x++;
            boxes[player.x][player.y].setLeft(false);
            boxes[player.x][player.y].setEdited(true);
            playerSaves.add(new Point(player));
        } else if (randMove == 2 && player.y != (frame.getHeight() / boxSize) - 1 && boxes[player.x][player.y + 1].isEdited()) {
            boxes[player.x][player.y].setBottom(false);
            boxes[player.x][player.y].setEdited(true);
            player.y++;
            boxes[player.x][player.y].setTop(false);
            boxes[player.x][player.y].setEdited(true);
            playerSaves.add(new Point(player));
        } else if (randMove == 3 && player.x != 0 && boxes[player.x - 1][player.y].isEdited()) {
            boxes[player.x][player.y].setLeft(false);
            boxes[player.x][player.y].setEdited(true);
            player.x--;
            boxes[player.x][player.y].setRight(false);
            boxes[player.x][player.y].setEdited(true);
            playerSaves.add(new Point(player));
        } else if (!canMove(player)) {
            while (!canMove(player)) {
                player = playerSaves.getLast();
                playerSaves.removeLast();
            }
        } else if (canMove(player)) {
            movePlayer(canMoveSide(player));
        }
    }

    private boolean canMove(Point player) {
        if (player.y != 0 && boxes[player.x][player.y - 1].isEdited()) {
            return true;
        } else if (player.x != (frame.getWidth() / boxSize) - 1 && boxes[player.x + 1][player.y].isEdited()) {
            return true;
        } else if (player.y != (frame.getHeight() / boxSize) - 1 && boxes[player.x][player.y + 1].isEdited()) {
            return true;
        } else return player.x != 0 && boxes[player.x - 1][player.y].isEdited();
    }

    private int canMoveSide(Point player) {
        if (player.y != 0 && boxes[player.x][player.y - 1].isEdited()) {
            return 0;
        } else if (player.x != (frame.getWidth() / boxSize) - 1 && boxes[player.x + 1][player.y].isEdited()) {
            return 1;
        } else if (player.y != (frame.getHeight() / boxSize) - 1 && boxes[player.x][player.y + 1].isEdited()) {
            return 2;
        } else if (player.x != 0 && boxes[player.x - 1][player.y].isEdited()) {
            return 3;
        }

        return -1;
    }

    private void sleep(long time) {
        if (time == 0) return;

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
