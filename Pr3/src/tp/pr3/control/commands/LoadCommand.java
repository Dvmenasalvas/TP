package tp.pr3.control.commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import tp.pr3.exceptions.*;

import tp.pr3.util.*;
import tp.pr3.logic.multigames.Game;
import tp.pr3.logic.multigames.GameType;

public class LoadCommand extends Command {
	
	private String _fileName;
	
	public LoadCommand(String filename) {
		super("load <filename>", "Este comando sirve para cargar una partida a medias de un fichero.");
		_fileName = filename;
	}

	@Override
	public boolean execute(Game game) throws GameOverException, ExecutionException {
		try(BufferedReader in = new BufferedReader(new FileReader(_fileName)))
		{
			GameType type = game.load(in);
			in.close();
			System.out.println("Se ha cargado correctamente el juego " + type);
		} catch (IOException ioe) {
			System.out.println("Ha habido un problema con la lectura del archivo.");
			return false;
		}
		return true;
	}

	@Override
	public Command parse(String[] commandWords, Scanner in) throws ExecutionException {
		Command command = null;
		if(commandWords[0].toLowerCase().equals(_commandName)) {
			if(commandWords.length == 2) {
				String filename = confirmFileNameStringForRead(commandWords[1] + ".txt", in);
				if (filename != null) {
					_fileName = filename;
					command = this;
				}
			}
			else if (commandWords.length == 1) throw new ExecutionException("Load debe ir seguido de un nombre de archivo.");
			else throw new ExecutionException("Load debe ir seguido de un solo nombre de archivo.");
		}
		return command;
	}

	private String confirmFileNameStringForRead(String filenameString, Scanner in) throws ExecutionException{
		String loadName = filenameString;
		if (MyStringUtils.validFileName(loadName)) {
			File file = new File(loadName);
			if (!file.exists() ) {
				loadName = null;
				throw new ExecutionException("El fichero referenciado no existe.");
			}
		} else {
			loadName = null;
			throw new ExecutionException("El nombre del fichero no es válido.");
		}
		return loadName;
	}
	
	
}
