/**
 * JSONTask
 * 
 * This is a utility class for converting json string to Event object
 */

package org.ndacm.acmgroup.cnp.task;


public class JSONTask {
	private String type;	//the type of json

	@Override
	/**
	 *toString()
	 *@return String	Override the toString that will return the parameters 
	 */
	public String toString()
	{
		return type.toString();
	}
}
