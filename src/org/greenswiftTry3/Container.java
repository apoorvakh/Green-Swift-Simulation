package org.greenswiftTry3;

import java.util.*;

//import GreenSwift_1.UserFile;

public class Container {
	
	private final int totalCapacity=900;
	//private final int numNodes=10;
	private final int numPNodes = 30;
	private final int numSNodes = 60;
	//private final int numSSDs = 1;
	private int currCapacity=0;
	private int containerID;
	
	private List<PStorageNode> listOfPStorageNodes = new ArrayList<PStorageNode>();
	private List<SStorageNode> listOfSStorageNodes = new ArrayList<SStorageNode>();
	
	private static int currConID=0;


	//
	public Container()
	{
		this.setContainerID(++currConID);
		for(int i=0;i<numPNodes;i++)
			listOfPStorageNodes.add(new PStorageNode(this));
		for(int i = 0;i<numSNodes;i++)
			listOfSStorageNodes.add(new SStorageNode((this)));
	}

	public void putFileInContainer(UserFile f) throws NoSpaceException//called in the main class  ... Forwards the file to the selected Node
	{
		this.selectPStorageNode(f);
		f.setContainerNo(this.getContainerID());
	}

	public void putFileInContainerForSec(UserFile f) throws NoSpaceException {
		this.selectSStorageNode(f);
		f.setContainerNo(this.getContainerID());
	}
	//called to check for files in Primary Storage Nodes
	public UserFile getFileInContainer(Account acc,String fPathGet) throws NoSpaceException,FileNotFoundException
	{
		try
		{
			for(PStorageNode node : this.getListOfPStorageNodes())
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
	
	private void selectPStorageNode(UserFile f) throws NoSpaceException
	{
		Ring r=new Ring(this,listOfPStorageNodes,f);
		PStorageNode selectedPNode=r.getPStorageNode();
		try_file.out.println("Storage Node Selected : "+selectedPNode.getNodeID());
		selectedPNode.putFileInNode(f);
	}

	private void selectSStorageNode(UserFile f) throws NoSpaceException
	{
		Ring r=new Ring(this,listOfSStorageNodes,f);
		SStorageNode selectedSNode = r.getSStorageNode();
		try_file.out.println("Storage Node Selected : "+selectedSNode.getNodeID());
		selectedSNode.putFileInNode(f);
	}
	
	// changed to PStorageNodes, this is called only when a file is found in SSD
	public boolean delPFileInContainer(String fPathDel, Account acc) throws FileNotFoundException
	{
		boolean flagDone=false;
		for(PStorageNode node : this.getListOfPStorageNodes())
		{
			flagDone = node.delFileInNode(fPathDel, acc);
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
	public boolean delSFileInContainer(String fPathDel, Account acc) throws FileNotFoundException
	{
		boolean flagDone=false;
		for(SStorageNode node : this.getListOfSStorageNodes())
		{
			flagDone = node.delFileInNode(fPathDel, acc);
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

	
	public List<PStorageNode> getListOfPStorageNodes()
	{
		return listOfPStorageNodes;
	}
	public List<SStorageNode> getListOfSStorageNodes()
	{
		return listOfSStorageNodes;
	}



	public int getNumPNodes(){
		return numPNodes;
	}
	public int getNumSNodes(){
		return numSNodes;
	}

}

