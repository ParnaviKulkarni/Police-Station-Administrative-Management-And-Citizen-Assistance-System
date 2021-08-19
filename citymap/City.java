package citymap;

import java.util.*;

class Place {
	public int loc_id;
	int graph_id;
	public String place_name;
	public String place_type;

	Place(int gid, int addr, String nm, String type) {
		graph_id = gid;
		loc_id = addr;
		place_name = nm;
		place_type = type;
	}

	Place() {
		place_name = null;
	}
}

class Road {
	Place destination;
	double distance;

	boolean vis;

	Road() {
		destination = null;
		distance = 0;
		vis = false;
	}

	Road(Place dest, double dist) {
		destination = dest;
		distance = dist;

	}
}

public class City {		//Graph of places & Roads
	static int MAX = 50;
	int tot_places;
	LinkedList<Road> visited = new LinkedList<Road>();
	double[] dist = new double[MAX];
	ArrayList<ArrayList<Road>> adj = new ArrayList<ArrayList<Road>>();
	LinkedList<Road> pqueue = new LinkedList<>();
// PriorityQueue<Road> pqueue;

	int djksta_dist;

	public void addEdge(String source, String destination, double dist) {
		Place src = get_place_from_name(source);
		Place dest = get_place_from_name(destination);
		Road nroad = new Road(dest, dist);
		adj.get(src.graph_id).add(nroad);
		nroad = new Road(src, dist);
		adj.get(dest.graph_id).add(nroad);
	}

	public City() {
		tot_places = 25;
		dist = new double[tot_places];

// pqueue = new PriorityQueue<Road>(tot_places, new Road());
		for (int i = 0; i < tot_places; i++) {
			ArrayList<Road> temp = new ArrayList<>();
			adj.add(temp);
// create 1st node
		}
		//Adding Places to the map
		adj.get(0).add(new Road(new Place(0, 4121, "Deenanath Mangeshkar Hospital", "HOSPITAL"), 0)); //
		adj.get(1).add(new Road(new Place(1, 4122, "The VegeTable", "Restaurant"), 0)); //
		adj.get(2).add(new Road(new Place(2, 4123, "KALPANA Police Station", "POLICE STATION"), 0)); //
		adj.get(3).add(new Road(new Place(3, 4124, "Kothrud Police Station", "POLICE STATION"), 0)); //
		adj.get(4).add(new Road(new Place(4, 4125, "Swargate", "BUS STOP"), 0)); //
		adj.get(5).add(new Road(new Place(5, 4126, "Karishma", "SOCIETY"), 0)); //
		adj.get(6).add(new Road(new Place(6, 4127, "Prabhat Police Station", "POLICE STATION"), 0)); //

		adj.get(7).add(new Road(new Place(7, 4128, "Chaturshringi Mandir", "Temple"), 0)); //
		adj.get(8).add(new Road(new Place(8, 4129, "Law College Road", "FILM INSTITUTE"), 0)); //
		adj.get(9).add(new Road(new Place(9, 4130, "Vetal Hill", "Hill"), 0)); //
		adj.get(10).add(new Road(new Place(10, 4131, "City Pride Kothrud", "Cinema Hall"), 0)); //
		adj.get(11).add(new Road(new Place(11, 4132, "CoEP Engineering College", "Educational Institute"), 0)); //
		adj.get(12).add(new Road(new Place(12, 4133, "JW MARRIOT", "Restaurant"), 0)); //
		adj.get(13).add(new Road(new Place(13, 4134, "Yashwantrao Chavan Natyagruha", "Theatre Hall"), 0)); //
		adj.get(14).add(new Road(new Place(14, 4135, "Ramamani Iyengar Yoga Institute", "Yoga Institute"), 0)); //
		adj.get(15).add(new Road(new Place(15, 4136, "Parvati Hill", "Tourist Attraction"), 0)); //

		adj.get(16).add(new Road(new Place(16, 4137, "Le Plaisir", "Restaurant"), 0)); //
		adj.get(17).add(new Road(new Place(17, 4138, "Sambhaji Police Chowky", "POLICE STATION"), 0)); //
		adj.get(18).add(new Road(new Place(18, 4139, "Chaturshringi Police Station", "POLICE STATION"), 0)); //
		adj.get(19).add(new Road(new Place(19, 4140, "Swargate Police Station", "POLICE STATION"), 0)); //
		adj.get(20).add(new Road(new Place(20, 4141, "Shivajinagar Police Station", "POLICE STATION"), 0)); //
		adj.get(21).add(new Road(new Place(21, 4142, "Cummins College of engineering", "Educational Institute"), 0)); //
		adj.get(22).add(new Road(new Place(22, 4143, "CoEP Engineering College", "Educational Institute"), 0)); //
		adj.get(23).add(new Road(new Place(23, 4144, "Central Bank of India", "BANK"), 0)); //
		adj.get(24).add(new Road(new Place(24, 4145, "Deccan Multispeciality Hospital", "Hospital"), 0)); //
		
		//Adding Roads to the map
		addEdge("The VegeTable", "Le Plaisir", 0.8);
		addEdge("Le Plaisir", "Prabhat Police Station", 0.3);
		addEdge("Law College Road", "Prabhat Police Station", 0.5);
		addEdge("Cummins College of engineering", "Karishma", 2.7);
		addEdge("Karishma", "KALPANA Police Station", 0.8);
		addEdge("Deenanath Mangeshkar Hospital", "KALPANA Police Station", 1.2);
		addEdge("Ramamani Iyengar Yoga Institute", "Central Bank of India", 1);
		addEdge("Central Bank of India", "Deccan Multispeciality Hospital", 2.3);
		addEdge("Deccan Multispeciality Hospital", "Shivajinagar Police Station", 0.9);
		addEdge("Deccan Multispeciality Hospital", "Parvati Hill", 3);
		addEdge("Chaturshringi Mandir", "JW Marriot", 1.1);
		addEdge("JW MARRIOT", "Chaturshringi Police Station", 2);
		addEdge("Cummins College of engineering", "Yashwantrao Chavan Natyagruha", 2.6);
		addEdge("Yashwantrao Chavan Natyagruha", "Sambhaji Police Chowky", 5);
		addEdge("CoEP Engineering College", "JW Marriot", 4.3);
		addEdge("JW MARRIOT", "Law College Road", 2.4);
		addEdge("CoEP Engineering College", "Shivajinagar Police Station", 1.1);
		addEdge("City Pride Kothrud", "Karishma", 0.6);
		addEdge("Vetal Hill", "Kothrud Police Station", 4.8);
		addEdge("Parvati Hill", "Swargate", 2.8);
		addEdge("Swargate", "Swargate Police Station", 0.2);
		addEdge("Parvati Hill", "Deenanath Mangeshkar Hospital", 4.5);
	}

	public void display() {		//Display all locations, unused in policestation.java
		System.out.println("Location 1");

		for (int i = 0; i < tot_places; i++) {
			for (int j = 0; j < adj.get(i).size(); j++) {

				System.out.print("->" + adj.get(i).get(j).destination.place_name);

			}
			System.out.println();
		}
		//Time Complexity: O(n^2)
	}

	public Place get_place_from_name(String str) {		//Returns place whose place_name is str (Argument to the function)
		int flag=0;
		Place location = new Place();
		for (int i = 0; i < tot_places; i++) {
			if (adj.get(i).get(0).destination.place_name.equalsIgnoreCase(str)) {
				location.graph_id = i;
				location.loc_id = adj.get(i).get(0).destination.loc_id;
				location.place_name = adj.get(i).get(0).destination.place_name;
				location.place_type = adj.get(i).get(0).destination.place_type;
				flag=1;
				break;
			} 
		}
		if(flag==0) {
			System.out.println("Location does not exist in the map!");
		}
		return location;
	}

	public void search(int loc) { //Search locations according to their IDs , unused in policestation.java
		for (int i = 0; i < tot_places; i++) {
			if (adj.get(i).get(0).destination.loc_id == loc) {
				System.out.println(adj.get(i).get(0).destination.loc_id);
				System.out.println(adj.get(i).get(0).destination.place_name);
				System.out.println(adj.get(i).get(0).destination.place_type);
			}
		}
	}

	public void near_me_djkstra(String src) {	//Dijkstra's Algorithm
		for (int i = 0; i < tot_places; i++)
			dist[i] = Integer.MAX_VALUE;
		djksta_dist = 0;
		Place loc = get_place_from_name(src);
		if (loc.place_name!=null) {
		Road source = new Road(loc, 0);
// first add source vertex to PriorityQueue

		pqueue.add(source);
		dist[source.destination.graph_id] = 0;
	// Distance to the source from itself is 0
	Road u = new Road();// pqueue.remove();
	int min_from_q = 0;
	// u is removed from PriorityQueue and has min distance
	while (visited.size() != tot_places) {
		double min = Integer.MAX_VALUE;
		for (int i = 0; i < pqueue.size(); i++) {
			if (dist[pqueue.get(i).destination.graph_id] < min) {
				min = dist[pqueue.get(i).destination.graph_id];
				min_from_q = i;
			}

		}

		u = pqueue.remove(min_from_q);

		visited.add(u);
		u.vis = true;
		graph_adjacentNodes(u);
		if (u.destination.place_type.equalsIgnoreCase("Police Station") && u.destination.place_type != null) {
			break;
		}
	} 
	System.out.println("Nearest Police Station: " + u.destination.place_name);
}
	}

	private void graph_adjacentNodes(Road u) {// processes all neighbours of the just visited node
		double edgeDistance = -1;
		double newDistance = -1;

// process all neighbouring nodes of u
		for (int i = 1; i < adj.get(u.destination.graph_id).size(); i++) {
			Road v = adj.get(u.destination.graph_id).get(i);

// proceed only if current node is not in 'visited'
			if (!v.vis) {
				edgeDistance = v.distance;
				newDistance = dist[u.destination.graph_id] + edgeDistance;

// compare distances
				if (newDistance < dist[v.destination.graph_id])
					dist[v.destination.graph_id] = newDistance;
// Add the current vertex to the "PriorityQueue"
				pqueue.add(v);

			}
		}

	}
}
