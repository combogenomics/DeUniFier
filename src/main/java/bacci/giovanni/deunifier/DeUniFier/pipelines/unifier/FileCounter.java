package bacci.giovanni.deunifier.DeUniFier.pipelines.unifier;

import bacci.giovanni.deunifier.DeUniFier.io.FrequencyWriter;
import bacci.giovanni.deunifier.DeUniFier.io.SequenceWriter;
import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;
import java.util.Observable;

public abstract class FileCounter extends Observable {
	protected List<Path> pathList = null;
	protected long numSeq;
	protected long scannedSeq;

	public FileCounter(List<Path> list) {
		this.pathList = list;
		this.numSeq = 0L;
		this.scannedSeq = 0L;
	}

	protected synchronized void writeSequence(Sequence s, SequenceWriter wr) {
		wr.writeSequence(s);
		notifyAll();
	}

	protected synchronized void writeId(String id, Writer writer)
			throws IOException {
		writer.write(id);
		writer.write(System.lineSeparator());
		writer.flush();
		notifyAll();
	}

	protected void setDoing(String doing) {
		setChanged();
		notifyObservers(doing);
	}

	protected abstract void incrementProgress();

	public abstract void writeOutput(SequenceWriter paramSequenceWriter,
			FrequencyWriter paramFrequencyWriter, Writer paramWriter)
			throws IOException;
}