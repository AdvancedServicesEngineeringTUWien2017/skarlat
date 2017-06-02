package at.ac.tuwien.infosys;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenaskarlat on 4/19/17.
 */
public class Utils {

    public static String transformLongToDate(String longTimeStamp){
            long time= Long.parseLong(longTimeStamp);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
            return sdf.format(time);
    }

    public static String[][] makeFrameMatrix(String data){
        data=data.replaceAll("\\s","");   //delete all spaces
        data = data.substring(1,data.length()-1); //delete [ ]
        String[] dataArray = data.split(",");//delete commas
        System.out.println("length= "+dataArray.length);

        String[][] matrix = new String[120][160];
        int row=0;
        int column =0;
        for (int i=0; i<dataArray.length; i++) {
            matrix[row][column] = dataArray[i];
            column++;
            if (column ==160) {
                column =0;
                row++;
            }
        }
        //output in 0 and 1(is there is any measurement- output it 1)
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (Integer.parseInt(matrix[i][j])==0)
                    System.out.print( String.format("%2s",matrix[i][j]));
                else System.out.print( String.format("%2s",1));
            }
            System.out.println();
        }
        return matrix;
    }

    //output in colors, may be needed in javascript
    public static String[][] makeFrameColors (String[][] matrix)
    {
        String[][] colors = new String[120][160];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int element = Integer.parseInt(matrix[i][j]);
                int elementScaled = element/1000;
                switch (elementScaled){
                    case 0: colors[i][j]="white"; break;
                    case 1:
                    case 2: colors[i][j]= "red"; break;
                    case 3:
                    case 4: colors[i][j]= "orange"; break;
                    case 5:
                    case 6: colors[i][j]="yellow"; break;
                    case 7:
                    case 8: colors[i][j]= "green"; break;
                    case 9:
                    case 10: colors[i][j]="lightblue"; break;
                    case 11:
                    case 12: colors[i][j]="blue"; break;
                    default: colors[i][j]="grey"; break;
                }
                System.out.print( String.format("%10s",colors[i][j]));
            }
            System.out.println();
        }
        return colors;
    }
}