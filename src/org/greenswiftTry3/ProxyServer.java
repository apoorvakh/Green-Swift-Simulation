package org.greenswiftTry3;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProxyServer {
	
	private static int numAccounts=0;
	private static int numContainers=10;
	// one container has one SSD.
	private static int numSSDs=numContainers;

	public static long timeSSDUsed = 0;
	public static long timePNodeUsed = 0;
	public static long timeSNodeUsed = 0;
	public static int ssdPower = 3;   // power consumed per second
	public static int nodePower = 5;   // power consumed per second

	//private List<Container> listOfContainers = new ArrayList<Container>();
	//private int serverID;
	
	private List<Container> listOfContainers= new ArrayList<Container>();
	private List<SSD> listOfSSDs= new ArrayList<SSD>();


	public Timestamp ts1 = new Timestamp(System.nanoTime());  // for putting into SSds or Nodes
	public Timestamp ts2= new Timestamp(System.nanoTime());
	public Timestamp ts3= new Timestamp(System.nanoTime());
	public Timestamp ts4= new Timestamp(System.nanoTime());

	public ProxyServer()
	{
		for(int i=0;i<numContainers;i++)
			listOfContainers.add(new Container());

		for(int i=0;i<numSSDs;i++)
			listOfSSDs.add(new SSD());
	}
	/*public void putFileInSSD(UserFile f)//called in the main class  ... Forwards the file to the selected Node
	{
		listOfSSDs.add(f);
		f.setNodeNo(this.getNodeID());

		try_file.out.println("File Added.");
		try_file.out.println("Old Size : "+currNodeSize);
		currNodeSize+=f.getFileSize();
		try_file.out.println("New Size : "+currNodeSize);
	}*/
	
	public void putFile(Account acc,UserFile f) throws NoSpaceException
	{
		/*** Select one SSD ***/
		ts1.setTime(System.nanoTime());
		long flushTime = this.selectSSD(acc, f);    // We need to pass acc for further steps toflushing out SSD and we nned to select a container and we need acc no.
		ts2.setTime(System.nanoTime());
		long t = Math.abs(ts2.getTime()-ts1.getTime()-flushTime);
		timeSSDUsed += t;
		try_file.out.println("---Time to put in SSD : "+t);

		/*** Select one primary node ***/
		ts1.setTime(System.nanoTime());
		this.selectContainer(acc, f);
		ts2.setTime(System.nanoTime());
		t = Math.abs(ts2.getTime()-ts1.getTime());
		timePNodeUsed += t;
		try_file.out.println("---Time to put in Primary Node : "+t);
	}
	
	private void selectContainer(Account acc, UserFile f) throws NoSpaceException
	{
		/*** Select one primary node ***/

		Ring r = new Ring(acc,getListOfContainers(),f);
		Container selectedContainer = r.getContainer();
		//Container SelectedContainer
		try_file.out.println("Container Selected : "+selectedContainer.getContainerID());

		selectedContainer.putFileInContainer(f);

		//PStorageNode selectedNode = selectedCont.selectStorageNode();
	}
	private long selectSSD(Account acc, UserFile f) throws NoSpaceException
	{
		/*** Select one SSD ***/
		long flushTime=0;

		Ring r = new Ring(getListOfSSDs(),f);
		SSD selectedSSD = r.getSSD();
		if(selectedSSD.isFull()) {

			ts3.setTime(System.nanoTime());
			this.flushFiles(acc, selectedSSD);
			ts4.setTime(System.nanoTime());
			flushTime=Math.abs(ts4.getTime()-ts3.getTime());
			timeSNodeUsed += flushTime;
			try_file.out.println("---Time to flush into in two Secondary Node : "+flushTime);

			selectedSSD.flush();
		}

		try_file.out.println("SSD Selected : "+selectedSSD.getSSDID());


		selectedSSD.putFileInSSD(f);


		return flushTime;
		//if(selectedSSD == null){ throw new NoSpaceException();}

		/*if(!selectedSSD.putFileInSSD(f))
		{
			// First copy all files to two 2dary nodes by using Ring for containers

			this.flushFiles(acc, selectedSSD);

			// Flush the SSD
			selectedSSD.flush();
		}*/

		//PStorageNode selectedNode = selectedCont.selectStorageNode();
	}

	public void flushFiles(Account acc, SSD ssd) throws NoSpaceException
	{
		//called before actual deletion
		List<UserFile> listOfFilesDel=ssd.getListOfFiles();


			/*** Select two 2ndary nodes ***/
		for( UserFile f : listOfFilesDel)
		{
			Ring r = new Ring(acc, getListOfContainers(), f);
			List<Container> selectedContainers = r.getContainerForSec();
			//Container SelectedContainer
			for(Container cont:selectedContainers)
			{
				try_file.out.println("Container Selected : "+cont.getContainerID());
				cont.putFileInContainerForSec(f);
			}
		}

	}


	/*** Getting Files ***/


	public UserFile getFile(Account acc,String fPathGet) throws NoSpaceException,FileNotFoundException
	{
	
		UserFile f=null;
		long t;
		//checking in SSDs first
		ts1.setTime(System.nanoTime());
		for(SSD ssd:this.listOfSSDs)
		{
			f = ssd.getFileInSSD(acc,fPathGet);
			if(f != null)
				return f;
		}
		ts2.setTime(System.nanoTime());
		t = Math.abs(ts2.getTime()-ts1.getTime());
		timeSSDUsed += t;


		if(f==null) {

			/*** Then check in containers ***/

			ts1.setTime(System.nanoTime());
			for (Container con : getListOfContainers()) {
				f = con.getFileInContainer(acc, fPathGet);
				if (f != null)
					return f;
			}
			ts2.setTime(System.nanoTime());
			t = Math.abs(ts2.getTime()-ts1.getTime());
			timePNodeUsed += t;
			throw new FileNotFoundException();
		}
		return null;
	}
	
	public boolean delFile(Account acc,String fPathDel) throws FileNotFoundException {
		boolean flagDone = false;
		boolean foundInSSD = false;
		long t;
		//check if the file is in SSD
		ts1.setTime(System.nanoTime());
		for (SSD ssd : this.listOfSSDs) {
			UserFile gotFile = ssd.getFileInSSD(acc, fPathDel);
			if (gotFile != null) {
				foundInSSD = true;
				ssd.delFile(gotFile);
				break;
			}
		}
		ts2.setTime(System.nanoTime());
		t = Math.abs(ts2.getTime()-ts1.getTime());
		timeSSDUsed += t;
		// called if file is found in the SSD only
		//for(int i=3;i>0;i--)
		//{

			//int i = 1; //only one file to be deleted
		ts1.setTime(System.nanoTime());
			flagDone = false;
			//try_file.out.println("" + "Original in SSD:");
			for (Container con : getListOfContainers()) {
				flagDone = con.delPFileInContainer(fPathDel,acc);
				//try_file.out.println(" flag " + flagDone);
				if (flagDone)
					break;
			}
		ts2.setTime(System.nanoTime());
		t = Math.abs(ts2.getTime()-ts1.getTime());
		timePNodeUsed += t;
			//if (flagDone == false)
				//throw new FileNotFoundException();
			//else {
				//if(i==0)
				//return true;


		 if(foundInSSD==false) {
			// file not in SSD
			boolean flagSDone = false;
			 ts1.setTime(System.nanoTime());
			for (int i = 2; i > 0; i--) {
				flagSDone=false;
				//try_file.out.println("" + i);
				for (Container con : getListOfContainers()) {
					//call delPFileinContainer
					//flagPDone = con.delPFileInContainer(fPathDel);
					flagSDone = con.delSFileInContainer(fPathDel, acc);
					//try_file.out.println(" flag " + flagSDone);
					if (flagSDone)
						break;
				}
				if (!flagSDone)
					throw new FileNotFoundException();
				else {
					if (i == 0)
						return true;
				}
			}
			 ts2.setTime(System.nanoTime());
			 t = Math.abs(ts2.getTime()-ts1.getTime());
			 timeSNodeUsed += t;
		}
		return false;
	}

	public static int getNumContainers()
	{

		return numContainers;
	}
	
	public List<Container> getListOfContainers()
	{
		return listOfContainers;
	}

	public List<SSD> getListOfSSDs()
	{
		return listOfSSDs;
	}

	public static int getNumSSDs(){
		return numSSDs;
	}
	
}

