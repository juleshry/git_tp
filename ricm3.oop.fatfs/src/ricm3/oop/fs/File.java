package ricm3.oop.fs;

import java.io.IOException;

import ricm3.filesys.EOFException;
import ricm3.filesys.FSException;
import ricm3.oop.dev.ChainAllocationTable;

public class File extends ricm3.filesys.oop.File {

  FileSystem m_fs;
  byte [] bytes;
  int position;
  int headBlock;

  public File(Directory parent, String name) throws FSException, IOException {
    super(parent, name);
    m_fs = parent.m_fs;
    headBlock = m_fs.m_bat.allocate(ChainAllocationTable.EOF_BLOCK);
    bytes = new byte[0];
    }
  
  public void sync() throws FSException, IOException {
	  ChainOutputStream cos = new ChainOutputStream(m_fs.m_bat,headBlock);
	  cos.write(bytes.length);
	  cos.write(bytes);
	  cos.flush();
  }
  
  public void fil(int head) throws IOException {
	  ChainInputStream cos = new ChainInputStream(m_fs.m_bat,head);
	  int ln = cos.read();
	  bytes = new byte[ln];
	  for (int i = 0; i<ln; i++) {
		  bytes[i] = (byte) cos.read();
	  }
  }

  /*@Override
  public void removed() throws FSException {
    throw new RuntimeException("Not Yet Implemented");
  }*/

  @Override
  public int size()  throws FSException {
    // Where should this method be implemented?
    // Here, on class File, or on class Node?
	  if (!valid()) {
		  throw new FSException("this Directory is not valid");
	  }
	  return this.bytes.length;  }

  @Override
  public int available() throws FSException {
    // Where should this method be implemented?
    // Here, on class File, or on class Node?
	  if (!valid()) {
		  throw new FSException("this Directory is not valid");
	  }
	  return this.bytes.length - this.position;  }

  @Override
  public byte read() throws FSException, EOFException {
    // Where should this method be implemented?
    // Here, on class File, or on class Node?
	  if (!valid()) {
		  throw new FSException("this Directory is not valid");
	  }
	  if (position==bytes.length) {
		  throw new EOFException();
	  }
	  return bytes[position++];  }

  @Override
  public void write(byte val) throws FSException {
    // Where should this method be implemented?
    // Here, on class File, or on class Node?
	  if (!valid()) {
		  throw new FSException("this Directory is not valid");
	  }
	  if (this.available()==0) {
		  int l = bytes.length+1;
		  byte[] tmp = bytes;
		  this.bytes = new byte[l];
		  for (int i = 0 ; i<tmp.length;i++) {
			  bytes[i]=tmp[i];
		  }
	  }
	  bytes[position++] = val;   }

  @Override
  public void seek(int offset) throws FSException {
    // Where should this method be implemented?
    // Here, on class File, or on class Node?
	  if (!valid()) {
		  throw new FSException("this Directory is not valid");
	  }
	  while (offset>=bytes.length) {
		  int l = bytes.length+1;
		  this.bytes = new byte[l];
	  }
	  this.position = offset;
	  }

}
