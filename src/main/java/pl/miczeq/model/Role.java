package pl.miczeq.model;

public class Role
{
	private Long id;
	private String name;
	private Long userId;

	public Role(Long id, String name, Long userId)
	{
		this.id = id;
		this.name = name;
		this.userId = userId;
	}

	public Role(String name, Long userId)
	{
		this.name = name;
		this.userId = userId;
	}

	public Role() {}
	
	public Long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public Long getUserId()
	{
		return userId;
	}

	@Override
	public String toString()
	{
		return "Role [id=" + id + ", name=" + name + ", userId=" + userId + "]";
	}
}
