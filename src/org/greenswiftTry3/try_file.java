package org.greenswiftTry3;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by aarti on 11/12/2015.
 */
public class try_file {


    private static List<String> fileList = new ArrayList<String>();
    public static PrintWriter out;

    public static Timestamp ts1 = new Timestamp(System.nanoTime());  // total outer
    public static Timestamp ts2= new Timestamp(System.nanoTime());

    public static PrintWriter randomFile ;

    public static void main(String[] args) throws IOException, ParameterException {

        try {
            randomFile = new PrintWriter("C:\\Users\\Apoorva\\Desktop\\rno.txt");
            out = new PrintWriter("C:\\Users\\Apoorva\\Desktop\\LogGreenSwift.txt", "UTF-8");
        } catch (java.io.FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        Files.walk(Paths.get("F:\\ABCD\\PESIT\\Year3\\Cloud\\Sem5\\swift-opensim\\src\\workload\\planetlab")).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                //System.out.println(filePath+" "+(filePath.toFile()).length()/50);
                fileList.add(filePath+"");
            }
        });
        Random rn = new Random();
        int rNo=1;
        Account acc=new Account();
        out.println("Green Swift Simulation");
        System.out.println("Green Swift Simulation");


        ts1.setTime(System.nanoTime());
        for(int i=0 ; i<fileList.size() ; i++)
        {
            rNo = rn.nextInt(3 - 1 + 1) + 1 ;
            randomFile.println(rNo);
            SwiftMain.operations(acc, rNo, fileList.get(i));
        }
        /*for(int i=0 ; i<fileList.size() ; i++)
        {
            rNo = rn.nextInt(3 - 1 + 1) + 1 ;
            randomFile.println(rNo);
            SwiftMain.operations(acc, rNo, fileList.get(i));
        }*/
        ts2.setTime(System.nanoTime());

        long timeTaken = Math.abs(ts2.getTime()-ts1.getTime());
        long tt = (ts2.getTime()-ts1.getTime());
        double ssdp = SwiftMain.SSDPower();  // Power of an SSD per second
        //System.out.println("Time taken     "+timeTaken + "  *****   "+tt);
        out.println(" Power Consumed by SSDs : "+ssdp*timeTaken);
        //System.out.println(" Power Consumed by SSDs : "+ssdp*timeTaken);
        double pnp=SwiftMain.PnodePower();   // Power of an Primary node per second
        out.println(" Power Consumed by Primary Disks(Nodes) :: "+pnp*timeTaken);
        //System.out.println(" Power Consumed by Primary Disks(Nodes) :: "+pnp*timeTaken);
        double sntp=SwiftMain.SnodePowerConsumption();      // Total Power consumed by Secondary nodes during their usage
        out.println(" Power Consumed by Secondary Disks(Nodes) during their usage:: "+sntp);
        //System.out.println(" Power Consumed by Secondary Disks(Nodes) during their usage:: "+sntp);

        System.out.println(" Total power consumed : " + ((ssdp+pnp)*timeTaken+sntp));
        out.println(" Total power consumed : " + ((ssdp+pnp)*timeTaken+sntp));

        out.close();
        randomFile.close();
    }
}
