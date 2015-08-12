package soleng.framework.standard.email;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

public class EmailTemplateDigester {

	public static EmailTemplate getEmailTemplate(InputStream aStream) throws IOException, SAXException
			 {
		Digester digester = new Digester();
		digester.setValidating(false);

		digester.addObjectCreate("email", EmailTemplate.class);

		digester.addBeanPropertySetter("email/from", "from");
		digester.addBeanPropertySetter("email/to", "to");
		digester.addBeanPropertySetter("email/subject", "subject");
		digester.addBeanPropertySetter("email/style", "style");
		digester.addBeanPropertySetter("email/body", "body");
		

		return (EmailTemplate) digester.parse(aStream);

	}
}
