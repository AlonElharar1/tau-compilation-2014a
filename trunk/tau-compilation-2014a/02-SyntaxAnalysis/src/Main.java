/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

import ic.lexer.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {

		// Validate arguments
		if (args.length < 1) {
			System.out.println("Not cool man... I need a file!");
			return;
		}

		FileInputStream fileStream = null;

		try {
			// Open the file and extract tokens
			fileStream = new FileInputStream(args[0]);
			List<Token> tokens = new Lexer(fileStream).getAllTokens();

		} catch (LexicalException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("Error reading file...");
		} finally {
			if (fileStream != null)
				fileStream.close();
		}
	}

}
