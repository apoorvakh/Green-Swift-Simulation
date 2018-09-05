package swiftTry3;



import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProxyServer {
	
	private static int numAccounts=0;
	private static int numContainers=10;
	
	//private List<Container> listOfContainers = new ArrayList<Container>();

	public static long timeNodeUsed = 0;
	public static int nodePower = 5;

	private int serverID;
	
	private List< Container> listOfContainers= new ArrayList< Container>();

	public Timestamp ts1 = new Timestamp(System.nanoTime());  // for putting into Nodes
	public Timestamp ts2= new Timestamp(System.nanoTime());

	//public static int ssdPower = 3;   // power consumed per second


	public ProxyServer()
	{
		for(int i=0;i<numContainers;i++)
			listOfContainers.add(new  Container());
	}
	
	
	public void putFile( Account acc, UserFile f) throws  NoSpaceException
	{

		/*** Select one SSD ***/
		

		/*** Select one primary node ***/
		ts1.setTime(System.nanoTime());
		this.selectContainer(acc,f);
		ts2.setTime(System.nanoTime());
		long t = Math.abs(ts2.getTime()-ts1.getTime());
		timeNodeUsed += t;
		try_file.out.println("---Time to put in Primary Node : "+t);
	}
	
	private void selectContainer( Account acc, UserFile f) throws  NoSpaceException
	{
		/*** Select one SSD ***/
		
		
		/*** Select one primary node ***/
		
		Ring r = new Ring(acc,getListOfContainers(),f);
		List< Container> selected3Containers = r.getContainer();
		
		for( Container cont:selected3Containers)
		{
			try_file.out.println("Container Selected : "+cont.getContainerID());
			cont.putFileInContainer(f);
		}	
		//StorageNode selectedNode = selectedCont.selectStorageNode();
	}
	
	public UserFile getFile( Account acc, String fPathGet) throws  NoSpaceException,  FileNotFoundException
	{
	
		UserFile f;
		
		
		/**** FIrst check in all SSDs ****/
		
		
		
		/*** Then check in containers ***/
		ts1.setTime(System.nanoTime());
		for( Container con : getListOfContainers())
		{
			f=con.getFileInContainer(acc,fPathGet);	
			if(f!=null)
				return f;
		}
		ts2.setTime(System.nanoTime());
		long t = Math.abs(ts2.getTime()-ts1.getTime());
		timeNodeUsed += t;
		throw new FileNotFoundException();
		//return null;
	}
	
	public boolean delFile(Account acc, String fPathDel) throws  FileNotFoundException
	{
		boolean flagDone=false;

		ts1.setTime(System.nanoTime());
		for(int i=3;i>0;i--)
		{
			flagDone=false;
			try_file.out.println(""+i);
			for( Container con : getListOfContainers())
			{
				flagDone= con.delFileInContainer(fPathDel,acc);
				//try_file.out.println(" flag "+flagDone);
				if(flagDone)
					break;
			}
			if(flagDone==false)
				throw new FileNotFoundException();
			else
			{
				if(i==0)
					return true;
			}
			
		}
		ts2.setTime(System.nanoTime());
		long t = Math.abs(ts2.getTime()-ts1.getTime());
		timeNodeUsed += t;
		
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
	
}

