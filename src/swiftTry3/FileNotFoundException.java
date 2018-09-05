package swiftTry3;

public class FileNotFoundException extends Exception {

	FileNotFoundException()
	{
		try_file.out.println("File not found!");
	}
}
