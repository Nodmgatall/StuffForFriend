

public class Food extends GameObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	public Food(double arg0, double arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
		m_element_type = GameObjectType.FOOD;
	}
	
	public Food(double arg0, double arg1, double arg2, double arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
		m_element_type = GameObjectType.FOOD;
	}


}
