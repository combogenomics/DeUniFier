package bacci.giovanni.deunifier.DeUniFier.itools;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import bacci.giovanni.deunifier.DeUniFier.assignments.Assignments;

/**
 * Standard implementation of an Assignments iterator over a file. 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 * @param <E> the {@link Assignments} type
 */
public abstract class FileAssignmentsIterator<E extends Assignments> {
	
	private BufferedReader reader = null;
	private Path input = null;
	private boolean next = true;
	protected String line = null;
	
	/**
	 * Constructor. When this constructor is called a {@link BufferedReader} is created
	 * in order to read through the input file specified in the given {@link Path}
	 * @param input the input file with the assignments
	 * @throws IOException if an IO error occurs during the creation of the reader
	 */
	public FileAssignmentsIterator(Path input) throws IOException {
		this.input = input;
		this.reader = Files.newBufferedReader(input, Charset.defaultCharset());
		nextLine();
	}

	/**
	 * Returns <code>true</code> if the file has at least another assignment
	 * @return a boolean.
	 */
	public boolean hasNext() {
		return next;
	}

	/**
	 * Abstract method. This method must return an {@link Assignments} type specified 
	 * during the creation of the iterator.
	 * @return an {@link Assignments}
	 * @throws IOException if an IO error occurs reading in the file
	 */
	public abstract E next() throws IOException;
	
	/**
	 * Protected method. This method pick up the next line in the file. If the line is <code>null</code>
	 * a call to the method {@link #hasNext()} will return <code>false</code>.
	 * @throws IOException if an IO error occurs reading in the file
	 */
	protected void nextLine() throws IOException{
		line = reader.readLine();
		if(line  == null){
			next = false;
			reader.close();
		}		
	}
	
	/**
	 * This method creates a new {@link BufferedReader} equal to the one created with the constructor.
	 * @throws IOException if an IO error occurs during the creation of the reader
	 */
	public void reset() throws IOException{
		this.reader = Files.newBufferedReader(input, Charset.defaultCharset());
		next = true;
		nextLine();
	}
}
