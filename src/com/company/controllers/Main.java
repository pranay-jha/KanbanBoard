package com.company.controllers;

import com.company.models.StickyNote;
import com.company.views.Window;

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static ArrayList<StickyNote> stickyNotes = new ArrayList<>();
    public static ArrayList<StickyNote> backlogStickyNotes = new ArrayList<>();
    public static ArrayList<StickyNote> inProgressStickyNotes = new ArrayList<>();
    public static ArrayList<StickyNote> completedStickyNotes = new ArrayList<>();

    public static DBConnect db = new DBConnect("sticky.db");
    public static Window window;

    public static void main(String[] args) {
        db.createNewDatabase();
        db.addTables();

        stickyNotes = db.getData();

        for (int i = 0; i < stickyNotes.size(); i++) {
            StickyNote currentNote = stickyNotes.get(i);
            if (currentNote.isBacklog() == 1) {
                backlogStickyNotes.add(currentNote);
                currentNote.getText();
            }
            else if (currentNote.isInProgress() == 1) {
                inProgressStickyNotes.add(currentNote);
            }
            else {
                completedStickyNotes.add(currentNote);
            }
        }

        window = new Window();
        window.display();

    }
}