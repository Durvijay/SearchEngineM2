package set.queryprocessing;

public class editdistance {

	public static void main(String args[]) {
		String str1 = "laern";
		String str2 = "lantern";

		System.out.println(editDistance(str1, str2, str1.length(), str2.length()));
	}

	private static int editDistance(String userString, String candidateString, int usrStrLen, int candidateStrLen) {
		if (usrStrLen == 0)
			return candidateStrLen;
		if (candidateStrLen == 0)
			return usrStrLen;
		if (userString.charAt(usrStrLen - 1) == candidateString.charAt(candidateStrLen - 1))
			return editDistance(userString, candidateString, usrStrLen - 1, candidateStrLen - 1);
		return 1 + Math.min(Math.min(editDistance(userString, candidateString, usrStrLen, candidateStrLen - 1), editDistance(userString, candidateString, usrStrLen - 1, candidateStrLen)), editDistance(userString, candidateString, usrStrLen - 1, candidateStrLen - 1) 
		);
	}
}
