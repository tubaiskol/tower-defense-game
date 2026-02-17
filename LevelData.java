import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//This class will read the data of level txt files.
public class LevelData {
    //in this level files there is 4 information about level. First two infos are width and height of grid map. Third one is coordinates of enemys path. Last one is wave of enemys information. We are saving wave informations and path coordinates in array lists.
    public int width;
    public int height;
    public ArrayList<int[]> map;
    public ArrayList<double[]> waveData;


    public LevelData(int width, int height, ArrayList<int[]> map, ArrayList<double[]> waveData) {//Constructor of level data.
        this.width = width;
        this.height = height;
        this.map = map;
        this.waveData = waveData;
    }

public static LevelData readLevelFile(String filePath) {//This method is the main method of our levelData class. in this method we are taking a txt file name and reading them. Then we are saving the informations to our arraylists.
    ArrayList<int[]> map = new ArrayList<>();
    ArrayList<double[]> waveData = new ArrayList<>();
    int width = 0;//Default 
    int height = 0;//Default

    try (Scanner input = new Scanner(new File(filePath))) {//We are using try with resource instead of input.close
        boolean wave = false;//In every txt files there is a part of wave data. We are creating a boolean wave data. If we are read a WAVE_DATA then we are setting it true and changing mod of reading to wave.
        while (input.hasNext()) {
            String a = input.nextLine().trim();//Taking a line from txt.
            if (a.isEmpty()) {
                continue;//If this line is empty continue to next line.
            }
            if (a.startsWith("WIDTH:")) {
                width = Integer.parseInt(a.substring(6));//If this line gives us the width. Take last part of the line and save width value.
            } 
            else if (a.startsWith("HEIGHT:")) {
                height = Integer.parseInt(a.substring(7));//If this line gives us the height. Take last part of the line and save height value.
            } 
            else if (a.equals("WAVE_DATA:")) {
                wave = true;//Activating the wave mode
            } 
            else if (wave) {//this part is wave reading mode
                //in this part we are reading wave data part of txt. 
                String[] b = a.split(",");//spliting the line with ,
                if (b.length == 3) {
                    double[] waveInfo = new double[3];
                    //Creating a array of a wave data
                    waveInfo[0] = Double.parseDouble(b[0].trim());
                    waveInfo[1] = Double.parseDouble(b[1].trim());
                    waveInfo[2] = Double.parseDouble(b[2].trim());
                    waveData.add(waveInfo);//Saving this array to our arraylist
                }
            } else {//This part is our enemy path saving part.
                String[] c = a.split(",");//spliting the line with ,
                if (c.length == 2) {
                    int[] coordinates = new int[2];
                    //Creating a array of a path data. This part will save the coordinates.
                    coordinates[0] = Integer.parseInt(c[0].trim());
                    coordinates[1] = Integer.parseInt(c[1].trim());
                    map.add(coordinates);
                }
            }
        }
    }
    catch(FileNotFoundException ex){
    }
    return new LevelData(width, height, map, waveData);
}

}
