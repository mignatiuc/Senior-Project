/////////////////////////////////////////////////////////////////
//Test Program
//import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class RoverTest{
 
   public static void main(String[] args){
   
      //Scanner keyboard = new Scanner(System.in);
   
     
      //Starting Location. Grid 25 x 25
      DescribePoint x1 = new DescribePoint(0, 24);
      DescribePoint y1 = new DescribePoint(0, 24);
      
      //Go North
      Compass dir1 = Compass.N;
      
      List<Obstacle> obstacles1 = new ArrayList<Obstacle>();
      //Add obstacles to the list
      obstacles1.add(new Obstacle(0, 5));
      obstacles1.add(new Obstacle(0, 10));
       
      
      Grid roverCoordinates1 = new Grid(x1, y1, dir1, obstacles1);
    
      //System.out.println("Imagine we have to move from (0,0)
      // to (0,6) having an obstacle at (0,5)");
      //System.out.println("Start at (0,0)");
      MarsRover rover1 = new MarsRover(roverCoordinates1);
      System.out.println(rover1.getLocationUpdate());
    
   
      rover1.readInstructionStr("FFFF");
      System.out.println(rover1.getLocationUpdate());
      rover1.readInstructionStr("F");
      System.out.println(rover1.getLocationUpdate());
      rover1.readInstructionStr("RFLFFLFR");
      System.out.println(rover1.getLocationUpdate());
   
      //System.out.println("Done!");
      //System.out.println("Now, let's travel to the other side of the map");
      rover1.readInstructionStr("LF");
      System.out.println(rover1.getLocationUpdate());
      
      
      //Encounter an obstacle moving backward
      rover1.readInstructionStr("BRB");
      System.out.println(rover1.getLocationUpdate());
    
   
   
      System.exit(0);
   }
}


/////////////////////////////////////////////////////////////////
//Describes a Point
class DescribePoint{

   int pointLocation, maxLimPointLocation;
   
   //Location
   public void setLocation(int pointLocation){
      this.pointLocation = pointLocation; 
   }
   public int getLocation(){ 
      return pointLocation; 
   }

   //Boundaries
   public void setMaxLimPointLocation(int maxLimPointLocation){
      this.maxLimPointLocation = maxLimPointLocation; 
   }
   public int getMaxLimPointLocation(){ 
      return maxLimPointLocation; 
   }


  //Describe Point
   public DescribePoint(int pointLocation, int maxLimPointLocation){
      setLocation(pointLocation);
      setMaxLimPointLocation(maxLimPointLocation);
   }

   //Get location and check if it is in the "boundaries"
   public int getFlocation(){
      if (getLocation() == getMaxLimPointLocation()){
         return getMaxLimPointLocation()-getLocation();
      }
      else{
         return getLocation()+1;
      }
   }

   public int getBlocation(){
      if (getLocation() > 0){
         return (getLocation() - 1);
      }
      else{
         return getMaxLimPointLocation();
      }
   }
}


/////////////////////////////////////////////////////////////////
// Describes Compass Arrow
enum Compass{

//clockwise
   N(0,'N'),E(1,'E'),S(2,'S'),W(3,'W'); 
   
   char letterDisplay;
   int numDisplay;

   
   private Compass(int numDisplay, char letterDisplay){
      this.numDisplay = numDisplay;
      this.letterDisplay = letterDisplay;
   }

   public Compass rotateCompassOneEighty(){
   //ex: (S Value + 2)/4 = 0 = North Value = opposite side
      return values()[(this.getNumDisplay()+2)%4]; 
   }

   public int getNumDisplay(){
      return numDisplay;
   }

   public char getLetterDisplay(){ 
      return letterDisplay; 
   }
}


/////////////////////////////////////////////////////////////////
//Instruction handling
class MarsRover{

   private Grid coordinates;
  
   public void setGrid(Grid coordinates){
      this.coordinates = coordinates;
   }
   public Grid getGrid(){
      return coordinates;
   }


   public MarsRover(Grid coordinatesValue){
      setGrid(coordinatesValue);
   }
   
   
   public void readInstructionStr(String instructionString){
      for (int i=0; i < instructionString.length(); i++){
         
         char instructionChar=(instructionString.toCharArray())[i];
         if (readInstructionChar(instructionChar) == false){
            break;
         }
      }
   }

   public boolean readInstructionChar(char instructionChar){
      switch(instructionChar){
         case 'F':
            return getGrid().moveF();
         case 'R':
            getGrid().rotateCompassR();
            return true;
         case 'B':
            return getGrid().moveB();
         case 'L':
            getGrid().rotateCompassL();
            return true; 
      }
      return true; 
   }

   public String getLocationUpdate(){
      return getGrid().toString();
   }
}



/////////////////////////////////////////////////////////////////
// Describes the Grid
class Grid{

   boolean obsFound = false; //no obstacles by default
   DescribePoint xGridCoord, yGridCoord;
   Compass compassGrid;
   List<Obstacle> obstacles;
   
   
   public void setGridX(DescribePoint xGridCoord){
      this.xGridCoord = xGridCoord;
   }
   public DescribePoint getGridX(){ 
      return xGridCoord; 
   }
   
 
   public void setGridY(DescribePoint yGridCoord){
      this.yGridCoord = yGridCoord;
   }
   public DescribePoint getGridY(){ 
      return yGridCoord; 
   }

    
   
   public void setCompass(Compass compassGrid){
      this.compassGrid = compassGrid;
   }
   public Compass getCompass(){
      return compassGrid;
   }

   public void setObstacles(List<Obstacle> obstacles){
      this.obstacles = obstacles;
   }
   public List<Obstacle> getObstacles(){
      return obstacles;
   }
 
   public Grid(DescribePoint xGridCoord,DescribePoint yGridCoord,
   Compass compassGrid,List<Obstacle> obstaclesGrid){
      setGridX(xGridCoord);
      setGridY(yGridCoord);
      setCompass(compassGrid);
      setObstacles(obstaclesGrid);
   }

   private boolean nextStep(Compass compassGrid){
      int xCoord = xGridCoord.getLocation();
      int yCoord = yGridCoord.getLocation();
      
      switch (compassGrid){
         case N:
            yCoord = yGridCoord.getFlocation();
            break;
         case E:
            xCoord = xGridCoord.getFlocation();
            break;
         case S:
            yCoord = yGridCoord.getBlocation();
            break;
         case W:
            xCoord = xGridCoord.getBlocation();
            break;
      }
      if (obstacleFinder(xCoord, yCoord)==false){
         xGridCoord.setLocation(xCoord);
         yGridCoord.setLocation(yCoord);
         return true;
      } 
      else{
         return false;
      }
   }

   private boolean obstacleFinder(int xGridCoord, int yGridCoord){
      for (
      
      Obstacle obstacle : obstacles){
         if (obstacle.getObsX() == xGridCoord &&
          obstacle.getObsY()== yGridCoord){
            obsFound = true;
            return true;
         }
      }
      return false;
   }

   public boolean moveF(){ //step forward
      return nextStep(compassGrid);
   }

   public boolean moveB(){ //step backward
   //Face the same direction but move in the opposite one
      return nextStep(compassGrid.rotateCompassOneEighty());  
   }

   private void updateCompass(Compass compassVal, int compassStep){
      int numOfCompassRotations = Compass.values().length;
      int numDisplayIdx =
         (compassStep+
         compassVal.getNumDisplay()+
         numOfCompassRotations)
         % numOfCompassRotations;
         
      compassGrid = Compass.values()[numDisplayIdx];
   }

  
  
   public void rotateCompassR(){
      updateCompass(compassGrid, 1); //go clockwise
   }

   public void rotateCompassL(){
      updateCompass(compassGrid, -1); //go counterclockwise
   }

   @Override
   public String toString(){
      String msg = "Clear";
      
      if (obsFound){
         msg = "Obstacle Found";
      }
      obsFound = false;
      
      return "x:" + getGridX().getLocation() +
         "   y:" + getGridY().getLocation() + 
         "  Compass Shows:" +
          getCompass().getLetterDisplay() 
          + " Status:" + msg;
   }
}


/////////////////////////////////////////////////////////////////
//Describes an Obstacle
class Obstacle{

   int xObsCoord,yObsCoord;

   
   //Construct an Obstacle
   public Obstacle(int xObsCoord, int yObsCoord){
      setObsX(xObsCoord);
      setObsY(yObsCoord);
   }
   
   // X Coordinate
   public int getObsX(){
      return xObsCoord;
   }
   public void setObsX(int xObsCoord){
      this.xObsCoord = xObsCoord;
   }

   // Y Coordinate
   public int getObsY(){
      return yObsCoord;
   }
   public void setObsY(int yObsCoord){
      this.yObsCoord = yObsCoord;
   }
}
 /////////////////////////////////////////////////////////////////
