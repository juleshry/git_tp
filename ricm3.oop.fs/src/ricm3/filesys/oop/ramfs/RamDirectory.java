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
package ricm3.filesys.oop.ramfs;

import edu.polytech.ricm.oop.collections.LinkedList;

import java.io.IOException;

import edu.polytech.ricm.oop.collections.ICollection.Iterator;
import ricm3.filesys.FSException;
import ricm3.filesys.IDirectory;
import ricm3.filesys.IFile;
import ricm3.filesys.oop.Directory;
import ricm3.filesys.oop.FileSystem;
import ricm3.filesys.oop.Node;
import ricm3.filesys.oop.File;

public class RamDirectory extends Directory {

  public RamDirectory(FileSystem fs) throws FSException {
    super(fs);

    // Use your LinkedList from your collections to hold
    // all the files and directories of this directory

    nodes = new LinkedList();
  }

  public RamDirectory(Directory parent, String name) throws FSException {
    super(parent, name);

    // Use your LinkedList from your collections to hold
    // all the files and directories of this directory

	  nodes = new LinkedList();
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
}
