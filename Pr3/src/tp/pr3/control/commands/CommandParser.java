package tp.pr3.control.commands;

import java.util.Scanner;
import tp.pr3.exceptions.*;

public class CommandParser {
    
	private static Command[] availableCommands = { new HelpCommand(), new ResetCommand(), new ExitCommand(), new MoveCommand(), new RedoCommand(), new UndoCommand(), new PlayCommand(), new SaveCommand(""), new LoadCommand("")};
	
	/**Comprueba que commandWords coincide con alguno de los comandos validos y lo devuelve, si no coincide devuelve null*/
	public static Command parseCommand(String[] commandWords, Scanner in) throws ParseException, ExecutionException{
		Command salida = null;
		for(Command comando: availableCommands){
			if(comando.parse(commandWords, in) != null){
				salida = comando;
			}
		}
		if (salida == null)throw new ParseException();
		return salida;
	}
	
	/**Imprime la informacion(nombre y descripcion) de todos los comandos*/
	public static String commandHelp(){
		String salida ="";
		for(Command comando: availableCommands)
			salida += comando.helpText() + '\n';
		
		return salida;
	}

}
