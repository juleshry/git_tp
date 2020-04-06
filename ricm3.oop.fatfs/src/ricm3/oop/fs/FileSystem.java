package ricm3.oop.fs;

import java.io.IOException;

import ricm3.filesys.FSException;
import ricm3.oop.dev.BlockDevice;
import ricm3.oop.dev.ChainAllocationTable;

public class FileSystem extends ricm3.filesys.oop.FileSystem {

  public static final int EOF_BLOCK = ChainAllocationTable.EOF_BLOCK;

  BlockDevice m_device;
  ChainAllocationTable m_bat;
  Directory m_root;

  public FileSystem(String name, BlockDevice dev, boolean format) throws FSException {
    super(name, new Factory());
    m_device = dev;
    try {
      m_bat = new ChainAllocationTable(dev);
      if (format) {
        m_bat.format();
        m_root = new Directory(this);
      } else {
        m_root = new Directory(this, m_bat.getRoot());
      }
      setRoot(m_root);
    } catch (IOException ex) {
      throw new FSException(ex);
    }
  }

  /**
   * Synchronizes this file system with the underlying
   * block device. After this method has executed, all
   * directories and all files must have written back
   * any modifications on the block device.
   * 
   * @throws IOException
   */
  @Override
  public void sync() throws FSException, IOException {
    m_root.sync();
  }

}
