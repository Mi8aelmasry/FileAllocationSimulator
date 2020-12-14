/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileAllocationSimulator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Eng
 */
public class FileAllocationSimulator 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException 
    {
        ArrayList<MyFile> Files = new ArrayList<MyFile>();
        ArrayList<Integer> Disks =new ArrayList<>();
        InFile<MyFile> WriteF = new InFile<>();
        OutFile<MyFile> ReadF = new OutFile<>();
        InFile<Integer> WriteD = new InFile<>();
        OutFile<Integer> ReadD = new OutFile<>();
        AllocationMethods Main = new AllocationMethods(0);
        Scanner input =new Scanner(System.in);
        int choice;
        int DB ;
        int NOF ;
        System.out.println("______________WELCOME TO FILE ALLOCATION SIMULATOR______________");
        System.out.println("______________________CONTIGUOUS ALLOCATION_____________________");
        System.out.println("____Please chooose the method of passing data to the program____");
        System.out.println("1.Files");
        System.out.println("2.User_Input");
        choice=input.nextInt();
        while ( choice>2 || choice<1)    
        {
            System.out.println("wrong input please enter 1 or 2");
            choice=input.nextInt();
        }
        if(choice==1)
        {
            Files=ReadF.Read("Myfiles.bin");
            Disks=ReadD.Read("Diskblocks.bin");
            System.out.println("Available Disk blocks");
            for (int i=0; i<Disks.size();i++)
            {
                System.out.println((i+1)+" "+Disks.get(i));
            }
            System.out.println("Choose one of these Disk blocks :");
            int c=input.nextInt();
            while ( c>Disks.size() || c<1)    
            {
                System.out.println("wrong input please enter 1 or "+Disks.size());
                c=input.nextInt();
            }
            DB=Disks.get(c-1);
            Main =new AllocationMethods(DB);
            Main.setFiles(Files);
            Main.Run();
            Files=Main.getFiles();
        }
        else
        {
            System.out.print("Please enter the number of disk blocks :");
            while (!input.hasNextInt()) 
            {
                System.out.println("That's not a number!");
                input.next(); // this is important!
                System.out.print("Please enter a valid number for disk blocks :");
            }
            DB=input.nextInt();
            Disks.add(DB);
            WriteD.Write(Disks,"Diskblocks.bin");
            Main =new AllocationMethods(DB);
            System.out.print("Please enter the number of files :");
            while (!input.hasNextInt()) 
            {
                System.out.println("That's not a number!");
                input.next(); // this is important!
                System.out.print("Please enter a valid number for number of files :");
            }
            NOF = input.nextInt();
            System.out.println("Please enter the data for each file");
            for(int i = 0; i<NOF ;i++)
            {
                MyFile F = new MyFile();
                System.out.print("File("+(i+1)+") name :");
                F.setFilename( input.next());
                System.out.print("File("+(i+1)+") length :");
                while (!input.hasNextInt()) 
                {
                    System.out.println("That's not a number!");
                    input.next(); // this is important!
                    System.out.print("Please enter a valid number for file length :");
                }
                F.setFilesize(input.nextInt());
                Files.add(F);
            }
            WriteF.Write(Files, "Myfiles.bin");
            Main.setFiles(Files);
            Main.Run();
            Files=Main.getFiles();
        }
        System.out.println("______________________Directory______________________");
            System.out.println("File name \t  Starting block \t  Ending block  \t  File size");
            for(int i=0 ; i< Files.size() ; i++ )
            {
                if(Files.get(i).isAllocated())
                {
                        System.out.println(Files.get(i).getFilename()+"\t \t \t"
                        +Files.get(i).getStartingblock()+"\t \t \t"
                        +Files.get(i).getEndingblock()+"\t \t \t"
                        +Files.get(i).getFilesize());
                }
                else
                {
                        System.out.println( Files.get(i).getFilename()+"\t \t \t not allocated");
                }
            }
            System.out.println("______________________Mapping______________________");
            System.out.println("File Name \t File block number \t corresponding disk block");
            for (MyFile File : Files) 
            {
                for(int j=0, i=File.getStartingblock();i<=File.getEndingblock();j++, i++)
                {
                    System.out.println(File.getFilename()+"\t\t\t"+j+"\t\t\t"+i);
                }
            }
    }   
}