package swiftTry3;

public class NoSpaceException extends Exception{
	
	public NoSpaceException()
	{
		try_file.out.println("No Space Available");
	}
}