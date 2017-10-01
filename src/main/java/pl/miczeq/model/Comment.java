package pl.miczeq.model;

public class Comment
{
	private Long id;
	private String content;
	private Long topicId;
	private Long userId;

	public Comment(Long id, String content, Long topicId, Long userId)
	{
		this.id = id;
		this.content = content;
		this.topicId = topicId;
		this.userId = userId;
	}

	public Comment(String content, Long topicId, Long userId)
	{
		this.content = content;
		this.topicId = topicId;
		this.userId = userId;
	}

	public Long getId()
	{
		return id;
	}

	public String getContent()
	{
		return content;
	}

	public Long getTopicId()
	{
		return topicId;
	}

	public Long getUserId()
	{
		return userId;
	}

	@Override
	public String toString()
	{
		return "Comment [id=" + id + ", content=" + content + ", topicId=" + topicId + ", userId=" + userId + "]";
	}
}
