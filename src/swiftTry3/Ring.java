package swiftTry3;


import java.util.ArrayList;
import java.util.List;

public class Ring {
	
public static int currentContainerNum=0;
	
	private int numPartition;	
	private List<Container> containerList;
	//private UserFile f;
	
	public static int currentNodeNum=0;
	
	private int numStorageNode;	
	private List<StorageNode> nodeList;
	
	private UserFile f;
	
	public Ring(Account acc, List<Container> containerList, UserFile f)  //Account Ring : Selects a Container
	{
		this.numPartition= ProxyServer.getNumContainers();
		this.containerList= containerList;
		this.f=f;
	}
	
	public Ring(Container con, List<StorageNode> nodeList, UserFile f)  // Container Ring : selects a Node
	{
		this.numStorageNode=con.getNumNodes();
		this.nodeList= nodeList;
		this.f=f;
	}
	
	// Account Ring Code : Start
	
	public List<Container> getContainer() throws NoSpaceException
	{
		 return getFinalContainer();
	}

	private List<Container> getFinalContainer() throws NoSpaceException
	{
		List<Container> selected3 = new ArrayList<Container>();
		
		int i=numPartition;							// Storing Original Copy of File
		while(i-->0)
		{
			Container tempCon = containerList.get(currentContainerNum++ % numPartition);
			if(tempCon.isAvailable(f))
			{
				selected3.add(tempCon);
				f.setContainerNo(tempCon.getContainerID());
				break;
			}
			else
				throw new NoSpaceException();
		}
		
		
		UserFile freplica1=new UserFile(f,1);			// Storing Replica 1
		i=numPartition;
		while(i-->0)
		{
			Container tempCon = containerList.get(currentContainerNum++ % numPartition);
			if(tempCon.isAvailable(freplica1))
			{
				selected3.add(tempCon);
				freplica1.setContainerNo(tempCon.getContainerID());
				break;
			}
			else
				throw new NoSpaceException();
		}
		
		
		UserFile freplica2=new UserFile(f,2);			// Storing Replica 2
		i=numPartition;
		while(i-->0)
		{
			Container tempCon = containerList.get(currentContainerNum++ % numPartition);
			if(tempCon.isAvailable(freplica2))
			{
				selected3.add(tempCon);
				freplica2.setContainerNo(tempCon.getContainerID());
				break;
			}
			else
				throw new NoSpaceException();
		}
		return selected3;
    	
		
	}
	
	// Account Ring Code : Stop
	
	
	
	// Container Ring Code : Start
	
	public StorageNode getStorageNode() throws NoSpaceException
	{
		 return getFinalNode();
	}

	private StorageNode getFinalNode() throws NoSpaceException
	{
		int i=numStorageNode;
		while(i-->0)
		{
			//try_file.out.println("111");
			StorageNode tempNode = nodeList.get(currentNodeNum++ % numStorageNode);
			//try_file.out.println("222");
			if(tempNode.isAvailable( f )) /*  pass a file for its size  */
				return tempNode;
			//try_file.out.println("333");
		}
		throw new NoSpaceException();
		
	}
	
	// Container Ring Code : Stop
}
