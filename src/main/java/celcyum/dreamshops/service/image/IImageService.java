package celcyum.dreamshops.service.image;

import celcyum.dreamshops.dto.ImageDto;
import celcyum.dreamshops.model.Image;
import celcyum.dreamshops.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImage(Long id);
    void deleteImage(Long id);
    List<ImageDto> saveImages(List<MultipartFile> file, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
