package ricm3.oop.fs;

import edu.polytech.ricm.oop.collections.LinkedList;

import java.io.IOException;

import edu.polytech.ricm.oop.collections.ICollection.Iterator;
import ricm3.filesys.FSException;
import ricm3.filesys.IDirectory;
import ricm3.filesys.IFile;
import ricm3.filesys.oop.Node;
import ricm3.oop.dev.ChainAllocationTable;

public class Directory extends ricm3.filesys.oop.Directory {
  FileSystem m_fs;
  int headBlock;

  public Directory(FileSystem fs) throws FSException {
    super(fs);
    m_fs = fs;
    headBlock = m_fs.m_bat.getRoot();
    }

  public Directory(FileSystem fs, int headBlock) throws FSException, IOException {
    super(fs);
    m_fs = fs;
    this.headBlock = headBlock;
    ChainInputStream cis = new ChainInputStream(m_fs.m_bat, headBlock);
    int nnodes = cis.read();
    for (int i = 0 ; i < nnodes; i++) {
    	char c = cis.dis.readChar();
    	if (c == 'D') {
        	int y = cis.read();
        	int h = cis.read();
        	String s = cis.dis.readUTF();
    		Directory d = (Directory)this.mkdir(s);
    		if (h>0)
    			d.load(y);
    	} else {
        	int h = cis.read();
        	String s = cis.dis.readUTF();
    		((File)this.touch(s)).fil(h);    		
    	}
    }
  }

  public Directory(Directory parent, String name) throws FSException, IOException {
    super(parent, name);
    m_fs = parent.m_fs;
    headBlock = m_fs.m_bat.allocate(ChainAllocationTable.EOF_BLOCK);
  }
  
  public void load(int head) throws IOException, FSException {
	    ChainInputStream cis = new ChainInputStream(m_fs.m_bat, head);
	    int nnodes = cis.read();
	    for (int i = 0 ; i < nnodes; i++) {
	    	char c = cis.dis.readChar();
	    	if (c == 'D') {
	        	int y = cis.read();
	        	int h = cis.read();
	        	String s = cis.dis.readUTF();
	    		Directory d = (Directory) this.mkdir(s);
	    		if (h>0)
	    			d.load(y);
	    	} else {
	        	int h = cis.read();
	        	String s = cis.dis.readUTF();
	    		((File)this.touch(s)).fil(h);    		
	    	}
	    }
  }

  /**
   * Synchronizes this directory with the data saved 
   * on the block device. This means saving the list
   * of nodes and enough information about those nodes
   * to be able to reconstruct this directory object
   * from the saved data.
   * Nota Bene: this may or may not sync the contents
   *            of the files in this directory, it will
   *            depend on how you implemented your files.
   * 
   * @throws FSException
 * @throws IOException 
   */
  public void sync() throws FSException, IOException {
	  ChainOutputStream cos = new ChainOutputStream(m_fs.m_bat,headBlock);
	  Iterator it = nodes.iterator();
	  if(it.hasNext())
		  cos.write(nodes.length());
	  while (it.hasNext()) {
		  Node n = (Node)it.next();
		  if (n instanceof Directory) {
			  cos.dos.writeChar('D');
			  cos.write(((Directory) n).headBlock);
			  cos.write(((Directory)n).nodes.length());
			  cos.dos.writeUTF(n.name());
		  } else {
			  cos.dos.writeChar('F');
			  cos.write(((File)n).headBlock);
			  cos.dos.writeUTF(n.name());
		  }
		  cos.flush();
		  if (n instanceof Directory) {
			  ((Directory)n).sync();
		  } else {
			  ((File)n).sync();
		  }
	  }
  }
  
  @Override
  protected void add(Node child) throws FSException {
    // Where should this method be implemented?
    // Here, on class Directory, or on class Node?
	LinkedList tmp = (LinkedList)nodes;
	tmp.insertAt(0,child);
  }

  @Override
  protected void remove(Node child) throws FSException {
    // Where should this method be implemented?
    // Here, on class Directory, or on class Node?
	  LinkedList tmp = (LinkedList)nodes;
		tmp.remove(child);
  }

  /*============================================================
   * YOU SHOULD BE ABLE TO REMOVE ALL METHODS BELOW
   * If you get compiler errors, such as the compiler asking
   * you to implement missing methods required by IDirectory,
   * you should revisit your design, it seems that the
   * super class ricm3.filesys.oop.Directory does not do enough.
   =============================================================*/


}
