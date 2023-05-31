package pmf.ris.glassnet.service;

import java.time.Instant;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pmf.ris.glassnet.model.Message;
import pmf.ris.glassnet.model.User;
import pmf.ris.glassnet.repository.MessageRepository;

@Service
@Transactional
public class MessageService {
	
	@Autowired
	private MessageRepository messageRepository;

	public Message createMessage(User user, String title, String body) {
		Message message = new Message();
		
		message.setSubject(user);
		message.setTitle(title);
		message.setBody(body);
		message.setDate(Instant.now());
		
		messageRepository.save(message);
		user.getMessages().add(message);
		
		return message;
	}
	
	public void deleteMessage(Long id) {
		messageRepository.deleteById(id);
	}
	
}
