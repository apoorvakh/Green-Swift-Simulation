package swiftTry3;

import java.util.*;
public class Account {
	
	private int accID;
	//private String passWord;
	
	
	public static int accountID=0;
	
	
	
	public Account()
	{	
		this.accID=++accountID;
		
		//selectContainer();
	}
	
	
	
	
	public int getAccountID()
	{
		return this.accID;
	}
	/*
	public void putFile(UserFile f) throws NoSpaceException//called in the main class    ... Forwards the file to the selected Container
	{
		this.selectContainer(f);
	}
	*/
	
	
	

}
