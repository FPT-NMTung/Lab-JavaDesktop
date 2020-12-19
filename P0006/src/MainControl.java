
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nmtun
 */
public class MainControl {

    private FileControl fileControl;
    private EditControl editControl;
    private String url;
    private Display display;
    private boolean isSave;


    public MainControl() {
        display = new Display();
        display.setVisible(true);
        display.setTitle("New File");

        url = null;
        isSave = false;

        fileControl = new FileControl();
    }


    public MainControl(File file) {
        display = new Display();
        display.setVisible(true);
        display.setTitle(file.getName());

        url = file.getAbsolutePath();
        isSave = true;

        fileControl = new FileControl();
        fileControl.readFile(display, file);
    }


    public void control() {

        setEventTextArea();
        setEventCloseButton();

        setEventBtnNew();
        setEventBtnOpen();
        setEventBtnSave();
        setEventBtnSaveAs();
        setEventBtnExit();
    }


    private void setEventTextArea() {
        display.getTxtDisplay().getDocument().addUndoableEditListener((evt) -> {
            isSave = false;
        });
    }


    private void setEventCloseButton() {
        display.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("a");
                fileControl.closeWindows(display, url, isSave);
            }
        });
    }


    private void setEventBtnNew() {
        display.getMenu_file_new().addActionListener((evt) -> {
            System.out.println("Menu_file_new");
            fileControl.createNewWindows();
        });
    }


    private void setEventBtnOpen() {
        display.getMenu_file_open().addActionListener((evt) -> {
            System.out.println("Menu_file_open");
            File newFile = fileControl.chooseFile();
            if (newFile == null) {
                return;
            }

            fileControl.createNewWindows(newFile);
        });
    }


    private void setEventBtnSave() {
        display.getMenu_file_save().addActionListener((evt) -> {
            System.out.println("Menu_file_save");

            url = fileControl.saveFile(display, url);
            if (url == null) {
                isSave = false;
            } else {
                isSave = true;
            }
        });
    }


    private void setEventBtnSaveAs() {
        display.getMenu_file_saveas().addActionListener((evt) -> {
            System.out.println("Menu_file_saveas");

            String newUrl = fileControl.saveAsFile(display, url);
            if (newUrl == null) {
                isSave = false;
            } else {
                isSave = true;
            }
        });
    }


    private void setEventBtnExit() {
        display.getMenu_file_exit().addActionListener((evt) -> {
            System.out.println("Menu_file_exit");

            fileControl.closeWindows(display, url, isSave);
        });
    }


    public static void main(String[] args) {
        new MainControl().control();
    }
}
