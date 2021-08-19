/***************************************************************************************
POLICE STATION ADMINISTRATIVE MANAGEMENT & CITIZEN ASSISTANCE SYSTEM
****************************************************************************************
PROBLEM STATEMENT:-
Program to handle all the administrative works getting performed in an ordinary 
police station and also provide an user-friendly interface to citizens to address pressing
 needs of fellow citizens and to locate nearby police stations.


TWO INTERFACES:-
1)POLICE SUPPORT SYSTEM
2)CITIZEN SUPPORT SYSTEM

DATA STRUCTURES:-
-Graph: For creating City map	
-Heap: For Most Wanted Criminal List
-HashTable: For Storing FIR Records
-ArrayList: Used in Graph and hashTable
-Queue: Used in Dijkstra's algorithm
-Array: For Police Custody/Jail Records

GROUP MEMBERS:
-Gargi Kulkarni
-Parnavi Kulkarni
-Madhura Mhase
-Kinjel Mutha

***************************************************************************************/
package policestationmanagement;

import java.util.*;
import citymap.*;

class criminal {				//Criminal Class 
	String name;
	int ID;
	int no_of_crimes;
	ArrayList<String> Crime = new ArrayList<String>();
	Scanner sc1 = new Scanner(System.in);
	int severity;

	criminal() // default constructor
	{
		name = null;
		ID = 0;
		no_of_crimes = 0;
		severity = 0;
	}

	void accept() {			//Accept details of a criminal
		System.out.println("Enter the name of the criminal :");
		this.name = sc1.nextLine();
		System.out.println("Enter the number of crimes : ");
		this.no_of_crimes = sc1.nextInt();
		sc1.nextLine();
		for (int i = 0; i < this.no_of_crimes; i++) {
			System.out.println("Enter type of crime " + (i + 1));
			String temp = sc1.nextLine();
			this.Crime.add(temp);
		}
		calculate_severity();
	//Time Complexity: O(n), n number of crimes done by the criminal
	}

	void calculate_severity() {
		String temp;
		severity = 0;
		for (int i = 0; i < Crime.size(); i++) {
			temp = Crime.get(i);
			temp = temp.toUpperCase();
			switch (temp) {
			case "ABDUCTION":
				severity += 5;
				break;
			case "HUMAN TRAFFICKING":
				severity += 7;
				break;
			case "FRAUD":
				severity += 1;
				break;
			case "SEXUAL ABUSE":
				severity += 6;
				break;
			case "HEIST":
				severity += 2;
				break;
			case "SMUGGLING":
				severity += 4;
				break;
			case "TERRORISM":
				severity += 9;
				break;
			case "DRUG ABUSE":
				severity += 3;
				break;
			case "MURDER":
				severity += 8;
				break;

			default:
				severity += 0;
				break;
			}
		}
	//Time Complexity: O(n), size of crimes list of a criminal
	}
}					//End of Criminal Class

class Most_Wanted_Criminals {			   //Most Wanted Criminals Class- Implements Max Heap

	Scanner sc = new Scanner(System.in);
	criminal mwg[] = new criminal[27]; // Declaring Array storing elements of heap
	int totalno;

	Most_Wanted_Criminals() // default constructor
	{
		for (int i = 0; i < 27; i++) {
			mwg[i] = new criminal();
		}
		totalno = 0; 
	}
	
	public void add_new() {				//Add new records to the list
		System.out.print("Enter the number of criminals to enter:  ");
		int new_criminals = sc.nextInt();
		sc.nextLine();
		for(int i=0;i<new_criminals;i++) {
			criminal c = new criminal();
			c.accept();
			insert(c);
		}
	//Time Complexity: O(n), n is number of new records
	}
	public void accept() {		//Accept List
		System.out.println("Enter the number of criminals in the most wanted list. ");
		totalno = sc.nextInt();
		mwg[0].severity = totalno;

		for (int i = 1; i <= totalno; i++) {
			mwg[i].accept(); // accept function of criminal class 
		}
		build_mwg();
	//Time Complexity: O(n), n is number of new records
	}

	public void Down_Adjustment(int index, int n) {
		int largest = index; // initializing largest to index
		int left = 2 * index;
		int right = (2 * index) + 1;

		if (left <= n && mwg[left].severity > mwg[largest].severity) {
			largest = left;
		} else {
			largest = index;
		}
		if (right <= n && mwg[right].severity > mwg[largest].severity) {
			largest = right;
		}

		if (largest != index) {
			criminal temp = new criminal();
			temp = mwg[index];
			mwg[index] = mwg[largest];
			mwg[largest] = temp;

			Down_Adjustment(largest, n); // call method again

		}
	//Time Complexity: O(logn), n is number of  criminals in the list
	}

	public void build_mwg() // build mwg
	{
		for (int i = totalno / 2; i > 0; i--) {
			Down_Adjustment(i, totalno); // call to downAdjustment method
		}
	//Time Complexity: O(logn)
	}

	public void display() // print mwg
	{
		if (totalno != 0) {
			System.out.println("Most wanted criminal list is: ");
		}
		System.out.println("\n---------------------------------------------------------------------------------------------------------------------------");
		for (int i = 1; i <= totalno; i++) {
			System.out.println(mwg[i].name + "\t\t" + mwg[i].Crime + "\t\t" + mwg[i].severity);
		}
		System.out.println("\n---------------------------------------------------------------------------------------------------------------------------");
		if (totalno == 0) {
			System.out.println("List is empty.");
		}
		System.out.println(" ");
		//Time Complexity: O(n), n is number of  criminals in the list
	}

	public void display_most_wanted() {
		System.out.println("Most Wanted Criminal is : " + mwg[1].name);
		//Time Complexity: O(1)
	}

	public void Up_Adjustment(int index) {
		int i = index;
		while (i > 1) {
			int parent = i / 2;
			if (mwg[parent].severity < mwg[i].severity) {
				criminal temp = mwg[parent];
				mwg[parent] = mwg[i];
				mwg[i] = temp;
				i = i / 2;

			} else {
				i = i / 2;
			}
		}
		////Time Complexity: O(logn), n is number of  criminals in the list
	}

	public void insert(criminal element) {
		totalno = mwg[0].severity;
		totalno++;
		mwg[0].severity = totalno;
		mwg[totalno] = element;
		Up_Adjustment(totalno);
		//Time Complexity: O(1)
	}

	public void delete_max() {
		criminal crim = mwg[1];
		mwg[1] = mwg[totalno];
		mwg[totalno] = crim;
		totalno--;
		mwg[0].severity = totalno;
		for (int pos = totalno / 2; pos >= 1; pos--) {
			Down_Adjustment(totalno, pos);
		}
		//Time Complexity: O(logn), n is number of  criminals in the list
	}

	public void remove_any(String nm) {
		int del_index = Integer.MAX_VALUE;
		for (int i = 1; i <= totalno; i++) {
			if (mwg[i].name.equalsIgnoreCase(nm)) {
				del_index = i;
				break;
			}
		}
		if (del_index <= totalno) {
			criminal crim; // temporary variable
			crim = mwg[1];
			mwg[1] = mwg[del_index];
			mwg[del_index] = crim;
			delete_max();
			System.out.println(" Criminal is removed from the wanted list ");
		} else {
			System.out.println("Criminal is not in the most wanted list");
		}
	//Time Complexity: O(n), n is index of  criminal to be removed
	}
	
}//End of most wanted criminals class



class Police_Station_ops {	//Police Station End
	String password;
	static int max = 9;
	criminal jail[] = new criminal[max]; // array of criminals
	int present_in_jail;
	Scanner scan = new Scanner(System.in);

	Police_Station_ops() {
		present_in_jail = 0;
	}

	void add_to_jail() {

		if (present_in_jail != max) {
			present_in_jail++;
			jail[present_in_jail - 1] = new criminal();
			jail[present_in_jail - 1].accept();
		// time complexity:- O(n)
		}
	}

	void display_jail() {
		System.out.println("\n======================");
		System.out.println("         POLICE CUSTODY    ");
		System.out.println("======================");
		System.out.println("Sr.No\tName\t\tCrimes\t\t");
		System.out.println("======================");
		for (int i = 0; i < present_in_jail; i++) {
			System.out.println((i + 1) + "\t" + jail[i].name + "\t\t" + jail[i].Crime);
		}
		System.out.println("======================");
	// time complexity:- O(n), n is no. of criminals in the jail.
	}

	void delete_from_jail() {
		String name1;
		System.out.println("Enter the name of the criminal to be romoved from custody: ");
		name1 = scan.nextLine();
		for (int i = 0; i < present_in_jail; i++) {
			if (jail[i].name.equalsIgnoreCase(name1)) {
				for (int j = i; j < (present_in_jail - 1); j++) {
					jail[j] = jail[j + 1];
				}
				jail[present_in_jail - 1] = new criminal();
				present_in_jail--;
				break;
			}
		}
	//Time Complexity: O(n), n is index of  criminal to be removed
	}

	Boolean login(String authorization_key) // Police Station login facility
	{
		password = "admin@123";
		return password.equals(authorization_key);
		//Time Complexity: O(1)
	}

	int pol_menu() {
		int choice = 0;
			System.out.println("\n*****************************");
			System.out.println(" POLICE MENU");
			System.out.println("*****************************");
			System.out.println(
					"1. Jail Records\n"
					+"2. Most Wanted Criminals List\n"
					+"3. View All FIRs\n"
					+"4. View Active Cases(Investigating FIR)\n"
					+" 5. Update Case Status\n"
					+ "0. Exit");
			System.out.println("------------------------------");
			System.out.println("\nEnter your choice");
			choice = scan.nextInt();
			System.out.println("*****************************");
			scan.nextLine();
		return choice;
	//Time Complexity: O(1)
	}
	
	FIR_Filing display_active(FIR_Filing f) {
		System.out.println("ACTIVE CASES");
		f.display_active();
		return f;
	//Time Complexity: O(1)
	}
	
	FIR_Filing display_firs(FIR_Filing f) {
		System.out.println("All FIR Records");
		f.display_all();
		return f;
	//Time Complexity: O(1)
	}
	
	
	FIR_Filing update_cases(FIR_Filing f) {
		f.update_status();
		return f;
	//Time Complexity: O(1)
	}
	
	void jail_running() {	//Menu for police custody/Jail 
		int ch=0;
		do {
			System.out.println("\n____________________________________________________________________________________________");
			System.out.println("\n1.Add to Police Custody/Jail\n2.Remove from Police Custody/Jail"+
			"\n3.Display Police Custody/Jail Records\n0.Exit");
			System.out.println("\n____________________________________________________________________________________________");
			System.out.println("Enter your choice");
			ch = scan.nextInt();
			scan.nextLine();
			switch (ch) {
			case 1: add_to_jail();
				break;
			case 2: delete_from_jail();
				break;
			case 3: display_jail();
				break;
			case 0: System.out.println("Exiting");
			return;
			}
		} while (ch!=0);
	//Time Complexity: O(n), n is number of times any operation is getting performed.
	}
}

class Citizen  {			//Citizen Class
	Scanner scan = new Scanner(System.in);
	City city1 = new City();
	
	int cit_menu(FIR_Filing f) {	//Menu for Citizen Operations
	int choice = 0;
			System.out.println("\n*****************************");
			System.out.println(" CITIZEN MENU");
			System.out.println("*****************************");
			System.out.println(
					"1. File FIR\n"
					+ "2.Withdraw FIR\n"
					+ "3.Find nearest police station\n"
					+"0. Exit");
			System.out.println("------------------------------");
			System.out.print("Enter your choice: ");
			choice = scan.nextInt();
			System.out.println("*****************************");
			scan.nextLine();
	return choice;
	//Time Complexity: O(1)
	}
	
	FIR_Filing file_fir(FIR_Filing f) {
		f.insert();
		return f;
	//Time Complexity: O(1)
	}
	
	FIR_Filing fir_withdraw(FIR_Filing f) {
		f.delete();
		return f;
	//Time Complexity: O(1)
	}
	
	void find_nearest_pol() {
		String temp;
		System.out.println("Enter your current location : ");
		temp = scan.nextLine();
		System.out.print("\n");
		city1.near_me_djkstra(temp);
	//Time Complexity: O(1)
	}
	
}

public class Police_Station  { // Police_Station
	
	public static boolean authenticator() {	
		Scanner scan = new Scanner(System.in);
		Police_Station_ops ps = new Police_Station_ops();
		for (int i= 0; i < 3; i++)
        {
            System.out.println("\nEnter password: ");
            String authorization_key = scan.nextLine();
            if(ps.login(authorization_key))
            	return true;
            else
            	System.out.println("Wrong password!\n"+(2-i)+" attempt/attempts left");
        }
		return false;
		//Time Complexity: O(n), 1<=n<=3
	}
	
	public static int mainmenu() {
		int option =0;
		Scanner scan = new Scanner(System.in);
		
		System.out.println("\t\t\t\t=============================================");
		System.out.println("\t\t\t\t\t\t\t\tMAHARASHTRA POLICE  ");
		System.out.println("\t\t\t\t\t\t\t !             सद्ररक्षणाय खलनिग्रहणाय           !");
		System.out.println("\t\t\t\t=============================================");
		System.out.println("\t\t\t\tKALPANA POLICE STATION, KARVE NAGAR,PUNE -04 ");
		System.out.println("\t\t\t\t=============================================");
		System.out.println("\t\t\t\t\tWelcome to Online Police Station Program!");
		System.out.println("\t\t\t\t\tYou are a:");
		System.out.println("\t\t\t\t\t\t\t\t1: Police Official"
										+"\n\t\t\t\t\t\t\t\t2: Citizen");
		System.out.println("\n\t\t\t\tPress 0 to exit the program ");
		System.out.println("\t\t\t\t=============================================");
		System.out.print("\t\t\t\t\tEnter choice :  ");
		option = scan.nextInt();
		System.out.println("\t\t\t\t=============================================");
		return option;
		//Time Complexity: O(1)
	}
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		Police_Station_ops ps = new Police_Station_ops();
		Most_Wanted_Criminals h1 = new Most_Wanted_Criminals();
		Citizen c = new Citizen();
		FIR_Filing f = new FIR_Filing();
		int option=0;
		do {
			option = mainmenu();
			if(option==1 && authenticator()) {
		            	int choice=0;
		            	do {
		            	choice=ps.pol_menu();
		            	switch (choice) {
		    			case 1: ps.jail_running();
		    				break;
		    			case 2:
		    				int co = 0;
		    				do {
		    					System.out.println("__________________________________________________________________________________________________");
		    					System.out.println("\n1.Create List\n2.Display entire list"
		    					+"\n3.Display Most Wanted\n4.Add Criminal to most wanted list\n5. Delete from list(if criminal found)"
		    							+"\n0. Exit");
		    					System.out.println("__________________________________________________________________________________________________");
		    					System.out.println("Enter your choice");
		    					co = scan.nextInt();
		    					scan.nextLine();
		    					switch (co) {
		    					case 1:h1.accept();
		    					break;
		    					case 2: h1.display();
		    						break;
		    					case 3: h1.display_most_wanted();
		    						break;
		    					case 4: h1.add_new();
		    						break;
		    					case 5: System.out.print("Enter name of criminal to be deleted: ");
		    					String nm = scan.nextLine();
		    					h1.remove_any(nm);
		    					break;
		    					case 0: 
		    						break;
		    					}
		    				} while (co!=0);
		    				break;
		    			case 3: f=ps.display_firs(f);
		    				break;
		    			case 4: f=ps.display_active(f);
		    				break;
		    			case 5: f=ps.update_cases(f);
		    				break;
		    			case 0:
		    				break;
		    		    default: System.out.println("Invalid");
		    			}
		    		} while (choice != 0);	
		        }
		         
			if(option==2) {
				int choice=0;
				do {
					choice = c.cit_menu(f);
					switch (choice) {
					case 1:	f=c.file_fir(f);
					break;
					case 2: f=c.fir_withdraw(f);
						break;
					case 3:	c.find_nearest_pol();
						break;
					case 0:
						break;
					default: System.out.println("Invalid Input!");
					}
				} while (choice != 0);
			}
	}while(option!=0);
		scan.close();
		//Time Complexity: O(n^3)
	}
	
}

