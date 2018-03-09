import java.util.*;
import java.io.*;

class node{
	int data;
	//node prev;
	node next;

	public node(int d){
		next = null;
		//prev = null;
		data = d;
	}
	
	public node(int d,node nxt){
		data=d;
		next=nxt;
	}
}



public class LinkedListImage implements CompressedImageInterface {

	int w;
	int h;
	node [] arr;
	int[] black;
	boolean inv = false;
	
	public void addAfter(node n, int d){
		node temp = new node(d);
		//temp.prev = n;
		temp.next = n.next;

		n.next = temp;
		/*if(temp.next!=null)
			temp.next.prev = temp;*/
	}
	
	public void addBefore(node n,int d){
		node temp = new node(n.data);
		temp.next = n.next;
		n.next = temp;
		n.data = d;
	}

	// public LinkedListImage(String filename)
	// {
	// 	//you need to implement this
	// 	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	//
	// 	FileInputStream fstream = new FileInputStream(filename);
	// 	Scanner s = new Scanner(fstream);
	//
	// 	w = s.nextInt();
	// 	h = s.nextInt();
	//
	// 	boolean grid[][] = new boolean[w][h];
	// 	for(int i=0;i<w;i++){
	// 		for(int j=0;j<h;j++)
	// 			grid[i][j] =s.nextBoolean();
	// 	}
	//
	// 	this(grid,w,h);
	// }

	public LinkedListImage(String filename)
	{
		//you need to implement this
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");

		try{
			FileInputStream fstream = new FileInputStream(filename);
			Scanner s = new Scanner(fstream);

			w = s.nextInt();
			h = s.nextInt();
			arr = new node[h];
			black = new int[h];

			boolean x,y;
			node temp;
			int j,t=0;
			for(int i=0;i<h;i++){
				black[i]=0;
				arr[i] = new node(-2);
				//addAfter(arr[i],-1);
				temp = arr[i];
				x = y = true;

				for(j=0;j<w;j++){
					y = s.nextInt()==1 ;
			
					if(x == y){
						continue;
					}

					else{
						if(y == false){
							addAfter(temp,j);
							temp = temp.next;
							t=j;
						}

						else{
							addAfter(temp,j-1);
							temp = temp.next;
							black[i] += j-t;
						}

						x = y;
					}

				}

				if(y == false){
					addAfter(temp,j-1);
					temp = temp.next;
					black[i] += w-t;
				}

				addAfter(temp,-1);
				arr[i] = arr[i].next;
			}
		}
		catch(FileNotFoundException e){
			System.err.println("File not Found");
		}
	}

  public LinkedListImage(boolean[][] grid, int width, int height)
  {
		//you need to implement this
//		throw new java.lang.UnsupportedOperationException("Not implemented yet.");

		h = height;
		w = width;
		arr = new node[height];
		black = new int[h];

		boolean x;
		node temp;
		int j,t=0;
		for(int i=0;i<height;i++){
			black[i]=0;
			arr[i] = new node(-2);
			//addAfter(arr[i],-1);
			temp = arr[i];
			x = true;
			for(j=0;j<width;j++){
				if(x == grid[i][j]){
					continue;
				}

				else{
					if(grid[i][j] == false){
						addAfter(temp,j);
						temp = temp.next;
						t=j;
					}

					else{
						addAfter(temp,j-1);
						temp = temp.next;
						black[i] += j-t;
					}

					x = grid[i][j];
				}

			}

			if(j>0 && grid[i][j-1] == false){
				addAfter(temp,j-1);
				temp = temp.next;
				black[i] += w-t;
			}

			addAfter(temp,-1);
			arr[i] = arr[i].next;
		}
  }

    public boolean getPixelValue(int x, int y) throws PixelOutOfBoundException
    {
		//you need to implement this
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    		
    		if(x>h-1 || x<0 || y<0 || y>w-1)
    			throw new PixelOutOfBoundException("Pixel out of bounds error");
    		else{
    			node temp = arr[x];
    			int i,j;
    			boolean flag = false;
    			while(temp.data!=-1){
    				i=temp.data;
    				temp=temp.next;
    				j=temp.data;
    				temp=temp.next;
    				
    				if(i<=y && y<=j)
    					flag=true;
    			}
    			
    			return ( (flag && inv) || ( (!flag) && (!inv) ) );
    		}
    }

    public void setPixelValue(int x, int y, boolean val) throws PixelOutOfBoundException
    {
		//you need to implement this
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		
		
			//System.out.println("hi 3" );
		if(x>h-1 || x<0 || y<0 || y>w-1)
			throw new PixelOutOfBoundException("Pixel out of bounds error");
    		else if(getPixelValue(x,y) == val)
    			return;
    		else{
    		
    			//System.out.println("hi 4 \n previously blacks was " + black[x]);
    			black[x] += val==true? -1 : 1 ;
    			//System.out.println(" now blacks is " + black[x] );
    		
    			node temp1 = arr[x];
    			node temp2=temp1;
    			node temp3=temp1;
    			int i=-1,j=-1;
    			if( (val && !inv) || (!val && inv) ){//the element will be between one of the pairs of nodes
    				
    				//System.out.println("hi5" );
    				while(temp1.data!=-1){
    					temp2=temp1;
    					
    					i=temp2.data;
    					temp1=temp1.next;
    					
    					temp3 = temp1;
    					
    					j=temp3.data;
    					temp1=temp1.next;
    					
    					if(y>=i && y<=j)
    						break;
    				}//i=temp2.data    j=temp3.data
    				
    				if(j>i+1){
    					if(y==i)
    						temp2.data +=1;
    					else if(y==j)
    						temp3.data -= 1;
    					else{
    						addAfter(temp2,y+1);
    						addAfter(temp2,y-1);
    					}
    				}
    				else if(j==i){
    					temp2.data = temp1.data;
    					temp2.next = temp1.next;
    					temp3=temp1=temp2;
    				}
    				else{//when j=i+1
    					if(y==i)
    						temp2.data+=1;
    					else
    						temp3.data-=1;
    				}
    			}
    			else{//when the element will not lie between one of the pairs of nodes
    				//System.out.println("hi 6" );
    				//System.out.println("Entered 1" );
    				if(y==temp1.data-1){
    					//System.out.println("Entered 2" );
    					temp1.data-=1;
    					return;
    				}
    				else if(y<temp1.data-1){
    				//System.out.println("Entered 3" );
    					addBefore(arr[x],y);
    					addBefore(arr[x],y);
    					return;
    				}
    			
    				
    				while(temp1.data!=-1){
    				//System.out.println("Entered 4" );
    					temp2=temp1.next;
    					temp3=temp2.next;
    					temp1=temp3;
    					
    					i=temp2.data;
    					j=temp3.data;
    					
    					if(y>i && y<j)//should be > and <
    						break;
    				}
    				
    				if(j==i+2){
    					temp2.data = temp3.next.data;
    					temp2.next = temp3.next.next;
    					temp1=temp3=temp2;
    				}
    				else if(j>i+2){
    					if(y==i+1)
    						temp2.data += 1;
    					else if(y==j-1)
    						temp3.data -= 1;
    					else{
    						addAfter(temp2,y);
    						addAfter(temp2,y);
    					}
    				}
    				else if(j==-1 && i!=-1){
    					if(y==i+1)
    						temp2.data += 1;
    					else{
    						addAfter(temp2,y);
    						addAfter(temp2,y);
    					}
    				}
    				else if(j==-1 && i==-1){
    					addBefore(temp2,y);
    					addBefore(temp2,y);
    				}
    				else{
    					System.out.println("CASE LEFT UNATTENDED");
    				}
    				
    				/*
    				if(j==i+2){//when y==i+1 && y==j-1
    				//System.out.println("hi 7" );
    				System.out.println("Entered 5" );
    					temp2.data=temp3.next.data;
    					temp2.next = temp3.next.next;
    					//System.out.println("hi 8" );
    				}
    				else if(y==i+1 && i!=-1 && (y<j-1 || j==-1)){
    				System.out.println("Entered 6" );
    					temp2.data+=1;
    				}
    				else if(y==j-1 && y>i+1){
    				System.out.println("Entered 7" );
    					temp3.data-=1;
    				}
    				else{//also includes the case when i==-1 and j==-1
    					System.out.println("Entered else part" );
    					addBefore(temp2,y);
    					addBefore(temp2,y);
    					//temp2.next = new node(y,temp2.next);
    					//temp2.next = new node(y,temp2.next);
    					//temp = new node(y);
    					//temp.next = temp2.next;
    					//temp2.next = temp;
    					
    				}*/
    			}
    		}
    }

    public int[] numberOfBlackPixels()
    {
		//you need to implement this
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		return black;
    }

    public void invert()
    {
		//you need to implement this
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		
		inv = !inv;
		for(int i=0;i<h;i++)
			black[i] = w - black[i];
    }

    public void performAnd(CompressedImageInterface img) throws BoundsMismatchException
    {
		//you need to implement this
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		
		boolean ex1=false,ex2=false;
		try{
			img.getPixelValue(0,h-1);
		}
		catch(PixelOutOfBoundException e){
			ex1=true;
		}
		
		try{
			img.getPixelValue(0,h);
		}
		catch(PixelOutOfBoundException e){
			ex2=true;
		}
		
		if(!(!ex1 && ex2))
			throw new BoundsMismatchException("bounds of the two images don't match");
		else{
			for(int i=0;i<h;i++){
				for(int j=0;j<w;j++){
					try{
						setPixelValue(i,j,getPixelValue(i,j) && img.getPixelValue(i,j) );
					}
					catch(PixelOutOfBoundException e){}
				}
			}
		}
    }

    public void performOr(CompressedImageInterface img) throws BoundsMismatchException
    {
		//you need to implement this
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		
		boolean ex1=false,ex2=false;
		try{
			img.getPixelValue(0,h-1);
		}
		catch(PixelOutOfBoundException e){
			ex1=true;
		}
		
		try{
			img.getPixelValue(0,h);
		}
		catch(PixelOutOfBoundException e){
			ex2=true;
		}
		
		if(!(!ex1 && ex2))
			throw new BoundsMismatchException("bounds of the two images don't match");
		else{
			for(int i=0;i<h;i++){
				for(int j=0;j<w;j++){
					try{
						setPixelValue(i,j,getPixelValue(i,j) || img.getPixelValue(i,j) );
					}
					catch(PixelOutOfBoundException e){}
				}
			}
		}
    }

    public void performXor(CompressedImageInterface img) throws BoundsMismatchException
    {
		//you need to implement this
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		
		boolean ex1=false,ex2=false;
		try{
			img.getPixelValue(0,h-1);
		}
		catch(PixelOutOfBoundException e){
			ex1=true;
		}
		
		try{
			img.getPixelValue(0,h);
		}
		catch(PixelOutOfBoundException e){
			ex2=true;
		}
		
		if(!(!ex1 && ex2))
			throw new BoundsMismatchException("bounds of the two images don't match");
		else{
			boolean x,y;
			for(int i=0;i<h;i++){
				for(int j=0;j<w;j++){
					try{
						x=getPixelValue(i,j);
						y=img.getPixelValue(i,j);
						setPixelValue(i,j, ( (!x)&&y)||(x&&(!y)) );
					}
					catch(PixelOutOfBoundException e){}
				}
			}
		}
    }

    public String toStringUnCompressed()
    {
		//you need to implement this
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		
		String s = h + " " + w + "," ;
		node temp;
		int j,x,y=-1,z;
		
		z = inv ? 1 : 0;
		
		for(int i=0;i<h;i++){
			temp = arr[i];
			j=0;
			y=-1;
			while(temp.data !=-1 && j<w){
				x=temp.data;
				temp=temp.next;
				
				for(int k=y+1;k<x;k++){
					s = s + " " + (1-z);
					j++;
				}
				
				y=temp.data;
				temp=temp.next;
				for(int k=x;k<=y;k++){
					s = s+" "+z;
					j++;
				}
			}
			while(j<w){
				s = s+ " " + (1-z);
				j++;
			}
				
			if(i<h-1)
				s = s + ",";
			
		}
		
		return s;
    }

//"16 16, 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1, 1 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1, 1 1 1 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1 1, 1 1 0 0 0 1 1 1 1 1 1 1 1 1 1 1, 1 1 0 0 1 1 1 1 1 1 1 1 1 1 0 0, 1 1 0 1 1 1 1 1 1 1 1 1 1 0 0 0, 1 1 1 1 1 1 1 1 1 1 1 0 0 0 1 1, 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1, 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1, 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1, 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 1"

    public String toStringCompressed()
    {
		//you need to implement this
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		
		String s = h + " " + w + ",";
		int x,y;
		node temp;
		
		if(inv == false){
			for(int i=0;i<h;i++){
				temp = arr[i];
				x=temp.data;
				while(x!=-1){
					s = s+" " + x;
					temp=temp.next;
					x=temp.data;
				}
				s=s+" -1";
				if(i<h-1)
					s = s + ",";
			}
		}
		else{
			for(int i=0;i<h;i++){
				temp = arr[i];
				x=temp.data;
				y=-1;
				if(x==-1){
					s = s + " 0 " + (w-1);
				}
				else{
					if(x>0){
						s = s+" 0 " + (x-1);
					}
					
					while(x!=-1){
						temp = temp.next;
						y=temp.data;
						temp=temp.next;
						x=temp.data;
					
						if(x==-1){
							if(y==w-1)
								continue;
							else
								s = s + " " + (y+1) + " " + (w-1);
						}
						else
							s = s + " " + (y+1) + " " + (x-1) ;
					}
				}
				s=s+" -1";
				if(i<h-1)
					s = s + ",";
			}
		}
		
		//System.out.print(s);
		return s;
    }

/*	public static void main(String args[]){
		LinkedListImage img = new LinkedListImage("sampleInputFile.txt");
		try{
			img.performXor(img);
		}
		catch(BoundsMismatchException e){
			System.out.println("error");
		}
		
		String ss = img.toStringUnCompressed();
		System.out.println(ss);
		String s = "16 16, 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1, 1 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1, 1 1 1 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1 1, 1 1 0 0 0 1 1 1 1 1 1 1 1 1 1 1, 1 1 0 0 1 1 1 1 1 1 1 1 1 1 0 0, 1 1 0 1 1 1 1 1 1 1 1 1 1 0 0 0, 1 1 1 1 1 1 1 1 1 1 1 0 0 0 1 1, 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1, 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1, 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1, 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 1";
		System.out.println(s);
		System.out.println( s.equals(ss));
		
		for(int i=0;i<s.length();i++){
			if(s.charAt(i)!=ss.charAt(i))
				System.out.println(i);
			else
				System.out.print("-");
		}
	}
}*/

/*	public static void main(String args[]){
		LinkedListImage img = new LinkedListImage("sampleInputFile.txt");
		/*System.out.println(img.toStringCompressed() );
		img.invert();
		System.out.println(img.toStringCompressed() );
		
		int[] temp = img.numberOfBlackPixels();
		for(int i=0;i<img.h;i++)
			System.out.println(temp[i]);
		
		System.out.println(img.toStringUnCompressed() );
		img.invert();
		System.out.println(img.toStringUnCompressed() );
		
		temp = img.numberOfBlackPixels();
		for(int i=0;i<img.h;i++)
			System.out.println(temp[i]);//*/
			
		/*System.out.println(img.toStringUnCompressed() );
		
		System.out.println(img.toStringCompressed() );
		System.out.println("\n Ideal inverted output\n");
		img.invert();
		System.out.println(img.toStringCompressed() );
		img.invert();
		
		
		
		img.invert();
		String s1 = img.toStringCompressed();
		img.invert();
		
		try{
			//System.out.println(img.getPixelValue(6,8));
			
			/*img.setPixelValue(0,0,false);
			img.setPixelValue(1,6,true);
			img.setPixelValue(2,6,true);
			img.setPixelValue(3,6,true);
			img.setPixelValue(2,6,false);
			img.setPixelValue(2,13,false);
			img.setPixelValue(0,1,false);
			img.setPixelValue(1,0,false);
			img.setPixelValue(1,1,false);
			
			

			
			for(int i=0;i<img.h;i++){
				for(int j=0;j<img.w;j++){
					//System.out.print(img.getPixelValue(i,j)? " 1": " 0");
					img.setPixelValue(i,j,!(img.getPixelValue(i,j)) );
					//System.out.println("For j = " + j + "\n" + img.toStringCompressed() );
				}
			}
			
			String s2 = img.toStringCompressed();
			
			System.out.println(s1.equals(s2));
			//System.out.println("\n My inverted output \n");
			//System.out.println(img.toStringCompressed() );
			
		}
		catch(PixelOutOfBoundException e){
			System.out.println(e);
		}
	}
	
}*/

    public static void main(String[] args) {
    	// testing all methods here :
    	boolean success = true;

    	// check constructor from file
    	CompressedImageInterface img1 = new LinkedListImage("sampleInputFile.txt");

    	// check toStringCompressed
    	String img1_compressed = img1.toStringCompressed();
    	String img_ans = "16 16, -1, 5 7 -1, 3 7 -1, 2 7 -1, 2 2 6 7 -1, 6 7 -1, 6 7 -1, 4 6 -1, 2 4 -1, 2 3 14 15 -1, 2 2 13 15 -1, 11 13 -1, 11 12 -1, 10 11 -1, 9 10 -1, 7 9 -1";
    	success = success && (img_ans.equals(img1_compressed));

    	if (!success)
    	{
    		System.out.println("Constructor (file) or toStringCompressed ERROR");
    		return;
    	}

    	// check getPixelValue
    	boolean[][] grid = new boolean[16][16];	
    	for (int i = 0; i < 16; i++)
    		for (int j = 0; j < 16; j++)
    		{
                try
                {
        			grid[i][j] = img1.getPixelValue(i, j);
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
    		}

    	// check constructor from grid
    	CompressedImageInterface img2 = new LinkedListImage(grid, 16, 16);
    	String img2_compressed = img2.toStringCompressed();
    	success = success && (img2_compressed.equals(img_ans));

    	if (!success)
    	{
    		System.out.println("Constructor (array) or toStringCompressed ERROR");
    		return;
    	}

    	// check Xor
        try
        {
        	img1.performXor(img2);
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        
    	for (int i = 0; i < 16; i++)
    		for (int j = 0; j < 16; j++)
    		{
                try
                {
        			success = success && (!img1.getPixelValue(i,j));
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
    		}

    	if (!success)
    	{
    		System.out.println("performXor or getPixelValue ERROR");
    		return;
    	}

    	// check setPixelValue
    	for (int i = 0; i < 16; i++)
        {
            try
            {
    	    	img1.setPixelValue(i, 0, true);
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }

    	// check numberOfBlackPixels
    	int[] img1_black = img1.numberOfBlackPixels();
    	success = success && (img1_black.length == 16);
    	for (int i = 0; i < 16 && success; i++)
    		success = success && (img1_black[i] == 15);
    	if (!success)
    	{
    		System.out.println("setPixelValue or numberOfBlackPixels ERROR");
    		return;
    	}

    	// check invert
        img1.invert();
        for (int i = 0; i < 16; i++)
        {
            try
            {
                success = success && !(img1.getPixelValue(i, 0));
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }
        if (!success)
        {
            System.out.println("invert or getPixelValue ERROR");
            return;
        }

    	// check Or
        try
        {
            img1.performOr(img2);
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && img1.getPixelValue(i,j);
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performOr or getPixelValue ERROR");
            return;
        }

        // check And
        try
        {
            img1.performAnd(img2);
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && (img1.getPixelValue(i,j) == img2.getPixelValue(i,j));
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performAnd or getPixelValue ERROR");
            return;
        }

    	// check toStringUnCompressed
        String img_ans_uncomp = "16 16, 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1, 1 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1, 1 1 1 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1 1, 1 1 0 0 0 1 1 1 1 1 1 1 1 1 1 1, 1 1 0 0 1 1 1 1 1 1 1 1 1 1 0 0, 1 1 0 1 1 1 1 1 1 1 1 1 1 0 0 0, 1 1 1 1 1 1 1 1 1 1 1 0 0 0 1 1, 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1, 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1, 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1, 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 1";
        success = success && (img1.toStringUnCompressed().equals(img_ans_uncomp)) && (img2.toStringUnCompressed().equals(img_ans_uncomp));

        if (!success)
        {
            System.out.println("toStringUnCompressed ERROR");
            return;
        }
        else
            System.out.println("ALL TESTS SUCCESSFUL! YAYY!");
    }
}


