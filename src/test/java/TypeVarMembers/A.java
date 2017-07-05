package TypeVarMembers;

class C { 
    public    void mCPublic()    {System.out.println("this is mCPublic");}
    protected void mCProtected() {} 
              void mCPackage()   {}
    private   void mCPrivate()   {} 
} 

interface I {
    void mI();
}

class CT extends C implements I {
    public void mI() {}
}

class Test {
    <T extends C & I> void test(T t) { 	
        t.mI();           // OK
        t.mCPublic();     // OK 
        t.mCProtected();  // OK 
        t.mCPackage();    // OK
    } 
    
    public static void main(String[] args)
    { 
    	CT ct = new CT();
    	Test t = new Test();
    	t.test(ct);
    }
}