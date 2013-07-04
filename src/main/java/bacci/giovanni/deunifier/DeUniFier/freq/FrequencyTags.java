package bacci.giovanni.deunifier.DeUniFier.freq;

import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public abstract interface FrequencyTags
  extends Serializable
{
  public abstract void addSequence(Sequence paramSequence, String paramString);
  
  public abstract long getCount(Sequence paramSequence, String paramString);
  
  public abstract Collection<Sequence> getSequences();
  
  public abstract Collection<String> getTags();
  
  public abstract Iterator<Map.Entry<Sequence, TaggedFrequency>> getEntrySetIterator();
  
  public abstract long getUniqueCount();
}