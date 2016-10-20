package set.queryprocessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import set.beans.TokenDetails;
import set.docprocess.BiWordIndexing;
import set.docprocess.PorterStemmer;
import set.docprocess.PositionalInvertedIndex;

/**
 * Class QueryResultProcessing
 * 
 * @author Durvijay Sharma
 * @author Mangesh Adalinge
 * @author Surabhi Dixit
 *
 */
public class QueryResultProcessing {

	private HashMap<String, List<TokenDetails>> results = new HashMap<>();
	private Integer kNearVal = 0;
	private String message = "";
    private PositionalInvertedIndex pindexobj;
    private BiWordIndexing bindexobj;
    private PorterStemmer pStemmer=new PorterStemmer();

    
	public PositionalInvertedIndex getPindexobj() {
		return pindexobj;
	}

	public void setPindexobj(PositionalInvertedIndex pindexobj) {
		this.pindexobj = pindexobj;
	}

	public BiWordIndexing getBindexobj() {
		return bindexobj;
	}

	public void setBindexobj(BiWordIndexing bindexobj) {
		this.bindexobj = bindexobj;
	}

	public HashMap<String, List<TokenDetails>> getResults() {
		return results;
	}

	public void setResults(HashMap<String, List<TokenDetails>> results) {
		this.results = results;
	}

	/**
	 * gets phrase query results
	 * 
	 * @param oldProcessString:
	 *            input query
	 */
	public void getPhraseQueryresult(String oldProcessString) {
		String[] phraseQuery = pStemmer.processWord(oldProcessString).split(" ");
		if (phraseQuery.length == 1) {
			results.put("\""+phraseQuery[0].trim()+"\"", pindexobj.getPostings(phraseQuery[0]));
		} else if (phraseQuery.length < 3 && kNearVal == 0) {
			getPhraseQueryBiword(phraseQuery[0], phraseQuery[1]);
		} else {

			System.out.println(phraseQuery);
			String firstElement = pStemmer.processWord(phraseQuery[0]);
			List<TokenDetails> firstPhraseElement = new ArrayList<>();
			if (null != pindexobj.getPostings(firstElement))
				firstPhraseElement = pindexobj.getPostings(firstElement);
			else
				message = firstElement + " is not in the list, so phrase query is not possible";
			for (int k = 1; k < phraseQuery.length; k++) {
				String nextElement = pStemmer.processWord(phraseQuery[k]);
				if (nextElement.toLowerCase().startsWith("near/")) {
					String[] temp = nextElement.split("/");
					kNearVal = Integer.parseInt(temp[1]);
					//kNearVal = kNearVal*k;
					
					nextElement = pStemmer.processWord(phraseQuery[k+1]);
					k=k+1;
				} else {
					kNearVal = k;
				}
				List<TokenDetails> temp = new ArrayList<>();
				List<TokenDetails> nextPhraseElement = new ArrayList<>();
				if (null != pindexobj.getPostings(nextElement))
					nextPhraseElement = pindexobj.getPostings(nextElement);
				else
					message = nextElement + " is not in the list, so phrase query is not possible";

				int i = 0, j = 0;

				while (i < firstPhraseElement.size() && j < nextPhraseElement.size()) {
					TokenDetails docList1 = firstPhraseElement.get(i);
					TokenDetails docList2 = nextPhraseElement.get(j);
					if (docList1.getDocId() == docList2.getDocId()) {

						List<Integer> temp1 = matchPosition(docList1.getPosition(), docList2.getPosition(), k,
								kNearVal);
						if (temp1.size() > 0) {
							docList1.setPosition(temp1);
							temp.add(docList1);
						}

						System.out.println(docList1.getDocId() + "-Normal Phrase--");
						i++;
						j++;
					} else if (docList1.getDocId() > docList2.getDocId()) {
						j++;
					} else {
						i++;
					}
				}

				firstPhraseElement = temp;
				temp = new ArrayList<>();

			}
			results.put(oldProcessString.replace(" ", ""), firstPhraseElement);
		}

	}

	/**
	 * gets result for biword phrase query
	 * 
	 * @param term1
	 * @param term2
	 */
	private void getPhraseQueryBiword(String term1, String term2) {
		String term = pStemmer.processWord(term1) + " " + pStemmer.processWord(term2);
		List<TokenDetails> firstPhraseElement = bindexobj.getPostings(term);
		results.put("\""+term1+term2+"\"", firstPhraseElement);

	}

	/**
	 * Matches the token position in document
	 * 
	 * @param position
	 * @param position2
	 * @param positionDiff
	 * @param kNearVal2
	 * @return
	 */
	private List<Integer> matchPosition(List<Integer> position, List<Integer> position2, int positionDiff,
			Integer kNearVal2) {
		int x = 0, y = 0;
		/*if (kNearVal2 > 0) {
			positionDiff = kNearVal2 * positionDiff;
		}*/
		positionDiff=kNearVal2;
		List<Integer> result = new ArrayList<>();
		while (x < position.size() && y < position2.size()) {
			int firstElementPosition = position.get(x);
			int secondElementPosition = position2.get(y);
			if (secondElementPosition - firstElementPosition <= positionDiff
					&& secondElementPosition - firstElementPosition >= 0) {
				result.add(firstElementPosition);
				x++;
				y++;
			} else if (firstElementPosition > secondElementPosition) {
				y++;
			} else {
				x++;
			}
		}
		return result;
	}

	/**
	 * Gets result of and query
	 * 
	 * @param oprand12
	 * @param oprand22
	 */
	public void getAndQueryresult(String oprand12, String oprand22) {
		if (oprand12.startsWith("-") || oprand22.startsWith("-")) {
			getAndNotQueryresult(oprand12, oprand22);
			return;
		}
		String oprand1 = null;
		String oprand2 = null;
		List<TokenDetails> oprand1DocList = new ArrayList<>();
		List<TokenDetails> oprand2DocList = new ArrayList<>();
		List<TokenDetails> andQueryresult = new ArrayList<>();
		System.out.println(pindexobj.getTermCount() + " 125");
		if (oprand12.contains(" ") || oprand12.contains("\"")) {
			oprand1DocList = results.get(oprand12);
			if (oprand1DocList == null)
				oprand1DocList = new ArrayList<>();
		} else {
			oprand1 = pStemmer.processWord(pStemmer.processWordHypen(oprand12)[0]);
			if (null != pindexobj.getPostings(oprand1))
				oprand1DocList = pindexobj.getPostings(oprand1);
			else
				message = oprand1 + " is not in the list, so anding is not possible";
		}
		if (oprand22.contains(" ")) {
			oprand2DocList = results.get(oprand22);
			if (oprand2DocList == null)
				oprand2DocList = new ArrayList<>();
		} else {
			oprand2 = pStemmer.processWord(pStemmer.processWordHypen(oprand22)[0]);
			if (null != pindexobj.getPostings(oprand2))
				oprand2DocList = pindexobj.getPostings(oprand2);
			else
				message = oprand2 + " is not in the list, so anding is not possible";

		}

		int i = 0, j = 0;

		while (oprand1DocList != null && oprand2DocList != null && i < oprand1DocList.size()
				&& j < oprand2DocList.size()) {
			TokenDetails docList1 = oprand1DocList.get(i);
			TokenDetails docList2 = oprand2DocList.get(j);
			if (docList1.getDocId() == docList2.getDocId()) {
				andQueryresult.add(docList1);
				i++;
				j++;
			} else if (docList1.getDocId() > docList2.getDocId()) {
				j++;
			} else {
				i++;
			}
		}
		results.put(oprand12+ " "+ oprand22, andQueryresult);
	}

	/**
	 * Gets result of and not query
	 * 
	 * @param oprand12
	 * @param oprand22
	 */
	public void getAndNotQueryresult(String oprand12, String oprand22) {
		String oprand1 = null;
		String oprand2 = null;
		List<TokenDetails> oprand1DocList = new ArrayList<>();
		List<TokenDetails> oprand2DocList = new ArrayList<>();
		List<TokenDetails> andQueryresult = new ArrayList<>();

		if (oprand12.startsWith("-")) {
			String temp=oprand22;
			oprand22=oprand12;
			oprand12=temp;
		}
		
		if (oprand12.contains(" ") || oprand12.contains("\"")) {
			oprand1DocList = results.get(oprand12);
			if (oprand1DocList == null)
				oprand1DocList = new ArrayList<>();
		} else {
			oprand1 = pStemmer.processWord(pStemmer.processWordHypen(oprand12)[0]);
			if (null != pindexobj.getPostings(oprand1))
				oprand1DocList = pindexobj.getPostings(oprand1);
			else
				message = oprand1 + " is not in the list, so anding is not possible";
		}
		if (oprand22.contains(" ")) {
			oprand2DocList = results.get(oprand22);
			if (oprand2DocList == null)
				oprand2DocList = new ArrayList<>();
		} else {
			oprand2 = pStemmer.processWord(pStemmer.processWordHypen(oprand22)[0]);
			if (null != pindexobj.getPostings(oprand2))
				oprand2DocList = pindexobj.getPostings(oprand2);
			else
				message = oprand2 + " is not in the list, so anding is not possible";

		}

		int i = 0, j = 0;
		TokenDetails docList1 = new TokenDetails();
		TokenDetails docList2 = new TokenDetails();
		while ((oprand1DocList.size() > i || (oprand2DocList.size() > j))) {

			if (oprand1DocList.size() <= i && oprand2DocList != null && oprand2DocList.size() > j) {

				break;
			}
			if (oprand2DocList.size() <= j && oprand1DocList != null && oprand1DocList.size() > i) {
				for (int k = i; k < oprand1DocList.size(); k++) {
					docList1 = oprand1DocList.get(k);
					andQueryresult.add(docList1);
				}
				break;
			}
			docList1 = oprand1DocList.get(i);

			docList2 = oprand2DocList.get(j);

			if (docList1.getDocId() > docList2.getDocId()) {

				j++;
			} else if (docList2.getDocId() > docList1.getDocId()) {
				andQueryresult.add(docList1);
				i++;
			} else {
				i++;
				j++;
			}
		}
		results.put(oprand12 + " " + oprand22, andQueryresult);
	}

	/**
	 * Gets result for single word query
	 * 
	 * @param oprand12
	 */
	public void getSingleOprandResult(String oprand12) {
		results.put(oprand12.trim(), pindexobj.getPostings(pStemmer.processWord(pStemmer.processWordHypen(oprand12)[0])));
	}

	/**
	 * Gets results for or query
	 * 
	 * @param oprand12
	 * @param oprand22
	 */
	public void getOrQueryresult(String oprand12, String oprand22) {
		if (oprand12.startsWith("-") || oprand22.startsWith("-")) {
			getAndNotQueryresult(oprand12, oprand22);
			return;
		}
		String oprand1 = null;
		String oprand2 = null;

		List<TokenDetails> orQueryresult = new ArrayList<>();
		List<TokenDetails> oprand1DocList = new ArrayList<>();
		List<TokenDetails> oprand2DocList = new ArrayList<>();

		if (oprand12.contains(" ") || oprand12.contains("\"")) {
			oprand1DocList = results.get(oprand12);
			if (oprand1DocList == null)
				oprand1DocList = new ArrayList<>();
		} else {
			oprand1 = pStemmer.processWord(pStemmer.processWordHypen(oprand12)[0]);
			if (null != pindexobj.getPostings(oprand1))
				oprand1DocList = pindexobj.getPostings(oprand1);
			else
				message = oprand1 + " is not in the list, so oring is not possible";

		}
		if (oprand22.contains(" ")) {
			oprand2DocList = results.get(oprand22);
			if (oprand2DocList == null)
				oprand2DocList = new ArrayList<>();
		} else {
			oprand2 = pStemmer.processWord(pStemmer.processWordHypen(oprand22)[0]);
			if (null != pindexobj.getPostings(oprand2))
				oprand2DocList = pindexobj.getPostings(oprand2);
			else
				message = oprand2 + " is not in the list, so anding is not possible";

		}

		int i = 0, j = 0;
		TokenDetails docList1 = new TokenDetails();
		TokenDetails docList2 = new TokenDetails();
		while ((oprand1DocList.size() > i || (oprand2DocList.size() > j))) {

			if (oprand1DocList.size() <= i && oprand2DocList != null && oprand2DocList.size() > j) {
				for (int k = j; k < oprand2DocList.size(); k++) {
					docList2 = oprand2DocList.get(k);
					orQueryresult.add(docList2);
				}
				break;
			}
			if (oprand2DocList.size() <= j && oprand1DocList != null && oprand1DocList.size() > i) {
				for (int k = i; k < oprand1DocList.size(); k++) {
					docList1 = oprand1DocList.get(k);
					orQueryresult.add(docList1);
				}
				break;
			}
			docList1 = oprand1DocList.get(i);

			docList2 = oprand2DocList.get(j);

			if (docList1.getDocId() > docList2.getDocId()) {
				orQueryresult.add(docList2);
				j++;
			} else if (docList2.getDocId() > docList1.getDocId()) {
				orQueryresult.add(docList1);
				i++;
			} else {
				orQueryresult.add(docList1);
				i++;
				j++;
			}
		}
		results.put(oprand12 + " + " + oprand22, orQueryresult);
	}

	/**
	 * Gets the result for or not query
	 * 
	 * @param oprand12
	 * @param oprand22
	 */
	public void getOrNotQueryresult(String oprand12, String oprand22) {
		String oprand1 = null;
		String oprand2 = null;

		List<TokenDetails> orQueryresult = new ArrayList<>();
		List<TokenDetails> oprand1DocList = new ArrayList<>();
		List<TokenDetails> oprand2DocList = new ArrayList<>();

		if (oprand12.startsWith("-")) {
			String temp=oprand22;
			oprand22=oprand12;
			oprand12=temp;
		}
		if (oprand12.contains(" ") || oprand12.contains("\"")) {
			oprand1DocList = results.get(oprand12);
			if (oprand1DocList == null)
				oprand1DocList = new ArrayList<>();
		} else {
			oprand1 = pStemmer.processWord(pStemmer.processWordHypen(oprand12)[0]);
			if (null != pindexobj.getPostings(oprand1))
				oprand1DocList = pindexobj.getPostings(oprand1);
			else
				message = oprand1 + " is not in the list, so oring is not possible";

		}
		if (oprand22.contains(" ")) {
			oprand2DocList = results.get(oprand22);
			if (oprand2DocList == null)
				oprand2DocList = new ArrayList<>();
		} else {
			oprand2 = pStemmer.processWord(pStemmer.processWordHypen(oprand22)[0]);
			if (null != pindexobj.getPostings(oprand2))
				oprand2DocList = pindexobj.getPostings(oprand2);
			else
				message = oprand2 + " is not in the list, so anding is not possible";

		}

		int i = 0, j = 0;
		TokenDetails docList1 = new TokenDetails();
		TokenDetails docList2 = new TokenDetails();
		while ((oprand1DocList.size() > i || (oprand2DocList.size() > j))) {

			if (oprand1DocList.size() <= i && oprand2DocList != null && oprand2DocList.size() > j) {

				break;
			}
			if (oprand2DocList.size() <= j && oprand1DocList != null && oprand1DocList.size() > i) {
				for (int k = i; k < oprand1DocList.size(); k++) {
					docList1 = oprand1DocList.get(k);
					orQueryresult.add(docList1);
				}
				break;
			}
			docList1 = oprand1DocList.get(i);

			docList2 = oprand2DocList.get(j);

			if (docList1.getDocId() > docList2.getDocId()) {
				j++;
			} else if (docList2.getDocId() > docList1.getDocId()) {
				orQueryresult.add(docList1);
				i++;
			} else {
				orQueryresult.add(docList1);
				i++;
				j++;
			}
		}
		results.put(oprand12+ " + " + oprand22, orQueryresult);
	}
/*//takes as input the individual   app*le  *ball* app*
	
	appy
	pie 
	$a
	
	
	//"appl*e" 
*/	/**
	 * generate kgrams 1,2 3
	 * 
	 * merge all tokens and get the document list
	 * @param group
	 */
	public void getWildCardQueryResult(String group) {
		List<TokenDetails> kgramQueryresult = new ArrayList<>();
		
		
		results.put(group, kgramQueryresult);
		
	}

}
