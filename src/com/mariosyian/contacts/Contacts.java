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
  private final static String FILENAME = "src/com/mariosyian/contacts/data.txt";
  private LinkedList<String> DATA = new LinkedList<>();
  private LinkedList<JButton> BUTTONS = new LinkedList<>();
  
    //Bounds for window
  int xCoord;
  int yCoord;
  int guiWidth = 125;
  int guiHeight = 400;
  
  public Contacts(int x, int y) {
  	setTitle("Contacts");
    Container contacts = getContentPane();
    
    contacts.setLayout(new GridLayout(0,1));

    try {
      readFile(new File(FILENAME));
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    for (JButton button : BUTTONS) {
    	this.add(button);
    }
    
     /**
      * Instead of pack that shrinks gui to exactly required size
      * setBounds allows to set absolute size of window
      * x, y, width, height
      */
    this.xCoord = x + 25;
    this.yCoord = y;
    setBounds(xCoord, yCoord, guiWidth, guiHeight);
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    JButton btn = (JButton) (event.getSource());
    String[] nameSplit = findName(btn.getText()).split(";");

    Device.setIDLabel(nameSplit[0]);
    Device.setContactName(nameSplit[1]);
    Device.setPhone(nameSplit[2]);
    Device.setEmail(nameSplit[3]);
    Device.setAge(nameSplit[4]);
    Device.setCity(nameSplit[5]);
    Device.setBday(nameSplit[6]);
    Device.setOccupation(nameSplit[7]);
  }
  
   /**
    * Method used to return the data of the button from the DATA array.
    * @return String Complete record as one ';' separated String, hard coded data if not found.
    */
  private String findName(String nameToFind) {
    String[] nameSplit;
    
    for (String record : DATA) {
    	nameSplit = record.split(";");
    	String current = nameSplit[0] + ":" + nameSplit[1];
      if (current.equals(nameToFind)) {
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
    
//    BUTTONS.add(newButton); -- Button is added in readFile()
    
    guiHeight += 35;
    this.setSize(guiWidth, guiHeight);    
    getContentPane().add(newButton);
    // Read data file again to update DATA list
    try {
			readFile(new File(FILENAME));
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found Error: " + FILENAME + " was not found or does not exist.");
		}
  }
  
  /**
   * Deletes JButton object with specified name
   * @param name Text of JButton to delete
   */
	public void deleteBtn(String name)
	{
    for (JButton button : BUTTONS) {
      if (button.getText().equals(name)) {
        getContentPane().remove(button);
        BUTTONS.remove(button);
        deleteContact(name);
        guiHeight -= 35;
        this.setSize(guiWidth, guiHeight);
        break;
      }
    }	  
	}
	
	/**
   * Updates JButton object with specified name
   * @param name New text of JButton
   */
	public void updateBtn(int index, String name)
	{
		JButton btn = BUTTONS.get(index);
    btn.setText(name);
		BUTTONS.set(index, btn);
	}
	
  private void readFile(File data) throws FileNotFoundException {
    BufferedReader read = new BufferedReader(new FileReader(data));
    String line = "";
    String[] nameSplit;
    DATA.clear();
    BUTTONS.clear();
    
    try {
      line = read.readLine();
    } catch (Exception e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }

    while (line != null) {
    	// Add the new record into list
      DATA.add(line);
      // Split to extract name
      nameSplit = line.split(";");
      // Create new button from data
      JButton newBtn = new JButton(nameSplit[0] + ":" + nameSplit[1]);
      newBtn.addActionListener(this);
      // Add it to the list
      BUTTONS.add(newBtn);
      
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
  
  /**
   * Deletes a contact record from the list.
   * @param contact Contact to be removed
   */
  private void deleteContact(String contact) {
  	String[] nameSplit;
    for (String record : DATA) {
    	nameSplit = record.split(";");
    	String current = nameSplit[0] + ":" + nameSplit[1];
      if (current.equals(contact)) {
        DATA.remove(record);
        break;
      }
    }
  }
  
  /**
   * Update a contact record from the list.
   * @param contact Contact to be updated
   */
  public void updateContact(int index, String contact) {
  	DATA.set(index, contact);
  	String[] split = contact.split(";");
  	JButton btn = BUTTONS.get(index);
  	btn.setText(split[0] + ":" + split[1]);
  	BUTTONS.set(index, btn);
  }
  
  /**
   * Get index of record in list.
   * @param contact Record to be found.
   * @return int Index of record if found, -1 otherwise.
   */
  public int getIndex(String contact) {
  	return DATA.indexOf(contact);
  }
}