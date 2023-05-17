package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.shape.Polyline;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Game extends Pane {
	
	// Constant for cell size
	private static final int CELL_SIZE = 50;
	
	// ArrayLists
	private ArrayList<Passenger> passengersList = new ArrayList<Passenger>();
	private ArrayList<City> cityList = new ArrayList<City>();
	private ArrayList<FixedCell> fixedList = new ArrayList<FixedCell>();
	
	// !!! Vehicle and Path
	private Vehicle vehicle = new Vehicle("1", "5");
	private Path pathClass;
	private Polyline path;
	
	
		// This is constructor for Level that will be invoked in Main class
		// Its parameter is the filename of the level text file
		public Game(String filename) {
			setPrefHeight(500);
			setPrefWidth(500);
			readFile(filename);
			placeCities();
			placeFixedCells();
			placeVehicle(vehicle);		

		}
		
		// Read level file
		private void readFile(String filename) {
			// Create new file object to read it through Scanner
			File file = new File(filename);
			// Use try-catch clause is required as there is a checked exception (FileNotFoundException)
			try {
				// input object will read level text file
				Scanner input = new Scanner(file);
				// Reading
				while(input.hasNext()) {
					// Get first line of text file
					String line = input.nextLine();
					
					// Split it into words and put them in array
					// We get: list = {"City","Istanbul","14","1"};
					String[] list = line.split(",");
					
					// Fill ArrayLists: passengersList, cityList, fixedList
					// Also set Vehicle object
					initObjs(list);
				}
				input.close();
			}
			catch(FileNotFoundException ex) {
				System.out.println("File not found!");
			}
			
		}	
		
		// This method is used in readFile() to initialize Game's data fields
		private void initObjs(String[] list) {
			// In switch we check what is the first word of a line; City, Fixed, Passenger or Vehicle
			switch (list[0]) {
			case "City":
				// public City(String name, String cellId, String cityId)
				City city = new City(list[1], list[2], list[3]);
				cityList.add(city);
				break;
			case "Fixed":
				// public Fixed(String cellId)
				FixedCell fixed = new FixedCell(list[1]);
				fixedList.add(fixed);
				break;
			case "Passenger":
				// public Passenger(String numberOfPassengers, String startingCityId, String destinationCityId)
				Passenger passenger = new Passenger(list[1], list[2], list[3]);
				passengersList.add(passenger);
				break;
			case "Vehicle":
				// public Vehicle(String currentCityId, String passengerCapacity)
				vehicle = new Vehicle(list[1], list[2]);
				break;
			}
		}
		
		// Place city in the game (pane)
		private void placeCity(City city) {
			// Image of the city
			ImageView image = city.getImage();
			image.setFitHeight(CELL_SIZE);
			image.setFitWidth(CELL_SIZE);
			// Name of the city
			Label label = new Label(city.getCityName(), image);
			label.setTextFill(Color.BLACK);
			label.setContentDisplay(ContentDisplay.TOP);
			if(true)
			{	
				label.setOnMouseClicked(e-> {
					getChildren().remove(path);
					pathClass = new Path(getCellIdByCityId(vehicle.getCurrentCityId()));
					pathClass.setendingCellId(city.getCellId());
					path = pathClass.drawPath(fixedList, cityList);
					getChildren().add(path);
				});
				
			}
			// Place city
			int cellID = city.getCellId();
			label.setLayoutX(getX(cellID));
			label.setLayoutY(getY(cellID));
			
			
			// Add it to the game (pane)
			getChildren().add(label);
//				if(true)
//				{	
//					image.setOnMouseClicked(e-> {
//						path.setEndingCityId(city.getCityId());
//					});
//				}
//				add(vImage,city.getCellId()%10,city.getCellId()/10);
//				add(text,city.getCellId()%10,city.getCellId()/10);
			
		}
			
		// Place all cities from the list
		private void placeCities() {
			for(int i = 0;i<cityList.size();i++)
				placeCity(cityList.get(i));
			
		}
			
		// Place fixed cell in the game (pane)
		private void placeFixedCell(FixedCell fixedCell) {
			// Image of the fixed cell
			ImageView image = FixedCell.getFixedImage();
			image.setFitHeight(50);
			image.setFitWidth(50);
			
			// Place fixed cell
			int cellID = fixedCell.getCellId();
			image.setLayoutX(getX(cellID));
			image.setLayoutY(getY(cellID));
			
			// Add it to the game (pane)
			getChildren().add(image);
		}
			
		// Place all fixed cells from the list
		private void placeFixedCells() {
			for(int i = 0;i<fixedList.size();i++)
				placeFixedCell(fixedList.get(i));
		}
			
		// Place vehicle in the game (pane)
		private void placeVehicle(Vehicle vehicle) {
			// Image of the vehicle
			ImageView image = vehicle.getVehicleImage();
			image.setFitHeight(CELL_SIZE);
			image.setFitWidth(CELL_SIZE);
			
			// Place vehicle
			int cityID = vehicle.getCurrentCityId();
			int cellId = getCellIdByCityId(cityID);
			image.setLayoutX(getX(cellId));
			image.setLayoutY(getY(cellId)+25);
			
			// Add it to the game (pane) 
			getChildren().add(image);
		}
			
		// This method is used in placeVehicle() to get cell id 
		private int getCellIdByCityId(int cityId) {
			for(int i=0;i<cityList.size();i++) {
				if(cityList.get(i).getCityId() == cityId) {
					return cityList.get(i).getCellId();
				}
			}
			return -1; // not found
		}
			
		// Get value of Y coordinate that's used in setLayoutY() method
		public static int getY(int cellID) {
			int rowID = (cellID - 1) / 10;
			return (rowID * CELL_SIZE);
		}
		
		// Get value of X coordinate that's used in setLayoutX() method
		public static int getX(int cellID) {
			int columnID = (cellID - 1) % 10;
			return (columnID * CELL_SIZE);
		}
		
}
