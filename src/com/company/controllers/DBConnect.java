package com.company.controllers;

import com.company.models.StickyNote;

import java.sql.*;
import java.util.*;

public class DBConnect {

    private String dbName;
    private String url;

    public DBConnect(String dbName) {
        this.dbName = dbName;
        this.url = "jdbc:sqlite:C:/sqlite/db/" + dbName;
    }

    public void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addTables(){
        String sql = "CREATE TABLE IF NOT EXISTS sticky (\n"
                + " id integer PRIMARY KEY,\n"
                + "	isBacklog integer NOT NULL,\n"
                + "	isInProgress integer NOT NULL,\n"
                + "	isCompleted integer NOT NULL,\n"
                + " text text NOT NULL\n"
                + ");";

        try(Connection conn = DriverManager.getConnection(url)){
            Statement statement = conn.createStatement();
            statement.execute(sql);
            System.out.println("Tables added");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addData(int isBacklog, int isInProgress, int isCompleted, String theText){
        String sql = "INSERT INTO sticky(isBacklog, isInProgress, isCompleted, text) VALUES (?,?,?,?)";
        try(Connection conn = DriverManager.getConnection(url)){
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, isBacklog);
            statement.setInt(2, isInProgress);
            statement.setInt(3, isCompleted);
            statement.setString(4, theText);
            statement.executeUpdate();
            System.out.println("data added");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<StickyNote> getData() {
        String sql = "SELECT id, isBacklog, isInProgress, isCompleted, text FROM sticky";
        ArrayList<StickyNote> stickyNotes = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url)){
            Statement statement = conn.createStatement();
            ResultSet sticky = statement.executeQuery(sql);
            while(sticky.next())
            {
                int isBacklog = sticky.getInt("isBacklog");
                int isInProgress = sticky.getInt("isInProgress");
                int isCompleted = sticky.getInt("isCompleted");
                String text = sticky.getString("text");
                StickyNote stickyNote = new StickyNote(isBacklog, isInProgress, isCompleted, text);
                stickyNotes.add(stickyNote);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return stickyNotes;
    }

    /*public int getLastId() {
        String sql = "SELECT MAX(ID) AS LAST FROM sticky";
        int lastId = 0;
        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement pst1 = conn.prepareStatement(sql);
            ResultSet rs1 = pst1.executeQuery();
            String max = rs1.getString("LAST");
            lastId = (Integer.parseInt(max));
            pst1.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lastId;
    }*/

    public void update() {
        try(Connection conn = DriverManager.getConnection(url)) {
            String sql1 = "DELETE FROM sticky";
            Statement statement = conn.createStatement();
            statement.execute(sql1);
            System.out.println("database erased");
            for (int i = 0; i < Main.stickyNotes.size(); i++) {
                int isBacklog = Main.stickyNotes.get(i).isBacklog();
                int isInProgress = Main.stickyNotes.get(i).isInProgress();
                int isCompleted = Main.stickyNotes.get(i).isCompleted();
                String theText = Main.stickyNotes.get(i).getText();

                String sql2 = "INSERT INTO sticky(isBacklog, isInProgress, isCompleted, text) VALUES (?,?,?,?)";
                PreparedStatement pstatement = conn.prepareStatement(sql2);
                pstatement.setInt(1, isBacklog);
                pstatement.setInt(2, isInProgress);
                pstatement.setInt(3, isCompleted);
                pstatement.setString(4, theText);
                pstatement.executeUpdate();
            }
            System.out.println("database rewritten");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

