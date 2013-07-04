package bacci.giovanni.deunifier.DeUniFier.io;

import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;

public abstract interface SequenceReader {
	public abstract Sequence nextSequence(boolean hasId);

	public abstract boolean hasNext();
}