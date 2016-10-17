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

	public int processCnt = 0;
	public Boolean phraseQueryFlag = false;
	public String oldProcessString = null;
	public String[] split;
	public String oprand1 = null;
	public String oprand2 = null;
	public int i = 0;
	public Boolean processCntFlag = false;
	private List<String> oprands = new ArrayList<>();
	private List<String> operators = new ArrayList<>();
	private static final Pattern ltrs = Pattern.compile("[a-z\"]");
	private QueryResultProcessing qrp = new QueryResultProcessing();
	
	//Modified new variable
	private Pattern phrasePattern = Pattern.compile("-\\s*\"[^\"]+\"");
	private Pattern wildCardPattern = Pattern.compile("-\\s*\"[^\"]+\"");
	private Matcher matcher;

	/**
	 * Splits the query string
	 * @param kIndex 
	 * 
	 * @param queryString:
	 *            Query string to process for search results
	 * @param indexObj:
	 *            Contains both objects of PI and BI
	 * @return
	 */
	public List<TokenDetails> splitQueryString(String queryString, PositionalInvertedIndex pindexObj,BiWordIndexing bIndexObj, KGramIndex kIndex) {
		//split = queryString.split("\\s+");
		
	//	index = indexObj;
		qrp.setPindexobj(pindexObj);
		qrp.setBindexobj(bIndexObj);
		matcher = phrasePattern.matcher(queryString.trim());
		
		if (queryString.contains("*")) {
			
		}
		
		
		while (matcher.find()) {
			System.out.println("--"+matcher.group()+"--");
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


	/**
	 * search results according to query
	 * 
	 * @param oprand11
	 * @param oprand22
	 * @param operator
	 * @return: returns processed query output
	 */
	public String search(String oprand11, String oprand22, String operator) {
		return operator;
/*		String oprand1 = oprand11.toLowerCase();
		String oprand2 = oprand22.toLowerCase();
		// String operator = operator1.trim().toLowerCase();

		if (!operator.trim().equalsIgnoreCase("phrase")) {
			oprand1 = oprand1.replaceAll(" ", "");
			oprand2 = oprand2.replaceAll(" ", "");
		}
		String oldProcessString = null;
		if (operator.replace(" ", "").equalsIgnoreCase("PHRASE")) {
			completeQuery = oprand1.replaceAll("\"", "");
		} else {
			completeQuery = oprand1 + operator + oprand2;
		}

		System.out.println("Query: " + oprand11 + "--" + oprand22 + "--" + operator + "--");

		qrp.setBiWordIndex(index);
		System.out.println(oldProcessString);
		switch (operator.trim()) {
		case "PHRASE":
			oldProcessString = oprand1.replaceAll("\"", "");
			qrp.getPhraseQueryresult(oldProcessString);
			break;
		case "AND":
			oldProcessString = oprand1.replaceAll(" ", "") + operator.replaceAll(" ", "") + oprand2.replaceAll(" ", "");
			qrp.getAndQueryresult(oprand1.replaceAll(" ", ""), oprand2.replaceAll(" ", ""));
			break;
		case "OR":
			oldProcessString = oprand1.replaceAll(" ", "") + operator.replaceAll(" ", "") + oprand2.replaceAll(" ", "");
			qrp.getOrQueryresult(oprand1.replaceAll(" ", ""), oprand2.replaceAll(" ", ""));
			break;
		case "PHRASE AND":
			oldProcessString = oprand1.replaceAll(" ", "") + operator.replaceAll(" ", "") + oprand2.replaceAll(" ", "");
			qrp.getAndQueryresult(oprand1, oprand2);
			System.out.println(oldProcessString);
			break;
		case "PHRASE OR":
			oldProcessString = oprand1.replaceAll(" ", "") + operator.replaceAll(" ", "") + oprand2.replaceAll(" ", "");
			qrp.getOrQueryresult(oprand1, oprand2);
			System.out.println(oldProcessString);
			break;
		case "NOTPHRASE":
			oldProcessString = oprand1.replaceAll("\"", "");
			qrp.getPhraseQueryresult(oldProcessString);
			break;
		case "OrNot":
			oldProcessString = oprand1.replaceAll(" ", "") + operator.replaceAll(" ", "") + oprand2.replaceAll(" ", "");
			qrp.getOrNotQueryresult(oprand1.replaceAll(" ", ""), oprand2.replaceAll(" ", ""),
					operator.replaceAll(" ", "").toLowerCase());
			break;
		case "AndNot":
			oldProcessString = oprand1.replaceAll(" ", "") + operator.replaceAll(" ", "").toLowerCase()
					+ oprand2.replaceAll(" ", "");
			qrp.getAndNotQueryresult(oprand1.replaceAll(" ", ""), oprand2.replaceAll(" ", ""),
					operator.replaceAll(" ", ""));
			break;
		case "NotOr":
			oldProcessString = oprand1.replaceAll(" ", "") + operator.replaceAll(" ", "") + oprand2.replaceAll(" ", "");
			completeQuery = oprand2.replaceAll(" ", "") + operator.replaceAll(" ", "").toLowerCase()
					+ oprand1.replaceAll(" ", "");
			qrp.getOrNotQueryresult(oprand2.replaceAll(" ", ""), oprand1.replaceAll(" ", ""),
					operator.replaceAll(" ", "").toLowerCase());
			break;
		case "NotAnd":
			oldProcessString = oprand1.replaceAll(" ", "") + operator.replaceAll(" ", "") + oprand2.replaceAll(" ", "");
			completeQuery = oprand2.replaceAll(" ", "") + operator.replaceAll(" ", "").toLowerCase()
					+ oprand1.replaceAll(" ", "");
			qrp.getAndNotQueryresult(oprand2.replaceAll(" ", ""), oprand1.replaceAll(" ", ""),
					operator.replaceAll(" ", "").toLowerCase());
			break;
		case "SingleOprandQuery":
			oldProcessString = oprand1.replaceAll(" ", "") + operator.replaceAll(" ", "");
			qrp.getSingleOprandResult(oprand1.replaceAll(" ", ""), operator.replaceAll(" ", ""));

			break;
		}
		return oldProcessString;

	*/
	}


}
