package com.synchrony.controller;

import java.util.List;

import com.synchrony.model.Image;
import com.synchrony.service.ImageDetail;
import com.synchrony.model.User;
import com.synchrony.repository.ImageRepository;
import com.synchrony.service.ImageService;
import com.synchrony.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synchrony.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired
  UserRepository userRepository;
  @Autowired
  ImageRepository imageRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private ImageService imageService;

  @PostMapping("/createUser")
  public ResponseEntity<User> createUser(@RequestBody User user) {

    try {
      final User userDetail = userService.createUser(user);
      return new ResponseEntity<>(userDetail, HttpStatus.CREATED);
    } catch(final RuntimeException ex) {
      LOGGER.error("Exception occurred while creating user ",ex.getMessage(),ex);
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/uploadImage")
  public ResponseEntity<String> uploadImageForRegisteredUser(@RequestBody ImageDetail imageDetail) {

    try {
      final String message = imageService.uploadImage(imageDetail);
      return new ResponseEntity<>(message, HttpStatus.CREATED);
    } catch (final RuntimeException ex) {
      LOGGER.error("Exception occurred while uploading image ",ex.getMessage(),ex);
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @PostMapping("/getImage")
  public ResponseEntity<List<Image>> fetchUserImages(@RequestBody ImageDetail imageDetail) {

    List<Image> imageList = imageService.fetchAllImagesOfUser(imageDetail);

    if (CollectionUtils.isNotEmpty(imageList)) {
      return new ResponseEntity<>(imageList, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }




  @PostMapping("/updateImage")
  public ResponseEntity<String> updateUserImage(@RequestBody ImageDetail imageDetail) {

      final String msg = imageService.updateUserImage(imageDetail);
      if (StringUtils.isNotBlank(msg)){
        return new ResponseEntity<>(msg, HttpStatus.OK);
      } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/deleteImage")
  public ResponseEntity<String> deleteUserImage(@RequestBody ImageDetail imageDetail) {

    final String msg = imageService.deleteUserImage(imageDetail);
    if (StringUtils.isNotBlank(msg)){
      return new ResponseEntity<>(msg, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/deleteAllUsers")
  public ResponseEntity<HttpStatus> deleteAllUsers() {
    try {
      userRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/getAllUsers")
  public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String userid) {
    try {

      final List<User> users = userService.getAllUsers();
      if (CollectionUtils.isEmpty(users)) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(users, HttpStatus.OK);
    }
    catch (RuntimeException e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


}
