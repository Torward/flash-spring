package ru.lomov.flash.saves.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flash.saves.dto.SaveResponse;
import ru.lomov.flash.saves.entities.Save;
import ru.lomov.flash.saves.repositories.SaveRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaveServiceImpl implements SaveService {

    private final SaveRepository saveRepository;

    @Override
    public SaveResponse savePost(String postId, String userId) {
        Optional<Save> existingSave = saveRepository.findByPostIdAndUserId(postId, userId);
        
        Save save;
        if (existingSave.isPresent()) {
            save = existingSave.get();
            save.setIsActive(true);
            save.setIsSaved(true);
            save.setUpdatedAt(LocalDateTime.now());
        } else {
            save = new Save();
            save.setPostId(postId);
            save.setUserId(userId);
            save.setSavedPostId(postId);
            save.setSavedByUserId(userId);
            save.setIsActive(true);
            save.setIsSaved(true);
        }
        
        Save savedSave = saveRepository.save(save);
        return SaveResponse.fromEntity(savedSave);
    }

    @Override
    public SaveResponse unsavePost(String postId, String userId) {
        Optional<Save> existingSave = saveRepository.findByPostIdAndUserId(postId, userId);
        
        if (existingSave.isPresent()) {
            Save save = existingSave.get();
            save.setIsActive(false);
            save.setIsSaved(false);
            save.setUpdatedAt(LocalDateTime.now());
            
            Save savedSave = saveRepository.save(save);
            return SaveResponse.fromEntity(savedSave);
        }
        
        // Return empty response if not saved
        SaveResponse response = new SaveResponse();
        response.setPostId(postId);
        response.setUserId(userId);
        return response;
    }

    @Override
    public SaveResponse getSave(String postId, String userId) {
        Optional<Save> save = saveRepository.findByPostIdAndUserId(postId, userId);
        return save.map(SaveResponse::fromEntity).orElse(null);
    }

    @Override
    public List<SaveResponse> getUserSaves(String userId) {
        List<Save> saves = saveRepository.findActiveSavesByUserId(userId);
        return saves.stream()
                .map(SaveResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaveResponse> getPostSaves(String postId) {
        List<Save> saves = saveRepository.findActiveSavesByPostId(postId);
        return saves.stream()
                .map(SaveResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public long getUserSavesCount(String userId) {
        return saveRepository.countActiveSavesByUserId(userId);
    }

    @Override
    public long getPostSavesCount(String postId) {
        return saveRepository.countActiveSavesByPostId(postId);
    }

    @Override
    public boolean isPostSaved(String postId, String userId) {
        return saveRepository.isPostSaved(postId, userId);
    }

    @Override
    public SaveResponse savePostFirebase(String postId, String userId) {
        return savePost(postId, userId);
    }

    @Override
    public SaveResponse unsavePostFirebase(String postId, String userId) {
        return unsavePost(postId, userId);
    }
}
