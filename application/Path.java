package application;
import javafx.scene.shape.Polyline;
import java.util.ArrayList;

public class Path {
	// Data fields
	private int startingCellId;
	private int endingCellId;
	private Polyline path;
	
	
		
	// Constructor
	public Path(int startingCellId) {
		this.startingCellId = startingCellId;
	}
	
	// Getters and setters
	public int getstartingCellId() {
		return startingCellId;
	}

	public void setstartingCellId(int startingCellId) {
		this.startingCellId = startingCellId;
	}

	public int getendingCellId() {
		return endingCellId;
	}

	public void setendingCellId(int endingCelId) {
		this.endingCellId = endingCelId;
	}
	
	// Get value of Y coordinate that's used in setLayoutY() method
	private static int getY(int cellID) {
		int rowID = (cellID - 1) / 10;
		return (rowID * 50)+25;
	}
			
	// Get value of X coordinate that's used in setLayoutX() method
	private static int getX(int cellID) {
		int columnID = (cellID - 1) % 10;
		return (columnID * 50)+25;
	}
	private static double[] convertDoubles(ArrayList<Double> doubles)
	{
	    double[] ret = new double[doubles.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = doubles.get(i).doubleValue();
	    }
	    return ret;
	}
	public Polyline drawPath(ArrayList<FixedCell> fixeds,ArrayList<City> cities)
	{
		ArrayList<Double> points =new ArrayList<Double>();
		//Adding starting points x and y
		double startingCityrow=getY(startingCellId);
		double startingCitycol= getX(startingCellId);
		
		double endingCityrow=getY(endingCellId);
		double endingCitycol= getX(endingCellId);
	
		double x = startingCitycol;
		double y= startingCityrow;
		
		points.add(x);
		points.add(y);
	
		//towards up-true , towards down-false
		boolean upOrDown= (endingCityrow-startingCityrow) > 0 ? false:true;
		//towards right-true, towards left-false
		boolean rightOrLeft= (endingCitycol-startingCitycol) > 0 ? true:false;;
		
		System.out.println(endingCityrow);

		while(true)
		{
			//for x axis
			if(rightOrLeft)
				while(x < endingCitycol)
				{
					x+=50;
					if(!isTheWayClear(false, rightOrLeft,x, cities, fixeds))
					{
						break;
					}
				}
			else
				while(x > endingCitycol)
				{
					x-=50;
					if(!isTheWayClear(false, rightOrLeft,x,cities, fixeds))
					{
						break;
					}
				}
			points.add(x);
			points.add(y);
			
			//for y axis
			if(upOrDown)
				while(y > endingCityrow)
				{
					y-=50;
					if(!isTheWayClear(true, upOrDown,y,cities, fixeds))
					{
						break;
					}
				}
			else
				while(y < endingCityrow)
				{
					y+=50;
					if(!isTheWayClear(true, upOrDown,y,cities, fixeds))
					{
						break;
					}
				}
			
			points.add(x);
			points.add(y);
			
			if(endingCityrow == y && endingCitycol==x)
				break;
		}
		return (new Polyline(convertDoubles(points)));
		
	}
	//Change direction
	//isVertical -> false-horizontal true-vertical
	private boolean isTheWayClear(boolean isVertical,boolean whichDirection,double coodinate,ArrayList<City> cities,ArrayList<FixedCell> fixeds) {
		
		for(int i=0;i<fixeds.size();i++)
		{
			if(isVertical)
			{
				// +50 is cell size
				if((Game.getX(fixeds.get(i).getCellId()) <coodinate +75 &&
						Game.getX(fixeds.get(i).getCellId()) >coodinate -75))
					return false;
			}
			else if(!isVertical)
			{
				// +50 is cell size
				if(Game.getY(fixeds.get(i).getCellId()) <coodinate +75 && 
						Game.getY(fixeds.get(i).getCellId()) >coodinate -75)
					return false;
			}
		}
		for(int j=0;j<cities.size();j++)
		{
			if(isVertical)
			{
				// +50 is cell size
				if(Game.getX(cities.get(j).getCellId()) <coodinate +75 ||
						Game.getX(cities.get(j).getCellId()) >coodinate -75)
					return false;
			}
			else if(!isVertical)
			{
				// +50 is cell size
				if(Game.getY(cities.get(j).getCellId()) < coodinate +75 ||
						Game.getY(cities.get(j).getCellId()) >coodinate -75)
					return false;
			}
		}
		
		return true;
	}
}