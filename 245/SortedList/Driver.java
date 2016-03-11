
public class Driver {
	
	public static void main (String [] args){
		int keys = 3;
		MyMultiKeyList list = new MyMultiKeyList(keys);
		Comparable [] array = new Comparable [keys];
		Comparable [] array2 = new Comparable [keys];
		Comparable [] array3 = new Comparable [keys];
		
		
		array[0] = "apple";
		array[1] = 5;
		array[2] = 2014;
		
		array2[0] = "banana";
		array2[1] = 1;
		array2[2] = 1000;
		
		array3[0] = "carrot";
		array3[1] = 7; 
		array3[2] = 2000;
		
		
		list.add(array, new Object ());
		list.add(array2, new Object ());
		list.add(array3, new Object ());

		list.remove(array);
		
		
		int listKeys = 2;
		MultiKeyList lst = new MyMultiKeyList(listKeys);

		//  Fill lst with values

		MultiKeyListIterator it = lst.iterator(0);
		while (it.hasNext())
		{
		   ListElem l = it.next();
		   for (int i = 0; i < l.numKeys(); i++)
		   {
		       System.out.println("Key (" + i + ") = " + l.key(i));
		   }
		   System.out.print("Data = " + l.data());
		   System.out.println();
		} 
		
		MultiKeyListIterator iter = list.iterator(0);
		
	}

}