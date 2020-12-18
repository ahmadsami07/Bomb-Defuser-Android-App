package com.e.cmpt276assignment3.model;


/*
Ahmad As Sami
Assignment 3
SFU ID 301404717
The following class starts making the maze on which the bombs will be placed
It will contain the getters and setters for the bomb rows and columns which will remain constant
throughout the application.
 */

import java.util.Random;

/*
    Here, we will give flags to the nested array, so that we can understand the situation with the
    corresponding button.
    We will keep:
    0= a field which is not searched and no bomb
    1=a field which is not searched and has bomb
    2=a field which is searched and has bomb
    3=a field which is searched and no bombs
    4=a field which has bomb and is being researched for scanning purposes
*/
public class BombField {

    //These will contain the necessary materials required to stay in the system in the activity. There are no
    //calculations occurring in the following class.

    private int scannum;
    private int defuseno;
    private int rowno;
    private int colno;
    private int bombno;
    private int timesplayed;
    private int topscoremode1;
    private int topscoremode2;
    private int topscoremode3;

    //This gets the nested array which is to be populated with 0,1,2 and 3 which denote current
    //bomb situation

    private int[][] bombfieldcoord = new int[rowno][colno];
    private static BombField instance;
    //singleton support
    public static BombField getInstance() {
        if (instance == null) {
            instance=new BombField();
        }
        return instance;
    }


    //Creates the bomb field
    public void bombfieldmaker() {
        bombfieldcoord = new int[getRowno()][getColno()];
        //  System.out.println(getRowno());
        //System.out.println(getColno());
        //First, we set all the elements of the bombfield to 0
        for (int row = 0; row < getRowno(); row++) {
            for (int col = 0; col < getColno(); col++) {
                // System.out.println(bombfieldcoord[0][0]);
                bombfieldcoord[row][col] = 0;
            }
        }

        //Then, we use random to randomly fill up the elements, with the help
        //of a while loop

        int bombrandnumber = bombno;

        while(bombrandnumber!=0)
        {
            Random randbomb=new Random();
            int randbomb1 = randbomb.nextInt(rowno-1);
            int randbomb2 = randbomb.nextInt(colno-1);
            if(bombfieldcoord[randbomb1][randbomb2]==0)
            {
                bombfieldcoord[randbomb1][randbomb2]=1;
                bombrandnumber--;
            }
        }
    }
    //If bomb is found, increase defuse number, while if not found
    //Increase the scans while incrementing the coordinate with correct flag for use later
   public boolean if_bomb_found(int row,int col) {
        if (bombfieldcoord[row][col] == 1) {
            bombfieldcoord[row][col]++;
            defuseno++;
           // scannum++;
            return true;
        }
       else if(bombfieldcoord[row][col]==0) {
            bombfieldcoord[row][col]+=3;//incrementing from 0 to 1 to 2 for particular field
            scannum++;
        }
        else if(bombfieldcoord[row][col]==2) {
            //incrementing from 2 to 4 to accommodate next level bomb search
            bombfieldcoord[row][col]+=2;
            scannum++;
        }

       return false;
    }

    //Following class scans the rows and columns of a particular coordinate to find a bomb
    //Checks left of the chosen coordinate and right to provide correct scannumber
    //If an unsearched bomb is found, scannumber is increased.
    public int scanbombs(int bombrow, int bombcol)
    {
        int scanbombsnumber =0;
        for(int col=0;col<getColno();col++)
        {
            //this iterates over the increasing rows while keeping a constant column
            //where bomb is in
            if(bombfieldcoord[bombrow][col]==1)
            {
                scanbombsnumber++;
            }
        }
        for(int row=0;row<getRowno();row++)
        {
            //this iterates over the increasing rows while keeping a constant column
            //where bomb is in
            if(bombfieldcoord[row][bombcol]==1)
            {
                scanbombsnumber++;
            }
        }

        return scanbombsnumber;
    }
    //Crucial getters and setters which are required for access throughout all the activities
    //For setting rows columns used in shared preferences and singleton access
    public int getScannum() { return scannum; }
    public void setScannum(int scannum) { this.scannum = scannum; }
    public int getDefuseno() { return defuseno; }
    public void setDefuseno(int defuseno) { this.defuseno = defuseno; }
    public int getRowno() { return rowno; }
    public void setRowno(int rowno) { this.rowno = rowno; }
    public int getColno() { return colno; }
    public void setColno(int colno) { this.colno = colno; }
    public int getBombno() { return bombno; }
    public void setBombno(int bombno) { this.bombno = bombno; }
    public void plusscan() { this.scannum = scannum+1; }



    //More getters and setters required for implementing further functions in another activities,
    //especially related to the sharedprefs
    public int getval(int row,int col) { return bombfieldcoord[row][col]; }
    public void increasetimesplayed() { timesplayed++; }
    public int getTimesplayed() { return timesplayed; }
    public void setTimesplayed(int timesplayed) { this.timesplayed = timesplayed; }
    public int getTopscoremode1() { return topscoremode1; }
    public void setTopscoremode1(int topscoremode1) { this.topscoremode1 = topscoremode1; }
    public int getTopscoremode2() { return topscoremode2; }
    public void setTopscoremode2(int topscoremode2) { this.topscoremode2 = topscoremode2; }
    public int getTopscoremode3() { return topscoremode3; }
    public void setTopscoremode3(int topscoremode3) { this.topscoremode3 = topscoremode3; }
    public void increasescore1() { topscoremode1++; }
}
