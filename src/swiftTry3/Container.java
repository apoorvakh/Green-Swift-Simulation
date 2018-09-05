package swiftTry3;


import java.util.*;

//import GreenSwift_1.UserFile;

public class Container {
	
	private final int totalCapacity=90;
	private final int numNodes=9;
	private int currCapacity=0;
	private int containerID;
	
	private List<StorageNode> listOfStorageNodes = new ArrayList<StorageNode>();
	
	private static int currConID=0;
	
	
	public Container()
	{
		this.setContainerID(++currConID);
		for(int i=0;i<numNodes;i++)
			listOfStorageNodes.add(new StorageNode(this));
	}
	

	public int getNumNodes()
	{
		return numNodes;
	}

	public void putFileInContainer(UserFile f) throws NoSpaceException//called in the main class  ... Forwards the file to the selected Node
	{
		this.selectStorageNode(f);
		f.setContainerNo(this.getContainerID());
	}
	
	public UserFile getFileInContainer(Account acc, String fPathGet) throws NoSpaceException,FileNotFoundException
	{
		try
		{
			for(StorageNode node : this.getListOfStorageNodes())
			{
				UserFile f = node.getFileInNode(acc, fPathGet);	
				if(f!=null)
					return f;
			}
		}
		catch(FileNotFoundException e)
		{
				//throw e;
		}
		//return null;
		return null;
	}
	
	private void selectStorageNode(UserFile f) throws NoSpaceException
	{
		Ring r=new Ring(this,listOfStorageNodes,f);
		StorageNode selectedNode=r.getStorageNode();
		try_file.out.println("Storage Node Selected : "+selectedNode.getNodeID());
		selectedNode.putFileInNode(f);
	}
	
	public boolean delFileInContainer(String fPathDel,Account acc) throws FileNotFoundException
	{
		boolean flagDone=false;
		for(StorageNode node : this.getListOfStorageNodes())
		{
			flagDone = node.delFileInNode(fPathDel,acc);
			if(flagDone)
				break;
		}
		/*
		if(flagDone==false)
			return false;
		else
				return true;
	*/
		return flagDone;
	}
	
	public boolean isAvailable(UserFile f)
	{
		return ( this.currCapacity<this.totalCapacity && (this.totalCapacity-this.currCapacity)>f.getFileSize());
	}
	
	// Getter and Setter methods
	public int getContainerID() {
		return containerID;
	}

	private void setContainerID(int containerID) {
		this.containerID = containerID;
	}

	
	public List<StorageNode> getListOfStorageNodes()
	{
		return listOfStorageNodes;
	}
}

