package set.queryprocessing;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.homework5.IndexWriter;

import set.beans.TokenDetails;
import set.docprocess.PorterStemmer;
import set.docprocess.PositionalInvertedIndex;

/**
 * Created by surabhi on 9/17/16.
 */
public class KGramIndex {
	private static HashMap<String, List<String>> kgrams = new HashMap<>();
	private List<String> onegram;
	private List<String> twogram = new ArrayList<>();
	private List<String> threegram = new ArrayList<>();
	private PorterStemmer stemmer = new PorterStemmer();
	private String tokenSubstring="";
	public HashMap<String, List<String>> getkgramlist() {
		return kgrams;
	}

	@SuppressWarnings("unchecked")
	public void generateKgram(String token) {
		// taking as input a single type from the json body
		// earlier was taking a string[] from the document processing
		int i = 0;
		// for(String token:str) {
		while (i < token.length()) {
			tokenSubstring=token.substring(i, i + 1);
			
			if (!kgrams.containsKey(tokenSubstring)) {
				onegram = new ArrayList<>();
				onegram.add(token);
				kgrams.put(tokenSubstring, onegram);
			}else if (!kgrams.get(tokenSubstring).contains(token)) {
				kgrams.get(tokenSubstring).add(token);
			}  
			
			String tokentemp = "$" + token + "$";
			if (i < token.length() - 1) {
				tokenSubstring=tokentemp.substring(i, i + 2);
				if (!kgrams.containsKey(tokenSubstring)) {
					twogram = new ArrayList<>();
					twogram.add(token);
					kgrams.put(tokenSubstring, twogram);
				}else if (!kgrams.get(tokenSubstring).contains(token)) {
					kgrams.get(tokenSubstring).add(token);
				}  
			}

			if (i < token.length() - 2) {
				tokenSubstring=tokentemp.substring(i, i + 3);
				if (!kgrams.containsKey(tokenSubstring)) {
					threegram = new ArrayList<>();
					threegram.add(token);
					kgrams.put(tokenSubstring, threegram);
				}else if (!kgrams.get(tokenSubstring).contains(token)) {
					kgrams.get(tokenSubstring).add(token);
				}  
			}

			// System.out.println(kgrams.get(token.substring(i,i+1)));

			i++;
		}
	}

	// to retrieve the list of document that match a given wildcard
	public List<TokenDetails> getKGrams(String token1, PositionalInvertedIndex index) throws IOException {
		int i = 0;
		List<TokenDetails> result = new ArrayList<>();
		List<String> kgramWords = new ArrayList<>();
		String[] splitWords = token1.split("*");
		for (String token : splitWords) {
			while (i < token.length()) {

				if (kgrams.containsKey(token.substring(i, i + 1))) {
					kgramWords.addAll(kgrams.get(token.substring(i, i + 1)));
				}
				String tokentemp = "$" + token + "$";
				if (i < token.length() - 1) {
					if (kgrams.containsKey(tokentemp.substring(i, i + 2))) {
						kgramWords.addAll(kgrams.get(tokentemp.substring(i, i + 2)));

					}
				}

				if (i < token.length() - 2) {
					if (kgrams.containsKey(tokentemp.substring(i, i + 3))) {
						kgramWords.addAll(kgrams.get(tokentemp.substring(i, i + 3)));
					}
				}
				i++;
			}

			List<String> tempo = kgramWords.stream().distinct().collect(Collectors.toList());
			System.out.println("List of words :" + tempo.toString());
			for (String string : tempo) {
				result.addAll(index.getPostings(stemmer.processToken(stemmer.processWord(string))));
			}
		}

		return result;
	}

	// to perform conjunction of two postings
	public ArrayList<Integer> mergePostings(ArrayList<Integer> x, ArrayList<Integer> y) {
		int i = 0, j = 0;
		ArrayList<Integer> merge = new ArrayList<>();
		while (i < x.size() || j < y.size()) {
			// if the two values are equal add
			if (x.get(i) == y.get(j)) {
				merge.add(x.get(i));
				i++;
				j++;
			} else if (x.get(i) < y.get(j)) {
				i++;
			} else
				j++;

		}
		return merge;

	}

	// filter the candidate types for a given query
	public HashSet<String> filterPostings(HashSet<String> candidate, String query) {
		// WildCard wc=new WildCard();
		HashSet<String> sb = new HashSet<>();
		if (candidate != null) {
			for (String s : candidate) {
				/*
				 * if(wc.isMatchRecursive(s,query)) { System.out.print(s+" ");
				 * sb.add(s); }
				 */
			}
		}
		return sb;
	}

	public void buildKgram(String type) throws IOException {
		// takes as input the type from a single json file
		/*
		 * NaiveInvertedIndex nv=new NaiveInvertedIndex(); StringBuilder sb=new
		 * StringBuilder(); DocumentProcessing dp=new DocumentProcessing();
		 * //takes one file as input and generates kgrams String
		 * body=dp.parseFile(x);
		 * 
		 * SimpleTokenStream st=new SimpleTokenStream(body);
		 */
		// for every token in the file
		/*
		 * while(st.hasNextToken()) { String term=st.nextToken();
		 * 
		 * }
		 */
		// Iterator it = ki.kgrams.entrySet().iterator();
		/*
		 * while (it.hasNext()) { Map.Entry pair = (Map.Entry)it.next();
		 * ArrayList<String>str=(ArrayList)pair.getValue();
		 * System.out.println(pair.getKey() + " = " +
		 * Arrays.toString(str.toArray()));
		 * 
		 * }
		 */
		// String[]type=dp.preprocessToken(term);
		KGramIndex ki = new KGramIndex();
		ki.generateKgram(type);
		// HashSet<Integer>arr=ki.getKGrams("coral");
		// System.out.println(Arrays.toString(arr.toArray()));
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
