package in.ayush.swasthyapath.mail;

public interface MailService {

    public boolean sendMail(String to, String subject, String text);

}
