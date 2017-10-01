package pl.miczeq.model;

public class Topic
{
	private Long id;
	private String name;
	private String content;
	private Long userId;

	public Topic(Long id, String name, String content, Long userId)
	{
		this.id = id;
		this.name = name;
		this.content = content;
		this.userId = userId;
	}

	public Topic(String name, String content, Long userId)
	{
		this.name = name;
		this.content = content;
		this.userId = userId;
	}

	public Long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getContent()
	{
		return content;
	}

	public Long getUserId()
	{
		return userId;
	}

	@Override
	public String toString()
	{
		return "Topic [id=" + id + ", name=" + name + ", content=" + content + ", userId=" + userId + "]";
	}
}
