package com.company.models;

import com.company.controllers.Main;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class StickyNote extends JPanel implements ActionListener{

    private int isBacklog;
    private int isInProgress;
    private int isCompleted;
    private JTextArea displayedText;

    public StickyNote(int isBacklog, int isInProgress, int isCompleted, String text) {
        System.out.println("new sticky note created");
        this.isBacklog = isBacklog;
        this.isInProgress = isInProgress;
        this.isCompleted = isCompleted;
        setPreferredSize(new Dimension(150, 150));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton leftArrow = new JButton("<");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(leftArrow, gbc);
        if (isBacklog == 1)
            leftArrow.setEnabled(false);
        leftArrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("left arrow is clicked");
                leftArrowIsClicked();
            }
        });

        JButton rightArrow = new JButton(">");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(rightArrow, gbc);
        if (isCompleted == 1)
            rightArrow.setEnabled(false);
        rightArrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("right arrow is clicked");
                rightArrowIsClicked();
            }
        });

        JButton deleteButton = new JButton("Delete");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        add(deleteButton, gbc);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("delete is clicked");
                deleteStickyNote();
            }
        });


        displayedText = new JTextArea(text);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 80;      //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 1;
        displayedText.setLineWrap(true);
        displayedText.setEditable(false);
        add(displayedText, gbc);

        if (isBacklog == 1) {
            setBackground(Color.RED);
            displayedText.setBackground(Color.RED);
        }
        else if (isInProgress == 1) {
            setBackground(Color.YELLOW);
            displayedText.setBackground(Color.YELLOW);
        }
        else {
            setBackground(Color.GREEN);
            displayedText.setBackground(Color.GREEN);
        }
    }

    public void setIsBacklog(int isBacklog) {
        this.isBacklog = isBacklog;
    }
    public int isBacklog() {
        return isBacklog;
    }

    public void setIsInProgress(int isInProgress) {
        this.isInProgress = isInProgress;
    }
    public int isInProgress() {
        return isInProgress;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }
    public int isCompleted() {
        return isCompleted;
    }

    public void setText(String text) {
        displayedText = new JTextArea(text);
    }
    public String getText() {
        return displayedText.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    public void rightArrowIsClicked() {
        if(isBacklog() == 1) {
            setIsBacklog(0);
            setIsInProgress(1);
            setIsCompleted(0);
            StickyNote newStickyNote = new StickyNote(isBacklog(), isInProgress(), isCompleted(), getText());
            for(int i = 0; i < Main.backlogStickyNotes.size(); i++) {
                System.out.println("backlog loop is running");
                if (this == Main.backlogStickyNotes.get(i)) {
                    System.out.println("removed from backlog");
                    Main.backlogStickyNotes.remove(Main.backlogStickyNotes.get(i));
                    break;
                }
            }
            Main.inProgressStickyNotes.add(newStickyNote);

        }

        else if (isInProgress() == 1) {
            setIsBacklog(0);
            setIsInProgress(0);
            setIsCompleted(1);
            StickyNote newStickyNote = new StickyNote(isBacklog(), isInProgress(), isCompleted(), getText());
            for(int i = 0; i < Main.inProgressStickyNotes.size(); i++) {
                System.out.println("in progress loop is running");
                if (this == Main.inProgressStickyNotes.get(i)) {
                    System.out.println("removed from in progress");
                    Main.inProgressStickyNotes.remove(Main.inProgressStickyNotes.get(i));
                    break;
                }
            }
            Main.completedStickyNotes.add(newStickyNote);
            System.out.println("added to completed");
        }

        Main.stickyNotes.clear();
        Main.stickyNotes.addAll(Main.backlogStickyNotes);
        Main.stickyNotes.addAll(Main.inProgressStickyNotes);
        Main.stickyNotes.addAll(Main.completedStickyNotes);

        Main.window.updateBacklog();
        Main.window.updateInProgress();
        Main.window.updateCompleted();

        Main.db.update();
    }

    public void leftArrowIsClicked() {
        if(isCompleted() == 1) {
            setIsBacklog(0);
            setIsInProgress(1);
            setIsCompleted(0);
            StickyNote newStickyNote = new StickyNote(isBacklog(), isInProgress(), isCompleted(), getText());
            for(int i = 0; i < Main.completedStickyNotes.size(); i++) {
                System.out.println("backlog loop is running");
                if (this == Main.completedStickyNotes.get(i)) {
                    System.out.println("removed from backlog");
                    Main.completedStickyNotes.remove(Main.completedStickyNotes.get(i));
                    break;
                }
            }
            Main.inProgressStickyNotes.add(newStickyNote);
        }
        else if (isInProgress() == 1) {
            setIsBacklog(1);
            setIsInProgress(0);
            setIsCompleted(0);
            StickyNote newStickyNote = new StickyNote(isBacklog(), isInProgress(), isCompleted(), getText());
            for(int i = 0; i < Main.inProgressStickyNotes.size(); i++) {
                System.out.println("in progress loop is running");
                if (this == Main.inProgressStickyNotes.get(i)) {
                    System.out.println("removed from in progress");
                    Main.inProgressStickyNotes.remove(Main.inProgressStickyNotes.get(i));
                    break;
                }
            }
            Main.backlogStickyNotes.add(newStickyNote);
            System.out.println("added to completed");
        }

        Main.stickyNotes.clear();
        Main.stickyNotes.addAll(Main.backlogStickyNotes);
        Main.stickyNotes.addAll(Main.inProgressStickyNotes);
        Main.stickyNotes.addAll(Main.completedStickyNotes);

        Main.window.updateBacklog();
        Main.window.updateInProgress();
        Main.window.updateCompleted();

        Main.db.update();
    }

    public void deleteStickyNote() {
        if (isBacklog() == 1) {
            for(int i = 0; i < Main.backlogStickyNotes.size(); i++) {
                System.out.println("loop is running");
                if (this == Main.backlogStickyNotes.get(i)) {
                    System.out.println("deleted from backlog");
                    Main.stickyNotes.remove(Main.backlogStickyNotes.get(i));
                    Main.backlogStickyNotes.remove(Main.backlogStickyNotes.get(i));
                    break;
                }
            }
        }
        else if (isInProgress() == 1) {
            for(int i = 0; i < Main.inProgressStickyNotes.size(); i++) {
                System.out.println("loop is running");
                if (this == Main.inProgressStickyNotes.get(i)) {
                    System.out.println("deleted from in progress");
                    Main.stickyNotes.remove(Main.inProgressStickyNotes.get(i));
                    Main.inProgressStickyNotes.remove(Main.inProgressStickyNotes.get(i));
                    break;
                }
            }
        }
        else {
            for(int i = 0; i < Main.completedStickyNotes.size(); i++) {
                System.out.println("loop is running");
                if (this == Main.completedStickyNotes.get(i)) {
                    System.out.println("deleted from completed");
                    Main.stickyNotes.remove(Main.completedStickyNotes.get(i));
                    Main.completedStickyNotes.remove(Main.completedStickyNotes.get(i));
                    break;
                }
            }
        }
        Main.window.updateBacklog();
        Main.window.updateInProgress();
        Main.window.updateCompleted();

        Main.db.update();
    }


}
