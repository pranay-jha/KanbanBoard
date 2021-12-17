package com.company;

import java.util.*;

public class StickyNoteOps {

    ArrayList<StickyNote> backlogStickyNotes;
    ArrayList<StickyNote> inProgressStickyNotes;
    ArrayList<StickyNote> completedStickyNotes;
    Window window;

    public StickyNoteOps() {

    }

    public StickyNoteOps(ArrayList<StickyNote> backlogStickyNotes, ArrayList<StickyNote> inProgressStickyNotes,
                         ArrayList<StickyNote> completedStickyNotes, Window window) {
        this.backlogStickyNotes = backlogStickyNotes;
        this.inProgressStickyNotes = inProgressStickyNotes;
        this.completedStickyNotes = completedStickyNotes;
        this.window = window;
    }

    /*public void backlogToInProgress(StickyNote moved) {
        backlogStickyNotes.remove(moved);
        inProgressStickyNotes.add(moved);
        window.updateBacklog();
        //Window.updateInProgress(inProgressStickyNotes);
    }*/

}
