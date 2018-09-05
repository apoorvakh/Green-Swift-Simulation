package org.greenswiftTry3;

import java.util.*;
import java.text.*;
import java.sql.Timestamp;

//getTime()
//setTime()


public class SwiftMain {
	
	static Scanner in=new Scanner(System.in);
	
	private static List<Account> listOfAccounts= new ArrayList<Account>();
	
	
	private static ProxyServer myproxyServer = new ProxyServer();

	public static Timestamp ts1 = new Timestamp(System.nanoTime());  // total outer
	public static Timestamp ts2= new Timestamp(System.nanoTime());
	
	public static void main(String[] args) throws NoSpaceException, ParameterException, FileNotFoundException
	{
		
		///******************** Creating 3 accounts for testing ***********************///
				                                      // Option to create an account will be given

		try_file.out.println(" GREEN SWIFT ");

		for(int i=0;i<3;i++)
			listOfAccounts.add(new Account());
		
		while(true)
		{

			try_file.out.println("******Account Login******");
			try_file.out.println("Enter the account number(ID) : ");
			
			Account myAccount=listOfAccounts.get(in.nextInt());
			try_file.out.println(" Enter Choice : \n\t1. Put\n\t2. Get\n\t3. Delete\n");
			int op = in.nextInt();
			try_file.out.println("Enter file path : ");
			in.nextLine();
			String fPathPut=in.nextLine();
			operations(myAccount, op, fPathPut);

		}
		
	}

	public static void operations(Account myAccount, int op, String fPath) throws ParameterException {

		switch(op)
		{
			case 1 :


				try_file.out.println("file path is :"+fPath);
				//try_file.out.println("Enter size : ");
				//int fSize=in.nextInt();
				int fSize=10;
				//try_file.out.println("Security level ( public /private ) : Enter true for private : ");
				//boolean fSecure=in.nextBoolean();
				boolean fSecure = false;
				/** Time Stamp insertion **/


				UserFile myFile=new UserFile(fPath,fSize,myAccount,fSecure);
				try
				{
					ts1.setTime(System.nanoTime());
					String a=ts1.toString();
					try_file.out.println("Start Time : "+a);

					myproxyServer.putFile(myAccount, myFile);

					ts2.setTime(System.nanoTime());
				}
				catch(Exception e)
				{}

				String b = ts2.toString();
				try_file.out.println("Stop Time : "+b);

				long diff = ts2.getTime() - ts1.getTime();
				try_file.out.println("---Time to put File : "+Math.abs(diff));

				break;

			case 2:

				try
				{
					ts1.setTime(System.nanoTime());
					//StopWatch stopwatch = new StopWatch().start();
					UserFile myFileGot = myproxyServer.getFile(myAccount, fPath);
					//try_file.out.println(stopwatch.stop());
					ts2.setTime(System.nanoTime());


					try_file.out.println("The file(with path) is : "+myFileGot.getFileName());
					try_file.out.println("---Time to get File : "+Math.abs(ts2.getNanos()-ts1.getNanos()));
				}
				catch(Exception e)
				{
					//try_file.out.println("lalala"+e.getMessage());
					//throw e;
				}
					/*
					for(Container con : myAccount.getListOfContainers())
					{
						for(PStorageNode node : con.getListOfStorageNodes())
						{
							for(UserFile f : node.getListOfFiles())
							{
								if(f.getFileName()==fPathGet)

							}
						}
					}
					*/

				break;

			case 3:
				//try_file.out.println("Under Development !!! ");


				try {
					ts1.setTime(System.nanoTime());
					boolean allDeleted = myproxyServer.delFile(myAccount, fPath);
					ts2.setTime(System.nanoTime());

					try_file.out.println("---Time to delete File : "+Math.abs(ts2.getTime()-ts1.getTime()));
				}
				catch(Exception e)
				{}
				break;


			default:
				try_file.out.println(" Wrong choice ! Try again");

		}
	}

	public static double SSDPower()
	{
		return ProxyServer.ssdPower;  // power per sec = 3
	}

	public static double PnodePower()
	{
		return ProxyServer.nodePower;  // power per sec = 3
	}

	public static double SnodePowerConsumption()
	{
		return ProxyServer.nodePower*ProxyServer.timeSNodeUsed;  // power per sec = 3
	}
	
}

