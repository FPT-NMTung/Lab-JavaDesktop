
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Random;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author NMTung - EmilyBP
 */
public class MainControl {

    Display display;
    GridLayout gridLayout;
    Random intRandom = new Random();
    Thread time = new Thread();

    int size = 3;
    int maxSize;
    int countMove = 0;
    int countTime = 0;
    final int SIZE_BUTTON = 60;
    final int SIZE_GAP = 20;
    int sizeOfBoard = size * SIZE_BUTTON + (size - 1) * SIZE_GAP;
    JButton[][] button = new JButton[size][size];

    boolean canMove;

    public MainControl(Display display) {
        this.display = display;
    }

    private void control() {
        display.setVisible(true);
        display.setResizable(false);
        display.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        display.getPnlMainDisplay().setLayout(new GridBagLayout());
        statusBtnNewGame();
        addCbnSize();
        displayBoardGame();
    }

    private void statusBtnNewGame() {
        display.getBtnNewGame().addActionListener((e) -> {

            // get number of column and row from combobox
            getSize();

            button = new JButton[size][size];

            // clean all elements in JPanel display game
            display.getPnlDisplayGame().removeAll();
            displayBoardGame();

            // shuffle number in game
            shuffle();

            // set event for each button in game
            setEventBtnMove();

            // reset count move and time -> 0
            countMove = 0;
            countTime = 0;

            display.getLblMoveCount().setText(countMove + "");

            canMove = true;

            // check the Thread is Alive then stop before start new Thread
            if (time.isAlive()) {
                time.stop();
            }

            // start new Thread for count time
            startCountTime();
        });
    }

    private void addCbnSize() {
        display.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                int sizeBoard = display.getPnlMainDisplay().getSize().height;
                int temp = (sizeBoard + SIZE_GAP) / (SIZE_BUTTON + SIZE_GAP);
                if (temp < maxSize) return;
                maxSize = temp;
                System.out.println(maxSize);

                DefaultComboBoxModel listSize = new DefaultComboBoxModel();
                for (int i = 3; i < maxSize + 1; i++) {
                    listSize.addElement(i);
                }
                display.getCbxGameSize().setModel(listSize);
            }
        });
    }

    // get size game in combo box
    private void getSize() {
        size = Integer.parseInt(display.getCbxGameSize().getSelectedItem() + "");
    }

    // method to display game board
    private void displayBoardGame() {
        gridLayout = new GridLayout(size, size, SIZE_GAP, SIZE_GAP);
        display.getPnlDisplayGame().setSize(sizeOfBoard, sizeOfBoard);
        display.getPnlDisplayGame().setLayout(gridLayout);
            
//        System.out.println("size:" + size);
        
        int count = 1;

        // display and setText for each button
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
//                System.out.println("count:" + count);
                // with end button is " ", not number
                if (count == size * size) {
                    button[i][j] = new JButton(" ");
                } else {
                    button[i][j] = new JButton(count + "");
                }
                count++;
                // set font and size for button
                button[i][j].setPreferredSize(new Dimension(SIZE_BUTTON, SIZE_BUTTON));
                button[i][j].setRequestFocusEnabled(false);
                button[i][j].setFont(new Font("Tahoma", 1, 12));

                display.getPnlDisplayGame().add(button[i][j]);
            }
        }

        display.setVisible(true);
        display.pack();
    }

    // method to shuffle all number in game
    private void shuffle() {
        int x = size - 1;
        int y = size - 1;

        boolean canShuffleMove;

        JButton whiteSpace = button[x][y];

        int count = 0;

        while (!(count++ > Math.pow(size, 4) && button[size - 1][size - 1].getText().equals(" "))) {

            switch (intRandom.nextInt(4)) {

                //move button white space -> up
                case 0:
                    canShuffleMove = shuffleMove(whiteSpace, x - 1, y);
                    if (canShuffleMove) {
                        x = x - 1;
                    }
                    break;

                //move button white space -> down
                case 1:
                    canShuffleMove = shuffleMove(whiteSpace, x + 1, y);
                    if (canShuffleMove) {
                        x = x + 1;
                    }
                    break;

                //move button white space -> left
                case 2:
                    canShuffleMove = shuffleMove(whiteSpace, x, y - 1);
                    if (canShuffleMove) {
                        y = y - 1;
                    }
                    break;

                //move button white space -> right
                case 3:
                    canShuffleMove = shuffleMove(whiteSpace, x, y + 1);
                    if (canShuffleMove) {
                        y = y + 1;
                    }
                    break;
            }

            whiteSpace = button[x][y];
        }
        System.out.println("shuffle: " + count);
    }

    // method to move will called by shuffle method
    private boolean shuffleMove(JButton whiteSpace, int x, int y) {

        try {
            whiteSpace.setText(button[x][y].getText());
            button[x][y].setText(" ");

            return true;
        } catch (Exception e) {
        }

        return false;
    }

    // method to start count time
    private void startCountTime() {
        time = new Thread() {
            @Override
            public void run() {

                while (true) {

                    display.getLblTimeCount().setText(countTime + "");
                    countTime++;
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        };

        // start Thread
        time.start();
    }

    // set all button in game with same event
    private void setEventBtnMove() {

        // 2 loop for select each elements in array button
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                final int final_x = i;
                final int final_y = j;

                // set event when click
                button[final_x][final_y].addActionListener((e) -> {
                    if (!canMove) {
                        return;
                    }

                    // move when button is spaceWhite
                    if (!(button[final_x][final_y].getText().equals(" "))) {
                        String number = button[final_x][final_y].getText();

                        //check and move up
                        move(final_x, final_y, final_x - 1, final_y);

                        //check and move down
                        move(final_x, final_y, final_x + 1, final_y);

                        //check and move left
                        move(final_x, final_y, final_x, final_y - 1);

                        //check and move right
                        move(final_x, final_y, final_x, final_y + 1);
                    }

                });
            }
        }
    }

    // method to move button
    private void move(int select_x, int select_y, int x, int y) {
        if (!canMove) {
            return;
        }

        try {
            String text = button[x][y].getText();

            // near button is whiteSpace -> move
            if (text.equals(" ")) {
                button[x][y].setText(button[select_x][select_y].getText());
                button[select_x][select_y].setText(" ");

                countMove++;
                display.getLblMoveCount().setText(countMove + "");
            }
        } catch (Exception e) {
        }

        isWin();
    }

    // method to check win or not for stop game
    private boolean isWin() {
        int count = 1;

        // 2 loop for check each elements
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                // skip the last element
                if (i == size - 1 && j == size - 1) {
                    continue;
                }

                // check with count, button need increasing 1
                if (!(button[i][j].getText().equals(count + ""))) {
                    return false;
                }
                count++;
            }
        }

        // set user can't move button and stop time
        canMove = false;
        time.stop();

        return true;
    }

    public static void main(String[] args) {
        new MainControl(new Display()).control();
    }
}
