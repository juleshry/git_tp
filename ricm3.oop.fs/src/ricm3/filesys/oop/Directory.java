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
package ricm3.filesys.oop;

import java.io.IOException;

import edu.polytech.ricm.oop.collections.ICollection;
import edu.polytech.ricm.oop.collections.LinkedList;
import edu.polytech.ricm.oop.collections.ICollection.Iterator;
import ricm3.filesys.FSException;
import ricm3.filesys.IDirectory;
import ricm3.filesys.IFile;
import ricm3.filesys.IFileSystem;
import ricm3.filesys.oop.ramfs.RamDirectory;
import ricm3.filesys.oop.ramfs.RamFile;

public abstract class Directory extends Node implements IDirectory {

  protected ICollection nodes;
  protected FileSystem fs;

  /**
   * Creates the root of the file system
   * 
   * @param fs
   * @throws FSException
   */
  public Directory(FileSystem fs) throws FSException {
	  if (fs == null) {
			throw new FSException("No Filesystem");
		}
	    this.fs = fs;
	    this.name = fs.name();
	    valid = true;
	    nodes = new LinkedList();
  }

  /**
   * Creates a directory as a child of the given parent.
   * The constructor must add this directory to the given parent, 
   * with the given name.
   * Note: a directory may not have the same name as a file,
   *       two directories may not have the same name.
   * @throws FSException if there is a name conflict as described 
   *         in the note above.
   */
  public Directory(Directory parent, String name) throws FSException {
    super(parent, name);
    this.fs = parent.fs;
    nodes = new LinkedList();
  	}

  @Override
  public IFileSystem getFileSystem() {
    return fs;
  }

  public String path() {
    return super.path() + "/";
  }

  /**
   * Adds the given node to the set of children of this directory.
   * @throws FSException if there is a problem, such as 
   * a name conflict.
   */
  abstract protected void add(Node node) throws FSException;

  /**
   * Removes the given node from the set of children of this directory.
   * 
   * Nota Bene: if your code invokes this method, it must also 
   *            invoke the method Node.removed() to notify the node
   *            that it was removed from its parent (this directory).
   */
  abstract protected void remove(Node node) throws FSException;

  public ICollection nodes() {
	  return nodes;
  }
  
  public IDirectory dir(String name) throws FSException {
	    // Where should this method be implemented?
	    // Here, on class Directory, or on class Node?
		  if (!valid()) {
			  throw new FSException("this Directory is not valid");
		  }
		  if (name==null || name == "") {
			  throw new FSException("the name is not correct");
		  }
		  if (nodes==null) {
			  return null;
		  }
		  Iterator iter = nodes.iterator();
		  Node tmp;
		  while (iter.hasNext()) {
			  tmp = (Node)iter.next();
			  if (tmp instanceof Directory) {
				  if (((Directory) tmp).name().equals(name)) {
					  return ((Directory) tmp);
				  }
			  }
		  }
		  return null;
		  }
  
  public IDirectory[] dirs() throws FSException {
	    // Where should this method be implemented?
	    // Here, on class Directory, or on class Node?
		  if (!valid()) {
			  throw new FSException("this Directory is not valid");
		  }
		  Directory[] ret = new Directory[nodes.length()];
		  Iterator iter = nodes.iterator();
		  Node tmp;
		  int i = 0;
		  while(iter.hasNext()) {
			  tmp = (Node)iter.next();
			  if (tmp instanceof Directory) {
				  ret[i] = (Directory) tmp;
				  i++;
			  }
		  }
		  return ret;
		  }
  
  public IFile file(String name) throws FSException {
	    // Where should this method be implemented?
	    // Here, on class Directory, or on class Node?
		  if (!valid()) {
			  throw new FSException("this Directory is not valid");
		  }
		  if (name==null || name == "") {
			  throw new FSException("the name is not correct");
		  }
		  Iterator iter = nodes.iterator();
		  Node tmp;
		  while (iter.hasNext()) {
			  tmp = (Node)iter.next();
			  if (tmp instanceof File) {
				  if (((File) tmp).name().equals(name)) {
					  return ((File) tmp);
				  }
			  }
		  }
		  return null;
		  }
  
  public IFile[] files() throws FSException {
	    // Where should this method be implemented?
	    // Here, on class Directory, or on class Node?
		  if (!valid()) {
			  throw new FSException("this Directory is not valid");
		  }
		  File[] ret = new File[nodes.length()];
		  Iterator iter = nodes.iterator();
		  Node tmp;
		  int i = 0;
		  while (iter.hasNext()) {
			  tmp = (Node)iter.next(); 
			  if (tmp instanceof File) {
				  ret[i] = (File) tmp;
				  i++;
			  }
		  }
		  return ret;
		  }
  
  public IDirectory mkdir(String name) throws FSException, IOException {
	    // Where should this method be implemented?
	    // Here, on class Directory, or on class Node?
		  if (!valid()) {
			  throw new FSException("this Directory is not valid");
		  }
		  if (nodes==null) {
			  nodes = new LinkedList();
		  }
		  if ((dir(name)!=null)||(file(name)!=null)) {
			  throw new FSException("this Directory is not valid");
		  }
		  Directory d = this.fs.newDirectory(this, name);
		  System.out.println("new Directory called "+name.toString()+" created in Directory "+ this.toString());
		  return d;
		  }
  
  public boolean rmdir(String name) throws FSException {
	    // Where should this method be implemented?
	    // Here, on class Directory, or on class Node?
		  if (!valid()) {
			  throw new FSException("this Directory is not valid");
		  }
		  if (nodes==null) {
			  throw new FSException("directories isn't initialized");
		  }
		  if (name == null || name =="") {
			  throw new FSException("The name isn't correct");
		  }
		  Directory direct = (Directory)this.dir(name);
		  if((direct.nodes() == null)||((direct).nodes().length()==0)) {
				  remove((Node)this.dir(name));  
				  direct.removed();
				  return true;
		  }
		  return false;
		  }
  
  public IFile touch(String name) throws FSException, IOException {
	    // Where should this method be implemented?
	    // Here, on class Directory, or on class Node?
		  if (!valid()) {
			  throw new FSException("this Directory is not valid");
		  }
		  if (nodes==null) {
			  nodes =  new LinkedList();
		  }
		  if (dir(name)!=null) {
			  throw new FSException("this Directory is not valid");
		  }
		  if (file(name)!=null) {
			  return file(name);
		  }
		  File f = this.fs.newFile(this, name);
		  System.out.println("new File called "+name.toString()+" created in Directory "+ this.toString());
		  return f;
		  }
  
  public boolean rm(String name) throws FSException {
	    // Where should this method be implemented?
	    // Here, on class Directory, or on class Node?
		  if (!valid()) {
			  throw new FSException("this Directory is not valid");
		  }
		  if (name == null || name == "") {
			  throw new FSException("The name isn't correct");
		  }
		  File fil = (File)this.file(name);
		  if (fil == null) {
			  return false;
		  }
		  remove(fil);
		  fil.removed();
		  return true;
		  }

protected Iterator iterator() {
	// TODO Auto-generated method stub
	return null;
}

}
