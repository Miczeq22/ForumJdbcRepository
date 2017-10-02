package pl.miczeq.model;

public class Comment
{
	private Long id;
	private String content;
	private Long topicId;
	private Long userId;
	private int likes;

	public Comment(Long id, String content, Long topicId, Long userId, int likes)
	{
		this.id = id;
		this.content = content;
		this.topicId = topicId;
		this.userId = userId;
		this.likes = likes;
	}

	public Comment(String content, Long topicId, Long userId, int likes)
	{
		this.content = content;
		this.topicId = topicId;
		this.userId = userId;
		this.likes = likes;
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

	public int getLikes()
	{
		return likes;
	}

	@Override
	public String toString()
	{
		return "Comment [id=" + id + ", content=" + content + ", topicId=" + topicId + ", userId=" + userId + ", likes="
				+ likes + "]";
	}
}
