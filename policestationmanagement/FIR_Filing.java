package policestationmanagement;

import java.util.*;
import java.time.LocalDate; 

class FIR {
	int id;
	String phone_no;
	String name;
	String category;			//Details/Fields of FIR
	String address;
	LocalDate filing_date;
	boolean statement_uploaded;
	boolean id_proof_uploaded;
	String status;
	
	Scanner scan = new Scanner(System.in);
	
	String which_cat(){
		 System.out.println("------------------------------------------------------------------------------");
	    System.out.println("\nWhich category does your complaint come under?");
	    System.out.println("Missing Person: MP");
	    System.out.println("Missing Object : MO");
	    System.out.println("Accident : AO");
	    System.out.println("Theft : TH");
	    System.out.println("Mental Harassment : MH");
	    System.out.println("Physical Harassment : PH");
	    System.out.println("Tresspassing : TR");
	    System.out.println("Public Property Damage: PD");
	    System.out.println("------------------------------------------------------------------------------");
	    System.out.println("Enter category: ");
	    category = scan.next();
	    return category.toUpperCase();
	    //Time Complexity: O(1)
	}
    FIR next;
    FIR(){
        id=0;//default constructor
    }
	FIR(String name,String phno,String addr) {
		this.phone_no = phno;
		this.name = name;
		this.address =addr;
		this.status="Investigating";
		next=null;
	}
	
	FIR accept(int ID){			//Accept FIR Details
		System.out.println("Your FIR ID is "+ID);
	    System.out.print("\nEnter name:");
    	String nm=scan.nextLine();
    	System.out.print("\nEnter Phone no: ");
		String phone_no = scan.nextLine();
		while (phone_no.length() != 10) {   //Checking for validity of phone number
			System.out.println("Invalid telephone number entered.");
			System.out.print("Enter a valid telephone number: ");
			phone_no = scan.nextLine();
		}
		System.out.println("Enter your address:");
		String addr = scan.nextLine();
    	FIR fir = new FIR(nm, phone_no,addr);
    	fir.id = ID;
    	fir.category = fir.which_cat();
    	fir.filing_date = LocalDate.now();
    	System.out.print("Press P to upload file describing crime scene: ");
    	char state = scan.next().charAt(0);
    	if(state=='P' ||state=='p' ) {
    		fir.statement_uploaded =true;
    		System.out.println("File uploaded successfully!");
    	}
    	System.out.print("Press I to upload your Identity Proof: ");
    	char id = scan.next().charAt(0);
    	scan.nextLine();
    	if(id=='I' ||state=='i' ) {
    		fir.id_proof_uploaded =true;
    		System.out.println("ID proof uploaded successfully!");
    	}
    	return fir;
    }
	
	public String toString() {	//to print FIR data conveniently
		String s1,s2,s3,s4;
		s1 = "{FIR ID: "+id + "\nname= " + name + ", phone number=" + phone_no  
				+"\nDate: "+filing_date+", Address: " +address
				+"\nFIR Category: "+category;
		
		if(statement_uploaded) {
			s2 = ", Statement Uploaded";
		}
		else {
			s2 = ", Statement Not Uploaded";
		}
		if(id_proof_uploaded) {
			s3 = ", ID Proof Uploaded";
		}
		else {
			s3 = ", ID Proof Not Uploaded";
		}
		s4 = "\nStatus : "+status+"}";
		return s1+s2+s3+s4;
	}
}

public class FIR_Filing {	//FIR_Filing Class - Implements Hash Table with chaining
	int n;

	int hashaddress=0;
	int size;
	int fir_id;
    Scanner sc=new Scanner(System.in);
	FIR hashtable[] = new FIR[40];
	
	FIR_Filing() {			//Default Constructor
		size=8;
		fir_id=0;
	}

	int hash(String nm)			//Hash Function, Convert String(FIR Category) to index of hashTable
	{
	    int hash_val=8;
	    switch(nm) {
	    case "MP": hash_val=0;
	    break;
	    case "MO": hash_val=1;
	    break;
	    case "AO": hash_val=2;
	    break;
	    case "TH": hash_val=3;
	    break;
	    case "MH": hash_val=4;
	    break;
	    case "PH": hash_val=5;
	    break;
	    case "TR": hash_val=6;
	    break;
	    case "PD": hash_val=7;
	    break;
	    default: System.out.println("Invalid input!");
	    }
	    return hash_val;
	//Time Complexity: O(1)
	}

	void insert(){	    //Insert new record to the table
    	FIR fir = new FIR();
    	fir_id++;
    	fir = fir.accept(fir_id);
    	hashaddress = hash(fir.category);
    	FIR ptr;
	    if(hashtable[hashaddress]==null){
	        hashtable[hashaddress]=fir;
	    }
	    else{
	        ptr=hashtable[hashaddress];
	        while(ptr.next!=null){
	            ptr=ptr.next;
	        }
	        ptr.next=fir;
	    }
	 //Time Complexity: O(n)
    }

	String fir_type(int index) {					//For Displaying Category wise cases
		String s="";
		switch(index) {
		case 0: s="Missing Person(MP)";
			break;
		case 1:s="Missing Object(MO)";
			break;
		case 2:s="Accident(AO)";
			break;
		case 3:s="Theft(TH)";
			break;
		case 4:s="Mental Harassment(MH)";
			break;
		case 5:s="Physical Harassment(PH)";
			break;
		case 6:s="Tresspassing(TR)";
			break;
		case 7: s="Public Property Damage:(PD)";
			break;
		}
		return s;
	//Time Complexity: O(1)
	}
	
	void display_active()			//Display FIRs with status "Investigating"
	{	
		FIR ptr;
		System.out.println("\n===================================================");
		for(int i=0;i<size;i++){
			System.out.println(fir_type(i)+ " Cases");
		    ptr=hashtable[i];
		    if(ptr==null){
		        System.out.println();
		    }
		    while(ptr!=null){
		    	if(ptr.status.equalsIgnoreCase("Investigating"))
		        System.out.println(ptr);
		        ptr=ptr.next;
		    }
		    System.out.println("\n===================================================");
		    System.out.println();
		}
		//Time Complexity: O(n)
	}
	
	public void display_all()
	{	
		System.out.println("\n===================================================");
		FIR ptr;
		for(int i=0;i<size;i++){
			System.out.println(fir_type(i)+ " Cases");
		    ptr=hashtable[i];
		    if(ptr==null){
		        System.out.println();
		    }
		    while(ptr!=null){
		        System.out.println(ptr);
		        ptr=ptr.next;
		    }
		    System.out.println("\n==================================================="); 
		    System.out.println();
		}
	//Time Complexity: O(n)
	}
	
	void update_status()		//Change status of searched FIR
	{
	    System.out.println("Enter the FIR id:");
	    int search_id=sc.nextInt();
	    sc.nextLine();
	    System.out.println("Enter category code of that FIR ");
	    String cat = sc.next();
	    hashaddress=hash(cat);
	    FIR ptr=hashtable[hashaddress];
	    while(ptr != null){
	        if(ptr.id==search_id){
	            System.out.println("\nFIR Id is found");
	            System.out.println(ptr);
	            System.out.println("Enter \"Solved\" if the case is solved: ");
	            ptr.status=sc.next();
	        }
	        ptr=ptr.next;
	    }
		if(ptr==null){
		    System.out.println("\nFIR Id not found");
		}
	//Time Complexity: O(n)
	}
	
	void delete()			//Delete an FIR
	{
	    System.out.println("Enter the FIR id you want to delete:");
	    int search_id=sc.nextInt();
	    sc.nextLine();
	    System.out.println("Enter category code of that FIR ");
	    String cat = sc.next();
		hashaddress=hash(cat.toUpperCase());
		FIR ptr=hashtable[hashaddress];
		if(ptr.id==search_id){
	            hashtable[hashaddress]=hashtable[hashaddress].next;
	    }
	    ptr=ptr.next;
	    FIR prev=hashtable[hashaddress];
	    while(ptr!=null){
	        if(ptr.id==search_id){
	            prev.next=ptr.next;
	        }
	        prev=ptr;
	        ptr=ptr.next;
	    }
	    if(ptr==null){
		    System.out.println("\nFIR Id not found");
		}
	 //Time Complexity: O(n)
	}

}