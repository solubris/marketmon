package solubris.marketmon.requestor;

class GpwData {
	  static short tris[][][] = null;
	  static long sigma[] = null; // 125729

	  GpwData () {
	    int c1, c2, c3;
	    tris = new short[26][26][26];
	    sigma = new long[1];
	    GpwDataInit1.fill(this); // Break into two classes for NS 4.0
	    GpwDataInit2.fill(this); // .. its Java 1.1 barfs on methods > 65K
	    for (c1=0; c1 < 26; c1++) {
	      for (c2=0; c2 < 26; c2++) {
		for (c3=0; c3 < 26; c3++) {
		  sigma[0] += (long) tris[c1][c2][c3];
		} // for c3
	      } // for c2
	    } // for c1
	  } // constructor

	  void set(int x1, int x2, int x3, short v) {
	    tris[x1][x2][x3] = v;
	  } // set()

	  long get(int x1, int x2, int x3) {
	    return (long) tris[x1][x2][x3];
	  } // get()

	  long getSigma() {
	    return sigma[0];
	  } // get()

} // GpwData
