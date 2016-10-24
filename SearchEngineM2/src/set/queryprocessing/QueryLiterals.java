package set.queryprocessing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import set.beans.TokenDetails;
import set.docprocess.BiWordIndexing;
import set.docprocess.PositionalInvertedIndex;

/**
 * Class QueryLiterals
 * 
 * @author Durvijay Sharma
 * @author Mangesh Adalinge
 * @author Surabhi Dixit
 */

public class QueryLiterals {


	private QueryResultProcessing qrp = new QueryResultProcessing();
	
	//Modified new variable
	private Pattern phrasePattern = Pattern.compile("-\\s*\"[^\"]+\"");
	private Pattern wildCardPattern = Pattern.compile("\\S*\\*\\S*");
	private Matcher matcher;
	private Matcher matcher1;

	/**
	 * Splits the query string
	 * @param kIndex 
	 * @param folderPath 
	 * 
	 * @param queryString:
	 *            Query string to process for search results
	 * @param indexObj:
	 *            Contains both objects of PI and BI
	 * @return
	 */
	public List<TokenDetails> splitQueryString(String queryString, PositionalInvertedIndex pindexObj,BiWordIndexing bIndexObj, KGramIndex kIndex, String folderPath) {
		//split = queryString.split("\\s+");
		
	//	index = indexObj;
		qrp.setPindexobj(pindexObj);
		qrp.setBindexobj(bIndexObj);
		qrp.setDiskIndexPath(folderPath);
		//phrase detection
		matcher = phrasePattern.matcher(queryString.trim());
		//wild card token detection
		matcher1 = wildCardPattern.matcher(queryString.trim());
		
		
		/*while (matcher1.find()) {
			qrp.getWildCardQueryResult(matcher.group(),kIndex);
			queryString=queryString.replaceAll(matcher.group(), matcher.group().replaceAll(" ", ""));
		    System.out.println(matcher.group());
		
		}
		*/
		while (matcher.find()) {
			qrp.getPhraseQueryresult(matcher.group());
			queryString=queryString.replaceAll(matcher.group(), matcher.group().replaceAll(" ", ""));
		    System.out.println(matcher.group());
		
		}

			
		
		
		String [] orOperation = queryString.split("\\+");
		String finalAndString="";
		String finalOrString="";
		//List<String> finalAndResults = new ArrayList<>();
		//System.out.println(orOperation[0] + orOperation.length);

		for (int j = 0; j < orOperation.length; j++) {
		
			String [] AndOperation = orOperation[j].trim().split(" ");
			String prevousAnd="";
			
			for (int i = 0; i < AndOperation.length; i++) {
				
				if (i<AndOperation.length-1 && AndOperation.length>1) {
					prevousAnd+=AndOperation[i].trim()+" ";
					if (!AndOperation[i+1].trim().startsWith("-") || !prevousAnd.trim().startsWith("-")) {
						qrp.getAndQueryresult(prevousAnd.trim(), AndOperation[i+1].trim());
					}else{
						if (AndOperation[i+1].trim().startsWith("-")) {
							qrp.getAndNotQueryresult(prevousAnd.trim(), AndOperation[i+1].trim());
						}else if (prevousAnd.trim().startsWith("-")) {
							qrp.getAndNotQueryresult(AndOperation[i+1], prevousAnd);
						}
					}
					
			//		System.out.println(prevousAnd+" and "+AndOperation[i+1]);				
					finalAndString=prevousAnd.trim()+" "+AndOperation[i+1].trim();
				}/*else if (i==0) {
					finalAndString=AndOperation[i];
					qrp.getSingleOprandResult(AndOperation[i].trim(), "f");
			//		System.out.println(AndOperation[i] +" single oprand");
				}*/
				/*if (i==AndOperation.length-1) {
					finalAndResults.add(finalAndString);
				}*/
				
			}
			
		}
		finalOrString=finalAndString;
		boolean temp=true;
		for (int z = 0; z < orOperation.length; z++) {
			
			if (z<orOperation.length-1 && orOperation.length>1) {
				if (temp) {
					finalOrString="";
					finalOrString+=orOperation[z].trim();
					temp=false;
				}
				
				
				
				if (!finalOrString.trim().startsWith("-") || !orOperation[z+1].trim().startsWith("-")) {
					qrp.getOrQueryresult(finalOrString.trim(), orOperation[z+1].trim());
				}else{
					if (finalOrString.trim().startsWith("-")) {
						qrp.getOrNotQueryresult(orOperation[z+1].trim(), finalOrString.trim());
					}else if (orOperation[z+1].trim().startsWith("-")) {
						qrp.getAndNotQueryresult(finalOrString.trim(), orOperation[z+1].trim());
					}
				}
				finalOrString=finalOrString.trim()+" + "+orOperation[z+1].trim();
				
				System.out.println(finalOrString+" or "+ orOperation[z+1]);
			}else if (z==0 && !orOperation[z].contains(" ")) {
				finalOrString+=orOperation[z];
				qrp.getSingleOprandResult(finalOrString.trim());
				System.out.println(finalOrString+"---");
			}
			
		}
		
		
		
		
		
		//splitQueryStringLiterals();
		if (qrp.getResults() != null) {
			System.out.println(finalOrString + "--" + qrp.getResults());
			return qrp.getResults().get(finalOrString);
		} else
			return null;

	}




}
