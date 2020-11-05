package tp.pr2.control.commands;

import tp.pr2.control.Controller;

public class CommandParser {
    
	private static Command[] availableCommands = { new HelpCommand(), new ResetCommand(), new ExitCommand(), new MoveCommand(), new RedoCommand(), new UndoCommand(), new PlayCommand()};
	
	/**Comprueba que commandWords coincide con alguno de los comandos validos y lo devuelve, si no coincide devuelve null*/
	public static Command parseCommand(String[] commandWords, Controller controller){
		Command salida = null;
		for(Command comando: availableCommands){
			if(comando.parse(commandWords, controller) != null){
				salida = comando;
			}
		}
		if(salida == null) controller.setNoPrintGameState();
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
