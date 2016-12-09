package test;

/**
 * 用户信息_VO
 */
public class UserVO {
	/**
	 * id
	 */
	private int id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 备注
	 */
	private String desc;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
