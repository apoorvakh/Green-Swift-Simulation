package org.greenswiftTry3;

import java.util.ArrayList;
import java.util.List;

public class SSD {

    private final double totCapacity = 120;
    private double currSize = 0;
    private int SSDID;
    private static int currSSDID = 0;

    private List<UserFile> listOfFiles = new ArrayList<UserFile>();

    public SSD() {
        this.setSSDID(Math.abs((++currSSDID)));//- ProxyServer.getNumSSDs() * myCont.getContainerID() ))
    }

    public double getCurrSize() {

        return currSize;
    }

    public void setCurrSize(double currSize) {

        this.currSize = currSize;
    }

    public double getTotCapacity() {
        return totCapacity;
    }

    public boolean isAvailable(UserFile f) { // Size is available OR it is full but to be flushed(it'll become empty)

        return (this.totCapacity - this.currSize) >= f.getFileSize() || isFull();
    }

    public boolean isFull()
    {
        return getTotCapacity()==getCurrSize();
    }

    public int getSSDID() {
        return this.SSDID;
    }

    public void setSSDID(int i) {
        this.SSDID = i;
    }

    public boolean putFileInSSD(UserFile f) {

        if(!isAvailable( f) )   return false;
        if(isAvailable( f) ) {
            listOfFiles.add(f);
            f.setSSDID(this.getSSDID());

            try_file.out.println("File Added in SSD.");
            try_file.out.println("Old Size : " + getCurrSize());
            setCurrSize(getCurrSize() + f.getFileSize());
            try_file.out.println("New Size : " + getCurrSize());
            try_file.out.println("Size Left : " + (getTotCapacity()-getCurrSize()));
        }
        return true;
    }

    public List<UserFile> getListOfFiles()
    {
        return this.listOfFiles;
    }


    // added morning
    public UserFile getFileInSSD(Account acc,String fPathGet)
    {
            //excpetion need not be thrown here since it is an SSD
            for (UserFile f : this.getListOfFiles())
            {
                //try_file.out.println("Test File Path : "+f.getFileName()+"\n get Path : "+fPathGet);
                if (f.getFileName().equals(fPathGet) && f.getAcc() == acc) {
                    return f;
                }
            }
        return null;
    }
    public void delFile(UserFile file2Delete)
    {
        try_file.out.println("File deleted from SSD");
        this.getListOfFiles().remove(file2Delete);
        this.setCurrSize(getCurrSize() - file2Delete.getFileSize());
    }
    /**
     * selection of the SSD happens using ProxyServer.
     */

    //flush is called currSize == 0
    //done in the morning
    public void flush()
    {
        // emptying the SSD

        this.listOfFiles.clear();
        try_file.out.println(" SSD Flushed. ");
        setCurrSize(0);
    }


}


