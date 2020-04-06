package ricm3.oop.fs;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import ricm3.oop.dev.ChainAllocationTable;

public class ChainInputStream extends java.io.InputStream {

	DataInputStream dis;
	ByteArrayInputStream bis;
	ChainAllocationTable cat;
	int head;
	
	public ChainInputStream(ChainAllocationTable cat, int headblock) throws IOException {
		byte[] b = new byte[1028];
		cat.getDevice().read(headblock, b);
		bis = new ByteArrayInputStream(b);
		dis = new DataInputStream(bis);
		this.cat = cat;
		this.head = headblock;
	}
	@Override
	public int read() throws IOException {
		return dis.readInt();
	}

}
