package CLI;

public class CommandLineArguments {

	public static void main(String[] args) {
		System.out.println("Number of Command Line Argument = " + args.length);

		for (int i = 0; i < args.length; i++) {
			System.out.println(String.format("Command Line Argument %d is %s", i, args[i]));
		}
	}

}

//	java CommandLineArguments Biswajit Samal

//	java Cmd Value1 Value2
