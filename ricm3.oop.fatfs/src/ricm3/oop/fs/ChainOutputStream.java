package ricm3.oop.fs;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ricm3.oop.dev.ChainAllocationTable;

public class ChainOutputStream extends java.io.OutputStream {

	DataOutputStream dos;
	ByteArrayOutputStream bos;
	int head;
	ChainAllocationTable cat;
	
	public ChainOutputStream(ChainAllocationTable cat, int headblock) {
		bos = new ByteArrayOutputStream();
		dos = new DataOutputStream(bos);
		this.cat = cat;
		this.head = headblock;
	}

	@Override
	public void write(int b) throws IOException {
		dos.writeInt(b);
	}
	
	public void flush() throws IOException {
		dos.flush();
		cat.getDevice().write(head, bos.toByteArray());
	}
	
}
