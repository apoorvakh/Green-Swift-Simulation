package org.greenswiftTry3;

import java.io.*;

public class UserFile {
	
	static int fileCount=0;
	//File f;
	private String fileName;
	private int fileSize;
	private boolean secure;           // public : false ... private : true
	private File f;
	private Account acc;
	
	int containerNo;
	int nodeNo;
	int SSDNo;
	int fileID;
	private int replicaNum=0;
	
	public UserFile(String fName, int fileSize, Account acc, boolean secure) throws ParameterException {
		//super(fileName, fileSize);
		this.fileName=fName;
		try_file.out.println("Cons File Name set :"+this.fileName+" given :"+fName);
		this.fileSize=fileSize;
		this.secure = secure;
		this.f=new File(fileName);
		this.setAcc(acc);
		this.replicaNum=0;

		fileID=++fileCount;
		nodeNo=-1;
		containerNo=-1;
		SSDNo = -1;
		//Log.printLine(fileName);
	}
	public UserFile(UserFile original,int replicaNum)
	{
		this.fileName=original.getFileName();
		this.fileSize=original.getFileSize();
		this.secure=original.secureType();
		this.replicaNum=replicaNum;
		this.setAcc(original.acc);
		nodeNo=-1;
		containerNo=-1;
		SSDNo = -1;
	}
	public boolean secureType()
	{
		return this.secure;
	}
	
	public void setContainerNo(int par)
	{

		this.containerNo=par;
	}
	
	public void setNodeNo(int node)
	{

		this.nodeNo=node;
	}

	public void setSSDID(int node)
	{

		this.SSDNo=node;
	}

	public String getFileName()
	{

		return this.fileName;
	}
	
	public int getFileSize()
	{

		return this.fileSize;
	}
	
	public int getFileID()
	{

		return this.fileID;
	}
	
	public int getNodeNo()
	{

		return this.nodeNo;
	}

	public int getSSDID()
	{

		return this.SSDNo;
	}


	public int getContainerNo()
	{

		return this.containerNo;
	}
	
	public int getReplicaNo()
	{

		return replicaNum;
	}
	public Account getAcc() {

		return acc;
	}
	public void setAcc(Account acc) {

		this.acc = acc;
	}
	
}

