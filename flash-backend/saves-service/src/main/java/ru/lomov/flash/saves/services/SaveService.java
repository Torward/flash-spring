package ru.lomov.flash.saves.services;

import ru.lomov.flash.saves.dto.SaveResponse;

import java.util.List;

public interface SaveService {
    
    SaveResponse savePost(String postId, String userId);
    
    SaveResponse unsavePost(String postId, String userId);
    
    SaveResponse getSave(String postId, String userId);
    
    List<SaveResponse> getUserSaves(String userId);
    
    List<SaveResponse> getPostSaves(String postId);
    
    long getUserSavesCount(String userId);
    
    long getPostSavesCount(String postId);
    
    boolean isPostSaved(String postId, String userId);
    
    // Firebase-compatible methods
    SaveResponse savePostFirebase(String postId, String userId);
    
    SaveResponse unsavePostFirebase(String postId, String userId);
}
