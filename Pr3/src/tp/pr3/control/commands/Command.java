package tp.pr3.control.commands;

import java.util.Scanner;
import tp.pr3.exceptions.*;
import tp.pr3.logic.multigames.Game;
/**La clase abstracta Command será luego heredada por la clase NoParamsCommand */
public abstract class Command {
	private String _helpText;
	private String _commandText;
	protected final String _commandName;
	
	/**Construye la clase Command utilizando el nombre del comando y la descripción del comando*/
	public Command(String commandInfo, String helpInfo) {
		_commandText = commandInfo;
		_helpText = helpInfo;
		String[] commandInfoWords = _commandText.split("\\s+");
		_commandName = commandInfoWords[0];
	}
	
	/**Metodo abstracto que es heredo por NoParamsCommand*/
	public abstract boolean execute(Game game) throws GameOverException, ExecutionException;
	
	/**Metodo abstracto que es heredo por NoParamsCommand*/
	public abstract Command parse(String[] commandWords, Scanner in) throws ParseException, ExecutionException;
	
	/**Devuelve un String con el nombre y descripcion del comando*/
	public String helpText() {
		return " " + _commandText + ": " + _helpText;
	}
} 