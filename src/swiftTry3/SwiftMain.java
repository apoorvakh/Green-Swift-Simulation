package swiftTry3;



import java.util.*;
import java.sql.*;
import java.util.Date;

public class SwiftMain {
	
	static Scanner in=new Scanner(System.in);
	
	private static List <Account> listOfAccounts= new ArrayList <Account>();
	
	
	private static ProxyServer myproxyServer = new ProxyServer();
	
	public static void main(String[] args) throws NoSpaceException, ParameterException, FileNotFoundException
	{
		
		///******************** Creating 3 accounts for testing ***********************///
				                                      // Option to create an account will be given

		try_file.out.println(" NORMAL SWIFT ");

		for(int i=0;i<3;i++)
			listOfAccounts.add(new Account());
		
		while(true)
		{
			try_file.out.println("******Account Login******");
			try_file.out.println("Enter the account number(ID) : ");
			
			Account myAccount=listOfAccounts.get(in.nextInt());
			
			try_file.out.println(" Enter Choice : \n\t1. Put\n\t2. Get\n\t3. Delete\n");
			

		}
	}

	public static void operations(Account myAccount, int op, String fPath) throws ParameterException, NoSpaceException, FileNotFoundException {
		switch(op)
		{
			case 1 :

				try_file.out.println("file path is :"+fPath);


				//try_file.out.println("Enter size : ");
				//int fSize=in.nextInt();
				int fSize=10;
				//Stopwatch s = new Stopwatch().start();
				//try_file.out.println("Security level ( public /private ) : Enter true for private : ");
				//boolean fSecure=in.nextBoolean();
				boolean fSecure=false;
				Timestamp ts1 = new Timestamp(System.nanoTime());
				Timestamp ts2 = new Timestamp(System.nanoTime());

				UserFile myFile=new UserFile(fPath,fSize,myAccount,fSecure);

				try {

					ts1.setTime(System.nanoTime());
					try_file.out.println("start time: " + ts1.toString());
					myproxyServer.putFile(myAccount, myFile);
					ts2.setTime(System.nanoTime());
				}
				catch(Exception e)
				{

				}
				try_file.out.println("stop time: "+ts2.toString());
				long diff = Math.abs(ts2.getNanos()-ts1.getNanos());
				try_file.out.println("---Time to put File : "+diff);
				break;

			case 2:



					Timestamp ts3 = new Timestamp(System.nanoTime());
					Timestamp ts4 = new Timestamp(System.nanoTime());

					try{
						ts3.setTime(System.nanoTime());

						UserFile myFileGot = myproxyServer.getFile(myAccount, fPath);
						ts4.setTime(System.nanoTime());
						try_file.out.println("start time: " + ts3.toString());
						try_file.out.println("The file(with path) is : " + myFileGot.getFileName());
						try_file.out.println("stop time: " + ts4.toString());
						long diff2 = Math.abs(ts4.getNanos() - ts3.getNanos());
						try_file.out.println("---Time to get File : " + diff2);

				}
				catch(Exception e)
				{

				}


				break;

			case 3:
				//try_file.out.println("Under Development !!! ");

				Timestamp ts5 = new Timestamp(System.nanoTime());
				Timestamp ts6 = new Timestamp(System.nanoTime());

				try {
					ts5.setTime(System.nanoTime());
					try_file.out.println("start time: " + ts5.toString());
					boolean allDeleted = myproxyServer.delFile(myAccount, fPath);
					ts6.setTime(System.nanoTime());
					try_file.out.println("stop time: " + ts6.toString());
					long diff3 = Math.abs(ts6.getNanos() - ts5.getNanos());
					try_file.out.println("---Time to delete File : " + diff3);
				}
				catch(Exception e)
				{

				}
				break;


			default:
				try_file.out.println(" Wrong choice ! Try again");

		}
	}


	public static double nodePower()
	{
		return ProxyServer.nodePower;  // power per sec = 3
	}


}

