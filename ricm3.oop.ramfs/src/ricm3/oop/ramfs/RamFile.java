/*
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Created on: November, 2017
 *      Author: Pr. Olivier Gruber <olivier dot gruber at acm dot org>
 */
package ricm3.oop.ramfs;

import ricm3.filesys.EOFException;
import ricm3.filesys.FSException;
import ricm3.filesys.IDirectory;
import ricm3.filesys.oop.Directory;
import ricm3.filesys.oop.File;

public class RamFile extends File {
	
	byte [] bytes;
	  int position;

  public RamFile(Directory parent, String name) throws FSException {
    super(parent, name);
    this.position = 0;
	  this.bytes = new byte[0];
	  }
  
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
