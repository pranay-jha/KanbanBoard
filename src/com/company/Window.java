package com.company;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Window {

    JFrame frame = new JFrame();

    GridBagConstraints gbc = new GridBagConstraints();
    JPanel kanbanPane = new JPanel();
    JPanel backlogPanel = new JPanel();
    JPanel inProgressPanel = new JPanel();
    JPanel completedPanel = new JPanel();


    public Window() {
        frame.setTitle("Kanban Board");
        kanbanPane.setLayout(new GridLayout(1,3));
    }

    public void display() {

        //*****KANBAN PANE INITIALIZATION STUFF*****
        backlogPanel.setLayout(new GridBagLayout());
        JScrollPane backlogScrollPanel = new JScrollPane(backlogPanel);
        backlogScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        kanbanPane.add(backlogScrollPanel);

        inProgressPanel.setLayout(new GridBagLayout());
        JScrollPane inProgressScrollPanel = new JScrollPane(inProgressPanel);
        inProgressScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        kanbanPane.add(inProgressScrollPanel);

        completedPanel.setLayout(new GridBagLayout());
        JScrollPane completedScrollPanel = new JScrollPane(completedPanel);
        completedScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        kanbanPane.add(completedScrollPanel);

        for (int i = 0; i < Main.backlogStickyNotes.size(); i++) {
            gbc.insets = new Insets(5, 5, 5, 5);
            StickyNote currentBacklogStickyNote = Main.backlogStickyNotes.get(i);
            gbc.gridx = 0;
            gbc.gridy = i;
            backlogPanel.add(currentBacklogStickyNote, gbc);
        }
        for (int i = 0; i < Main.inProgressStickyNotes.size(); i++) {
            gbc.insets = new Insets(5, 5, 5, 5);
            StickyNote currentInProgressStickyNote = Main.inProgressStickyNotes.get(i);
            gbc.gridx = 0;
            gbc.gridy = i;
            inProgressPanel.add(currentInProgressStickyNote, gbc);
        }
        for (int i = 0; i < Main.completedStickyNotes.size(); i++) {
            gbc.insets = new Insets(5, 5, 5, 5);
            StickyNote currentCompletedStickyNote = Main.completedStickyNotes.get(i);
            gbc.gridx = 0;
            gbc.gridy = i;
            completedPanel.add(currentCompletedStickyNote, gbc);
        }


        //*****BUTTON PANE INITIALIZATION STUFF*****
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridBagLayout());

        gbc.insets = new Insets(50, 50, 50, 50);

        JButton addCard = new JButton("Add Card");
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPane.add(addCard, gbc);
        addCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog addCardDialog = new JDialog();
                addCardDialog.setTitle("Add Card");
                addCardDialog.setLayout(new GridBagLayout());
                GridBagConstraints g = new GridBagConstraints();
                g.insets = new Insets(10, 10, 10, 10);

                JTextField cardText = new JTextField("Enter card info here", 25);
                g.gridx = 0;
                g.gridy = 0;
                addCardDialog.add(cardText, g);

                JButton submit = new JButton("Submit");
                g.gridx = 0;
                g.gridy = 1;
                addCardDialog.add(submit, g);
                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        StickyNote newStickyNote = new StickyNote(1, 0, 0, cardText.getText());
                        Main.stickyNotes.add(newStickyNote);
                        Main.backlogStickyNotes.add(newStickyNote);
                        updateBacklog();
                        Main.db.addData(1, 0, 0, cardText.getText());
                        addCardDialog.setVisible(false);
                    }
                });

                addCardDialog.setSize(new Dimension(300, 150));
                addCardDialog.setLocationRelativeTo(null);
                addCardDialog.setVisible(true);
            }
        });

        JPanel contentPane = new JPanel(new GridLayout(2, 1));
        contentPane.add(kanbanPane);
        contentPane.add(buttonPane);
        frame.setContentPane(contentPane);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public void updateBacklog() {
        System.out.println("updated backlog");
        backlogPanel.removeAll();
        for (int i = 0; i < Main.backlogStickyNotes.size(); i++) {
            gbc.insets = new Insets(5, 5, 5, 5);
            StickyNote currentBacklogStickyNote = Main.backlogStickyNotes.get(i);
            gbc.gridx = 0;
            gbc.gridy = i;
            backlogPanel.add(currentBacklogStickyNote, gbc);
        }
        backlogPanel.revalidate();
        backlogPanel.repaint();
    }

    public void updateInProgress() {
        System.out.println("updated in progress");
        inProgressPanel.removeAll();
        for (int i = 0; i < Main.inProgressStickyNotes.size(); i++) {
            gbc.insets = new Insets(5, 5, 5, 5);
            StickyNote currentInProgressStickyNote = Main.inProgressStickyNotes.get(i);
            gbc.gridx = 0;
            gbc.gridy = i;
            inProgressPanel.add(currentInProgressStickyNote, gbc);
        }
        inProgressPanel.revalidate();
        inProgressPanel.repaint();
    }

    public void updateCompleted() {
        System.out.println("updated completed" + Main.completedStickyNotes.size());
        completedPanel.removeAll();
        for (int i = 0; i < Main.completedStickyNotes.size(); i++) {
            gbc.insets = new Insets(5, 5, 5, 5);
            StickyNote currentCompletedStickyNote = Main.completedStickyNotes.get(i);
            gbc.gridx = 0;
            gbc.gridy = i;
            completedPanel.add(currentCompletedStickyNote, gbc);
        }
        completedPanel.revalidate();
        completedPanel.repaint();
    }

}