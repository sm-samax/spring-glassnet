package pmf.ris.glassnet.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pmf.ris.glassnet.model.Image;
import pmf.ris.glassnet.model.User;
import pmf.ris.glassnet.repository.ImageRepository;

@Transactional
@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;

	public void saveAvatar(String fileName, MultipartFile file) throws IOException {
		byte[] data = file.getBytes();
		String encoded = Base64.getEncoder().encodeToString(data);

		User authUser = userService.getAuthenticatedUser();
		Image image = authUser.getAvatar();

		if (image == null) {
			image = new Image();
			image.setName(fileName);
			image.setData(encoded);
			authUser.setAvatar(image);
		} else {
			image.setData(encoded);
		}

		imageRepository.save(image);
		messageService.createMessage(userService.getAuthenticatedUser(), "Notification", "Your avatar has been changed.");
	}

	public List<Image> saveImages(MultipartFile[] images) throws IOException {
		List<Image> savedImages = new ArrayList<>();

		for (MultipartFile image : images) {
			if (image.getSize() <= 0L)
				continue;

			Image current = new Image();
			String encoded = Base64.getEncoder().encodeToString(image.getBytes());

			current.setData(encoded);
			current.setName(image.getOriginalFilename());

			imageRepository.save(current);
			savedImages.add(current);
		}

		return savedImages;
	}

	public Image saveImage(MultipartFile image) throws IOException {
		Image current = new Image();

		if (image.getSize() <= 0L) {
			throw new IOException();
		}

		String encoded = Base64.getEncoder().encodeToString(image.getBytes());

		current.setData(encoded);
		current.setName(image.getOriginalFilename());

		imageRepository.save(current);

		return current;
	}
}
