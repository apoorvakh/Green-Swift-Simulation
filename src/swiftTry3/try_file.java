package swiftTry3;

import java.io.*;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class try_file {


    private static List<String> fileList = new ArrayList<String>();
    public static PrintWriter out;

    public static FileReader fr ;
    public static Timestamp ts1 = new Timestamp(System.nanoTime());  // total outer
    public static Timestamp ts2= new Timestamp(System.nanoTime());
    static String line = null;
    static List <Integer> r_no = new ArrayList<Integer>();
    public static void main(String[] args) throws IOException, ParameterException, NoSpaceException, swiftTry3.FileNotFoundException {

        try {
            out = new PrintWriter("C:\\Users\\Apoorva\\Desktop\\LogSwift.txt", "UTF-8");
            fr = new FileReader("C:\\Users\\Apoorva\\Desktop\\rno.txt");
            BufferedReader bufferedReader = new BufferedReader(fr);
            while((line = bufferedReader.readLine()) != null) {
                r_no.add(Integer.parseInt(line));
            }

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        Files.walk(Paths.get("F:\\ABCD\\PESIT\\Year3\\Cloud\\Sem5\\swift-opensim\\src\\workload\\planetlab")).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                //try_file.out.println(filePath+" "+(filePath.toFile()).length()/50);
                fileList.add(filePath+"");
            }
        });
        /**Random rn = new Random();
        int rNo=1;*/
        Account acc=new Account();
        out.println("Swift Simulation");
        System.out.println("Swift Simulation");


        ts1.setTime(System.nanoTime());
        for(int i=0,j=0; i<fileList.size()&&j<r_no.size() ; i++,j++)
        {
            //rNo = rn.nextInt(3 - 1 + 1) + 1 ;
            System.out.println(""+r_no.get(j));
            SwiftMain.operations(acc, r_no.get(j), fileList.get(i));
        }
        /*for(int i=0 ; i<fileList.size() ; i++)
        {
            rNo = rn.nextInt(3 - 1 + 1) + 1 ;
            rNo = 2;
            SwiftMain.operations(acc, rNo, fileList.get(i));
        }*/
        ts2.setTime(System.nanoTime());

        long timeTaken = Math.abs(ts2.getTime()-ts1.getTime());


        //out.println(" Power Consumed by SSDs : "+ swiftTry3.SwiftMain.SSDPowerConsumption());
        out.println(" Total Power Consumed by Disks(Nodes) :: "+swiftTry3.SwiftMain.nodePower()*timeTaken*3);
        System.out.println(" Total Power Consumed by Disks(Nodes) :: "+swiftTry3.SwiftMain.nodePower()*timeTaken*3);

        out.close();
    }
}
