package bacci.giovanni.deunifier.DeUniFier.freq;

import java.io.Serializable;

public abstract interface TaggedFrequency
  extends Serializable
{
  public abstract long getCount(Comparable<?> paramComparable);
  
  public abstract void addValue(Comparable<?> paramComparable);
  
  public abstract String getTabbedString(String paramString, boolean paramBoolean);
  
  public abstract String[] getTags();
  
  public abstract void setTags(String[] paramArrayOfString);
  
  public abstract void merge(TaggedFrequency paramTaggedFrequency);
}