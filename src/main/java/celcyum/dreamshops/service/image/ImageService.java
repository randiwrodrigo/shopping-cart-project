package celcyum.dreamshops.service.image;

import celcyum.dreamshops.dto.ImageDto;
import celcyum.dreamshops.exceptions.ResourcesNotFoundException;
import celcyum.dreamshops.model.Image;
import celcyum.dreamshops.model.Product;
import celcyum.dreamshops.repository.ImageRepository;
import celcyum.dreamshops.service.product.IProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final IProductService productService ;

    @Override
    public Image getImage(Long id) {
        return imageRepository.findById(id).orElseThrow(()-> new ResourcesNotFoundException("No image found with id:" + id));
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, ()-> {
            throw new ResourcesNotFoundException("No image found with id:" + id);
        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files){
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image saveImage = imageRepository.save(image);

                saveImage.setDownloadUrl(buildDownloadUrl+ image.getId());
                imageRepository.save(image);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(saveImage.getId());
                imageDto.setImageName(saveImage.getFileName());
                imageDto.setDownloadUrl(saveImage.getDownloadUrl());
                savedImageDto.add(imageDto);

            }catch (IOException | SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImage(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
