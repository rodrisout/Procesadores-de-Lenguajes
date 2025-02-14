
import java.io.*;
import lexer.*;
import lexer.exceptions.*;


public class Main {
	public static void main(String [] args) {
		Lexer lexer;

		lexer = new Lexer(System.in);
		try {
			while (true) {
				System.out.println(lexer.getToken().toString());
			}
		} catch (LexerEOFException e) {
			System.out.println("EOF");
		} catch (LexerParseException e) {
			System.out.println("[error] bad token");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
