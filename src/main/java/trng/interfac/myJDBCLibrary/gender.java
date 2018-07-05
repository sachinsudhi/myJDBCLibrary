package trng.interfac.myJDBCLibrary;

public enum gender {
		MALE(1), FEMALE(2);
		private int num;
		 gender(int num){
			this.num=num;
		}
		  public int getValue() 
		  { return num; }
}
