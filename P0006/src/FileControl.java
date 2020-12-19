
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nmtun
 */
public class FileControl {

    JFileChooser choose = new JFileChooser("L:\\Spring2021\\P0006\\file temp");


    public void createNewWindows() {
        new MainControl().control();
    }


    public void createNewWindows(File file) {
        new MainControl(file).control();
    }


    public void closeWindows(Display display, String url, boolean isSave) {
        System.out.println("closeWindows: isSave -> " + isSave);
        if (isSave) {
            display.dispose();
        } else {
            int choice = JOptionPane.showConfirmDialog(null, "hello", "Quit", 1);
            switch (choice) {
                case 0:
                    saveFile(display, url);
                    break;
                case 1:
                    display.dispose();
                    break;
            }
        }
    }


    public void readFile(Display display, File file) {
        try {
            Scanner reFile = new Scanner(file);

            while (reFile.hasNextLine()) {
                String temp = reFile.nextLine();
                display.getTxtDisplay().append(temp + "\n");
            }
        } catch (Exception e) {
            System.out.println("Display error!!! (method displayFile)");
        }
    }


    public File chooseFile() {
        File file = null;

        choose.addChoosableFileFilter(new FileNameExtensionFilter("File .txt", "txt"));
        choose.setAcceptAllFileFilterUsed(true);

        int temp = choose.showOpenDialog(null);

        if (temp == JFileChooser.APPROVE_OPTION) {
            file = choose.getSelectedFile();
        }

        return file;
    }


    private File getAddressSaveFile(String title) {
        int temp = choose.showDialog(null, title);
        File tempFile = null;

        if (temp == JFileChooser.APPROVE_OPTION) {
            tempFile = choose.getSelectedFile();
            System.out.println("url save file: " + tempFile.getAbsolutePath());
        }

        return tempFile;
    }


    private void writeFile(Display display, String urlWrite) {
        File file = new File(urlWrite);

        try {
            FileWriter writeFile = new FileWriter(file);
            BufferedWriter buffWrite = new BufferedWriter(writeFile);

            for (String line : display.getTxtDisplay().getText().split("\\n")) {
                buffWrite.append(line);
                buffWrite.newLine();
            }

            buffWrite.close();
        } catch (Exception e) {
        }
    }


    public String saveFile(Display display, String url) {
        if (url == null) {
            File file = getAddressSaveFile("Save ...");
            if (file != null) {
                url = file.getAbsolutePath();
                display.setTitle(file.getName());
            }
        }

        if (url != null) {
            writeFile(display, url);
        }

        return url;
    }


    public String saveAsFile(Display display, String url) {
        if (url == null) {
            return saveFile(display, url);
        } else {
            File file = getAddressSaveFile("Save as ...");
            if (file != null) {
                String newUrl = file.getAbsolutePath();

                writeFile(display, newUrl);
                return newUrl;
            }
        }
        return null;
    }
}
