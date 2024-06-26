package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFountException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.FileService;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    @Value("${project.image}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper modelMapper,
                           CategoryRepository categoryRepository, FileService fileService) {

        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.fileService = fileService;
    }


    // CREATING
    @Override
    public PostDto createPost(PostDto postDto, MultipartFile file) throws IOException {
        // 1. upload the file
        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            throw new FileAlreadyExistsException("File already exists! Please enter another file name!");
        }
        String uploadedFileName = fileService.uploadFile(path, file);

        // 2. set the value of field 'poster' as filename
        postDto.setImage(uploadedFileName);

        // Getting Category from the database
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFountException("Category not found ", " with id :", postDto.getCategoryId()));

        // 3. converting DTO to Entity
        Post post = mapToEntity(postDto);
        post.setCategory(category);

        // 4. below move is for saving the data into databases using JPA
        Post savePost = postRepository.save(post);

        // 5. generate the posterUrl
        String imageUrl = "image/" + uploadedFileName;

        // 6. converting entity to DTO in the return statement
        PostDto responseDto = mapToDTO(savePost);
        responseDto.setImageUrl(imageUrl);
        return responseDto;
    }

    // UPDATING BY ID
    @Override
    public PostDto updateById(Long id, PostDto postDto) {

        // Getting Post from the database if not find then thorws the exception
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFountException("Post", " id", id));

        // Getting category from the database if not find then thorws the exception
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFountException("Category not found",
                        " with the id ", postDto.getCategoryId()));

        post.setCategory(category);
        post.setId(id);
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost = postRepository.save(post);

        return mapToDTO(updatePost);
    }

    // GETTING ALL
    @Override
    public PostResponse getAllPost(int PageSize, int PageNo, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

//        Sort sort1 = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortDir).ascending()
//                : Sort.by(sortDir).descending();


        // creating pageable instance
        Pageable pageable = PageRequest.of(PageNo, PageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        // getting content for page object
        List<Post> listOfPost = posts.getContent();

        // converting all posts to DTOs and sending all list of posts throw stream
        // below is the code for converting posts to DTOs throw "lambda function" but the main return is using "method reference" method
        // return posts.stream().map(s -> mapToDTO(s)).collect(Collectors.toList());
        List<PostDto> content = listOfPost.stream().map(this::mapToDTO).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        postResponse.setTotalElements((int) posts.getTotalElements());

        return postResponse;

    }

    // GETTING BY ID
    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFountException("Post", " id", id));

        return mapToDTO(post);
    }

    // DELETING BY ID
    @Override
    public void deleteById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFountException("Post", " id", id));

        postRepository.delete(post);

//        postRepository.deleteById(id);

    }

    @Override
    public List<PostDto> getPostByCategory(Long categoryId) {

        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFountException("this Category is not found ", " with id ", categoryId));

        List<Post> posts = postRepository.findByCategoryId(categoryId);

        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }


    // method is for converting entity to DTO
    private PostDto mapToDTO(Post post) {

        PostDto postDto = modelMapper.map(post, PostDto.class);

//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());

        return postDto;
    }


    // this method is for converting DTO to Entity
    private Post mapToEntity(PostDto postDto) {

        Post post = modelMapper.map(postDto, Post.class);

//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        return post;
    }

}
