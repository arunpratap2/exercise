package com.synchrony.service;

import com.synchrony.controller.UserController;
import com.synchrony.exception.ImageUploadException;
import com.synchrony.model.Image;
import com.synchrony.model.User;
import com.synchrony.repository.ImageRepository;
import com.synchrony.repository.UserRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    UserRepository userRepository;
    public String uploadImage(final ImageDetail imageDetail){

        final String userName = imageDetail.getUserName();
        final String password = imageDetail.getPassword();
        final User userDetail = userRepository.findByUserName(userName);
        LOGGER.info("user userName  {}",userName);
        LOGGER.info("user detail  {}",userDetail);
        if(userDetail!=null){
            if(userDetail.getPassword().equals(password)){
                final List<String> imageURLs = imageDetail.getImageURL();
                if (CollectionUtils.isEmpty(imageURLs)) {
                    throw new ImageUploadException("Please provide valid image URL");
                } else{
                    final List<Image> imageList =new ArrayList<>();
                    imageURLs.forEach(image-> {
                        final Image images = new Image(userName,image);
                        images.setUser(userDetail);
                        imageList.add(images) ;
                    });
                    userDetail.setImageDetails(imageList);
                    userRepository.save(userDetail);
                    return "Image uploaded successfully, URL: " + imageURLs;
                }

            } else{
                throw new ImageUploadException("Please provide correct password");
            }
        } else{
            throw new ImageUploadException("Please provide correct user name "+userName);
        }
    }

    public List<Image> fetchAllImagesOfUser(final ImageDetail imageDetail){

        final String userName = imageDetail.getUserName();
        final String password = imageDetail.getPassword();
        final User userDetail = userRepository.findByUserName(userName);
        if(userDetail!=null){
            if(userDetail.getPassword().equals(password)){
                return imageRepository.findByUsername(userName);
            } else{
                throw new ImageUploadException("Please provide correct password");
            }
        } else{
            throw new ImageUploadException("Please provide correct user name");
        }

    }

    public String updateUserImage(final ImageDetail imageDetail){

        final String userName = imageDetail.getUserName();
        final String password = imageDetail.getPassword();
        final User userDetail = userRepository.findByUserName(userName);
        if(userDetail!=null){
            if(userDetail.getPassword().equals(password)){
                final List<String> imageURLs = imageDetail.getImageURL();
                if (CollectionUtils.isEmpty(imageURLs)) {
                    throw new ImageUploadException("Image urls not found for given username and password.");
                } else {
                    final List<Image> imageList =new ArrayList<>();
                    imageURLs.forEach(image-> {
                        imageList.add(new Image(userName,image)) ;
                    });
                    userDetail.setImageDetails(imageList);
                    userRepository.save(userDetail);
                    return "Image updated successfully, URL: " + imageURLs;
                }
            } else{
                throw new ImageUploadException("Please provide correct password");
            }
        } else{
            throw new ImageUploadException("Please provide correct user name");
        }

    }

    public String deleteUserImage(final ImageDetail imageDetail){

        final String userName = imageDetail.getUserName();
        final String password = imageDetail.getPassword();
        final User userDetail = userRepository.findByUserName(userName);
        if(userDetail!=null){
            if(userDetail.getPassword().equals(password)){
                    userDetail.setImageDetails(null);
                    userRepository.save(userDetail);
                    return "Image deleted successfully for user "+userName;

            } else{
                throw new ImageUploadException("Please provide correct password");
            }
        } else{
            throw new ImageUploadException("Please provide correct user name");
        }

    }

}
