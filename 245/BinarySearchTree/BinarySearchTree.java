class BinarySearchTree<T extends Comparable <T>> {

   private class BSTNode {
       public T data;
       public BSTNode left;
       public BSTNode right;

       BSTNode(T newdata) 
       {
          data = newdata;
       }
   }

   private BSTNode root;

   public void Insert(T elem) 
   {
      root = Insert(root, elem);
   }

   public boolean Find(T elem) 
   {
      return Find(root,elem);
   }

   public void Delete(T elem) 
   {
      root = Delete(root, elem);
   }
   
   public int search(BSTNode tree){
	   int count = 0;
	   
	   if (tree != null){
		   if (tree.left != null){
			   count += search(tree.left);
		   }
		   if (tree.right != null){
			   count += search(tree.right);
		   }
		   count ++;
	   }
	   return count;
//	   
//	   return true;
   }
   
   
   public int Findnum(BSTNode tree, T data, int num){
	   if (tree == null){
		   return num;
	   }
	   if (data.compareTo(tree.data) == 0){
		   num ++;
		   return num;
	   }
	   if (data.compareTo(tree.data) < 0){
		   num ++;
		   return num;
	   }
	   else{
		   num ++;
		   return Findnum(tree.right, data, num);
	   }
   }
   
   
   

   //Returns the ith largest element in the binary search tree.   
   //So, ElementAt(0) would return the smallest element in the tree, ElementAt(1) would 
   //return the second smallest element in the tree, and so forth.  
   //This method does not need to be efficient it only needs to compute the correct value.
   public T ElementAt(int i) 
   {
	   int count = i; 
	   int size = 0; 
	   while (root != null){
		   size = search(root.left);
		   if (size + 1 == count){
			   return root.data;
		   }
		   else if (size < count){
			   root = root.right;
			   count -= size + 1;
		   }
		   else{
			   root = root.left;
		   }
	   }
	   return null;
   }
	   
//	   if (root == null){
//		   return root.data;
//	   }
//	   if (root.left.data.compareTo(root.right.data) < 0){
//			   return root.left.data;
//	   }
//	   if (root.left.data.compareTo(root.right.data) > 0){
//		   return root.right.data;
//	   }
//	   return root.left.data;
	   
//	   for (BSTNode temp = root.left; temp != null; temp = root.right){
//		   if (!temp.data.equals(root.data)){
//			   Find(temp, temp.);
//			   return temp.data;
//		   }
//		   if (temp.data.compareTo(root.data) == -1){
//			   search(temp.left);
//			   return temp.data;
//		   }
//		   search(temp.right);
//		   return temp.data;
//	   }
//	   return root.data;
//   }
   
   
   
   //Returns the number of elements in the tree greater or equal to low
   //and less than or equal to high. Be sure to make your code as efficient as possible
   //-- only examine the sections of the tree that you need to in order to compute the 
   //desired information. Note that you need to be able to get the correct results for 
   //BSTs that have duplicate elements
   
   
    public int NumBetween(T low, T high) 
   {
    	int bigcount = 0;
    	int littlecount = 0;
    	int count;
    	boolean highbool = true;
    	boolean lowbool = true;
    	
    	if (root.data.compareTo(high) < 0){
    		highbool = false;
    	}
    	if (root.data.compareTo(low) < 0){
    		lowbool = false;
    	}
    	bigcount = Findnum(root, high, 0);
    	littlecount = Findnum(root, low, 0);
    	
    	if (highbool == lowbool){
    		count = (bigcount + littlecount);
    	}
    	else{
    		count = Math.abs(bigcount - littlecount);
    	}
    	count = Math.abs(bigcount - littlecount);
    	return count;
   }
    
    
    //Reorders the tree so that it is balanced -- that is, so the height is as small as possible.  
    //The easiest way to rebalance the tree is to:
    	//Create an array that contains all of the elements in the tree in order 
    //(which you can do by using a modified version of print that "prints" to an array)
    	//Create a new tree, by inserting all of the elements from the array into the tree.  
    //In order to make the tree balanced, you are likely going to want to write a recursive method to do the insertions:
    	//First insert the element in the middle of the array. 
    	//Then recursively insert the rest of the elements in the array 
    //(you will likely want to make two recursive calls here)
   public void Balance()
   {
	   int length = search(root);
	   BSTNode [] nums = sorted();
	   
	   root = tree(nums, 0, nums.length - 1); 
	   
   }
   
   public BSTNode [] sorted(){
	   int length = search(root);
	   BSTNode [] result = new BinarySearchTree.BSTNode[length];
	   nodeArray(root, result, length -1);
	   
	   return result;
   }
   
   private int nodeArray(BSTNode a, BSTNode [] result, int i){
	   
	   if (a.left != null){
		   i = nodeArray(a.right, result, i);
	   }
	   if (a.right != null){
		   i = nodeArray(a.right, result, i);
	   }
	   result[i] = a;
	   return i - 1;
   }
   
   public BSTNode tree(BSTNode [] nums, int start, int end){
	   if (start > end ){
		   return null;
	   }
	   int middle = start + (end - start) / 2;
	   BSTNode num = nums[middle];
	   
	   num.left = tree(nums, start, middle - 1);
	   num.right = tree(nums, middle + 1, end);
	   return num;
	   
   }

   public void Print() {
       Print(root);
   }

   public int Height()
   {
       return Height(root);
   }
   

   private int Height(BSTNode tree)
   {
      if (tree == null)
      {
         return 0;
      }
      return 1 + Math.max(Height(tree.left), Height(tree.right));
   }

   private boolean Find(BSTNode tree, T elem) 
   {
      if (tree == null)
      {
         return false;
      }
      if (elem.compareTo(tree.data) == 0)
      { 
         return true;
      }
      if (elem.compareTo(tree.data) < 0)
      {
         return Find(tree.left, elem);
      }
      else
      {
         return Find(tree.right, elem);
      }
   }

   private T Minimum(BSTNode tree) 
   {
      if (tree == null)
      {
         return null;
      }
      if (tree.left == null)
      {
         return tree.data;
      }
      else
      {
         return Minimum(tree.left);
      }
   }

   private void Print(BSTNode tree) 
   {
      if (tree != null) 
      {
         Print(tree.left);
         System.out.println(tree.data);
         Print(tree.right);
      }
   }

   private BSTNode Insert(BSTNode tree, T elem) 
   {
	if (tree == null) 
        {
	    return new BSTNode(elem);
	}
	if (elem.compareTo(tree.data) < 0) 
        {
	    tree.left = Insert(tree.left, elem);
	    return tree;
	} 
        else  
        {
	    tree.right = Insert(tree.right, elem);
	    return tree;
	}
    }


   private BSTNode Delete(BSTNode tree, T elem) 
   {
      if (tree == null) 
      {
         return null;
      }
      if (tree.data.compareTo(elem) == 0) 
      {
         if (tree.left == null)
         {
	    return tree.right;
         }
         else if (tree.right == null)
         {
	   return tree.left;
         }
         else 
         {
	    if (tree.right.left == null) 
            {
               tree.data = tree.right.data;
               tree.right = tree.right.right;
               return tree;
            } 
            else 
            {         
               tree.data = RemoveSmallest(tree.right);
               return tree;
            }
         }
      } 
      else  if (elem.compareTo(tree.data) < 0) 
      {
         tree.left = Delete(tree.left, elem);
         return tree;
      } 
      else 
      {
         tree.right = Delete(tree.right, elem);
         return tree;
     }
   }  
 
   T RemoveSmallest(BSTNode tree) 
   {
      if (tree.left.left == null) 
      {
         T smallest = tree.left.data;
         tree.left = tree.left.right;
         return smallest;
      } 
      else 
      {
         return RemoveSmallest(tree.left);
      }
   }
    public static void main(String args[])

    {
	BinarySearchTree<Integer> t= new BinarySearchTree<Integer>();
	for (int x = 0; x < 31; x++)
	    t.Insert(new Integer(x));
	System.out.println("element at " + t.ElementAt(new Integer(5)));
	System.out.println("num in between " + t.NumBetween(new Integer(10), new Integer(15)));
	System.out.println("height " + t.Height());
	t.Balance();
	System.out.println("new elem at " + t.ElementAt(new Integer(5)));
	System.out.println("new in between " + t.NumBetween(new Integer(10), new Integer(15)));
	System.out.println("new height " + t.Height());
    }

}