import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.io.*;
class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	private int custId;
	private String name;
	private String age;
	private String from;
	private String to;
		public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}

	Person() {
	};

	Person(int custId,String name, String age, String from,String to) {
		this.custId=custId;
		this.name = name;
		this.age = age;
		this.from = from;
		this.to=to;
	}

	@Override
	public String toString() {
		return "\ncustId"+custId+"\nName:" + name + "\nAge: " + age + "\nFrom: " + from+"\nto:"+to;
	}
}
public class Reserve {
	public static Scanner scan=new Scanner(System.in);
	public static int id=0;
	public static ArrayList<Person> personList=new ArrayList<Person>();
	@SuppressWarnings("unchecked")
	static ArrayList<Person> ReadFromFile(String key)
	{
		 ArrayList<Person> per=new ArrayList<>();

		try	
		{
			FileInputStream fis=new FileInputStream(key+".txt");
			ObjectInputStream ois=new ObjectInputStream(fis);
			per=(ArrayList<Person>)ois.readObject();
			ois.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return per;
	}
	static void WriteToFile(ArrayList<Person> per,String key)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(key+".txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);	
			oos.writeObject(per);
			oos.flush();
			oos.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
	static int readFileCount()
	{
		int fileCount=-1;
		try{
			File file=new File("fileCounter.txt");
			Scanner scan=new Scanner(file);
			if(scan.hasNextInt()){
				fileCount=scan.nextInt();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return fileCount;
	}
	static void writeFileCount(int fileCount)
	{ 
		File file=new File("fileCounter.txt");
		try{    
           FileWriter fw=new FileWriter(file);    
           fw.write(String.valueOf(fileCount));    
           fw.close();    
          }catch(Exception e){System.out.println(e);}    
			
     }  
		
	static int readCustId()
	{
		int custId=-1;
		try{
			File file=new File("custId.txt");
			Scanner scan=new Scanner(file);
			if(scan.hasNextInt()){
				custId=scan.nextInt();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return custId;
	}
	static void writeCustID(int custId)
	{ 
		File file=new File("custId.txt");
		try{    
           FileWriter fw=new FileWriter(file);    
           fw.write(String.valueOf(custId));    
           fw.close();    
          }catch(Exception e){System.out.println(e);}    
			
     }	  
	static void deleteFile(String key)
	{
		try
        {
            Files.deleteIfExists(Paths.get(key+".txt"));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists");
        }
        catch(DirectoryNotEmptyException e)
        {
            System.out.println("Directory is not empty.");
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions.");
        }
		System.out.println("Deletion successful.");
	}
	static boolean isFileExist(String key)
	{
		File tmpDir = new File(key+".txt");
		boolean exists = tmpDir.exists();
		return exists;
	}
	public static void main(String[] args) {
		boolean quit=false;
		int ch,max=4,fileCount=0,recordCount=0;
		String key;
		System.out.println("Welcome to Red Bus ");
		fileCount=readFileCount();
		if(fileCount>=0)
		{
			key="file"+fileCount;
			personList=ReadFromFile(key);
			recordCount=personList.size();
			if(recordCount>0)
				id=personList.get(recordCount-1).getCustId()+1;
			else
			{
				if((fileCount-1)>=0)
				{						
					String t="file"+(fileCount-1);
					ArrayList<Person> p=ReadFromFile(t);
					recordCount=personList.size();
					if(recordCount>0)
					{
						id=personList.get(recordCount-1).getCustId();
					}
				}
			}
		}
		else
		{
			fileCount=0;
			key="file"+fileCount;
		}
		
		while(!quit)
		{
		
		System.out.println("\n\n1.to book");
		System.out.println("2.to show");
		System.out.println("3.to update");
		System.out.println("4.to cancel");
		System.out.println("5.to exit");
		
		ch=scan.nextInt();
		scan.nextLine();		
		switch(ch)
		{
				
				case 1:
					
					id=readCustId();
					String name,from,to,age;
					System.out.println("Enter the passenger name ");
					name=scan.nextLine();
					System.out.println("Enter the Age ");
					age=scan.nextLine();
					System.out.println("Enter the from");
					from=scan.nextLine();
					System.out.println("Enter the to ");
					to=scan.nextLine();
		
					Person p1 = new Person(id,name, age, from,to);
					//System.out.println("Filecount:"+fileCount+" recordCount:"+recordCount);
					
					id++;
					if(recordCount==max)
					{
						++fileCount;
						System.out.println("Filecount increased :"+fileCount);
						key="file"+fileCount;
						recordCount=0;
						personList=new ArrayList<Person>();
						
					}
					personList.add(p1);
					recordCount++;
					WriteToFile(personList,key);
					writeFileCount(fileCount);
					writeCustID(id);
					break;
				case 2:
					System.out.println("Printing all Files");
					int c=readFileCount();
					ArrayList<Person> temp=new ArrayList<Person>();
					if(c!=-1)
					{							
						for(int j=0;j<=c;j++)
						{
							key="file"+j;
							if(isFileExist(key))
							{								
														
							System.out.println("Printing file : "+j);
							key="file"+j;
							temp=ReadFromFile(key);
							System.out.println("CustId\tName\tAge\tFrom\tTo");
							for(int i=0;i<temp.size();i++)
							{
									System.out.println(temp.get(i).getCustId()+"\t"+temp.get(i).getName()+"\t"+temp.get(i).getAge()+"\t"+temp.get(i).getFrom()+"\t"+temp.get(i).getTo());
									
							}
							}
						}
						
					}
					break;
				case 3:
					System.out.println("Enter the customer id");
					int custId=scan.nextInt(),flag=0;
					scan.nextLine();
					String update;
					int fileLoc=(custId)/max;
					String fileKey="file"+fileLoc;
					System.out.println("fileLoc : "+fileLoc+" fileCount "+readFileCount());
					
					if(fileLoc<=readFileCount() && fileLoc>=0 && isFileExist(fileKey))
					{
						personList=ReadFromFile(fileKey);
						for(int i=0;i<personList.size();i++)
						{
							if(personList.get(i).getCustId()==custId)
							{
								flag=1;
								boolean ex=false;
								while(!ex)
								{										
									System.out.println("Enter Your Choice");
									System.out.println("1.to update name");
									System.out.println("2.to update age");
									System.out.println("3.to update from");
									System.out.println("4.to update to");
									System.out.println("5.to exit");
									ch=scan.nextInt();
									scan.nextLine();
									switch(ch)
									{
										case 1:
											System.out.println("Enter New name");
											String newName=scan.nextLine();
											personList.get(i).setName(newName);
											break;
										case 2:
											System.out.println("Enter New age");
											String nage=scan.nextLine();
											personList.get(i).setAge(nage);
											break;
										case 3:
											System.out.println("Enter From");
											String nfrom=scan.nextLine();
											personList.get(i).setFrom(nfrom);
											break;
										case 4:
											System.out.println("Enter To");
											String nto=scan.nextLine();
											personList.get(i).setTo(nto);
											break;
										case 5:
											ex=true;
											break;
										}
									
								}
								
								WriteToFile(personList,fileKey);
							}
							
							
						}
						
							if(flag==0)
							{
									System.out.println("Record Doesnt Exist");
							}
						
					}
					else
					{
							System.out.println("File Doesnt Exist");
						
					}
					break;
				case 4:
					ArrayList<Person> db= new ArrayList<Person>();
					System.out.println("Cancelation Process");
					System.out.println("Enter the customer id you want to remove");
					custId=scan.nextInt();
					flag=0;
					int s=0;
					scan.nextLine();
					fileLoc=(custId)/max;
					fileKey="file"+fileLoc;
					if(fileLoc<=readFileCount() && fileLoc>=0)
					{
						personList=ReadFromFile(fileKey);
						for(int i=0;i<personList.size();i++)
						{
							if(personList.get(i).getCustId()==custId)
							{
								flag=1;
								s=i;
								personList.remove(i);
							}
							if(personList.isEmpty())
							{
								deleteFile(fileKey);
								personList=new ArrayList<Person>();
							}
							else
							{
								WriteToFile(personList,fileKey);
							}
							
						}
					/*	if(flag==1)
						{
							System.out.println("Updating.....");
							System.out.println("filecount----:"+readFileCount());
							fileCount=readFileCount();
							if(fileCount>=0)
							{
								key="file"+fileCount;
								personList=ReadFromFile(key);
							}
							else
							{
								fileCount=0;
								key="file"+fileCount;
							}
						}*/
					
						if(flag==0)
							{
									System.out.println("Record Doesnt Exist");
							}
						
					}
					else
					{
							System.out.println("File Doesnt Exist");
						
					}
					
					break;
				case 5:
					quit=true;
					break;
					
				

		}

}
	}

	}
	
