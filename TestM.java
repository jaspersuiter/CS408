import org.junit.*;
import static org.junit.Assert.*;

import java.io.PrintStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;

public class TestM {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
	M M = new M();

	@Before
	public void setUpStreams() {
			System.setOut(new PrintStream(outContent));
	}

	@After
	public void restoreStreams() {
			System.setOut(originalOut);
	}

	@Test
	public void testNodeCoverage() {
		M.m("", 0);
		assertEquals("zero\n", outContent.toString());
		M.m("a", 1);
		assertEquals("a", outContent.toString());
		M.m("ab", 2);
		assertEquals("b", outContent.toString());
	}

	@Test
	public void testEdgeCoverage() {
		M.m("ab", 1);
		assertEquals("a", outContent.toString());
	}

	@Test
	public void testEdgePairCoverage() {
		M.m("abc", 2);
		assertEquals("a\n", outContent.toString());
	}

	@Test
	public void testPrimePathCoverage() {
		M.m("abcd", 4);
		assertEquals("", outContent.toString());
	}

}


class M {
	public static void main(String [] argv){
		M obj = new M();
		if (argv.length > 0)
			obj.m(argv[0], argv.length);
	}
	
	public void m(String arg, int i) {
		int q = 1;
		A o = null;
		Impossible nothing = new Impossible();
		if (i == 0)
			q = 4;
		q++;
		switch (arg.length()) {
			case 0: q /= 2; break;
			case 1: o = new A(); new B(); q = 25; break;
			case 2: o = new A(); q = q * 100;
			default: o = new B(); break; 
		}
		if (arg.length() > 0) {
			o.m();
		} else {
			System.out.println("zero");
		}
		nothing.happened();
	}
}

class A {
	public void m() { 
		System.out.println("a");
	}
}

class B extends A {
	public void m() { 
		System.out.println("b");
	}
}

class Impossible{
	public void happened() {
		// "2b||!2b?", whatever the answer nothing happens here
	}
}
