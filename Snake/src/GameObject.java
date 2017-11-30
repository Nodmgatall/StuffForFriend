import acm.graphics.GRect;

public class GameObject extends GRect{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GameObjectType m_element_type;
	public GameObject(double arg0, double arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
		m_element_type = GameObjectType.BACKGROUND;
	}
	
	public GameObject(double arg0, double arg1, double arg2, double arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}
}
