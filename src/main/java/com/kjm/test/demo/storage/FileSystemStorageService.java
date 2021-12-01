package com.kjm.test.demo.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileSystemStorageService implements StorageService{
    // @Value는 application.yml의 값을 가져와서 주입해줌
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;    

    // file upload
    @Override
    public void init(){
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Colud not create upload folder!");
        }
    }

    // file upload
    @Override
    public void store(MultipartFile file){
        log.debug("upload store stat");
        try {
            if(file.isEmpty()){
                throw new Exception("ERROR : File is empty.");
            }
            // upload path에 파일이 존재한다면?
            Path root = Paths.get(uploadPath);        
            log.debug("root : {}, uploadpath : {}", root, uploadPath);  
            
            if(!Files.exists(root)){
                init();
            }
            // 파일 저장?
            try (InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error : " + e.getMessage());
        } finally{
            log.debug("upload store end");
        }
    }

    // get file list
    @Override
    public Stream<Path> loadAll(){
        try {
            Path root = Paths.get(uploadPath);
            // 이거 대체 뭔 문법임..?
            return Files.walk(root, 1).filter(path -> !path.equals(root));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read stored files", e);
        }
    }

    // file download
    @Override
    public Path load(String filename){
        return Paths.get(uploadPath).resolve(filename);
    }

    // file download
    @Override
    public Resource loadAsResource(String filename){
        try{
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new RuntimeException("Could not read file : " + filename);
            }
        }catch(MalformedURLException e){
            throw new RuntimeException("Could not read file : " + filename);
        }
    }

    // file delete all
    @Override
    public void deleteAll(){
        FileSystemUtils.deleteRecursively(Paths.get(uploadPath).toFile());
    }

    @Override
    public void delete(String filename){
        Path file = load(filename);
        FileSystemUtils.deleteRecursively(Paths.get(file.toString()).toFile());
    }
}
