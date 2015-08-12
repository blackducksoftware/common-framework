package soleng.framework.standard.email;

public class EmailTemplate
{
	private String from;
	private String to;
	private String subject;
	private String style;
	private String body;
	
	public void setFrom(String from)
	{
		this.from = from;

	}

	public void setTo(String to)
	{
		this.to = to;

	}
	
	public void setSubject(String subject)
	{
		this.subject = subject;

	}
	
	public void setStyle(String style)
	{
		this.style = style;

	}
	
	public void setBody(String body)
	{
		this.body = body;

	}
	
	public String getTo()
	{
		return to;
	}
	
	public String getFrom()
	{
		return from;
	}
	
	public String getSubject()
	{
		return subject; 
	}
	
	public String getStyle()
	{
		return style;
	}
	
	public String getBody()
	{
		return body; 
	}
	
}
