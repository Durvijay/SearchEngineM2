package set.queryprocessing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;

import com.homework5.IndexWriter;

import set.beans.TokenDetails;
import set.docprocess.PorterStemmer;
import set.docprocess.PositionalInvertedIndex;

/**
 * Created by Durvijay on 9/17/16.
 */
public class KGramIndex {
	public static HashMap<String, List<String>> kgrams = new HashMap<>();
	/*
	 * private List<String> onegram; private List<String> twogram = new
	 * ArrayList<>(); private List<String> threegram = new ArrayList<>();
	 */

	private String tokenSubstring = "";
	public static TreeMap<Integer, List<String>> topDictionarySuggestions = new TreeMap<>();

	public HashMap<String, List<String>> getkgramlist() {
		return kgrams;
	}

	public void generateKgram(String token) {
		// taking as input a single type from the json body
		int i = 0;
		token = "$" + token + "$";
		/*
		 * TreeSet<String> nGrams=getnGrams(token);
		 * 
		 * for (String gram : nGrams) { if (!kgrams.containsKey(gram)) {
		 * kgrams.put(gram, new ArrayList<>(Arrays.asList(token))); } else if
		 * (!kgrams.get(gram).contains(token)) { kgrams.get(gram).add(token); }
		 * }
		 */

		while (i < token.length()) {

			tokenSubstring = token.substring(i, i + 1);

			if (!tokenSubstring.equals("$")) {
				if (!kgrams.containsKey(tokenSubstring)) {
					kgrams.put(tokenSubstring, new ArrayList<>(Arrays.asList(token)));
				} else if (!kgrams.get(tokenSubstring).contains(token)) {
					kgrams.get(tokenSubstring).add(token);
				}
			}

			if (i < token.length() - 1) {
				tokenSubstring = token.substring(i, i + 2);
				if (!kgrams.containsKey(tokenSubstring)) {
					kgrams.put(tokenSubstring, new ArrayList<>(Arrays.asList(token)));
				} else if (!kgrams.get(tokenSubstring).contains(token)) {
					kgrams.get(tokenSubstring).add(token);
				}
			}

			if (i < token.length() - 2) {
				tokenSubstring = token.substring(i, i + 3);
				if (!kgrams.containsKey(tokenSubstring)) {
					kgrams.put(tokenSubstring, new ArrayList<>(Arrays.asList(token)));
				} else if (!kgrams.get(tokenSubstring).contains(token)) {
					kgrams.get(tokenSubstring).add(token);
				}
			}

			// System.out.println(kgrams.get(token.substring(i,i+1)));

			i++;
		}
	}

	/*
	 * private TreeSet<String> getnGrams(String token) { token = "$" + token +
	 * "$"; TreeSet<String> threegram = new
	 * TreeSet<>(Arrays.asList(token.split("(?<=\\G...)"))); ArrayList<String>
	 * twogram = new ArrayList<>(Arrays.asList(token.split("(?<=\\G..)")));
	 * ArrayList<String> onegram = new
	 * ArrayList<>(Arrays.asList(token.split("(?<=\\G.)")));
	 * onegram.remove("$"); onegram.remove("$"); threegram.addAll(onegram);
	 * threegram.addAll(twogram); return threegram;
	 * 
	 * }
	 */

	/*
	 * // to retrieve the list of document that match a given wildcard public
	 * List<TokenDetails> getKGrams(String token1, PositionalInvertedIndex
	 * index) throws IOException { int i = 0; List<TokenDetails> result = new
	 * ArrayList<>(); TreeSet<String> kgramWords = new TreeSet<>(); String[]
	 * splitWords = token1.split("*");
	 * 
	 * 
	 * for (String token : splitWords) { TreeSet<String>
	 * nGrams=getnGrams(token); for (String gram : nGrams) { if
	 * (kgrams.containsKey(gram)) { kgramWords.addAll(kgrams.get(gram)); } }
	 * token = "$" + token + "$"; while (i < token.length()) {
	 * 
	 * if (kgrams.containsKey(token.substring(i, i + 1))) {
	 * kgramWords.addAll(kgrams.get(token.substring(i, i + 1))); }
	 * 
	 * if (i < token.length() - 1) { if (kgrams.containsKey(token.substring(i, i
	 * + 2))) { kgramWords.addAll(kgrams.get(token.substring(i, i + 2)));
	 * 
	 * } }
	 * 
	 * if (i < token.length() - 2) { if (kgrams.containsKey(token.substring(i, i
	 * + 3))) { kgramWords.addAll(kgrams.get(token.substring(i, i + 3))); } }
	 * i++; }
	 * 
	 * List<String> tempo =
	 * kgramWords.stream().distinct().collect(Collectors.toList());
	 * System.out.println("List of words :" + kgramWords.toString()); for
	 * (String words : kgramWords) {
	 * result.addAll(index.getPostings(PorterStemmer.processToken(PorterStemmer.
	 * processWord(words)))); } }
	 * 
	 * return result; }
	 */

	@SuppressWarnings("unchecked")
	public static HashMap<String, List<String>> readKGramsFromFile(String path) {
		try {
			File toRead = new File(path + "/" + "KGram");
			FileInputStream fis = new FileInputStream(toRead);
			ObjectInputStream ois = new ObjectInputStream(fis);

			kgrams = (HashMap<String, List<String>>) ois.readObject();

			ois.close();
			fis.close();
			return kgrams;
		} catch (Exception e) {
			System.out.println(e);
		}
		return kgrams;

	}

	public static List<TokenDetails> getWildCardKGrams(String token1) {

		List<TokenDetails> result = new ArrayList<>();
		HashSet<String> oneGramCandidates = new HashSet<>();
		HashSet<String> twoGramCandidates = new HashSet<>();
		HashSet<String> threeGramCandidates = new HashSet<>();
		HashSet<TokenDetails> list = new HashSet<>();

		boolean threegramCondition = false;
		boolean twogramCondition = false;
		String[] splitWords;
		if (!token1.startsWith("*") && !token1.endsWith("*")) {
			token1 = "$" + token1 + "$";
		} else if (token1.startsWith("*")) {
			token1 = token1 + "$";
		} else {
			token1 = "$" + token1;
		}
		splitWords = token1.split("\\*");
		for (String token : splitWords) {
			threegramCondition = false;
			twogramCondition = false;
			int i = 0;
			while (i < token.length()) {
				// if onegram is present in the kgram

				if (i < token.length() - 2) {
					if (kgrams.containsKey(token.substring(i, i + 3))) {
						threeGramCandidates.addAll(kgrams.get(token.substring(i, i + 3)));
						threegramCondition = true;
						i = i + 2;

					}
				} else if (i < token.length() - 1 && !threegramCondition) {

					if (kgrams.containsKey(token.substring(i, i + 2))) {
						twoGramCandidates.addAll(kgrams.get(token.substring(i, i + 2)));
						i = i + 1;

					}
				} else if (kgrams.containsKey(token.substring(i, i + 1)) && !threegramCondition && !twogramCondition) {
					oneGramCandidates.addAll(kgrams.get(token.substring(i, i + 1)));
				}

				i++;
			}

		}
		threeGramCandidates.addAll(twoGramCandidates);
		threeGramCandidates.addAll(oneGramCandidates);

		for (String x : threeGramCandidates) {
			// splitWords=.replaceAll("[.,!]$", "");
			if (findmanystars(splitWords, x)) {
				List<TokenDetails> res = QueryResultProcessing
						.getPostingsResult(PorterStemmer.processToken(PorterStemmer.processWord(x)), "P");
				list.addAll(res);

			}
		}
		result = list.stream().distinct().collect(Collectors.toList());
		System.out.println("List size " + result.size());
		return result;
	}

	public void getSpellingCorrectionKGrams(String token) {

		new ArrayList<>();
		topDictionarySuggestions = new TreeMap<Integer, List<String>>();
		// List<String> oneGramCandidates = new ArrayList<>();
		List<String> twoGramCandidates = new ArrayList<>();
		new ArrayList<>();
		new HashSet<>();
		List<String> combinedGramsResult = new ArrayList<String>();

		int i = 0;
		/*
		 * TreeSet<String> nGrams = getnGrams(token.toLowerCase()); for (String
		 * grams : nGrams) { if (kgrams.containsKey(grams)) {
		 * combinedGramsResult.addAll(kgrams.get(grams)); } }
		 */

		token = ("$" + token + "$").toLowerCase();
		while (i < token.length()) { // if onegram is present in the kgram
			
			tokenSubstring = token.substring(i, i + 1);
		
				if (!tokenSubstring.equals("$") && kgrams.containsKey(tokenSubstring)) {
					combinedGramsResult.addAll(kgrams.get(tokenSubstring));
				}
			
			
			if (i < token.length() - 2) {
				tokenSubstring = token.substring(i, i + 3);
				if (kgrams.containsKey(tokenSubstring)) {
					// threeGramCandidates = new
					// ArrayList<>(kgrams.get(token.substring(i, i + 3)));
					combinedGramsResult.addAll(kgrams.get(tokenSubstring));

				}
			}

			if (i < token.length() - 1) {
				tokenSubstring = token.substring(i, i + 2);
				if (kgrams.containsKey(tokenSubstring)) {
					// twoGramCandidates = new
					// ArrayList<>(kgrams.get(token.substring(i, i + 2)));
					combinedGramsResult.addAll(kgrams.get(tokenSubstring));
				}
			}

			i++;
		}

		token = token.replace("$", "");
		List<String> usertoken = getnGrams(token);

		Set<String> uniqueWords = new HashSet<String>(combinedGramsResult);
		for (String word : uniqueWords) {
			int frequency = Collections.frequency(combinedGramsResult, word);
			if (frequency > token.length() && word.startsWith(token.substring(0,1))
					&& (word.length() > (token.length() - 3) && (word.length() < (token.length() + 3)))) {
				calulateJaccardCoefficent(word, usertoken, token);
			}
		}

		/*
		 * Collections.sort(combinedGramsResult); int k = 0; for (int j = 0; j <
		 * combinedGramsResult.size(); j++) { if
		 * ((combinedGramsResult.get(j)).startsWith(token.substring(0, 1)) &&
		 * (combinedGramsResult.get(j).length() > (token.length() - 2) &&
		 * (combinedGramsResult.get(j).length() < (token.length() + 3)))) { ++k;
		 * if (k > token.length() && (j != combinedGramsResult.size() - 1 &&
		 * !combinedGramsResult.get(j +
		 * 1).equalsIgnoreCase(combinedGramsResult.get(j)))) {
		 * 
		 * k = 0; calulateJaccardCoefficent(combinedGramsResult.get(j),
		 * usertoken, token); } else if (k > token.length() && j ==
		 * combinedGramsResult.size() - 1) { k = 0;
		 * calulateJaccardCoefficent(combinedGramsResult.get(j), usertoken,
		 * token); } } }
		 */ System.out.println("List size " + topDictionarySuggestions.size());
	}

	private void calulateJaccardCoefficent(String finalCandidates, List<String> usertoken, String token) {
		int previousValue = 100;
		// List<String> editDistResult = new ArrayList<>();
		List<String> cadidategram = getnGrams(finalCandidates);
		float calculatedValue = getJCvalue(usertoken, cadidategram);
		System.out.println(finalCandidates +" : "+calculatedValue);
		if (calculatedValue >= (0.37)) {
			int editDisRes = editDistance(token, finalCandidates, token.length(), finalCandidates.length());
			if (editDisRes <= previousValue) {
				System.out.println(finalCandidates + " : " + calculatedValue + " - editDistance: " + editDisRes);

				if (!topDictionarySuggestions.containsKey(editDisRes)) {
					// editDistResult.add(finalCandidates);
					topDictionarySuggestions.put(editDisRes, new ArrayList<>(Arrays.asList(finalCandidates)));
				} else {
					topDictionarySuggestions.get(editDisRes).add(finalCandidates);
					/*
					 * editDistResult =
					 * topDictionarySuggestions.get(editDisRes);
					 * editDistResult.add(finalCandidates);
					 * topDictionarySuggestions.put(editDisRes, editDistResult);
					 */
				}
			}
		}

	}

	private int editDistance(String userString, String candidateString, int usrStrLen, int candidateStrLen) {
		if (usrStrLen == 0)
			return candidateStrLen;
		if (candidateStrLen == 0)
			return usrStrLen;
		if (userString.charAt(usrStrLen - 1) == candidateString.charAt(candidateStrLen - 1))
			return editDistance(userString, candidateString, usrStrLen - 1, candidateStrLen - 1);
		return 1 + Math.min(
				Math.min(editDistance(userString, candidateString, usrStrLen, candidateStrLen - 1),
						editDistance(userString, candidateString, usrStrLen - 1, candidateStrLen)),
				editDistance(userString, candidateString, usrStrLen - 1, candidateStrLen - 1));
	}

	private float getJCvalue(List<String> usertoken, List<String> cadidategram) {
		List<?> union = Stream.concat(usertoken.stream(), cadidategram.stream()).distinct()
				.collect(Collectors.toList());
		cadidategram.retainAll(usertoken);
		return (float) cadidategram.size() / union.size();
	}

	private List<String> getnGrams(String token) {
		List<String> nGramResult = new ArrayList<>();
		token = "$" + token + "$";
		for (int i = 0; i < token.length(); i++) {
			
			tokenSubstring = token.substring(i, i + 1);
			if (!tokenSubstring.equals("$") && !nGramResult.contains(tokenSubstring))
				nGramResult.add(token.substring(i, i + 1));
						
			if (i < token.length() - 1) {
				tokenSubstring = token.substring(i, i + 2);
				if (!nGramResult.contains(tokenSubstring))
					nGramResult.add(tokenSubstring);
			}
	
			if (i < token.length() - 2) {
				tokenSubstring = token.substring(i, i + 3);
				if (!nGramResult.contains(tokenSubstring))
					nGramResult.add(tokenSubstring);
			}

		}
		return nGramResult;
	}

	public static boolean findmanystars(String[] splitWords, String testWord) {
		for (String token : splitWords) {
			if (token.startsWith("$")) {
				if (!testWord.startsWith(token.replace("$", ""))) {
					return false;
				}
			} else if (token.endsWith("$")) {
				if (!testWord.endsWith(token.replace("$", ""))) {
					return false;
				}

			} else {
				if (!testWord.contains(token)) {
					return false;
				}
			}

		}
		System.out.println("matched words " + testWord);
		return true;

	}

}

/*
 * public static void main(String[] args) throws IOException {
 * //System.out.println(Arrays.deepToString((generateKgram("hell"))));
 * NaiveInvertedIndex nv=new NaiveInvertedIndex(); StringBuilder sb=new
 * StringBuilder(); DocumentProcessing dp=new DocumentProcessing(); //takes one
 * file as input and generates kgrams String
 * body=dp.parseFile("/home/surabhi/Desktop/SET/1000files/file1.json");
 * KGramIndex ki=new KGramIndex(); SimpleTokenStream st=new
 * SimpleTokenStream(body); //for every token in the file
 * while(st.hasNextToken()) { String term=st.nextToken();
 * String[]type=dp.preprocessToken(term); ki.generateKgram(type); } // Iterator
 * it = ki.kgrams.entrySet().iterator();
 *//*
	 * while (it.hasNext()) { Map.Entry pair = (Map.Entry)it.next();
	 * ArrayList<String>str=(ArrayList)pair.getValue();
	 * System.out.println(pair.getKey() + " = " +
	 * Arrays.toString(str.toArray()));
	 * 
	 * }
	 *//*
	 * HashSet<Integer>arr=ki.getKGrams("coral");
	 * System.out.println(Arrays.toString(arr.toArray())); }
	 */
