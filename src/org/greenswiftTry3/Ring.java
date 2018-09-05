package org.greenswiftTry3;


import java.util.ArrayList;
import java.util.List;

public class Ring {
	
	public static int currentContainerNum=0;
	public static int currentSSD = 0;
	
	private int numContainers;
	private int numSSDs;
	private List<Container> containerList;
	//private UserFile f;
	//private List<SSD> listOfSSDs;

	public static int currentPNodeNum=0;
	public static int currentSNodeNum=0;
	
	private int numSStorageNode;
	private int numPStorageNode;
	private List<PStorageNode> pnodeList;
	private List<SStorageNode> snodeList;
	private List<SSD> SSDList;
	
	private UserFile f;

	public Ring(Account acc, List<Container> containerList,UserFile f)  //Account Ring : Selects a Container
	{
		this.numContainers=ProxyServer.getNumContainers();
		this.containerList= containerList;
		this.f=f;
	}

	public Ring(Container con, List<?>nodeList, UserFile f)  // Container Ring : selects a primary Node
	{
		if (nodeList.get(0) instanceof PStorageNode)
		{
			this.numPStorageNode=con.getNumPNodes();
			this.pnodeList = (List< PStorageNode>)nodeList;
			this.f=f;
		}
		else if (nodeList.get(0) instanceof SStorageNode)
		{
			this.numSStorageNode=con.getNumSNodes();
			this.snodeList = (List<SStorageNode>)nodeList;
			this.f=f;
		}

	}

	// SSD ring
	public Ring(List<SSD> listOfSSDs, UserFile f)  // Container Ring : selects a Node
	{
		this.numSSDs=ProxyServer.getNumSSDs();
		this.SSDList = listOfSSDs;
		this.f=f;
	}
	
	// Account Ring Code : Start
	
	public Container getContainer() throws  NoSpaceException
	{
		 return getFinalContainer();
	}

	private Container getFinalContainer() throws  NoSpaceException
	{
		//List<Container> selected3 = new ArrayList<Container>();
		Container selected = null;
		int i=numContainers;							// Storing Original Copy of File
		while(i-->0)
		{
			Container tempCon = containerList.get(currentContainerNum++ % numContainers);
			if(tempCon.isAvailable(f)) ///checking the available size
			{

				selected=tempCon;
				f.setContainerNo(tempCon.getContainerID());
				break;
			}
			//else
				//throw new NoSpaceException();
		}

		if (selected==null) throw new NoSpaceException();


		return selected;
		
		/***/
    	
		
	}

	public List<Container> getContainerForSec() throws NoSpaceException   // Ring sends 2 2dary nodes
	{
		List<Container> selected2 = new ArrayList<Container>();
		Container selected = null;
		int i;


		UserFile freplica1=new UserFile(f,1);			// Storing Replica 1
		i=numContainers;
		while(i-->0)
		{
			Container tempCon = containerList.get(currentContainerNum++ % numContainers);
			if(tempCon.isAvailable(freplica1))
			{
				selected2.add(tempCon);
				freplica1.setContainerNo(tempCon.getContainerID());
				//also Set SSD
				break;
			}
			//else
				//throw new NoSpaceException();
		}
		if(selected2.size()==0) throw new NoSpaceException();

		UserFile freplica2=new UserFile(f,2);			// Storing Replica 2
		i=numContainers;
		while(i-->0)
		{
			Container tempCon = containerList.get(currentContainerNum++ % numContainers);
			if(tempCon.isAvailable(freplica2))
			{
				selected2.add(tempCon);
				freplica2.setContainerNo(tempCon.getContainerID());
				break;
			}
			//else
				//throw new NoSpaceException();
		}
		if(selected2.size()==0) throw new NoSpaceException();
		return selected2;
	}


	// Account Ring Code : Stop

	// method to select SSD
	public SSD getSSD() throws  NoSpaceException
	{
		return getFinalSSD();
	}

	private SSD getFinalSSD() throws  NoSpaceException {
		//List<Container> selected3 = new ArrayList<Container>();
		SSD selected = null;
		int i = numSSDs;                            // Storing Original Copy of File
		while (i-- > 0) {
			//try_file.out.println("i in SSD : "+i);
			//try_file.out.println(" geting  :  "+SSDList.size());

			SSD tempSSD = SSDList.get(currentSSD++ % numSSDs);

			//try_file.out.println("i in SSD : "+tempSSD.getSSDID()+"   "+currentSSD++ % numSSDs);
			if (tempSSD.isAvailable(f)) ///checking the available size
			{
				try_file.out.println(" Selecteing ssd");
				selected = tempSSD;
				f.setSSDID(tempSSD.getSSDID());
				break;
			}
			else {

			}
		}
		if(selected==null) throw new NoSpaceException();
		return selected;

	}




	
	
	// Container Ring Code : Start
	
	public PStorageNode getPStorageNode() throws NoSpaceException
	{
		 return getFinalPNode();
	}

	private PStorageNode getFinalPNode() throws NoSpaceException
	{
		int i=numPStorageNode;
		while(i-->0)
		{
			//try_file.out.println("111");
			PStorageNode tempNode = pnodeList.get(currentPNodeNum++ % numPStorageNode);
			//try_file.out.println("222");
			if(tempNode.isAvailable( f )) /*  pass a file for its size  */
				return tempNode;
			//try_file.out.println("333");
		}
		throw new NoSpaceException();
		
	}

	public SStorageNode getSStorageNode() throws NoSpaceException
	{
		return getFinalSNode();
	}

	private SStorageNode getFinalSNode() throws NoSpaceException
	{
		int i=numSStorageNode;
		while(i-->0)
		{
			//try_file.out.println("111");
			SStorageNode tempNode = snodeList.get(currentSNodeNum++ % numSStorageNode);
			//try_file.out.println("222");
			if(tempNode.isAvailable( f )) /*  pass a file for its size  */
				return tempNode;
			//try_file.out.println("333");
		}
		throw new NoSpaceException();

	}
	
	// Container Ring Code : Stop
}
