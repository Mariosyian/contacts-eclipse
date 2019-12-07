package com.mariosyian.contacts;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Contacts extends JFrame implements ActionListener{
  /**
   * Automatically generated by eclipse
   */
  private static final long serialVersionUID = 1L;

  	//Text file data
  private final static String FILENAME = "./data.txt";
  private LinkedList<String> DATA = new LinkedList<>();
  private LinkedList<JButton> buttons = new LinkedList<>();
  
    //Bounds for window
  int xCoord = 350;
  int yCoord = 50;
  int guiWidth = 125;
  int guiHeight = 400;
  
  public Contacts() {
  	setTitle("Contacts");
    Container contacts = getContentPane();
    
    contacts.setLayout(new GridLayout(0,1));

    try {
      readFile(new File(FILENAME));
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    for (JButton button : buttons) {
    	this.add(button);
    }
    
     /**
      * Instead of pack that shrinks gui to exactly required size
      * setBounds allows to set absolute size of window
      * x, y, width, height
      */
    setBounds(xCoord, yCoord, guiWidth, guiHeight);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void actionPerformed(ActionEvent event) {
    JButton btn = (JButton) (event.getSource());
    String[] nameSplit = findName(btn.getText()).split(";");
    Device.setContactName(nameSplit[1]);
    Device.setPhone(nameSplit[2]);
    Device.setEmail(nameSplit[3]);
    Device.setAge(nameSplit[4]);
    Device.setCity(nameSplit[5]);
    Device.setBday(nameSplit[6]);
    Device.setOccupation(nameSplit[7]);
  }
  
   /**
    * TODO:
    * Method used to return the data of the button from the DATA array
    * This method will return the FIRST occurence so if there are at
    * least two contacts with the same name, the second one will not load.
    */
  private String findName(String nameToFind) {
    String[] nameSplit;
    
    for (String record : DATA) {
    	nameSplit = record.split(";");
      if (nameSplit[1].equals(nameToFind)) {
        return record;
      }
    }

    return "0;NO;SUCH;CONTACT;INFORMATION;HAS;BEEN;FOUND";
  }
  
    /**
     * Method used to create any new buttons during runtime of program
     * @param name Button text to display
     */
  public void newBtn(String name)
  {
    JButton newButton = new JButton(name);
    newButton.addActionListener(this);
    
    buttons.add(newButton);
    
    guiHeight += 35;
    this.setSize(guiWidth, guiHeight);
    
    getContentPane().add(newButton);
    
  }
  
  private void readFile(File data) throws FileNotFoundException {
    BufferedReader read = new BufferedReader(new FileReader(data));
    String line = "";
    
    try {
      line = read.readLine();
    } catch (Exception e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }

    while (line != null) {
      DATA.add(line);
      
      	//Read next line at end of loop to ensure no skipping or extra iteration
      try {
				line = read.readLine();
			} catch (IOException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
    }
    
    try {
    	read.close();
    } catch (IOException e) {
    	System.err.println("Unable to close BufferedReader...");
    	System.err.println(e.getMessage());
    }
  }
}