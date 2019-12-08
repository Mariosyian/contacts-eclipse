package com.mariosyian.contacts;

import static org.junit.Assert.assertTrue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

public class ContactsTest implements ActionListener {
	
	private static Device device;
	private static File data;
	private static BufferedReader read;
	
//	@BeforeClass
//	public static void create() throws FileNotFoundException {
//		device  = new Device();
//		data = new File("testData.txt");
//		read = new BufferedReader(new FileReader(data));
//	}
	
	@Before
	public void setUp() throws FileNotFoundException {
		device  = new Device();
		data = new File("testData.txt");
		read = new BufferedReader(new FileReader(data));
	}
	
	@After
	public void tidyUp() throws IOException {
		resetInitialTextFields();
		read.close();
		data.delete();
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testInitialTextFieldStates() {
		assertTrue(device.getContactName().equals("NAME_FIELD"));
		assertTrue(device.getAge().equals("AGE_FIELD"));
		assertTrue(device.getBday().equals("BDAY_FIELD"));
		assertTrue(device.getCity().equals("CITY_FIELD"));
		assertTrue(device.getEmail().equals("EMAIL_FIELD"));
		assertTrue(device.getOccupation().equals("JOB_FIELD"));
		assertTrue(device.getPhone().equals("PHONE_FIELD"));
	}
	
	/*
	@SuppressWarnings("static-access")
	@Test
	public void testThatRecordIsWrittenInFile() throws IOException {
			// Check that the file is empty
		
			// Insert data into text fields
		device.setContactName("testName");
		device.setPhone("testPhone");
		device.setEmail("testEmail");
		device.setAge("testAge");
		device.setCity("testCity");
		device.setBday("testBday");
		device.setOccupation("testOccupation");
		
			// Check that it did
		assertTrue(device.getContactName().equals("testName"));
		assertTrue(device.getPhone().equals("testPhone"));
		assertTrue(device.getEmail().equals("testEmail"));
		assertTrue(device.getAge().equals("testAge"));
		assertTrue(device.getCity().equals("testCity"));
		assertTrue(device.getBday().equals("testBday"));
		assertTrue(device.getOccupation().equals("testOccupation"));
		
			// Press the save button
		JButton save = new JButton("testSave");
		save.addActionListener(this);
//		save.doClick();
		
			// Check that text fields are now empty
		assertTrue(device.getContactName().equals(""));
		assertTrue(device.getPhone().equals(""));
		assertTrue(device.getEmail().equals(""));
		assertTrue(device.getAge().equals(""));
		assertTrue(device.getCity().equals(""));
		assertTrue(device.getBday().equals(""));
		assertTrue(device.getOccupation().equals(""));
		
			// Check data file
		assertTrue(read.readLine().equals("0;testName;testPhone;testEmail;testAge;testCity;testBday;testOccupation"));
	}
	*/
	
	@SuppressWarnings("static-access")
	private void resetTextFields() {
		device.setContactName("");
		device.setPhone("");
		device.setEmail("");
		device.setAge("");
		device.setCity("");
		device.setBday("");
		device.setOccupation("");
	}
	
	@SuppressWarnings("static-access")
	private void resetInitialTextFields() {
		device.setContactName("NAME_FIELD");
		device.setPhone("PHONE_FIELD");
		device.setEmail("EMAIL_FIELD");
		device.setAge("AGE_FIELD");
		device.setCity("CITY_FIELD");
		device.setBday("BDAY_FIELD");
		device.setOccupation("JOB_FIELD");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0){
		PrintWriter write = null;
		
		try {
			write = new PrintWriter(new FileWriter(data, false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		resetTextFields();
		
		write.write("0;testName;testPhone;testEmail;testAge;testCity;testBday;testOccupation");
	}
}
