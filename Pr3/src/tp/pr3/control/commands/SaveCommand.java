package tp.pr3.control.commands;

import java.util.Scanner;
import tp.pr3.util.*;
import java.io.*;
import tp.pr3.exceptions.*;
import tp.pr3.logic.multigames.Game;

public class SaveCommand extends Command {

	private boolean filename_confirmed;
	public static final String filenameInUseMsg = "El archivo ya existe, ¿quieres sobreescribirlo? (Y/N)";
	private String _fileName;
	
	public SaveCommand(String filename) {
		super("save <filename>", "Este comando sirve para guardar la partida actual en un fichero.");
		_fileName = filename;
	}

	@Override
	public boolean execute(Game game) throws GameOverException {
		try(BufferedWriter out = new BufferedWriter(new FileWriter(_fileName)))
		{
			game.store(out);
			out.close();
			System.out.println("El archivo se ha guardado con exito." + '\n');
		} catch (IOException ioe) {
			System.out.println("No se ha podido guardar correctamente el archivo.");
		}
		
		return false;
	}

	@Override
	public Command parse(String[] commandWords, Scanner in) throws ExecutionException {
		Command command = null;
		if(commandWords[0].toLowerCase().equals(_commandName)) {
			if(commandWords.length == 2) {
				String filename = confirmFileNameStringForWrite(commandWords[1] + ".txt", in);
				if (filename != null) {
					_fileName = filename;
					command = this;
				}	
			}
			else if (commandWords.length == 1) throw new ExecutionException("Save debe ir seguido de un nombre de archivo.");
			else throw new ExecutionException("Save debe ir seguido de un solo nombre de archivo.");
		}
		return command;
	}
	
	private String confirmFileNameStringForWrite(String filenameString, Scanner in) throws ExecutionException{
		String loadName = filenameString;
		filename_confirmed = false;
		while (!filename_confirmed) {
			if (MyStringUtils.validFileName(loadName)) {
				File file = new File(loadName);
				if (!file.exists() )
					filename_confirmed = true;
				else {
					loadName = getSaveName(filenameString, in);
				}
			} else {
				filename_confirmed = true;
				loadName = null;
				throw new ExecutionException("El nombre del fichero no es válido.");
			}
		}
		return loadName;
	}
	
	public String getSaveName(String filenameString, Scanner in) throws ExecutionException{
		String newFilename = null;
		boolean yesOrNo = false;
		while (!yesOrNo) {
			System.out.print(filenameInUseMsg + ": ");
			String[] responseYorN = in.nextLine().toLowerCase().trim().split("\\s+");
			if (responseYorN.length == 1) {
				switch (responseYorN[0]) {
				case "y":
					yesOrNo = true;
					newFilename = filenameString;
					filename_confirmed = true;
					break;
				case "n":
					yesOrNo = true;
					System.out.println("En tal caso, introduzca un nuevo nombre de archivo: ");
					String[] nombre = in.nextLine().split("\\s+");
					newFilename = nombre[0] + ".txt";
					break;
				default:
				System.out.println("Respuesta no válida.");
				}
			} else 
				System.out.println("La respuesta solo debe tener una palabra.");
		}
		return newFilename;
	}
}
