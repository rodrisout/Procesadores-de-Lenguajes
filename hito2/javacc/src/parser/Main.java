package parser;

import java.io.FileReader;

public class Main {
	public static void main(String[] args) throws Exception {
		String[] dict = { "tests/04", "tests/05", "tests/06", "tests/07", "tests/08", "tests/sample1", "tests/sample2", "tests/sample3" };
		for (int i = 0; i < dict.length; ++i) {
			AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTinyDJ(new FileReader(dict[i] + ".in"));
			System.out.println("-- file -- " + dict[i]);
			asint.disable_tracing();
			try {
				asint.programa();
			} catch (ParseException ex) {
				System.out.println("ERROR_SINTACTICO");
			}
		}
	}
}