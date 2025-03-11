import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Window implements Runnable {

    JFrame frame;
    Box[][] boxes;

    @Override
    public void run() {
        initFrame();
        initBoxes();
        frame.pack();
        frame.setLocationRelativeTo(null);
        initTimer();
    }

    void initFrame() {
        frame = new JFrame();
        frame.getContentPane().setLayout(new GridLayout(Config.WIDTH, Config.HEIGHT, 0, 0)); // Используем GridLayout
        //frame.setSize(Config.SIZE * Config.WIDTH + frame.getInsets().left + frame.getInsets().right,
        //    Config.SIZE * Config.HEIGHT + frame.getInsets().top + frame.getInsets().bottom);
        //frame.setUndecorated(true); // Убирает заголовок и границы окна

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setTitle("life game");
    }

    void initBoxes() {
        boxes = new Box[Config.WIDTH][Config.HEIGHT];
        for (int x = 0; x < Config.WIDTH; x++) {
            for (int y = 0; y < Config.HEIGHT; y++) {
                boxes[x][y] = new Box(x, y);
                frame.add(boxes[x][y]);
            }
        }
        for (int x = 0; x < Config.WIDTH; x++) {
            for (int y = 0; y < Config.HEIGHT; y++) {
                for (int sx = -1; sx <= 1; sx++) {
                    for (int sy = -1; sy <= 1; sy++) {
                        if (!(sx == 0 && sy == 0)){
                            boxes[x][y].cell.addNear(boxes
                                    [(x+sx+Config.WIDTH) % Config.WIDTH]
                                    [(y+sy+Config.HEIGHT) % Config.HEIGHT].cell);
                        }
                    }
                }
            }
        }
        for (int x = 10; x < 15; x++){
            boxes[x][10].cell.state = State.LIVE;
            boxes[x][10].setColor();
        }
    }

    private void initTimer() {
        TimerListener t1 = new TimerListener();
        Timer timer = new Timer(Config.SLEEP_MS, t1);
        timer.start();
    }

    private class TimerListener implements ActionListener {

        boolean flop = false;
        @Override
        public void actionPerformed(ActionEvent e) {
            flop = !flop;
            for (int x = 0; x < Config.WIDTH; x++) {
                for (int y = 0; y < Config.HEIGHT; y++) {
                    if (flop) {
                        boxes[x][y].step1();
                    }
                    else {
                        boxes[x][y].step2();
                    }
                }
            }
        }
    }
}
