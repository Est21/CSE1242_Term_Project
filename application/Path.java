package application;
import javafx.scene.shape.Polyline;
import java.util.ArrayList;

public class Path {
	// Data fields
	private int startingCellId;
	private int endingCellId;
	private Polyline path;
	private double xPoint;
	private double yPoint;
	
	
		
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
		double incOrDec = 50;
		
		//horizontal-true vertical-false
		boolean verticalOrHorizantal = true;
		points.add(x);
		points.add(y);
	
		//towards up-true , towards down-false
		boolean upOrDown= (endingCityrow-startingCityrow) > 0 ? false:true;
		//towards right-true, towards left-false
		boolean rightOrLeft= (endingCitycol-startingCitycol) > 0 ? true:false;;
		
		System.out.println(endingCityrow);
		System.out.println(endingCitycol);

		while(true)
		{
			
			//for x axis
			
			while(verticalOrHorizantal)
			{
				System.out.println(x +" aaa ");

				
				if(rightOrLeft && incOrDec<0)
				{
					incOrDec*= -1;
				}
				else if(!rightOrLeft && incOrDec>0)
				{
					incOrDec*= -1;
				}
					
				
				if(!isTheWayClearForFixed(verticalOrHorizantal,(incOrDec>0),x,y,fixeds) ||!isTheWayClearForFixed(verticalOrHorizantal,(incOrDec<0),x,y,fixeds))
				{
					verticalOrHorizantal=!verticalOrHorizantal;
					x+= incOrDec;
					break;
					
				}
				else if(x > endingCitycol-20 && (incOrDec>0))
				{
					verticalOrHorizantal=!verticalOrHorizantal;
					break;
						
				}
				else if(x < endingCitycol+20 && (incOrDec<0))
				{
					verticalOrHorizantal=!verticalOrHorizantal;
					break;
					
				}
				x+= incOrDec;
					
			}
			
			points.add(x);
			points.add(y);	
			
			//for y axis
			while(!verticalOrHorizantal)
			{
				System.out.println(y +" bbbb ");
				if(upOrDown && incOrDec>0)
				{
					incOrDec*= -1;
				}
				else if(!upOrDown && incOrDec<0)
				{
					incOrDec*= -1;
				}
				
				
				if(!isTheWayClearForFixed(!verticalOrHorizantal,(incOrDec<0),x,y,fixeds) ||
						!isTheWayClearForFixed(!verticalOrHorizantal,(incOrDec>0),x,y,fixeds))
				{
					verticalOrHorizantal=!verticalOrHorizantal;
					y+= incOrDec;
					break;
				}
				else if(y > endingCityrow-20 && (incOrDec>0))
				{
					verticalOrHorizantal=!verticalOrHorizantal;
					break;
					
				}
				else if(y < endingCityrow+20 && (incOrDec<0))
				{
					verticalOrHorizantal=!verticalOrHorizantal;
					break;
				}
				y+= incOrDec;
			
				
			}
			System.out.println(x);
			System.out.println(y);	
			points.add(x);
			points.add(y);
				
			
				
			
			
			
			if((endingCityrow +10 >= y && endingCityrow -10<=y)&& (endingCitycol+10>=x && endingCitycol-10<=x))
				break;
	}
		return (new Polyline(convertDoubles(points)));
		
	}
	//Change direction
	//isVertical -> true-horizontal false-vertical
	private boolean isTheWayClearForFixed(boolean isVertical,boolean whichDirection,double x,double y,ArrayList<FixedCell> fixeds) {
		
		for(int i=0;i<fixeds.size();i++)
		{
			if(isVertical)
			{
				
				
				// +50 is cell size
				if((Game.getX(fixeds.get(i).getCellId()) <=x +26 &&
						Game.getX(fixeds.get(i).getCellId()) >=x -26) &&
						(Game.getY(fixeds.get(i).getCellId()) <=y +26 && 
						Game.getY(fixeds.get(i).getCellId()) >=y-26))
					return false;
				
			}
			else if(!isVertical)
			{
				// +50 is cell size
				// +50 is cell size
				if((Game.getX(fixeds.get(i).getCellId()) <=x +26 &&
						Game.getX(fixeds.get(i).getCellId()) >=x -26) &&
						(Game.getY(fixeds.get(i).getCellId()) <=y +26 && 
						Game.getY(fixeds.get(i).getCellId()) >=y-26))
					return false;
				
			}
		}
		
		return true;
	}
private boolean isTheWayClearForCities(boolean isVertical,boolean whichDirection,double x,double y,ArrayList<City> cities) {
		
		
		for(int j=0;j<cities.size();j++)
		{
			if(cities.get(j).getCellId() == endingCellId)
				continue;
			if(isVertical)
			{
				// +50 is cell size
				if(Game.getX(cities.get(j).getCellId()) <x +26 &&
						Game.getX(cities.get(j).getCellId()) >x -26)
					return false;
			}
			else if(!isVertical)
			{
				// +50 is cell size
				if(Game.getY(cities.get(j).getCellId()) < y +26 &&
						Game.getY(cities.get(j).getCellId()) >y -26)
					return false;
			}
		}
		
		return true;
	}
}