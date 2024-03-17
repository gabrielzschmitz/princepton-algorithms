/*
  Program Description:
  --------------------
  This Java program, named HelloGoodbye.java, takes two names as command-line
  arguments and prints corresponding hello and goodbye messages. The main
  objective is to greet the provided names and bid them farewell in a specific
  order.

  Implementation Details:
  -----------------------
  - Validates whether exactly two names are provided as command-line arguments.
    If not, it prints a usage message and exits gracefully.
  - The main method processes command-line arguments and prints messages.
  - Retrieves provided names from the command-line arguments and stores them.
  - Prints hello messages with names in the same order as provided, followed by
    goodbye messages with names in reverse order.

  Usage Example:
  --------------
  To execute this program, run:
  java HelloGoodbye <name1> <name2>
  Where <name1> and <name2> are the names to greet and bid farewell.

  Note: Follows a specific greeting and farewell protocol, with hello messages
  maintaining the original order of names and goodbye messages reversing the
  order of names.
*/
public class HelloGoodbye {
  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println("Usage: java HelloGoodbye <name1> <name2>");
      return;
    }
    String name1 = args[0];
    String name2 = args[1];

    System.out.println("Hello " + name1 + " and " + name2 + ".");
    System.out.println("Goodbye " + name2 + " and " + name1 + ".");
  }
}
