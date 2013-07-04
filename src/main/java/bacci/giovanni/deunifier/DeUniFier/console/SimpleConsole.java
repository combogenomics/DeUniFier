package bacci.giovanni.deunifier.DeUniFier.console;

import bacci.giovanni.deunifier.DeUniFier.io.FastaSequenceWriter;
import bacci.giovanni.deunifier.DeUniFier.io.FrequencyWriter;
import bacci.giovanni.deunifier.DeUniFier.io.SequenceWriter;
import bacci.giovanni.deunifier.DeUniFier.io.SimpleFrequencyWriter;
import bacci.giovanni.deunifier.DeUniFier.pipelines.ProcessObserver;
import bacci.giovanni.deunifier.DeUniFier.pipelines.unifier.FileCounter;
import bacci.giovanni.deunifier.DeUniFier.pipelines.unifier.FileStreamCounter;
import bacci.giovanni.deunifier.DeUniFier.pipelines.unifier.SequenceFileCounter;
import bacci.giovanni.deunifier.DeUniFier.pipelines.unifier.TagFileCounterMod;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observer;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class SimpleConsole extends Console {
	private Options unifierOptions = null;
	private CommandLineParser parser = null;
	private HelpFormatter formatter = null;
	private Observer obs = null;

	private static final String VERSION = "0.0.1";

	private static final String DEVELOPER = "\n"
			+ "######################################################\n"
			+ "# Developed by Giovanni Bacci                        #\n"
			+ "#   Dep. of Biology - Florence University            #\n"
			+ "#   CRA-RPS         - Rome                           #\n"
			+ "# giovanni.bacci@unifi.it                            #\n"
			+ "######################################################\n";

	private static final String MAIN_HELP = "\n"
			+ "usage: command [Command Options ... ] \n"
			+ "    commands: \n"
			+ "      unifier    Search for unique sequences and save them into a fasta file and a \n"
			+ "                 frequency table.\n"
			+ "                 Type [unifier -h] for help \n"
			+ "    main options: \n"
			+ "      -h         Print this message. \n"
			+ "      -version   Print version number. \n";

	public SimpleConsole() {
		this.unifierOptions = new Options();
		this.parser = new BasicParser();
		this.formatter = new HelpFormatter();
		this.obs = new ProcessObserver();

		Option help = new Option("h", "Print help");
		Option version = new Option("version", "Print version");

		OptionBuilder.withArgName("files path");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("The path to the input sequences files. "
				+ "This path has to be a directory and all files in "
				+ "this directory will be scanned.");
		Option inputFile =

		OptionBuilder.create("in");
		OptionBuilder.withArgName("Output files prefix");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("Prefix to assign at each output file.");
		Option outputPrefix = OptionBuilder.create("pref");

		OptionBuilder.withArgName("Task number");
		OptionBuilder.hasArg();
		OptionBuilder
				.withDescription("Task to execute 1): fast method (no id will be collected). "
						+ "2): normal method (ids will be collected at the end). "
						+ "3): slow method (ids and sequences will be collected together");
		Option pipeline =

		OptionBuilder.create("task");

		OptionBuilder.withArgName("Output Path");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("Output directory path");
		Option outputDir = OptionBuilder.create("out");

		OptionBuilder.withArgName("Number of thread");
		OptionBuilder.hasArg();
		OptionBuilder.withDescription("Maximum number of active threads. "
				+ "This option is valid only in the option" + pipeline.getOpt()
				+ "is set to 2 or 3.");
		Option threadNumber = OptionBuilder.create("thread");

		this.unifierOptions.addOption(version);
		this.unifierOptions.addOption(help);
		this.unifierOptions.addOption(inputFile);
		this.unifierOptions.addOption(outputPrefix);
		this.unifierOptions.addOption(pipeline);
		this.unifierOptions.addOption(outputDir);
		this.unifierOptions.addOption(threadNumber);

		waitForInput();
	}

	private void parseUnifierOptions(String[] args) {
		String VERSION = "0.0.1";
		List<Path> pathList = null;
		Path output = null;
		FileCounter c = null;
		int threadnum = 1;
		String prefix = null;
		try {
			CommandLine cmd = this.parser.parse(this.unifierOptions, args);
			if (cmd.hasOption("h")) {
				this.formatter.printHelp("unifier", this.unifierOptions, true);
				return;
			}
			if (cmd.hasOption("version")) {
				System.out.println("Version: " + VERSION);
				return;
			}
			if (cmd.hasOption("in")) {
				try {
					pathList = new ArrayList<Path>();
					DirectoryStream<Path> stream = Files
							.newDirectoryStream(Paths.get(
									cmd.getOptionValue("in"), new String[0]));
					for (Path p : stream)
						pathList.add(p);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (cmd.hasOption("out")) {
				output = Paths.get(cmd.getOptionValue("out"), new String[0]);
			}
			if (cmd.hasOption("thread")) {
				threadnum = Integer.valueOf(cmd.getOptionValue("thread"))
						.intValue();
			}
			if (cmd.hasOption("task")) {
				int task = Integer.valueOf(cmd.getOptionValue("task"))
						.intValue();
				if (task == 1) {
					c = new SequenceFileCounter(pathList);
				} else if (task == 2) {
					c = new TagFileCounterMod(pathList, threadnum);
				} else if (task == 3) {
					c = new FileStreamCounter(pathList, threadnum);
				} else {
					c = new SequenceFileCounter(pathList);
				}
				c.addObserver(this.obs);
			}
			if (cmd.hasOption("pref")) {
				prefix = cmd.getOptionValue("pref");
			}

			String sequencesFileName = prefix + "_seq.fasta";
			String tableFileName = prefix + "_tab.csv";
			String idFileName = prefix + "_ids.txt";

			BufferedWriter sbw = Files.newBufferedWriter(
					output.resolve(sequencesFileName),
					Charset.defaultCharset(), new OpenOption[0]);
			BufferedWriter fbw = Files.newBufferedWriter(
					output.resolve(tableFileName), Charset.defaultCharset(),
					new OpenOption[0]);
			BufferedWriter idw = Files.newBufferedWriter(
					output.resolve(idFileName), Charset.defaultCharset(),
					new OpenOption[0]);

			SequenceWriter sw = new FastaSequenceWriter(sbw);
			FrequencyWriter fw = new SimpleFrequencyWriter(";", fbw);

			c.writeOutput(sw, fw, idw);
		} catch (ParseException e) {
			// TODO
			e.printStackTrace();
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
	}

	private void parseCommandsOptions(String[] args) {
		if (args[0].equals("unifier")) {
			parseUnifierOptions((String[]) Arrays.copyOfRange(args, 1,
					args.length));
			return;
		}

		for (String s : args) {
			if (s.equals("-h")) {
				System.out.println(MAIN_HELP);
				System.out.println(DEVELOPER);
				return;
			}
			if (s.equals("-version")) {
				System.out.println("Version: " + VERSION);
				return;
			}
		}
	}

	protected void act(String line) {
		parseCommandsOptions(line.split(" "));
	}
}