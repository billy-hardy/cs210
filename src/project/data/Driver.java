package project.data;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.xml.sax.SAXException;

import project.data.persistor.SAXReader;
import project.statements.ICommand;
import project.statements.ISetCommand;
import project.statements.RSetOperations;
import project.statements.ddl.*;
import project.statements.admin.*;
import project.statements.dml.*;
import project.statements.query.*;

/**
 * This class sets up the database user interface.
 */
public class Driver {

	ICommand[] commands = new ICommand[] {
			new UpdateCommand(),	  	new ExitCommand(),
			new ReadCommand(), 		  	new DeleteCommand(),
			new PrintCommand(),  	  	new BackupCommand(),
			new DefineTableCommand(), 	new RenameCommand(),
			new DropCommand(), 		  	new DefineIndexCommand(),
			new InsertCommand(),		new RestoreCommand() };
	
	ISetCommand[] setCommands = new ISetCommand[] {
			new ProjectCommand(),		new IntersectCommand(),
			new UnionCommand(),  	  	new MinusCommand(),
			new SelectCommand(),	  	new JoinCommand(),
			new SymmetricDifferenceCommand() };
 
	/**
	 * Sets up the user input.
	 */
	public void run(String debug) {
		if(debug.equalsIgnoreCase("debug"))
			BSTree.setToDebug();
		try {
			new SAXReader().saxReader();
		} catch (SAXException e) {
			//System.out.println("File not in correct format");
		} catch (IOException e) {
			//System.out.println("File not found");
		}
		Scanner sc = new Scanner(System.in);
		read(sc);
	}

	/**
	 * Reads the user/file input.
	 *
	 * @param sc the scanner
	 */
	public void read(Scanner sc) {
		NEXT:
			while(true) {
				boolean match = false;
				String in;
				try {
					System.out.print(">");
					in = sc.nextLine();
					while(!in.contains(";")) {
						in = in.concat(" " + sc.nextLine());
					}
				} catch (NoSuchElementException l) {
					break;
				}
				try {
					for(ISetCommand c: setCommands) {
						if(c.matches(in)) {
							RSetOperations rso = RSetOperations.get();
							System.out.println(rso.execute(in));
							match = true;
							break;
						}
					}
					if(!match) {
						for (ICommand c : commands) {
							if(c.matches(in)) {
								c.execute();
								match = true;
								break;
							}
						}
					}
				} catch (HardyException e) {
					System.out.println(e.getMessage());
//					e.printStackTrace();
					continue NEXT;
				}
				if(!match) {
					System.out.println("No match found");
				}
			}
	}
 
	/**
	 * The main method. Starts the entire database.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		String debug = "";
		if(args.length == 1)
			debug = args[0];
		new Driver().run(debug);
	}

}
