import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s="Two tests + vijay + were performed to + evaluate the accuracy + that cannot be applied at a global scale + vijay + ";
		
		
		
		
		//String ss="example \"of\" such program \"at a small\" scale is HICOMP in California + -\"Sand Creek\"+ --  (consider input before and after -- only)";
		String ss="\"app*le\" batman -\"wo*qrp*dsd\" dan9*sdsd sd f55fsdf";
		
		//Pattern pattern = Pattern.compile("-?\\s*\"[^\"]+\"");
		Pattern pattern = Pattern.compile("\\S*\\*\\S*");

		Matcher matcher = pattern.matcher(ss);
		//String [] spc=ss.split("[^\"+]");
		int h=0;
		while (matcher.find()) {
			h++;
		    System.out.println(matcher.group());
		
		}
		
		
		
		
		
		String [] orOperation = s.split("\\+");
		String finalAndString="";
		String finalOrString="";
		List<String> finalAndResults = new ArrayList<>();
		//System.out.println(orOperation[0] + orOperation.length);
		String prevousOr="";
		for (int j = 0; j < orOperation.length; j++) {
		
			String [] AndOperation = orOperation[j].trim().split(" ");
			String prevousAnd="";
			for (int i = 0; i < AndOperation.length; i++) {
				
				if (i<AndOperation.length-1) {
					prevousAnd+=AndOperation[i].trim()+" ";
			//		System.out.println(prevousAnd+" and "+AndOperation[i+1]);				
					finalAndString=prevousAnd.trim()+" "+AndOperation[i+1].trim();
				}else if (i==0) {
					finalAndString=AndOperation[i];
			//		System.out.println(AndOperation[i] +" single oprand");
				}
				if (i==AndOperation.length-1) {
					finalAndResults.add(finalAndString);
				}
				
			}
			
			/*if (j<orOperation.length-1) {
				prevousOr+=orOperation[j]+" ";
				System.out.println(prevousOr+" or "+orOperation[j+1]);
			}else if (j==0) {
				System.out.println(orOperation[j] +" single oprand");
			}*/
			
		}
		
		for (int z = 0; z < finalAndResults.size(); z++) {
			if (z<finalAndResults.size()-1) {
				finalOrString+=finalAndResults.get(z).trim()+" ";
			//	System.out.println(finalOrString+" or "+ finalAndResults.get(z+1));
			}else if (z==0) {
				finalOrString+=finalAndResults.get(z);
		//		System.out.println(finalOrString+"---");
			}
			
		}
	}

}
