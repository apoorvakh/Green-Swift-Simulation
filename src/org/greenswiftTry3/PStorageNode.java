package org.greenswiftTry3;


import java.util.ArrayList;
import java.util.List;

/**
 * create two classes, 1' and 2' nodes
 */
public class PStorageNode {

	private int nodeID;
	private static int totCapacity=200;
	private int currNodeSize=0;             // This value changes as files are PUT or DELETE
	
	private boolean stateActive = true; // True : Active ( Primary Node ) , False : Suspended Node
	
	private List<UserFile> listOfFiles= new ArrayList<UserFile>();
	
	private static int currNodeID=0;


	public PStorageNode(Container myCont)
	{
		//this.setNodeID(++currNodeID);  /* Old IDs :: Contiguous numbers irrespective of their containers in the whole datacenter */
		this.setNodeID( Math.abs( (++currNodeID) - myCont.getNumPNodes() * myCont.getContainerID() ));
	}
	
	
	
	public void putFileInNode(UserFile f)//called in the main class  ... Forwards the file to the selected Node
	{
		listOfFiles.add(f);
		f.setNodeNo(this.getNodeID());
		
		try_file.out.println("File Added in Primary Node.");
		try_file.out.println("Old Size : "+currNodeSize);
		currNodeSize+=f.getFileSize();
		try_file.out.println("New Size : "+currNodeSize);
		try_file.out.println("Size Left : " +getAvailableSize());
	}
	
	public UserFile getFileInNode(Account acc, String fPathGet) throws FileNotFoundException//called in the main class  ... Forwards the file to the selected Node
	{
		for(UserFile f : this.getListOfFiles())
		{
			//try_file.out.println("Test File Path : "+f.getFileName()+"\n get Path : "+fPathGet);
			if(f.getFileName().equals(fPathGet)&& f.getAcc()==acc)
			{				
				return f;
			}
		}
		//throw new FileNotFoundException();
		return null;
	}
	
	public boolean delFileInNode(String fPathDel, Account acc)
	{
		for(UserFile f : this.getListOfFiles())
		{
			if(f.getFileName().equals(fPathDel) && f.getAcc()==acc)
			{
				int repNo=f.getReplicaNo();
				listOfFiles.remove(f);
				
				try_file.out.println("File Deleted from primray node.");
/*
				if(repNo==0)
					try_file.out.println("Original Deleted.");
				else
					try_file.out.println("Replica no. "+repNo+" Deleted.");*/
				try_file.out.println("Old Size : "+currNodeSize);
				currNodeSize-=f.getFileSize();
				try_file.out.println("New Size : "+currNodeSize);
				return true;
			}
		}
		return false;
	}
	
	public int getNodeSize()
	{
		return currNodeSize;
	}
	
	public boolean isAvailable(UserFile f)
	{
		return ( this.currNodeSize<=this.totCapacity && getAvailableSize()>=f.getFileSize());
	}
	
	public int getAvailableSize()
	{
		return totCapacity-currNodeSize;
	}

	public int getNodeID() 
	{
		return nodeID;
	}

	private void setNodeID(int nodeID) 
	{
		this.nodeID = nodeID;
	}

	public List<UserFile> getListOfFiles() {
		return listOfFiles;
	}



	public boolean isStateActive() {
		return stateActive;
	}



	public void setStateActive(boolean stateActive) {
		this.stateActive = stateActive;
	}

}


