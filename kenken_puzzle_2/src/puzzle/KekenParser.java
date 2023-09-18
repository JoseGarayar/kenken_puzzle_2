package puzzle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KekenParser {

	public KekenParser() {
	}
	
	public  List<KenkenPuzzle> readFile (String fileName){
		
		List<KenkenPuzzle> kekenList =   new ArrayList<>();
		KenkenPuzzle kekenreturn =  new KenkenPuzzle(0);
		try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            int N = 0; // Tamaño del tablero
            //List<Constraint> constraints = new ArrayList<>();
            
            String line;
            while ((line = br.readLine()) != null) {
            	char inicio = line.charAt(0);
            	switch(inicio) {
                case '[':
                 //no se hace nada
                  break;
                case ']':
                  // Se termina pussle
                  break;
                case ')':
                    // Se termina pussle
                	kekenList.add(kekenreturn);
                    break;
                case '+':
                case '*':
                case 'x':	
                    // Se termina pussle
                	char operator = line.charAt(0);
                	String[] regionInfo = line.substring(2, line.length() - 1).split(",");                    
                	int targetValue=0;
                    //int pos= 0;
                    List<Cell> cells = new ArrayList<>();
                    for (int pos=0; pos< regionInfo.length; pos++)
                    //for (String linea : regionInfo) 
                    {
                    	if (pos==0){
                    		targetValue= Integer.parseInt(regionInfo[pos]);
                    	}
                    	else{
                    	
	                		 String[] coordinates = regionInfo[pos].split("\\|");
	                         int row = Integer.parseInt(coordinates[0].substring(coordinates[0].length()-1));
	                         int col = Integer.parseInt(coordinates[1].substring(0, 1));
	                         row--;
	                         col--;
	                         cells.add(new Cell(row, col));
                         
                    	}
                    	//pos++;
                    }                                       
                    kekenreturn.addRegion(targetValue, operator, cells);
                    break; 
                case '(': // se carga fixed value
                	String[] fixedinfo = line.substring(1, line.length() - 1).split(",");
                	if (fixedinfo.length==1){
                		N = Integer.parseInt(fixedinfo[0]);
                        kekenreturn = new KenkenPuzzle(N);            
                	}
                	else
                	{
	                	int fixedvalue = Integer.parseInt(fixedinfo[0]);
	                	String[] coordinates = fixedinfo[1].split("\\|");
	                	int row = Integer.parseInt(coordinates[0].substring(coordinates[0].length()-1));
	                    int col = Integer.parseInt(coordinates[1].substring(0, 1));
	                    row--;
	                    col--;
	                    kekenreturn.setCellValue(row, col, fixedvalue);
                	}
                	break;
                default:
                  // code block                	
                	break;
                }
            	    
            }
            
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return kekenList;
	}
	
	 
}
